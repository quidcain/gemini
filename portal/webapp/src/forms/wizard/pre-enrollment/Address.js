/**
 * Created by fran on 1/26/18.
 */
import React, {Component} from "react";
import CodeSelect from "../../../components/CodeSelect";
import TextInput from "../../../components/TextInput";
import {copyPhysicalToPostal, loadAddress, saveAddress, geoCodeAddress, getCountyCode} from "../../../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import AnimationHelper from "../../../components/AnimationHelper";
import Button from "../../../components/Button";
import * as UIHelper from "../../../UIHelper";
import AddressMap from "../../../components/AddressMap"
import cities from "../../../components/data/codes/municipios"
import * as Utils from "../../../Utils";


class Address extends Component {

    constructor(props) {
        super(props);
        let address = this.props.physical;
        this.state = {zipcode: address.zipcode, line1: address.line1};
        this.inputHandler = this.inputHandler.bind(this);
        this.check = this.check.bind(this);
        this.copyAddress = this.copyAddress.bind(this);
        this.updateFromMap = this.updateFromMap.bind(this);

    }

    componentWillMount() {
        this.props.loadAddress(this.props.requestId, ()=>{
            if(this.props.onLoad){
                this.props.onLoad();
            }
        });
    }

    inputHandler(e) {
        let form = {...this.props};
        let element = e.target;
        let tokens = element.id.split(".");
        let context = tokens[0];
        let id = tokens[1];
        form[context][id] = element.value;
        let address = this.props.physical;
        if (Utils.isEmpty(address.zipcode)) {
            address.city = "-1";
        }

        if (id === "zipcode") {
            this.state.zipcode = address.zipcode;
        } else if (id === "line1") {
            this.state.line1 = address.line1;
        }
    }

    check() {
        let address = this.props.physical;

        if (address.zipcode && address.zipcode.length === 5) {
            //change city
            this.props.getCountyCode(address.zipcode, (countyCode) => {
                if (countyCode)
                    address.city = countyCode;
                else
                    address.city = "-1";
                this.forceUpdate();
            })
        }else{
            address.city = "-1";
            this.forceUpdate();
        }

        if (address.zipcode && address.zipcode.length === 5 && address.line1 && address.line1.length >= 8) {
            //geocode
            this.props.geoCodeAddress(address, (lat, log) => {
                address.latitude = lat;
                address.longitude = log;
                if (lat && log)
                    this.refs.addressMap.doManualLocation({lat: lat, lng: log});
                this.forceUpdate();
            });
        }

    }

    copyAddress(e) {
        e.preventDefault();
        this.props.copyPhysicalToPostal();
    }

    onPress(onResult, onError) {
        let form = {physical: this.props.physical, postal: this.props.postal, needTransportation: this.props.needTransportation};
        //check if there any change
        this.props.saveAddress(form, onResult, onError, this.props.requestId);
    }

    updateFromMap(obj) {
        console.log(obj);
        let address = this.props.physical;
        address.zipcode = obj.postalCode;
        if (obj.city)
            for (const city of cities) {
                let src = city.description.trim().toUpperCase();
                let possibleMatch = obj.city.trim().toUpperCase();
                if (src === possibleMatch) {
                    address.city = city.value;
                    break;
                }
            }

        address.line1 = obj.addressLine;
        address.latitude = obj.lat;
        address.longitude = obj.lng;
        this.setState({line1: address.line1, zipcode: address.zipcode})
    }

    render() {
        let props = {...this.props};
        let address = props.physical;
        let startPos;
        if (address.latitude && address.longitude) {
            startPos = {lat: address.latitude, lng: address.longitude};
        }

        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description"><h2>{UIHelper.getText("addressPageTitleStart")}<span
                        className="f40sbb">{UIHelper.getText("addressPageTitleEnd")}</span></h2>
                        <div className="violet-line"></div>
                    </div>
                    <span className="f20slg">{UIHelper.getText("addressPageMessage")}</span>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form>
                        <div className="row">
                            <div className="col-md-12">
                                <h5 htmlFor="">{UIHelper.getText("addressPagePhysicalAddressButton")}</h5>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                {this.renderAddressForm("physical", props.physical)}
                            </div>
                        </div>
                    </form>
                    <div style={{marginTop: -90}}>
                        {this.props.footer}
                    </div>
                </div>

            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {props.addressLoad
                    ? (<AddressMap ref="addressMap" position={startPos} onResult={this.updateFromMap}
                                   new={Utils.isEmpty(address.zipcode)}/>)
                    : (null)}
            </div>
        ];
    }

    renderAddressForm(type, address) {
        address.type = type.toUpperCase();
        address.city = !address.city ? "-1" : address.city;
        return [
            <div className="row pt-2">
                <div className="col-md-12">
                    <TextInput required type="addressLine" id={`${type}.line1`} label="Línea 1"
                               onFocusLost={this.check}
                               value={this.state.line1 || address.line1} onChange={this.inputHandler}/>
                </div>
            </div>,
            <div className="row">
                <div className="col-md-12">
                    <TextInput type="addressLine" id={`${type}.line2`} label="Línea 2"
                               value={address.line2} onChange={this.inputHandler}/>
                </div>
            </div>,
            <div className="row">
                <div className="col-md-7">
                    <CodeSelect required id={`${type}.city`}
                                disabled={true}
                                label="Ciudad"
                                codeType="municipios"
                                grouped
                                value={address.city} onChange={this.inputHandler}/>
                </div>
                <div className="col-md-5">
                    <TextInput required type="zipcode" id={`${type}.zipcode`} label="Zip Code"
                               onFocusLost={this.check}
                               value={this.state.zipcode || address.zipcode} onChange={this.inputHandler}/>
                </div>
            </div>
        ];
    }
}

function mapStateToProps(store) {
    return {
        physical: store.studentInfo.physicalAddress,
        postal: store.studentInfo.postalAddress,
        addressLoad: store.studentInfo.addressLoad,
        geoCodeAddress: store.studentInfo.geoCodeAddress,
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({
        loadAddress,
        copyPhysicalToPostal,
        saveAddress,
        geoCodeAddress,
        getCountyCode
    }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(Address);
