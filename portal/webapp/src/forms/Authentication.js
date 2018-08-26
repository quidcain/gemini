/**
 * Created by fran on 1/24/18.
 */
import React, {Component} from "react";
// import {Alert} from "react-bootstrap";
import {Alert, Tooltip} from 'reactstrap';
import Immutable from "immutable";
import {Link} from "react-router-dom";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {clean, cleanLogin, login, toggleCleanTimeout} from "../redux/actions";
import TextInput from "../components/TextInput";
import AnimationHelper from "../components/AnimationHelper";
import * as UIHelper from "../UIHelper";
import * as Utils from "../Utils";
import Button from "../components/Button";

class Authentication extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showAlert: false,
            forgotTooltipOpen: false,
            noAccountTooltipOpen: false
        };

        this.login = this.login.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleDismiss = this.handleDismiss.bind(this);
        this.toggleForgot = this.toggleForgot.bind(this);
        this.toggleNoAccount = this.toggleNoAccount.bind(this);


    }

    componentWillMount() {
        this.props.clean();
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps)
            this.setState({showAlert: nextProps.errorAtLogin || nextProps.invalidCredentials}, () => {
                if (nextProps.errorAtLogin || nextProps.invalidCredentials) {
                    this.props.cleanLogin();
                }

            });
    }

    handleDismiss() {
        this.setState({showAlert: false})
    }

    handleInputChange(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
    }

    toggleForgot() {
        this.setState({
            forgotTooltipOpen: !this.state.forgotTooltipOpen
        });
    }

    toggleNoAccount() {
        this.setState({
            noAccountTooltipOpen: !this.state.noAccountTooltipOpen
        });
    }

    componentWillMount(){
        this.isMobile = Utils.isMobile();
    }

    render() {
        let form = this.props.form;
        let username = form.username;
        let password = form.password;
        let invalidCredentials = this.props.invalidCredentials;
        return [<div className="col-md-5 content-section">
            <Alert className="auth-error" color="danger" isOpen={this.state.showAlert} toggle={this.handleDismiss}>
                <strong>{UIHelper.getText("messageTitle")}</strong> {invalidCredentials ? UIHelper.getText("invalidCredentials") : UIHelper.getText("errorLogin")}
            </Alert>
            <div className="title">
                <div className="description">
                    <h2>{UIHelper.getText("initiateSectionTitle")}</h2>
                    <div className="violet-line"/>
                </div>
                <p className="f20slg text-justify">
                    {UIHelper.getText("loginPage")}
                </p>

            </div>

            <div className="body d-flex align-items-center flex-column justify-content-end">
                <form id="signin-form" className="signin-form" onSubmit={this.login}>
                    <TextInput id="username"
                               type="email"
                               label="Email"
                               required={false}
                               onChange={this.handleInputChange}
                               value={username}
                               iconName="icon-human"
                               grouped/>
                    <TextInput id="password"
                               type="password"
                               label="Contraseña"
                               required={false}
                               onChange={this.handleInputChange}
                               value={password}
                               iconName="icon-lock"
                               grouped/>
                    <Button size="xlarge" id="buttonGet" className="button-yellow"
                            type="submit">{UIHelper.getText("loginButton")}</Button>
                </form>
                <div className="row w-100 mt50">
                    <div className="col-md-6 p-0 text-lg-left text-center">
                        <Link className="red-tooltip" id="forgot" to="/forgot/password/help">
                            {UIHelper.getText("forgotPasswordLink")}
                        </Link>

                        <Tooltip placement="left"
                                 isOpen={this.state.forgotTooltipOpen && !this.isMobile}
                                 target="forgot"
                                 toggle={this.toggleForgot}>

                            {UIHelper.getText("tooltipForgotPassword")}
                        </Tooltip>


                    </div>
                    <div className="col-md-6 p-0 text-lg-right text-center">
                        <Link className="red-tooltip" id="noAccount" to="/registration">
                            {UIHelper.getText("noAccountLink")}
                        </Link>
                    </div>

                    <Tooltip placement="right"
                             isOpen={this.state.noAccountTooltipOpen && !this.isMobile}
                             target="noAccount"
                             toggle={this.toggleNoAccount}>
                        {UIHelper.getText("tooltipForUserWithoutAccount")}
                    </Tooltip>
                </div>
            </div>
        </div>,
            <div className="col-md-6 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={registrationIllustration} alt=""/></div>*/}
                <AnimationHelper type="home"/>
            </div>];
    }

    login(e) {
        let creds = Immutable.fromJS(this.props.form).toJS();
        e.preventDefault();

        this.props.login(creds, (nextPath) => {
                this.props.history.push(nextPath)
            },
            () => {
                alert(UIHelper.getText("errorLogging"));
            })
    }

}

function mapStateToProps(store) {
    return {
        form: store.profile.authentication,
        invalidCredentials: store.profile.invalidCredentials,
        errorAtLogin: store.profile.errorAtLogin,
        clean: store.profile.clean
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({clean, login, cleanLogin, toggleCleanTimeout}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions)(Authentication);