/**
 * Created by fran on 2/1/18.
 */
import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {submitPreEnrollment, unblockUI} from "../../../redux/actions";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

class PreEnrollmentRecordFound extends Component {

    constructor(props) {
        super(props);
    }

    onPress(onResult, onError) {
        let requestId = this.props.requestId;
        let form = this.props.preEnrollment;
        let submitRequest = {requestId: requestId, schoolId: form.schoolId, nextGradeLevel: form.nextGradeLevel};
        this.isConfirmAction = false;
        this.props.modal.confirm(UIHelper.getText("confirmationTitle"), UIHelper.getText("confirmationQuestion"), () => {
            this.isConfirmAction = true;
            this.props.submitPreEnrollment(submitRequest, onResult, onError);
        }, () => {
            if (!this.isConfirmAction)
                this.props.unblockUI()
        });
    }

    render() {
        let student = this.props.student;
        let preEnrollment = this.props.preEnrollment;

        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2
                    className="f90sbg">{UIHelper.getText("enrollmentPageQuestionNumber")}</h2>
                    <div className="violet-line"></div>
                </div>
                <p className="f30sbg">
                    {UIHelper.getText("enrollmentPageMessagePage")} <span
                    className="f30sbb">{UIHelper.getText("enrollmentYear")}</span>:
                </p>
                <p className="f22slg mb-1"><i className="fas fa-university mr5"></i> <span
                    id="school">{preEnrollment.schoolName}</span></p>
                <p className="f22slg mb-1"><i className="icon-teacher mr5"></i> <span
                    id="level">{preEnrollment.nextGradeLevelDescription}</span></p>
                <p className="f22slg mb-1"><i className="icon-gps mr5"></i> <span
                    id="adress">{preEnrollment.schoolAddress.addressFormatted}</span></p>
                <p className="f30sbg mt30">{UIHelper.getText("enrollmentChangeQuestion")}</p>
            </div>
            {this.props.footer}
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>]
    }
}

function mapStateToProps(store) {
    return {
        requestId: store.preEnrollment.requestId,
        student: store.studentInfo.student,
        preEnrollment: store.preEnrollment.info
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({submitPreEnrollment, unblockUI}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PreEnrollmentRecordFound);

