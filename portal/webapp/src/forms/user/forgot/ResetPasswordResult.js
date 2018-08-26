import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

export default class ResetPasswordResult extends Component {

    constructor(props) {
        super(props);
        this.onClick = this.onClick.bind(this);
    }

    onClick() {
        this.props.history.push("/login")
    }

    render() {
        let result = this.props.match.params.result;
        let isSuccess = this.props.match.params.result === "success";
        let title = isSuccess ? UIHelper.getText("resetSuccessTitle") : UIHelper.getText("resetErrorTitle");
        let content;
        if (result === "invalid")
            content = UIHelper.getText("resetErrorInvalidLinkMessage");
        else
            content = isSuccess ? UIHelper.getText("resetSuccessMessage") : UIHelper.getText("resetErrorMessage");


        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2>{title}!</h2>
                    <div className="violet-line"/>
                </div>
                <p>{content}</p>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <div className="row action-section">
                    <div className="col-md-12 text-center text-lg-left p-0">
                        <a className="button-white mr30 mob-mb30px"
                           onClick={this.onClick}><span>I</span>{UIHelper.getText("startSessionButton")}</a>
                    </div>
                </div>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>];
    }

}