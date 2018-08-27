/**
 * Created by fran on 2/2/18.
 */
import React, {Component} from "react";
import moment from "moment";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {loadHome, resetWizard, changeToScheduleView} from "../redux/actions";
import AnimationHelper from "../components/AnimationHelper";
import * as UIHelper from "../UIHelper";
import Button from "../components/Button";
import ModalHelper from "../components/ModalHelper";
import ChooseSharedSchool from "../modals/ChooseSharedSchool";
import DisclaimerModal from "../components/DisclaimerModal";

class Home extends Component {

    constructor(props) {
        super(props);
        this.preEnroll = this.preEnroll.bind(this);
    }

    componentWillMount() {
        this.props.resetWizard();
        this.props.loadHome();
    }

    preEnroll() {
        this.props.history.push("/wizard");
    }

    editPreEnroll = (pre) => e => {
        let id = pre.id;
        let requestStatus = pre.requestStatus;
        let requestStatusText = pre.requestStatusText;
        let studentName = pre.student.fullName;

        if (requestStatus !== "ACTIVE")
            this.refs.modal.confirm(`La matrícula de ${studentName} se encuentra ${requestStatusText}`, "¿Está seguro que desea editarla?", () => {
                this.props.history.push(`/wizard/${id}`);
            }, () => {
                // this.props.unblockUI()
            });
        else
            this.props.history.push(`/wizard/${id}`);
    };

    editAddress = (pre) => e => {
        let id = pre.id;
        this.props.history.push(`/edit/address/${id}`);
    };

    viewStudentSchedule = (pre) => e => {
        let studentNumber = pre.student.studentNumber;
        let fullName = pre.student.fullName;
        let gradeLevel = pre.nextGradeLevelDescription;
        let school = pre.schoolName;
        let obj = {fullName: fullName, gradeLevel: gradeLevel, school: school};

        this.props.changeToScheduleView(obj, () => {
            this.props.history.push(`/schedule/${studentNumber}`);
        });

    };

    addSharedSchool = (pre) => e => {
        let id = pre.id;
        let sharedSchoolId = pre.sharedSchoolId;
        let params = {
            studentName: pre.student.fullName,
            requestId: id,
            schoolId: sharedSchoolId,
            sharedSchool: pre.sharedSchool,
            nextGradeLevelDescription: pre.nextGradeLevelDescription,
            gradeLevel: pre.nextGradeLevel
        };
        this.refs.shareSchoolModal.getWrappedInstance().open(params, (needToReload) => {
            if (needToReload)
                window.location.reload();
        });

    };

    agreementModal = (pre) => e => {

        let params = {
            studentName: pre.student.fullName,
            studentId: pre.student.id
        };

        this.refs.disclaimerModal.getWrappedInstance().open(params, (needToReload) => {
            if (needToReload)
                window.location.reload();
        });
    };


    render() {
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <span className="f30sbg"> Resumen de&nbsp;<span
                        className="f30sbb">{UIHelper.getText("homeTitle")}</span></span>
                    <div className="violet-line"/>

                    <span
                        className="f20slg">A continuaci&oacute;n un resumen de las matr&iacute;culas realizadas.&nbsp;&nbsp;&nbsp;</span>
                    <div className="row">
                        <div className="col-md-12">
                            <Button size="small" style={{width: '40%'}}
                                    onClick={this.preEnroll}>{UIHelper.getText("searchEnrollmentButton")}</Button>
                        </div>
                    </div>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    {this.renderHome()}
                    <div style={{marginBottom: 300}}/>
                </div>
                {/*{this.props.footer}*/}
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>,
            <ModalHelper ref="modal"/>,
            <ChooseSharedSchool ref="shareSchoolModal"/>,
            <DisclaimerModal ref="disclaimerModal"/>
        ];
    }

    renderHome() {
        return [
            <div className="row">
                <div className="col-md-12 enrollmentList">
                    {this.renderPreEnrollmentList()}
                </div>
            </div>
        ];
    }

    renderPreEnrollmentList() {
        let preEnrollments = this.props.preEnrollments;
        if (!preEnrollments || preEnrollments.length <= 0)
            return (
                <div className="card">
                    <div className="card-block">
                        <span className="f20sbgr"><i
                            className="fa fa-times-circle"/> Ha concluido el proceso de Confirmaci&oacute;n de Matr&iacute;cula</span>
                    </div>
                </div>
            );
        return preEnrollments.map((pre, index) => {
            let colNum = 12;
            // if (pre.nextGradeLevelDescription)
            //     colNum = colNum - 2;
            // if (pre.submitDate)
            //     colNum = colNum - 2;
            let col1 = `col-md-${colNum}`;
            return (
                <div className="row pt-2" key={index} style={{borderBottom: "1px solid #edeef2"}}>
                    <div className="col-md-12">
                        <div className="row">
                            <div className={col1}>
                                <h5>Estudiante {pre.student.fullName}</h5>
                            </div>

                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                Estatus:
                            </div>
                            <div className="col-md-9">
                            <span
                                className={pre.requestStatus === "APPROVED" ? "text-success" : "text-danger"}>{pre.requestStatusText}
                                &nbsp;&nbsp;
                                {
                                    pre.deniedByUser
                                        ? [<span className="text-info"
                                                 style={{fontSize: 14}}>&nbsp;&nbsp;&nbsp;&nbsp;Solicitud de transcripción de créditos </span>,
                                            <a style={{fontSize: 14}} href={UIHelper.getText("transcriptAppUrl")}
                                               target="_blank">Pulse Aquí</a>]
                                        : (null)
                                }
                            </span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-3">
                                Grado:
                            </div>
                            <div className="col-md-9">
                                {pre.nextGradeLevelDescription ? pre.nextGradeLevelDescription : "No ha sido seleccionado aún"}
                            </div>
                        </div>
                        {this.renderConditionalRow(pre)}
                        {this.renderShareSchoolUI(pre)}

                        <div className="row pb-3">
                            <div className="col-md-2">
                                <Button bsSize="small" bsStyle="info"
                                        onClick={this.agreementModal(pre)}>
                                    <i className="far fa-file-alt"/>
                                </Button>
                            </div>

                            <div className="col-md-2">
                                <Button bsSize="small" bsStyle="info"
                                        onClick={this.editAddress(pre)}>
                                    <i className="fas fa-location-arrow"/>
                                </Button>
                            </div>

                            {
                                pre && pre.student && pre.student.studentNumber && pre.student.studentNumber > 0
                                    ? (<div className="col-md-2">
                                        <Button bsSize="small" bsStyle="info"
                                                onClick={this.viewStudentSchedule(pre)}>
                                            <i className="fas fa-calendar-alt"/>
                                        </Button>
                                    </div>)
                                    : (null)
                            }

                            {
                                pre && pre.student && pre.student.studentNumber <= 0
                                    ? (<div className="col-md-2">
                                        <Button bsSize="small" bsStyle="info"
                                                onClick={this.editPreEnroll(pre)}>
                                            <i className="fas fa-edit"/>
                                        </Button>
                                    </div>)
                                    : (null)
                            }

                            {/*{pre.nextGradeLevelDescription*/}
                            {/*? (<div className="col-md-2">*/}
                            {/*<Button bsSize="small" bsStyle="info"*/}
                            {/*onClick={this.addSharedSchool(pre)}>*/}
                            {/*<i className="fas fa-home"/>*/}
                            {/*</Button>*/}
                            {/*</div>)*/}
                            {/*: (null)*/}
                            {/*}*/}
                            {/*<div className="col-md-2">*/}
                            {/*<Button bsSize="small" bsStyle="info"*/}
                            {/*onClick={this.editPreEnroll(pre)}>*/}
                            {/*<i className="fas fa-edit"/>*/}
                            {/*</Button>*/}
                            {/*</div>*/}
                        </div>

                    </div>
                </div>
            )
        });
    }

    renderConditionalRow(pre) {
        if (pre.type === 'OCCUPATIONAL')
            return (<div className="row">
                <div className="col-md-3">
                    Matrícula:
                </div>
                <div className="col-md-9">
                    {pre.enrollmentTypeText}
                </div>
            </div>);

        let label = pre.type === 'REGULAR' ? "Escuela:" : "Escuelas:";
        return (<div className="row">
            <div className="col-md-3">
                {label}
            </div>
            <div className="col-md-9">
                {this.renderSchoolUI(pre)}
            </div>
        </div>)
    }

    renderSchoolUI(pre) {
        if (pre.type === 'REGULAR') {
            let school = pre.schoolName ? `${pre.extSchoolNumber} - ${pre.schoolName}` : "No ha sido seleccionado aún";
            return (school);
        }
        else
            return (pre.alternativeSchools.map((school, index) => (
                <div className="row">
                    <div className="col-md-12">
                        {`${index + 1}.`}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{`${school.extSchoolNumber} - ${school.schoolName}`}
                    </div>
                </div>
            )));
    }

    renderShareSchoolUI(pre) {
        if (pre.sharedSchool) {
            return (<div className="row">
                <div className="col-md-12">
                    Escuela 'Shared': &nbsp;&nbsp;{pre.sharedSchool.displayName}
                </div>
            </div>);
        }
        return (null);
    }

}

function mapStateToProps(store) {
    return {
        preEnrollments: store.home.preEnrollments
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({loadHome, resetWizard, changeToScheduleView}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions)(Home);



