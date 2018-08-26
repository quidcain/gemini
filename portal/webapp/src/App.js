import React, {Component} from "react";
import {withRouter} from "react-router-dom";
import "./assets/app.css";
import classnames from "classnames";
import Routes from "./Routes";
import moment from "moment";
import esLocale from "moment/locale/es";
import {connect} from "react-redux";
import {checkSession, logout, onBackAction, triggerErrorOff, triggerSessionExpiredOff} from "./redux/actions";
import ReduxBlockUi from 'react-block-ui/redux';
import {bindActionCreators} from "redux";
import * as env from "./env";
import ModalHelper from "./components/ModalHelper";
import Footer from "./Footer";

moment.updateLocale('es', esLocale);

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {showMenu: false};
        this.sessionCheck = false;
        this.onRouteChanged = this.onRouteChanged.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.goProfile = this.goProfile.bind(this);
        this.goBack = this.goBack.bind(this);
        this.goHome = this.goHome.bind(this);
        this.goAuthentication = this.goAuthentication.bind(this);
        this.goFAQ = this.goFAQ.bind(this);
        this.blockUIRegEx = new RegExp(/^\S*_START$/);
        this.unblockUIActionsRegEx = new RegExp(/^(\S*_END)|(\S*_OFF)$/);
    }

    componentWillReceiveProps(nextProps) {
        let hasGeneralError = nextProps && nextProps.generalErrorOccurred && nextProps.errorMessage;
        let sessionExpired = nextProps && nextProps.sessionExpired;

        if (hasGeneralError) {
            this.refs.modal.open("Upss!!!", nextProps.errorMessage, () => {
                this.props.triggerErrorOff();
            });
        } else if (sessionExpired) {
            this.refs.modal.open("Upss!!!", nextProps.sessionMessage, () => {
                this.props.triggerSessionExpiredOff();
                this.props.history.push(`/login`);
            });
        }
    }

    componentWillMount() {
        if (!env.isUserActionUrl(this.props.location.pathname) && !this.sessionCheck) {
            this.props.checkSession();
            this.sessionCheck = true;
        }
    }

    onRouteChanged(nextRoute) {
        this.setState({showMenu: !env.isPublicUrl(nextRoute.pathname) && this.props.authenticated});
    };

    goAuthentication() {
        this.props.history.push("/login");
    }

    goHome() {
        this.props.history.push(`/home`);
    }

    handleLogout() {
        this.props.logout(() => {
            this.props.history.push(`/login`);
        });
    }

    goProfile() {
        this.props.history.push("/profile");
    }

    goFAQ() {
        this.props.history.push("/faq");
    }

    componentDidMount() {
        //double check
        if (!this.state.showMenu) {
            let path = this.props.location.pathname;
            if (!env.isPublicUrl(path) && (this.props.authenticated || !this.props.sessionExpired)) {
                this.setState({showMenu: true})
            }
        }
    }

    goBack() {
        let currentPageType = this.props.currentPageType;
        let pathname = this.props.location.pathname;
        console.log(`pathname = ${pathname}`);
        if (pathname.startsWith("/wizard")) {
            if (!(currentPageType === "PRE_ENROLLMENT_COMPLETED"
                    || currentPageType === "PRE_ENROLLMENT_CONFIRMED"
                    || currentPageType === "INSTRUCTIONS")) {
                this.props.onBackAction(() => {
                    this.props.history.goBack()
                });
            }
        } else if (pathname === "/login") {
            this.props.history.push("/");
        } else if (pathname !== "/" && pathname !== "/home")
            this.props.history.goBack();

    }

    render() {

        let pathname = this.props.location.pathname;
        let currentPageType = this.props.currentPageType;
        let baseClass = classnames({
            "signin": pathname === "/login",
            "instructions": pathname === "/wizard" && currentPageType === "INSTRUCTIONS",
            "question": pathname === "/wizard" && currentPageType === "IS_VOCATIONAL_STUDENT_QUESTION"
        });
        let showNavigation = pathname === "/"
            ? (<div className="col-md-1 navigation-section d-flex align-items-center"
                    onClick={this.goAuthentication}>
                <i className="icon-arrow"/>
            </div>)
            : (<div className="col-md-1 navigation-section violet d-flex align-items-center"
                    onClick={this.goBack}>
                <i className="icon-arrow mirror"/>
            </div>);
        console.log(`pathname = ${pathname} ${baseClass} ${currentPageType} ${currentPageType === "INSTRUCTIONS"}`);

        return (
            <ReduxBlockUi tag="div" block={this.blockUIRegEx} unblock={this.unblockUIActionsRegEx}>
                <div className={`container-fluid ${baseClass}`}>
                    {this.renderUserBar()}

                    <div className="row content">
                        {showNavigation}
                        <Routes loading={this.props.loading} authenticated={this.props.authenticated}
                                onRouteChanged={this.onRouteChanged}/>
                    </div>
                    <Footer/>
                </div>
                <ModalHelper ref="modal"/>
            </ReduxBlockUi>
        );
    }

    renderUserBar() {
        let canShowFAQLink = !this.props.location.pathname.startsWith("/faq");
        return (<div className="fixed-top" style={{margin: 10}}>
            {canShowFAQLink
                ? (<div className="float-left">
                    <a onClick={this.goFAQ} style={{cursor: "pointer",}}><i
                        className="far fa-question-circle" style={{color: "#7988c9"}}/>&nbsp;&nbsp;&nbsp;Preguntas Frecuentes</a>
                </div>)
                : (null)}
            {this.state.showMenu
                ? (<div className="float-right">
                    <div className="dropdown">
                        <button id="user-bar" className="btn btn-secondary dropdown-toggle" type="button"
                                data-toggle="dropdown" aria-haspopup="true"
                                aria-expanded="false">
                            <span className="pr-2">{this.props.fullName}</span>
                            <i className="fas fa-user"/>
                        </button>
                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a className="dropdown-item" onClick={this.goHome}>Solicitudes</a>
                            <a className="dropdown-item" onClick={this.handleLogout}>Salir</a>
                        </div>
                    </div>
                </div>)
                : (null)}
        </div>);
    }

}

function mapStateToProps(store) {
    return {
        fullName: store.profile.user.fullName || "Sin Nombre",
        authenticated: store.profile.authenticated,
        loading: store.profile.loading,
        currentPageType: store.wizard.currentPageType,
        errorMessage: store.profile.errorMessage,
        generalErrorOccurred: store.profile.generalErrorOccurred,
        sessionMessage: store.profile.sessionMessage,
        sessionExpired: store.profile.sessionExpired,
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({logout, checkSession, onBackAction, triggerErrorOff, triggerSessionExpiredOff}, dispatch)
}

export default withRouter(connect(mapStateToProps, mapDispatchToActions)(App));
