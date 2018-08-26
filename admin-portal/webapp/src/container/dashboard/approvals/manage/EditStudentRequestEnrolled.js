import React, {Component} from "react";
import * as store from "../../../../setup/store";
import {withRouter} from "react-router-dom";
import swal from 'sweetalert';
import ReasonToDenyModal from "./ReasonToDenyModal";
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";

class EditStudentRequestEnrolled extends Component {

    constructor(props) {
        super(props);
        this.state = {studentRequest: null, programSelected: null};
        this.occupationalProgramChanged = this.occupationalProgramChanged.bind(this);
        this.save = this.save.bind(this);
        this.approve = this.approve.bind(this);
        this.deny = this.deny.bind(this);

    }

    componentWillMount() {
        let preEnrollmentId = this.props.match.params.id;
        let schoolId = this.props.match.params.schoolId;

        store.services()
            .getStudentApprovalInfo(preEnrollmentId, schoolId)
            .then((result) => {
                this.setState({
                    ...this.state,
                    studentRequest: result,
                    programSelected: (result.selectedProgram && result.selectedProgram.programCode) ? result.selectedProgram.programCode : null
                });
            })
    }

    inputHandler(e) {
        let element = e.target;
        let form = this.state;
        form[element.id] = element.value;
    }

    approve() {
        this.save({approve: true, reasonToDeny: null, comment: null, program: this.state.programSelected})

    }

    deny() {
        this.refs.reasonModal.open((reasonToDeny, comment) => {
            this.save({approve: false, reasonToDeny: reasonToDeny, comment: comment});
        });
    }

    save(result) {
        let preEnrollmentId = this.props.match.params.id;
        let studentRequest = this.state.studentRequest;

        if (studentRequest.occupationalEnrollment && !this.state.programSelected) {
            swal("!!!Debe seleccionar un programa para aprobar/rechazar la matrícula ocupacional!!!", {icon: "error"});
            return;
        }


        let form = {
            preEnrollmentId: preEnrollmentId,
            schoolId: studentRequest.schoolId,
            gradeLevel: studentRequest.gradeLevel,
            approve: result.approve,
            reasonToDeny: result.reasonToDeny,
            comment: result.comment,
            occupationEnrollment: studentRequest.occupationalEnrollment,
            program: {  schoolId: studentRequest.schoolId, programCode: this.state.programSelected}
        };


        store.services()
            .saveStudentApproval(form)
            .then((response) => {
                if (response.status === 200) {
                    swal("!!!Los datos sometidos fueron grabados correctamente!!!", {icon: "info"});
                    this.props.history.push(`/dashboard/approvals/manage/`)
                } else {
                    swal("!!!Los datos sometidos NO fueron grabados correctamente!!!", {icon: "error"});
                }
            })

    }

    render() {
        let isDirectorUser = store.getUser() ? store.getUser().directorUser : false;
        let studentRequest = this.state.studentRequest;
        if (!studentRequest)
            return (null);

        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Aprobaci&oacute;n</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white", padding: 40}}>
                <div className="col-md-12">
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Escuela:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.schoolName}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Escuela de Origen:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.originSchoolNumber ? `${studentRequest.originSchoolNumber}-${studentRequest.originSchoolName}` : 'Se desconoce'}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Grado:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.gradeLevelDescription}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Estudiante:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.studentName}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Num. Estudiante (SIE):
                        </div>
                        <div className="col-md-8">
                            <span
                                className="text-info">{studentRequest.studentId && studentRequest.studentId > 0 ? studentRequest.studentId : 'Nuevo Estudiante'}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Edad:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{`${studentRequest.age} años`}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Impedimento:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.disability}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Estudiante Montessori:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{studentRequest.montessori ? "Sí" : "No"}</span>
                        </div>
                    </div>


                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Estatus:
                        </div>
                        <div className="col-md-8">
                            <span className={studentRequest.colorCss}>{studentRequest.status}</span>
                        </div>
                    </div>

                    {this.occupationalPrograms()}

                    {this.conditionalReason()}

                    <div className="clearfix mt-4">
                        {isDirectorUser ? [
                                <div className="float-left">
                                    <button className="button-green" style={{paddingLeft: 10}} onClick={this.deny}>
                                        Denegar
                                    </button>
                                </div>,
                                <div className="float-right">
                                    <button className="button-green" style={{paddingLeft: 10}} onClick={this.approve}>
                                        Aprobar
                                    </button>
                                </div>]
                            : null}
                    </div>

                    <ReasonToDenyModal ref="reasonModal"/>

                </div>
            </div>];
    }

    occupationalProgramChanged(obj) {
        this.setState({...this.state, programSelected: obj.programCode})

    }

    occupationalPrograms() {
        let studentRequest = this.state.studentRequest;
        let programs = studentRequest.programs;

        if (studentRequest.occupationalEnrollment)
            return (
                <div className="row" style={{padding: 10}}>
                    <div className="col-md-4">
                        Seleccione Programa (Programas Solicitados):
                    </div>
                    <div className="col-md-8">
                        <RemoteCodeSelect id="occupationalProgram"
                                          label=""
                                          placeholder=""
                                          codes={programs}
                                          onObjectChange={this.occupationalProgramChanged}
                                          target="programCode"
                                          display="programDescription"
                                          value={this.state.programSelected}
                        />
                    </div>
                </div>
            );

        return (null);

    }

    conditionalReason() {
        let studentRequest = this.state.studentRequest;

        if (studentRequest.denied)
            return (
                <div className="row" style={{padding: 10}}>
                    <div className="col-md-4">
                        Razón para rechazar:
                    </div>
                    <div className="col-md-8">
                        <span className="text-info">{studentRequest.reasonToDeny}</span>
                    </div>
                </div>
            );

        return (null);

    }
}

export default withRouter(EditStudentRequestEnrolled);