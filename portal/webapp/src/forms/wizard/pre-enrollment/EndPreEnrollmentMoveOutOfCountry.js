import React, {Component} from "react";
import * as UIHelper from "../../../UIHelper";
import AnimationHelper from "../../../components/AnimationHelper";

export default class EndPreEnrollmentMoveOutOfCountry extends Component{

    constructor(props){
        super(props);
    }

    render() {
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb40"><h2>{UIHelper.getText("enrollmentEndByOutOfCountry")}</h2>
                        <div className="violet-line"></div>
                    </div>
                    <p className="f40sllg">
                        <span className="f40sbgr">{UIHelper.getText("enrollmentEndByOutOfCountryExplanation")}</span> </p>
                    <a href={UIHelper.getText("transcriptAppUrl")} target="_blank">Pulse Aquí</a>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;Solicitud de transcripción de créditos </span>
                </div>
                {this.props.footer}
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>
        ];
    }
}