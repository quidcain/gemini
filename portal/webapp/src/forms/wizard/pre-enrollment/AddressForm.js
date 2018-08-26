import React, {Component} from "react";
import Address from "./Address";
import {connect} from "react-redux";
import * as UIHelper from "../../../UIHelper";
import ModalHelper from "../../../components/ModalHelper";
import swal from "sweetalert";

class AddressForm extends Component {

    constructor(props) {
        super(props);
        this.state = {needTransportation: props.needTransportation};
        this.save = this.save.bind(this);
        this.onError = this.onError.bind(this);
        this.onTransportationCheck = this.onTransportationCheck.bind(this);
    }

    componentWillMount() {
        this.requestId = this.props.match.params.id;
    }

    onError(validationObj, afterCloseAction) {
        UIHelper.validationDialog(this.refs.modal, validationObj, afterCloseAction)
    }

    onTransportationCheck(e) {
        const target = e.target;
        this.setState({needTransportation: target.checked});
    }

    save() {
        let pageRef = this.refs.address.getWrappedInstance ? this.refs.address.getWrappedInstance() : null;
        if (pageRef && pageRef.onPress) {
            pageRef.onPress(() => {
                swal("Dirección guardada exitosamente", {icon: "info"})
                    .then(() => {
                        this.props.history.push("/home");
                    });

            }, this.onError);
        }
    }

    render() {
        return [<Address requestId={this.requestId} needTransportation={this.state.needTransportation} onLoad={() => {
            this.setState({needTransportation: this.props.needTransportation});

        }} ref="address" footer={this.renderFooter()}/>,
            <ModalHelper ref="modal"/>
        ];
    }

    renderFooter() {
        return (<div key="footer" style={{zIndex: 1000}}>
            <div className="row action-section">
                <div className="col-md-6 text-center text-lg-left p-0">
                    <a id="nextButton" className="button-white mr30 mob-mb30px" onClick={this.save}>
                        <span>G</span>Guardar
                    </a>
                </div>
                <div className="col-md-6">
                    <div className="form-group">

                        <input type="checkbox" checked={this.state.needTransportation}
                               onChange={this.onTransportationCheck}/>&nbsp;&nbsp;¿Marque si desea
                        Servicio de Transportaci&oacute;n?
                    </div>
                </div>
            </div>
        </div>);
    }
}

function mapStateToProps(store) {
    return {
        needTransportation: store.studentInfo.needTransportation,
    };
}

export default connect(mapStateToProps, null)(AddressForm);