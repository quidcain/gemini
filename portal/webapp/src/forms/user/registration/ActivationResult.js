import React, {Component} from "react";
import profileIllustration from "../../../assets/img/profile-illustration.png";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";


export default class ActivationResult extends Component {

    constructor(props) {
        super(props);
        this.onClick = this.onClick.bind(this);
    }


    onClick() {
        this.props.history.push("/login")
    }

    render() {
        let success = this.props.match.params.result === "success";
        let title = success ? UIHelper.getText("activationSuccessTitle") : UIHelper.getText("activationErrorTitle");
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2>{title}!</h2>
                    <div className="violet-line"/>
                </div>
                {this.renderMessage()}
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <div className="row action-section">
                    <div className="col-md-12 text-center text-lg-left p-0">
                        <a className="button-white mr30 mob-mb30px" onClick={this.onClick}><span>I</span>{UIHelper.getText("startSessionButton")}</a>
                    </div>
                </div>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={profileIllustration} alt=""/></div>*/}
                <AnimationHelper type="girlsTable"/>
            </div>];
    }

    renderMessage() {
        let success = this.props.match.params.result === "success";
        if (!success)
            return (<p>{UIHelper.getText("activationErrorMessage")}</p>);

        return (<p>
            <span className="f20slb"> {UIHelper.getText("activationSuccessStart")}</span>{UIHelper.getText("activationSuccessEnd")}
        </p>)
    }

}

