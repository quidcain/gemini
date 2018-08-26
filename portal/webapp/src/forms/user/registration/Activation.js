import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {activateAccount, existsCode, unblockUI} from "../../../redux/actions";
import ReCAPTCHA from "react-google-recaptcha";
import env from "../../../env";
import AnimationHelper from "../../../components/AnimationHelper";
import ModalHelper from "../../../components/ModalHelper";
import * as UIHelper from "../../../UIHelper";

class Activation extends Component {

    constructor(props) {
        super(props);
        this.state = {
            valid: false,
            token: null
        };
        this.inputHandler = this.inputHandler.bind(this);
        this.activate = this.activate.bind(this);
        this.validForm = this.validForm.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
    }


    componentWillMount() {
        let code = this.props.match.params.activationCode;
        this.props.existsCode(code, () => {
            this.props.history.push("/activate/result/error")
        })
    }


    getValidationMessages() {
        let messages = [];
        let form = this.props.form;
        let emailAreEquals = form.password === form.confirmPassword;
        let meetRequirement = emailAreEquals && this.refs.password.valid();

        if (!this.refs.password.valid())
            messages.push(UIHelper.getText("passwordInvalid"));
        if (!this.refs.confirmPassword.valid())
            messages.push(UIHelper.getText("confirmPasswordInvalid"));
        if (!emailAreEquals)
            messages.push(UIHelper.getText("passwordAndConfirmAreNotEquals"));
        if (!meetRequirement)
            messages.push(UIHelper.getText("passwordMeetRequirement"));
        if (!this.state.token)
            messages.push(UIHelper.getText("missingReCaptchaToken"));
        return messages;
    }

    verifyCallback(response) {
        let form = this.props.form;
        form.token = response;
        this.setState({...this.state, token: response}, () => {
            this.validForm();
        });

    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
        this.validForm();
    }

    validForm() {
        let allValid = true;
        let fields = this.refs;
        let form = this.props.form;
        let emailAreEquals = form.password === form.confirmPassword;
        for (let idx in fields) {
            if (idx !== "modal")
                allValid &= fields[idx].valid();
        }

        this.setState({...this.state, valid: allValid && emailAreEquals && this.state.token})
    }


    activate(e) {
        let form = this.props.form;
        form.activationCode = this.props.match.params.activationCode;
        e.preventDefault();
        if (this.state.valid)
            this.props.activateAccount(form, () => {
                this.props.history.push("/activate/result/success")
            }, () => {
                this.props.history.push("/activate/result/error")
            });
        else {
            UIHelper.validationDialog(this.refs.modal, {messages: this.getValidationMessages()}, () => {
                this.props.unblockUI();
            })
        }
    }


    render() {
        let form = this.props.form;
        if (!this.props.validCode)
            return (null);
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description"><h2>{UIHelper.getText("activationTitle")}</h2>
                        <div className="violet-line"></div>
                    </div>
                    <p className="f20slg">
                        Introduce la contrase&ntilde;a deseada para completar el registro.
                    </p>
                </div>
                <div className="body d-flex align-items-center flex-column justify-content-end">
                    <form onSubmit={this.activate}>
                        <div className="row plr15 ">
                            <div className="col-md-12">
                                <TextInput id="password"
                                           type="password"
                                           ref="password"
                                           label="Contraseña"
                                           onChange={this.inputHandler}
                                           value={form.password}
                                           iconName="icon-lock"
                                           grouped/>
                            </div>
                        </div>
                        <div className="row plr15">
                            <div className="col-md-12">
                                <TextInput id="confirmPassword"
                                           type="password"
                                           label="Confirmar Contraseña"
                                           ref="confirmPassword"
                                           onChange={this.inputHandler}
                                           value={form.confirmPassword}
                                           iconName="icon-lock"
                                           grouped/>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <ReCAPTCHA
                                    sitekey={env.reCAPTCHASiteKey}
                                    onChange={this.verifyCallback}
                                />
                            </div>
                        </div>

                        <div className="row mt50">
                            <div className="col-md-12 ">
                                <button className="button-green mr30 mob-mb30px" type="submit"><span>C</span>{UIHelper.getText("activationButton")}
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={profileIllustration} alt=""/></div>*/}
                <AnimationHelper type="girlsTable"/>
            </div>,
            <ModalHelper ref="modal"/>
        ]
    }
}

function mapStateToProps(store) {
    return {validCode: store.registration.validCode, form: store.registration.activationForm};
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({activateAccount, existsCode, unblockUI}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions)(Activation);
