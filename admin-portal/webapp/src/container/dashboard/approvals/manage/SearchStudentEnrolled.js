import React, {Component} from "react";
import * as store from "../../../../setup/store";
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";
import {withRouter} from "react-router-dom";
import {services} from "../../../../setup/store";
import swal from 'sweetalert';

class SearchStudentEnrolled extends Component {

    constructor(props) {
        super(props);
        this.schoolChanged = this.schoolChanged.bind(this);
        this.gradeLevelChanged = this.gradeLevelChanged.bind(this);

        this.goToDetail = this.goToDetail.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.cleanTimeout = this.cleanTimeout.bind(this);
        this.state = ({
            result: [],
            schoolId: null,
            gradeLevel: null,
            gradeLevels: null,
            schools: null,
            montessoriCheck: [],
            montessoriFeedbackUI: [],
            schoolMontessori: false
        })
    }

    componentWillMount() {
        services()
            .retrieveApprovalSchools()
            .then((schools) => {
                this.setState({...this.state, schools: schools}, () => {
                    let schoolId = store.getDataByKey("schoolId");
                    let gradeLevel = store.getDataByKey("gradeLevel");
                    let schoolMontessori = store.getDataByKey("schoolMontessori");

                    if (schoolId && gradeLevel) {
                        this.setState({...this.state, schoolId: schoolId}, () => {
                            this.loadGradeLevels(() => {
                                this.setState({
                                    ...this.state,
                                    gradeLevel: gradeLevel,
                                    schoolMontessori: schoolMontessori
                                }, () => {
                                    this.doSearch();
                                });
                            });

                        })
                    }

                });

            })
    }

    goToDetail = (resultObj) => (e) => {
        store.setSessionData({
            schoolId: this.state.schoolId,
            gradeLevel: this.state.gradeLevel,
            schoolMontessori: this.state.schoolMontessori
        });
        this.props.history.push(`/dashboard/approvals/edit/manage/${resultObj.preEnrollmentId}/school/${this.state.schoolId}`);
    };

    doSearch() {
        let schoolId = this.state.schoolId;
        let gradeLevel = this.state.gradeLevel;
        if (!schoolId || parseInt(schoolId) < 0) {
            swal("!!!Debes seleccionar una escuela para poder buscar los estudiantes!!!");
            return;
        }

        if (!gradeLevel || gradeLevel === '-1') {
            swal("!!!Debes seleccionar un grado de la escuela para poder buscar los estudiantes!!!");
            return;
        }

        let criteria = {schoolId: schoolId, gradeLevel: gradeLevel};
        store.services()
            .doStudentSearch(criteria)
            .then((result) => {
                this.setState({...this.state, result: result});
                if (!result || result.length < 1)
                    swal("!!!La búsqueda no produjo resultados!!!", {icon: "error"});
                else {
                    if (this.state.schoolMontessori) {
                        let montessoriCheck = [];
                        let montessoriFeedbackUI = [];
                        for (const student of result) {
                            montessoriCheck.push(student.montessori);
                            montessoriFeedbackUI.push(false);
                        }
                        this.setState({
                            ...this.state,
                            montessoriCheck: montessoriCheck,
                            montessoriFeedbackUI: montessoriFeedbackUI
                        });
                    }
                }
            })
    }

    loadGradeLevels(callback) {
        services()
            .retrieveGradeLevels(this.state.schoolId)
            .then((gradeLevels) => {
                this.setState({...this.state, gradeLevels: gradeLevels}, () => {
                    if (callback)
                        callback();
                });
            })
    }

    schoolChanged(schoolObj) {
        this.setState({
            ...this.state,
            schoolId: schoolObj.schoolId,
            gradeLevel: "-1",
            schoolMontessori: schoolObj.montessori
        }, () => {
            this.loadGradeLevels();
        })
    }

    gradeLevelChanged(gradeLevelObj) {
        this.setState({...this.state, gradeLevel: gradeLevelObj.value})
    }

    canEditMontessoriCheck() {
        return (store.getUser() ? (store.getUser().directorUser || store.getUser().sieUser) : false);
    }


    render() {
        let result = this.state.result;
        let grades = this.state.gradeLevels;
        let schools = this.state.schools;

        let selectedGradeCode = this.state.gradeLevel;
        let selectedSchoolId = this.state.schoolId;
        let disabledGradeSelector = !selectedSchoolId;
        let counter = 1;

        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Aprobación de Matrículas</h3>
                </div>
            </div>,

            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">

                    <div className="row" style={{paddingTop: 20}}>
                        <div className="col-md-10">
                            <RemoteCodeSelect id="schools"
                                              label="Escuela"
                                              placeholder="Escuelas"
                                              codes={schools}
                                              onObjectChange={this.schoolChanged}
                                              target="schoolId"
                                              display="displayName"
                                              value={selectedSchoolId}/>
                            {/*<AdminSchoolSelector ref="selector"/>*/}
                        </div>
                        <div className="col-md-2">
                            <RemoteCodeSelect id="gradeLevel"
                                              label="Grados"
                                              placeholder="Grados"
                                              codes={grades}
                                              onObjectChange={this.gradeLevelChanged}
                                              target="value"
                                              display="display"
                                              value={selectedGradeCode}
                                              disabled={disabledGradeSelector}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-10"/>
                        <div className="col-md-2">
                            <button className="button-green" style={{paddingLeft: 10}} onClick={this.doSearch}>
                                Buscar
                            </button>
                        </div>
                    </div>

                    <div className="row">
                        <table className="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th>Num.</th>
                                <th>Nombre</th>
                                <th>Proviene de escuela</th>
                                <th>Num. Estudiante (SIE)</th>
                                <th>Tiene Impedimento</th>
                                {this.state.schoolMontessori ? (<th>Est. Montessori</th>) : (null)}
                                <th>Prog. Ocupacional</th>
                                <th>Edad</th>
                                <th>Estatus</th>
                                <th/>
                            </tr>
                            </thead>
                            <tbody>
                            {result && result.length > 0
                                ? result.map((sgl, i) => {
                                    return (
                                        <tr style={{background: "white"}}>
                                            <td>{`${counter++}.`}</td>
                                            <td>{sgl.studentName}</td>
                                            <td>{sgl.originSchoolNumber ? sgl.originSchoolNumber : 'Se desconoce'}</td>
                                            <td>{sgl.studentId && sgl.studentId > 0 ? sgl.studentId : 'Nuevo Estudiante'}</td>
                                            <td>{sgl.haveDisability}</td>

                                            <td>{this.state.schoolMontessori ? (this.renderMontessori(sgl, i)) : (null)}</td>

                                            <td>{sgl.requestedPrograms}</td>
                                            <td>{sgl.age}</td>
                                            <td><span className={sgl.colorCss}>{sgl.status}</span></td>
                                            <td onClick={this.goToDetail(sgl)}><i className="fa fa-edit"
                                                                                  style={{fontSize: 32}}/></td>
                                        </tr>
                                    )
                                })
                                : (<tr style={{background: "white"}}>
                                    <td colSpan={8}>
                                        <span>No existen datos para los criterios de b&uacute;squeda ingresado</span>
                                    </td>
                                </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        ];
    }

    renderMontessori(obj, i) {
        return [<input type="checkbox" checked={this.state.montessoriCheck[i]}
                       onChange={this.saveIsMontessoriFlag(obj, i)} disabled={!this.canEditMontessoriCheck()}/>, <span
            className={`${this.state.montessoriFeedbackUI[i] ? "fa fa-check" : ""}`} style={{color: "#3d9e1b"}}/>];
    }

    cleanTimeout() {

        setTimeout(() => {
            this.setState({
                ...this.state,
                montessoriFeedbackUI: this.state.montessoriFeedbackUI
            });
        }, 300)
    }

    saveIsMontessoriFlag = (obj, i) => (e) => {
        const target = e.target;
        let form = {
            preEnrollmentId: obj.preEnrollmentId,
            montessori: target.checked
        };

        this.state.montessoriCheck[i] = target.checked;
        store.services()
            .saveMontessoriInfo(form)
            .then((response) => {
                if (response.status === 200) {
                    this.state.montessoriFeedbackUI[i] = true;

                    this.setState({
                        ...this.state,
                        montessoriCheck: this.state.montessoriCheck,
                        montessoriFeedbackUI: this.state.montessoriFeedbackUI
                    }, () => {
                        this.state.montessoriFeedbackUI[i] = false;
                        this.cleanTimeout()
                    })
                } else {
                    swal("!!!Ha ocurrido un error!!!", {icon: "error"});
                }
            })

    };

    saveConfirmedCap = (obj, i) => (e) => {

        let schoolCap = obj;
        let form = {
            schoolId: schoolCap.schoolId,
            gradeLevel: schoolCap.gradeLevel,
            newMaxCapacity: this.state.newMaxCapacity[i]
        };

        if (!this.state.newMaxCapacity[i]) {
            swal("!!!Debe ingresar una capacidad valida para poder guardar!!!", {icon: "error"});
            return;
        }


        store.services()
            .saveEditCapRequest(form)
            .then((response) => {
                if (response.status === 200) {
                    swal("!!!Los datos sometidos fueron grabados correctamente!!!", {icon: "info"});
                    this.props.history.push(`/dashboard/school/capacity/manage/${schoolCap.schoolId}`)
                } else {
                    swal("!!!Los datos sometidos NO fueron grabados correctamente!!!", {icon: "error"});
                }
                this.doSearch();
            })
    };
}

export default withRouter(SearchStudentEnrolled);