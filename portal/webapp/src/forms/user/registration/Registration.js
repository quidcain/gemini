/**
 * Created by fran on 2/1/18.
 */
import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {cleanRegistration, registerUser, unblockUI} from "../../../redux/actions";
import ReCAPTCHA from "react-google-recaptcha";
import env from "../../../env";
import AnimationHelper from "../../../components/AnimationHelper";
import ModalHelper from "../../../components/ModalHelper";
import * as UIHelper from "../../../UIHelper";


class Registration extends Component {

    constructor(props) {
        super(props);
        this.state = {
            valid: false,
            token: null
        };
        this.register = this.register.bind(this);
        this.inputHandler = this.inputHandler.bind(this);
        this.validForm = this.validForm.bind(this);
        this.verifyCallback = this.verifyCallback.bind(this);
    }

    componentWillMount() {
        this.props.cleanRegistration();
    }

    getValidationMessages() {
        let messages = [];
        let form = this.props.form;
        let emailAreEquals = form.email === form.confirmEmail;

        if (!this.refs.email.valid())
            messages.push(UIHelper.getText("emailInvalid"));
        if (!this.refs.confirmEmail.valid())
            messages.push(UIHelper.getText("confirmEmailInvalid"));
        if (!emailAreEquals)
            messages.push(UIHelper.getText("emailAndConfirmInvalid"));
        if (!this.state.token)
            messages.push(UIHelper.getText("missingReCaptchaToken"));
        return messages;
    }

    register(e) {
        let user = this.props.form;
        e.preventDefault();
        if (this.state.valid)
            this.props.registerUser(user, () => {
                this.props.history.push("/registration/result/success")
            }, () => {
                this.props.history.push("/registration/result/error")
            });
        else
            UIHelper.validationDialog(this.refs.modal, {messages: this.getValidationMessages()}, () => {
                this.props.unblockUI();
            })
    }

    verifyCallback(response) {
        let form = this.props.form;
        form.token = response;
        this.setState({...this.state, token: response}, () => {
            this.validForm();
        });

    }

    validForm() {
        let allValid = true;
        let form = this.props.form;
        let emailAreEquals = form.email === form.confirmEmail;
        let fields = this.refs;
        for (let idx in fields) {
            if (idx !== "modal")
                allValid &= fields[idx].valid();
        }
        this.setState({...this.state, valid: allValid && emailAreEquals && this.state.token})
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
        this.validForm();
    }

    render() {
        let form = this.props.form;
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description"><h2>{UIHelper.getText("registrationPageTitle")}</h2>
                        <div className="violet-line"/>
                    </div>
                    <span className="f20slg">{UIHelper.getText("registrationPageMessage")}</span>
                </div>
                <div className="body d-flex align-items-center flex-column justify-content-end">
                    <form onSubmit={this.register}>
                        <div className="row plr15 ">
                            <div className="col-md-12">
                                <TextInput id="email"
                                           type="email"
                                           ref="email"
                                           label="Email"
                                           onChange={this.inputHandler}
                                           value={form.email}
                                           iconName="icon-mail"
                                           grouped/>
                            </div>
                        </div>
                        <div className="row plr15">
                            <div className="col-md-12">
                                <TextInput id="confirmEmail"
                                           type="email"
                                           ref="confirmEmail"
                                           label="Confirmar Email"
                                           onChange={this.inputHandler}
                                           value={form.confirmEmail}
                                           iconName="icon-mail"
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
                                <button className="button-green mr30 mob-mb30px" type="submit"><span>s</span>
                                    {UIHelper.getText("registerButton")}
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
    return {form: store.registration.form};
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({registerUser, cleanRegistration, unblockUI}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions)(Registration);
