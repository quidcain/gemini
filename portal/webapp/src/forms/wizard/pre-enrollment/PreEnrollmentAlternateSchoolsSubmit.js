import React, {Component} from "react";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {submitAlternatePreEnrollment, unblockUI} from "../../../redux/actions";
import AnimationHelper from "../../../components/AnimationHelper";
import * as UIHelper from "../../../UIHelper";

class PreEnrollmentAlternateSchoolsSubmit extends Component {

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
            this.props.submitAlternatePreEnrollment(submitRequest, onResult, onError);
        }, () => {
            if (!this.isConfirmAction)
                this.props.unblockUI()
        });
    }

    render() {
        let student = this.props.student;
        let alternateSchools = this.props.alternatePreEnrollment.alternateSchools;
        let validAlternate = true;
        for (let altSchools of alternateSchools) {
            if (altSchools && altSchools.school && altSchools.schoolId === "-1")
                validAlternate = false;
        }
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2
                    className="f90sbg">{UIHelper.getText("alternateSubmitPageQuestionNumber")}</h2>
                    <div className="violet-line"></div>
                </div>
                <p className="f20sbg">{UIHelper.getText("alternateSubmitMessageStart")} <span
                    className="f20sbb">{student.fullName}</span>{UIHelper.getText("alternateSubmitMessageEnd")}</p>

                <div className="row">

                    {validAlternate && alternateSchools && alternateSchools.length > 0
                        ? alternateSchools.map((school, index) => (
                            this.renderAlternateSchool(school)
                        ))
                        : (<span style={{paddingLeft: 100, color: "#ff8700"}}
                                 className="f20sbb"><i
                            className="fas fa-exclamation-triangle"/>&nbsp;&nbsp;{UIHelper.getText("alternateSubmitValidationMessage")}</span>)}

                </div>

            </div>
            <div className="body d-flex flex-column justify-content-end">
                <form>
                    <div className="row">
                        <div className="col-md-12">
                            <span className="f22slb" style={{
                                color: "#000",
                                fontWeight: 600
                            }}>{UIHelper.getText("alternateChangeQuestion")}</span>
                        </div>
                    </div>
                </form>
                <div style={{marginTop: -50}}>
                    {this.props.footer}
                </div>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>];
    }

    renderAlternateSchool(altSchool) {
        let nextGradeLevelDescription = this.props.preEnrollment.nextGradeLevelDescription;
        return [
            <div className="col-md-1">
                <span className="f20sbb">{altSchool.priority}.</span>
            </div>,
            <div className="col-md-5" style={{paddingLeft: 0}}>
                <p className="f18slg mb-1"><i className="fas fa-university mr5"></i> <span
                    id="school">{altSchool.schoolName}</span></p>
                <p className="f18slg mb-1"><i className="icon-teacher mr5"></i> <span
                    id="level">{nextGradeLevelDescription}</span></p>
                <div className="row">
                    <div className="col-md-1">
                        <i className="icon-gps mr5"/>
                    </div>
                    <div className="col-md-9">
                        <p className="f18slg mb-1">
                            <span id="adress">{altSchool.schoolAddress.addressFormatted}</span>
                        </p>
                    </div>
                </div>

            </div>];
    }

}

function mapStateToProps(store) {
    return {
        requestId: store.preEnrollment.requestId,
        student: store.studentInfo.student,
        preEnrollment: store.preEnrollment.info,
        alternatePreEnrollment: store.preEnrollment.alternateSchoolEnrollment
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({submitAlternatePreEnrollment, unblockUI}, dispatch)
}


export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PreEnrollmentAlternateSchoolsSubmit);
