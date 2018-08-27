import React, {Component} from "react";
import * as store from "../../../../setup/store"
import swal from "sweetalert";
import * as Utils from "../../../../Utils";
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";

const enrollmentTypes = [
    {type: 'REGULAR', description: 'Escuela Regular'},
    {type: 'OCCUPATIONAL', description: 'Escuela Ocupacional'},
    {type: 'REGULAR_ALTERNATE_SCHOOLS', description: 'Escuelas alternas'},
];

export default class EditRequestForm extends Component {

    constructor(props) {
        super(props);
        this.state = {request: null, comment: null, enrollmentType: null};
        this.inputHandler = this.inputHandler.bind(this);
        this.reactivate = this.reactivate.bind(this);
        this.enrollmentTypeChanged = this.enrollmentTypeChanged.bind(this);
    }

    componentWillMount() {
        let requestId = this.props.match.params.id;
        store.services()
            .getEditRequest(requestId)
            .then((result) => {
                this.setState({...this.state, request: result});
            })
    }

    enrollmentTypeChanged(obj) {
        this.setState({...this.state, enrollmentType: obj.type});
    }

    reactivate() {
        let requestId = this.props.match.params.id;
        let form = {requestId: requestId, comment: this.state.comment, type: this.state.enrollmentType};
        store.services()
            .reactivateRequest(form)
            .then((response) => {
                if (response.status === 200) {
                    swal("!!!Solicitud fue reactivada correctamente!!!", {icon: "info"})
                        .then(() => {
                            this.props.history.push("/dashboard/request/manage")

                        });
                } else {
                    swal("!!!Solicitud no pudo reactivarse correctamente!!!", {icon: "error"});
                }
            })

    }

    inputHandler(e) {
        let element = e.target;
        this.state.comment = element.value;
    }


    render() {
        let request = this.state.request;
        if (!request)
            return (null);
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Detalles de matricula</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white", padding: 40}}>
                <div className="col-md-12">
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Nombre del Padre/Madre:
                        </div>
                        <div className="col-md-3">
                            <span className="text-info">{request.fatherName}</span>
                        </div>

                        <div className="col-md-2">
                            Ubicado en (según último email):
                        </div>
                        <div className="col-md-4">
                            <span className="text-info">{request.resultSchool}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Nombre del Estudiante:
                        </div>
                        <div className="col-md-3">
                            <span className="text-info">{request.studentName}</span>
                        </div>

                        <div className="col-md-2">
                            Enviado a:
                        </div>
                        <div className="col-md-4">
                            <span
                                className="text-info">{`${request.email ? request.email : ''} (${Utils.formatWithMsg(request.emailSentDate, 'DDMMMMYYYY h:mm:ss a', "Sin Enviar")})`}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Número de Estudiante SIE:
                        </div>
                        <div className="col-md-9">
                            <span className="text-info">{request.sieNumber ? request.sieNumber : "No posee aún"}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Fecha de nacimiento:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{Utils.format(request.dateOfBirth, 'DDMMMMYYYY')}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Sometido?
                        </div>
                        <div className="col-md-9">
                            <span className="text-info">{request.submitted}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Fecha de sometido
                        </div>
                        <div className="col-md-9">
                            <span
                                className="text-info">{Utils.formatWithMsg(request.submitDate, 'DDMMMMYYYY h:mm:ss a', "Sin Someter")}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Estatus de la Solicitud
                        </div>
                        <div className="col-md-9">
                            <span className={request.cssStatusColor}>{request.status}</span>
                        </div>
                    </div>


                    {/*<div className="row" style={{padding: 10}}>*/}
                        {/*<div className="col-md-3">*/}
                            {/*<span>Tipo de matrícula:</span>*/}
                        {/*</div>*/}
                        {/*<div className="col-md-3">*/}
                            {/*<RemoteCodeSelect id="enrollmentType"*/}
                                              {/*onObjectChange={this.enrollmentTypeChanged}*/}
                                              {/*codes={enrollmentTypes}*/}
                                              {/*target="type"*/}
                                              {/*display="description"*/}
                                              {/*value={request.type}/>*/}
                        {/*</div>*/}
                        {/*<div className="col-md-6"/>*/}
                    {/*</div>*/}
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Cuenta como:
                        </div>
                        <div className="col-md-9">
                            <span className="text-info">{request.summaryDescription}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            Grado:
                        </div>
                        <div className="col-md-9">
                            <span className="text-info">{request.gradeLevel}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-3">
                            {request.schoolLabel}
                        </div>
                        <div className="col-md-9" style={{marginLeft: -60}}>
                            {this.renderFatherSchoolSelection()}
                        </div>
                    </div>
                    {/*<div className="row" style={{padding: 10}}>*/}
                        {/*<div className="col-md-3">*/}
                            {/*Comentario:*/}
                        {/*</div>*/}
                        {/*<div className="col-md-9" style={{padding: 20}}>*/}
                            {/*<textarea id="comment" style={{height: 75, width: "100%"}}*/}
                                      {/*onChange={this.inputHandler}/>*/}
                        {/*</div>*/}
                    {/*</div>*/}
                    {/*<div className="clearfix mt-4">*/}
                        {/*<div className="float-right">*/}
                            {/*<button className="button-green" style={{paddingLeft: 10}}*/}
                                    {/*onClick={this.reactivate}>Re-activar Solicitud*/}
                            {/*</button>*/}
                        {/*</div>*/}
                    {/*</div>*/}
                </div>
            </div>];
    }

    renderFatherSchoolSelection() {
        let request = this.state.request;
        let schools = request.schoolsSelected;
        let cleanSchools = [];
        for (let school of schools) {
            if (school)
                cleanSchools.push(school);
        }

        if (cleanSchools && cleanSchools.length === 1)
            return (<span>{`${schools[0].extSchoolNumber}-${schools[0].schoolName}`}</span>);

        return (cleanSchools && cleanSchools.length > 0
            ? cleanSchools.map((s, i) => (
                <div className="row" style={{padding: 10}}>
                    <div className="col-md-2">
                        <label>{i + 1}.</label>
                    </div>
                    <div className="col-md-10">
                        <label className="text-info">{`${s.extSchoolNumber}-${s.schoolName}`}</label>
                    </div>
                </div>
            ))
            : (<span>No posee ninguna escuela aún</span>));
    }
}