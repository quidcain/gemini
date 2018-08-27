import React, {Component} from "react";
import {loadStudentSchedule} from "../../../redux/actions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import AnimationHelper from "../../../components/AnimationHelper";

class StudentScheduleView extends Component {

    constructor(props) {
        super(props);
    }

    componentWillMount() {
        let studentId = this.props.match.params.id;
        this.props.loadStudentSchedule(studentId);
    }

    render() {
        let result = this.props.studentScheduleView;
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40">
                    <h3 className="f20sbg">Programa de Clase del Estudiante</h3>
                    <div className="violet-line"/>
                </div>
            </div>

            <div className="body d-flex flex-column justify-content-end">
                <form>
                    <div className="row  pt-2">
                        <div className="col-md-3">
                            <span className="f18slg">Nombre del estudiante:</span>
                        </div>
                        <div className="col-md-9">
                            <span className="f20sbb">{this.props.fullName}</span>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">
                            <span className="f18slg">Grado:</span>
                        </div>
                        <div className="col-md-9">
                            <span className="f20sbb">{this.props.gradeLevel}</span>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3">
                            <span className="f18slg">Escuela:</span>
                        </div>
                        <div className="col-md-9">
                            <span className="f20sbb">{this.props.school}</span>
                        </div>
                    </div>
                    <div className="row pt-3">
                        <div className="col-md-12">
                            <table className="table table-striped table-hover table-bordered">
                                <thead>
                                <tr style={{background: "#7988c9", color: "#fff"}}>
                                    {/*<th>Periodo</th>*/}
                                    <th>Maestro</th>
                                    <th>Lunes</th>
                                    <th>Martes</th>
                                    <th>Miercoles</th>
                                    <th>Jueves</th>
                                    <th>Viernes</th>
                                </tr>
                                </thead>
                                <tbody>
                                {result && result.length > 0
                                    ?
                                    result.map((sgl, i) => (
                                        <tr>
                                            {/*<td style={{fontSize: 14}}>{sgl.periodText}</td>*/}
                                            <td style={{fontSize: 14}}>{sgl.teacher}</td>
                                            <td style={{fontSize: 14}}>{sgl.monday}</td>
                                            <td style={{fontSize: 14}}>{sgl.tuesday}</td>
                                            <td style={{fontSize: 14}}>{sgl.wednesday}</td>
                                            <td style={{fontSize: 14}}>{sgl.thursday}</td>
                                            <td style={{fontSize: 14}}>{sgl.friday}</td>
                                        </tr>
                                    ))
                                    : (<tr style={{background: "white"}}>
                                        <td colSpan={6}>
                                            <span>No existe programa de clase para el estudiante</span>
                                        </td>
                                    </tr>)
                                }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
                <div style={{marginTop: 80}}/>

                <div key="footer" style={{zIndex: 1000}}>
                    <div className="row action-section">
                        <div className="col-md-6 text-center text-lg-left p-0">
                            <a id="nextButton" className="button-white mr30 mob-mb30px" onClick={() => {
                                this.props.history.push('/home');
                            }}>
                                <span>S</span>Salir
                            </a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
            ,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>];
    }
}

function mapStateToProps(store) {
    return {
        student: store.studentInfo.student,
        fullName: store.studentInfo.fullName,
        gradeLevel: store.studentInfo.gradeLevel,
        school: store.studentInfo.school,
        preEnrollment: store.preEnrollment.info,
        studentScheduleView: store.studentInfo.studentScheduleView
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({loadStudentSchedule}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(StudentScheduleView);
