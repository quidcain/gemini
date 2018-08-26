import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import {saveBornPR} from "../../../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import * as UIHelper from "../../../UIHelper";

class IsStudentBornPRQuestion extends Component {

    constructor(props) {
        super(props);
    }

    onPress(onResult, onError) {
        this.save(true, onResult, onError);
    }

    onBack(onResult, onError) {
        this.save(false, onResult, onError);
    }

    save(answer, onResult, onError) {
        this.props.saveBornPR(answer, onResult, onError);
    }

    render() {
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2 className="f90sbg">{UIHelper.getText("isStudentBornPRQuestionNumber")}</h2>
                    <div className="violet-line"/>
                </div>
                <p className="f60sbg text-left">{UIHelper.getText("isStudentBornPRQuestionStart")}<span
                    className="f60sbb">{UIHelper.getText("isStudentBornPRQuestionEnd")}</span></p>
            </div>
            {this.props.footer}
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>];
    }
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({saveBornPR}, dispatch)
}

export default connect(null, mapDispatchToActions, null, {withRef: true})(IsStudentBornPRQuestion);


