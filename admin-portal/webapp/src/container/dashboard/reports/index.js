import React, {Component} from "react";
import Workbook from 'react-excel-workbook';
import {services} from "../../../setup/store"
import AdminSchoolSelector from "../../../components/AdminSchoolSelector";
import * as Utils from "../../../Utils";
import swal from 'sweetalert';

export default class Report extends Component {

    constructor(props) {
        super(props);
        this.state = {data: []};
        this.type = props.match.params.reportType.toUpperCase();
        this.loadConfig(this.type);
        this.retrieveData = this.retrieveData.bind(this);

    }

    retrieveData() {
        let schoolId = this.refs.selector.getSchoolId();
        if (!schoolId || parseInt(schoolId) < 0) {
            swal("!!!Debes seleccionar una escuela para poder generar el reporte!!!");
            return;
        }
        services()
            .retrieveReportData(this.type, schoolId)
            .then((data) => {
                this.setState({data: data});
                if (data && data.length > 0)
                    this.refs.excel.download();
                else
                    swal("!!!No existen datos para generar el reporte!!!");

            });
    }

    componentWillReceiveProps(nextProps) {
        let actualReportType = this.props.match.params.reportType;
        let nextReportType = nextProps.match.params.reportType;
        console.log(`${actualReportType} - ${nextReportType}`);

        if (actualReportType !== nextReportType) {
            this.type = nextReportType.toUpperCase();
            this.loadConfig(this.type);
            this.setState({data: []})
        }
    }

    render() {

        let pageTitle = `Reporte de ${this.config.pageTitle}`;
        let timestamp = Utils.format(new Date(), "DDMMMMYYYY_h:mm:ss a");
        let reportName = `${this.config.excelName}${timestamp.replace(" ", "")}`;
        let hasData = this.state.data && this.state.data.length > 0;
        console.log(`hasData = ${hasData}`);
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>{pageTitle}</h3>
                </div>
            </div>,
            <div className="row">
                <div className="col-md-12">
                    <AdminSchoolSelector ref="selector"/>
                </div>
            </div>,
            <div className="row">
                <div className="col-md-4"/>
                <div className="col-md-4">
                    <button className="btn btn-primary" onClick={this.retrieveData}>Descargar Reporte</button>
                </div>
                <div className="col-md-4"/>
            </div>
            ,
            hasData ?
                (<div className="row text-center">
                    <Workbook ref="excel" filename={`${reportName}.xlsx`} element={() => {
                    }}>
                        {this.determineReportType()}
                    </Workbook>
                </div>)
                : (null)
        ];
    }

    determineReportType() {
        let reportLayout = (this.baseLayout(this.state.data, this.config.excelName));
        if (this.type === "NEW_ENTRY_ENROLLMENT")
            reportLayout = (this.newEntryLayout(this.state.data, this.config.excelName));

        if(this.type === "VOCATIONAL_ENROLLMENT")
            reportLayout = (this.occupationalLayout(this.state.data, this.config.excelName));

        if(this.type === "PENDING_ENROLLMENT")
            reportLayout = (this.pendingLayout(this.state.data, this.config.excelName));

        if(this.type === "TRANSPORTATION_REQUESTED")
            reportLayout = (this.transportationLayout(this.state.data, this.config.excelName));

        return (reportLayout)
    }

    transportationLayout(data, sheetName) {
        return (<Workbook.Sheet data={data} name={sheetName}>
            <Workbook.Column label="Número Estudiante SIE" value="studentNumber"/>
            <Workbook.Column label="Nombre" value="name"/>
            <Workbook.Column label="Edad" value="age"/>
            <Workbook.Column label="Región" value="region"/>
            <Workbook.Column label="Municipio" value="city"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Grado" value="gradeLevel"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Direccion Física" value="addressFormatted"/>

        </Workbook.Sheet>)
    }

    pendingLayout(data, sheetName) {
        return (<Workbook.Sheet data={data} name={sheetName}>
            <Workbook.Column label="Número Estudiante SIE" value="studentNumber"/>
            <Workbook.Column label="Nombre" value="name"/>
            <Workbook.Column label="Edad" value="age"/>
            <Workbook.Column label="Región" value="region"/>
            <Workbook.Column label="Municipio" value="city"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Grado" value="gradeLevel"/>
            <Workbook.Column label="Escuela de Procedencia" value="originSchool"/>

        </Workbook.Sheet>)
    }

    baseLayout(data, sheetName) {
        return (<Workbook.Sheet data={data} name={sheetName}>
            <Workbook.Column label="Número Estudiante SIE" value="studentNumber"/>
            <Workbook.Column label="Nombre" value="name"/>
            <Workbook.Column label="Edad" value="age"/>
            <Workbook.Column label="Región" value="region"/>
            <Workbook.Column label="Municipio" value="city"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Grado" value="gradeLevel"/>
            <Workbook.Column label="Email del Padre/Madre" value="email"/>
            <Workbook.Column label="Escuela de Procedencia" value="originSchool"/>
            

        </Workbook.Sheet>)
    }

    occupationalLayout(data, sheetName) {
        return (<Workbook.Sheet data={data} name={sheetName}>
            <Workbook.Column label="Número Estudiante SIE" value="studentNumber"/>
            <Workbook.Column label="Nombre" value="name"/>
            <Workbook.Column label="Edad" value="age"/>
            <Workbook.Column label="Región" value="region"/>
            <Workbook.Column label="Municipio" value="city"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Grado" value="gradeLevel"/>
            <Workbook.Column label="Programa" value="program"/>
            <Workbook.Column label="Email del Padre/Madre" value="email"/>
            <Workbook.Column label="Escuela de Procedencia" value="originSchool"/>
        </Workbook.Sheet>)
    }

    newEntryLayout(data, sheetName) {
        return (<Workbook.Sheet data={data} name={sheetName}>
            {/*condiconado a si tiene rol de SIE*/}
            <Workbook.Column label="Nombre" value="name"/>
            <Workbook.Column label="Edad" value="age"/>
            <Workbook.Column label="Región" value="region"/>
            <Workbook.Column label="Municipio" value="city"/>
            <Workbook.Column label="Escuela" value="school"/>
            <Workbook.Column label="Grado" value="gradeLevel"/>
            <Workbook.Column label="Email del Padre/Madre" value="email"/>
            <Workbook.Column label="Escuela de Procedencia" value="originSchool"/>
        </Workbook.Sheet>)
    }

    loadConfig(type) {
        this.config = null;
        let excelName = "";
        let pageTitle = "";
        switch (type) {
            case "CONFIRMED":
                excelName = "Confirmados";
                pageTitle = "Estudiantes Confirmados";
                break;
            case "DENIED":
                excelName = "Denegados";
                pageTitle = "Estudiantes Rechazados";
                break;
            case "ALTERNATE_ENROLLMENT":
            case "ALTERNATE":
                excelName = "MatriculaAlterna";
                pageTitle = "Estudiantes con Matriculas Alternas";
                break;
            case "PENDING_ENROLLMENT":
            case "PENDING":
                excelName = "PendientesPorMatricular";
                pageTitle = "Estudiantes Pendientes por Matricular";
                break;
            case "NEW_ENTRY_ENROLLMENT":
            case "NEW":
                excelName = "NuevosIngresos";
                pageTitle = "Estudiantes Nuevo Ingresos";
                break;
            case "INCOMPLETE_ENROLLMENT":
                excelName = "MatriculasIncompletas";
                pageTitle = "Estudiantes con Matricula Incompleta";
                break;
            case "VOCATIONAL_ENROLLMENT":
                excelName = "MatriculasOcupacionales";
                pageTitle = "Estudiantes ocupacionales";
                break;
            case "TRANSPORTATION_REQUESTED":
                excelName = "SolicitandoTransportacion";
                pageTitle = "Estudiantes Solicitando Transportación";
                break;


        }
        this.config = {type: type, excelName: excelName, pageTitle: pageTitle};
    }
}