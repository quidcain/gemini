import React, {Component} from "react";
import profileIllustration from "../../../assets/img/profile-illustration.png";
import {connect} from "react-redux";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

class RegistrationResult extends Component {

    constructor(props) {
        super(props);
        this.onClick = this.onClick.bind(this);
    }

    onClick() {
        this.props.history.push("/login")
    }

    render() {
        let success = this.props.match.params.result === "success";
        let title = success ? UIHelper.getText("registrationSuccessTitle") : UIHelper.getText("registrationErrorTitle");
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2>{title}!</h2>
                    <div className="violet-line"/>
                </div>
                {this.renderMessage()}
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end"/>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={profileIllustration} alt=""/></div>*/}
                <AnimationHelper type="girlsTable"/>
            </div>];
    }

    renderMessage() {
        let email = this.props.form.email;
        let success = this.props.match.params.result === "success";
        if (!success)
            return (<p>{UIHelper.getText("registrationError")}</p>);

        return (<p>
            {UIHelper.getText("registrationSuccessStart")}
            <span
            className="f20slb">{email}. </span>
            {UIHelper.getText("registrationSuccessEnd")}
        </p>)
    }

}

function mapStateToProps(store) {
    return {form: store.registration.form};
}

export default connect(mapStateToProps)(RegistrationResult);


