import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

export default class DEProgramQuestion extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb40"><h2 className="f90sbg">{UIHelper.getText("programQuestionNumber")}</h2>
                        <div className="violet-line"/>
                    </div>
                    <p className="f40sbg text-justify">{UIHelper.getText("programSelectionQuestion")}</p>
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