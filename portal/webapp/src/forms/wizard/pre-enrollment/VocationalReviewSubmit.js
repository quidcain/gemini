/**
 * Created by fran on 2/1/18.
 */
import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {
    changeCurrentVocationalEnrollment,
    goToAction,
    partialSaveVocationalPreEnrollment,
    retrieveVocationalPreEnrollment,
    submitVocationalPreEnrollment,
    unblockUI
} from "../../../redux/actions";
import * as types from "../../../redux/types";
import Button from "../../../components/Button";
import * as UIHelper from "../../../UIHelper";
import AnimationHelper from "../../../components/AnimationHelper";

class VocationalReviewSubmit extends Component {

    constructor(props) {
        super(props);
        this.state = {enrollments: []};
        this.onError = this.onError.bind(this);
        this.load = this.load.bind(this);
        this.onAdd = this.onAdd.bind(this);
        this.isTechnical = this.props.preEnrollment.type === types.TECHNIQUE_ENROLLMENT;
        this.vocationalType = this.isTechnical
            ? "Institutos"
            : "Ocupacional";

    }

    componentWillMount() {
        this.load();
    }

    load() {
        this.props.retrieveVocationalPreEnrollment(() => {
        }, this.onError);
    }

    componentWillReceiveProps(nextProps) {
        let vocationalEnrollments = nextProps.preEnrollment.info.enrollments;
        if (vocationalEnrollments && vocationalEnrollments.length > 0) {
            let enrollments = [];
            for (let enrollment of vocationalEnrollments) {
                enrollments.push({collapsed: true})
            }
            this.setState({enrollments: enrollments});
        }
    }

    //todo: fran centralize this!!!
    onError() {
        alert("Ha occurrido un error");
    }

    getSubmitRequest() {
        let requestId = this.props.requestId;
        let form = this.props.preEnrollment;
        return {requestId: requestId, nextGradeLevel: form.nextGradeLevel};
    }

    //Not longer used
    onAdd() {
        this.props.changeCurrentVocationalEnrollment(null);
        let goPage = this.isTechnical ? "TECHNICAL_SCHOOL_SELECTION" : "VOCATIONAL_SCHOOL_SELECTION";
        this.props.goToAction(goPage);
    }

    onEdit = (enrollment) => e => {
        this.props.changeCurrentVocationalEnrollment(enrollment);
        this.props.goToAction("VOCATIONAL_PROGRAMS");
    };

    onDelete = (enrollment) => e => {
        let submitRequest = this.getSubmitRequest();
        submitRequest["schoolIdToDelete"] = enrollment.schoolId;
        submitRequest["validateSchoolSelection"] = false;
        submitRequest["validateProgramSchoolSelection"] = false;
        this.props.partialSaveVocationalPreEnrollment(submitRequest, this.load, this.onError);
    };

    onPress(onResult, onError) {
        this.isConfirmAction = false;
        this.props.modal.confirm(UIHelper.getText("confirmationTitle"), UIHelper.getText("confirmationQuestion"), () => {
            this.isConfirmAction = true;
            this.props.submitVocationalPreEnrollment(this.getSubmitRequest(), onResult, onError);
        }, () => {
            if (!this.isConfirmAction)
                this.props.unblockUI()
        });
    }


    onBack(onResult, onError) {
        this.props.modal.confirm(UIHelper.getText("confirmationTitle"), UIHelper.getText("addAnotherSchoolConfirmation"), () => {
            this.props.changeCurrentVocationalEnrollment(null);
            onResult();
        }, () => {
            this.props.unblockUI()
        });
    }


    render() {
        let student = this.props.student;
        let preEnrollment = this.props.preEnrollment;
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb30"><h2>Revise su Matr&iacute;cula <span>{this.vocationalType}</span>
                    </h2></div>
                    <p className="f22slg mb-1"><i className="icon-teacher mr5"/> <span
                        id="adress">Estudiante {student && student.fullName}</span></p>
                    {this.isTechnical
                        ? (null)
                        : (<p className="f22slg mb-1"><i className="icon-teacher mr5"/> <span
                            id="level">{preEnrollment.info.nextGradeLevelDescription}</span></p>)}
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form>
                        <div className="row">
                            <div className="col-md-12">
                                <span className="f30sbb">Escuelas y programas <span
                                    className="f30sbg"> seleccionados: </span></span>
                            </div>
                        </div>
                        {this.renderCollapseSchools()}
                        <span
                            className="f20sbg">{UIHelper.getText(this.isTechnical ? "instituteChangeQuestion" : "occupationalChangeQuestion")}</span>
                    </form>
                    <div style={{marginTop: -50}}>
                        {this.props.footer}
                    </div>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                <AnimationHelper type="blackboard"/>
            </div>
        ]
    }

    toggleCollapsed = (index) => (e) => {
        this.state.enrollments[index].collapsed = !this.state.enrollments[index].collapsed;
        this.setState({...this.state});
    };

    renderCollapseSchools() {
        let vocationalEnrollments = this.props.preEnrollment.info.enrollments;
        return (
            <div id="accordion" className="collapseScrollable">

                {vocationalEnrollments && vocationalEnrollments.length > 0
                    ?
                    vocationalEnrollments.map((enrollment, index) => (
                        <div className="card">
                            <div className="school-collapse-header" id={`panelTitle${index}`}>

                                <div key={index} className="row card-link"
                                     href="#"
                                     data-toggle="collapse"
                                     data-target={`#collapse${index}`}
                                     aria-controls={`collapse${index}`}
                                     aria-expanded="true">
                                    <div className="col-md-1">
                                        <i onClick={this.toggleCollapsed(index)}
                                           className={`fas ml-2 mt-1 ${ this.state.enrollments && this.state.enrollments.length > index && this.state.enrollments[index].collapsed ? "fa-chevron-circle-up" : "fa-chevron-circle-down" } `}/>
                                    </div>
                                    <div className="col-md-7">
                                        <span>Escuela <span className="f20slb">{enrollment.schoolName}</span></span>
                                    </div>

                                    <div className="col-md-2">
                                        <Button size="small" onClick={this.onEdit(enrollment)}
                                                style={{marginBottom: 5, marginRight: 5}}>
                                            <i className="fas fa-edit"/>
                                        </Button>

                                    </div>
                                    <div className="col-md-2">
                                        <Button size="small" onClick={this.onDelete(enrollment)}
                                                style={{marginBottom: 5}}>
                                            <i className="fas fa-trash"/>
                                        </Button>
                                    </div>
                                </div>
                            </div>

                            <div className="collapse collapsed"
                                 id={`collapse${index}`}
                                 aria-labelledby={`panelTitle${index}`}
                                 data-parent="#accordion">
                                <div className="card-body tableScrollable">
                                    {this.renderVocationalPrograms(enrollment)}
                                </div>
                            </div>
                        </div>
                    ))
                    : (null)

                }
            </div>

        )
    }

    renderVocationalEnrollments() {
        let vocationalEnrollments = this.props.preEnrollment.info.enrollments;
        return (vocationalEnrollments && vocationalEnrollments.length > 0
            ? vocationalEnrollments.map((enrollment, index) => [
                <div key={index} className="row">
                    <div className="col-md-8">
                        <span>Escuela {enrollment.schoolName}</span>
                    </div>

                    <div className="col-md-2">
                        <Button size="small" onClick={this.onEdit(enrollment)}
                                style={{marginBottom: 5, marginRight: 5}}>
                            <i className="fas fa-edit"/>
                        </Button>

                    </div>
                    <div className="col-md-2">
                        <Button size="small" onClick={this.onDelete(enrollment)} style={{marginBottom: 5}}>
                            <i className="fas fa-trash"/>
                        </Button>
                    </div>
                </div>,
                <div className="row">
                    <div className="col-md-12 tableScrollable">
                        {this.renderVocationalPrograms(enrollment)}
                    </div>
                </div>
            ])
            : (null))

    }

    renderVocationalPrograms(enrollment) {
        let programs = enrollment.programs;
        return ((<table className="table table-striped table-hover ">
            <thead>
            <tr>
                <th>#</th>
                <th>Programas</th>
            </tr>
            </thead>
            <tbody>
            {programs && programs.length > 0 ? programs.map((prog, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{prog.programDescription}</td>
                    </tr>
                ))
                : <div><label>No posee ningun programa aun</label></div>}
            </tbody>
        </table>));
    }

    renderAddSchoolButton() {
        return [<h3 style={{textAlign: "right"}}>Desea añadir otra escuela?</h3>,
            <Button size="large" onClick={this.onAdd}>Añadir</Button>]
    }
}

function mapStateToProps(store) {
    return {
        requestId: store.preEnrollment.requestId,
        student: store.studentInfo.student,
        preEnrollment: store.preEnrollment,
        currentVocationalEnrollment: store.preEnrollment.currentVocationalEnrollment
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({
        partialSaveVocationalPreEnrollment,
        changeCurrentVocationalEnrollment,
        submitVocationalPreEnrollment,
        retrieveVocationalPreEnrollment,
        goToAction,
        unblockUI
    }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(VocationalReviewSubmit);

