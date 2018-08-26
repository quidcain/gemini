import React, {Component} from "react";
import leisureIllustration from "../../../assets/img/leisure-illustration.png";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";


export default class CompletedPreEnrollment extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let enrollmentType = this.props.enrollmentType;
        let explanation = UIHelper.getText(enrollmentType === "REGULAR"
        ? "completedPreEnrollmentExplanation"
        : "completedOptionalPreEnrollmentExplanation");

        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2>{UIHelper.getText("completedPreEnrollment")}</h2>
                    <div className="violet-line"></div>
                </div>
                <p className="f40sllg">
                    <span className="f40sbgr">{explanation}</span>
                </p>
            </div>
            {this.props.footer}
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>];
    }
}