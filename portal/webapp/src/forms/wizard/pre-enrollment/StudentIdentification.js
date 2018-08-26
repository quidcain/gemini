/**
 * Created by fran on 1/26/18.
 */

import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {searchStudent} from "../../../redux/actions";
import SimpleDateInput from "../../../components/SimpleDateInput";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";
import SocialSecurityInput from "../../../components/SocialSecurityInput";
import * as Utils from "../../../Utils";

class StudentIdentification extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.onValidDate = this.onValidDate.bind(this);
        this.onCleanDate = this.onCleanDate.bind(this);
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
        this.forceUpdate();
    }

    onValidDate(date) {
        let form = this.props.form;
        form.dateOfBirth = date;
    }

    onCleanDate() {
        let form = this.props.form;
        form.dateOfBirth = null;
    }

    onPress(onResult, onError) {
        let form = this.props.form;
        this.props.searchStudent(form, onResult, onError);
    }


    render() {
        let form = this.props.form;
        let studentNumberRequired = (form && Utils.isEmptySSN(form.ssn)) || form.studentNumber;
        let lastSsnRequired = (form && !form.studentNumber) || !Utils.isEmptySSN(form.ssn);
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb40"><h2
                        className="f90sbg">{UIHelper.getText("searchPageQuestionNumber")}</h2>
                        <div className="violet-line"/>
                    </div>
                    <p className="f30slg">{UIHelper.getText("searchPageMessageStart")}<span
                        className="f20slb">{UIHelper.getText("searchPageMessageEnd")}</span></p>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form>
                        <div className="row">
                            <div className="col-md-5">
                                <TextInput id="studentNumber"
                                           type="studentNumber"
                                           value={form.studentNumber}
                                           onChange={this.inputHandler}
                                           required={studentNumberRequired}
                                           disabled={!studentNumberRequired}
                                           disabledBlock
                                           label="# Estudiante SIE"/>
                            </div>
                            <div className="col-md-2" style={{position: "relative"}}>
                                <div style={{position: "relative", height: "100%"}}>
                                    <span style={{position: "absolute", bottom: "25%", left: "40%"}} className="f20sbgr">o</span>
                                </div>
                            </div>
                            <div className="col-md-5">
                                {/*<TextInput id="ssn"*/}
                                {/*type="ssn"*/}
                                {/*value={form.ssn}*/}
                                {/*onChange={this.inputHandler}*/}
                                {/*required={lastSsnRequired}*/}
                                {/*disabled={!lastSsnRequired}*/}
                                {/*disabledBlock*/}
                                {/*label="SSN"/>*/}

                                <SocialSecurityInput id="ssn"
                                                     mask
                                                     label="Seguro Social"
                                                     value={form.ssn}
                                                     onChange={this.inputHandler}
                                                     required={lastSsnRequired}
                                                     disabled={!lastSsnRequired}
                                                     disabledBlock/>
                            </div>

                        </div>
                        <div className="row">
                            {/*<div className="col-md-4">*/}
                                {/*<SimpleDateInput id="dateOfBirth"*/}
                                                 {/*value={form.dateOfBirth}*/}
                                                 {/*onValidDate={this.onValidDate}*/}
                                                 {/*onCleanDate={this.onCleanDate}*/}
                                                 {/*label="Fecha Nac."/>*/}
                            {/*</div>*/}
                            {/*<div className="col-md-4">*/}
                                {/*<TextInput id="firstName"*/}
                                           {/*type="lastname"*/}
                                           {/*value={form.firstName}*/}
                                           {/*onChange={this.inputHandler}*/}
                                           {/*label="Nombre"/>*/}
                            {/*</div>*/}
                            <div className="col-md-12">
                                <TextInput id="lastName"
                                           type="lastname"
                                           value={form.lastName}
                                           onChange={this.inputHandler}
                                           required
                                           label="Apellidos"/>
                            </div>
                        </div>
                    </form>
                    <div style={{marginTop: -20}}>
                        {this.props.footer}
                    </div>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img className="w90" src={searchIllustration} alt=""/></div>*/}
                <AnimationHelper type="search"/>
            </div>

        ];
    }
}

function mapStateToProps(store) {
    return {form: store.studentLookup.form};
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({searchStudent}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(StudentIdentification);
