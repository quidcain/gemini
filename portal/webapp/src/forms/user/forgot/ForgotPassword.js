import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {forgotPassword, unblockUI} from "../../../redux/actions";
import env from "../../../env";
import ReCAPTCHA from "react-google-recaptcha";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";
import ModalHelper from "../../../components/ModalHelper";

class ForgotPassword extends Component {

    constructor(props) {
        super(props);
        this.state = {valid: false, token: null};
        this.inputHandler = this.inputHandler.bind(this);
        this.validForm = this.validForm.bind(this);
        this.sendEmail = this.sendEmail.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
        this.validForm();
    }

    validForm() {
        let valid = this.refs.email.valid();
        this.setState({...this.state, valid: valid && this.state.token})
    }

    verifyCallback(response) {
        let form = this.props.form;
        form.token = response;
        this.setState({...this.state, token: response}, () => {
            this.validForm();
        });
    }


    getValidationMessages() {
        let messages = [];
        if (!this.refs.email.valid())
            messages.push(UIHelper.getText("emailInvalid"));
        if (!this.state.token)
            messages.push(UIHelper.getText("missingReCaptchaToken"));
        return messages;
    }

    sendEmail(e) {
        e.preventDefault();
        let form = this.props.form;
        if (this.state.valid)
            this.props.forgotPassword(form, () => {
                this.props.history.push(`/forgot/password/result/success`);
            }, () => {
                this.props.history.push(`/forgot/password/result/error`);
            });
        else
            UIHelper.validationDialog(this.refs.modal, {messages: this.getValidationMessages()}, () => {
                this.props.unblockUI();
            })
    }

    render() {
        let form = this.props.form;

        return [<div className="col-md-5 content-section">
            <div className="title">
                <div className="description"><h2 className="f60sbg">{UIHelper.getText("forgotPasswordTitle")}</h2></div>
                <span className="f30slg"><span
                    className="f20slg">{UIHelper.getText("forgotPasswordExplanation")}</span></span>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <form id="recover-form" onSubmit={this.sendEmail}>
                    <TextInput id="email"
                               type="email"
                               ref="email"
                               label="Email"
                               onChange={this.inputHandler}
                               value={form.email}
                               iconName="icon-mail"
                               grouped/>
                    <ReCAPTCHA
                        sitekey={env.reCAPTCHASiteKey}
                        onChange={this.verifyCallback}
                    />
                    <button className="button-yellow mt50" id="buttonGet"
                            type="submit">{UIHelper.getText("forgotPasswordButton")}
                    </button>
                </form>
                <div className="row w-100 mt50"/>
            </div>
        </div>,
            <div className="col-md-6 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={registrationIllustration} alt=""/></div>*/}
                <AnimationHelper type="home"/>
            </div>,
            <ModalHelper ref="modal"/>]
    }

}

function mapStateToProps(store) {
    return {form: store.loginHelp};
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({forgotPassword, unblockUI}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions)(ForgotPassword);