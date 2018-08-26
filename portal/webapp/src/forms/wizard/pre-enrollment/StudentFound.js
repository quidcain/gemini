import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

export default class StudentFound extends Component {

    constructor(props) {
        super(props);
    }

    getArticle() {
        let gender = this.props.studentGender;
        return gender ? (gender === "M" ? "El" : "La") : ("El/la")
    }

    render() {
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb40"><h2
                        className="f90sbg">{UIHelper.getText("foundStudentPageNumber")}</h2>
                        <div className="violet-line"></div>
                    </div>
                    <p className="f45sbg"><span className="f45sbb"
                                                id="name">{UIHelper.getText("foundStudentPageInfoHighlight")}</span> {this.getArticle()} {UIHelper.getText("foundStudentPageInfoFragmentStart")}
                        <span className="f45sbb" id="name">{this.props.studentName}</span> {UIHelper.getText("foundStudentPageInfoFragmentEnd")}
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