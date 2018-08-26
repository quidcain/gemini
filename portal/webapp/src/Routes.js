/**
 * Created by fran on 1/24/18.
 */
import React, {Component} from "react";
import Authentication from "./forms/Authentication";
import Home from "./forms/Home";
import Wizard from "./forms/wizard/Wizard";
import NotFoundPage from "./NotFoundPage";
import {Redirect, Route, Switch, withRouter} from "react-router-dom";
import Registration from "./forms/user/registration/Registration";
import Activation from "./forms/user/registration/Activation";
import Profile from "./forms/Profile";
import ActivationResult from "./forms/user/registration/ActivationResult";
import RegistrationResult from "./forms/user/registration/RegistrationResult";
import ForgotPassword from "./forms/user/forgot/ForgotPassword";
import ForgotPasswordResult from "./forms/user/forgot/ForgotPasswordResult";
import ResetPassword from "./forms/user/forgot/ResetPassword";
import ResetPasswordResult from "./forms/user/forgot/ResetPasswordResult";
import CancelResetPasswordRequest from "./forms/user/forgot/CancelResetPasswordRequest";
import Welcome from "./forms/Welcome";
import registrationIllustration from "./assets/img/registration-illustration.png";
import FAQ from "./FAQ";
import DisableMessage from "./DisableMessage";
import AddressForm from "./forms/wizard/pre-enrollment/AddressForm";

class Routes extends Component {

    componentDidUpdate(prevProps) {
        if (this.props.location !== prevProps.location) {
            this.props.onRouteChanged(this.props.location);
        }
    }

    render() {
        let authenticated = this.props.authenticated;
        let loading = this.props.loading;

        return (
            <Switch>
                <Route path="/faq" component={FAQ}/>
                {/*<Route path="/" component={DisableMessage}/>*/}

                <Route exact path="/" component={Welcome}/>
                <Route path="/login" component={Authentication}/>
                {/*public routes*/}
                <Route path="/registration/result/:result(success|error)" component={RegistrationResult}/>
                <Route path="/registration" component={Registration}/>

                <Route path="/forgot/password/help" component={ForgotPassword}/>
                <Route path="/forgot/password/result/:result(success|error)" component={ForgotPasswordResult}/>

                <Route path="/reset/password/result/:result(success|error|invalid)" component={ResetPasswordResult}/>
                <Route path="/reset/password/:credentialLostKey" component={ResetPassword}/>

                <Route path="/activate/result/:result(success|error)" component={ActivationResult}/>
                <Route path="/activate/:activationCode" component={Activation}/>

                <Route path="/cancel/reset/password/:key" component={CancelResetPasswordRequest}/>


                {/*privates routes*/}
                <PrivateRoute path="/home" component={Home} authenticated={authenticated} loading={loading}/>
                <PrivateRoute path="/wizard/:id?" component={Wizard} authenticated={authenticated} loading={loading}/>
                <PrivateRoute path="/edit/address/:id" component={AddressForm} authenticated={authenticated} loading={loading}/>
                {/*<RefreshRoute path="/wizard/edit/:id?" component={Wizard} authenticated={authenticated} loading={loading}/>*/}
                <PrivateRoute path="/profile" component={Profile} authenticated={authenticated} loading={loading}/>

                {/*404 page*/}
                <Route component={NotFoundPage}/>
            </Switch>
        );

    }
}


const PrivateRoute = ({component: Component, ...rest}) => (
    <Route
        {...rest}
        render={props =>
            rest.authenticated || rest.loading
                ? rest.authenticated && !rest.loading ? (<Component {...props} />) : (
                    limboPage)
                : (<Redirect to={{pathname: "/login", state: {from: props.location}}}/>)
        }
    />
);

const RefreshRoute = ({path}) => (
    <Route
        path={path}
        component={({history, location, match}) => {
            history.replace({
                ...location,
                pathname: location.pathname.substring(match.path.length)
            });
            return null;
        }}
    />
);

const limboPage = [
    <div className="col-md-7 content-section">
        <div className="title">
            <div className="description mb40"/>
            <span className="f20slg"/>
        </div>
        <div className="body d-flex align-items-center flex-column justify-content-end">
            <div style={{paddingBottom: 300}} className="col-md-12 illustration-section d-flex align-items-center text-center">
                <div className="illustration"><img src={registrationIllustration} alt=""/></div>
            </div>
        </div>
    </div>,
    <div className="col-md-4 illustration-section d-flex align-items-center text-center"/>

];

export default withRouter(Routes);
