import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader, Button} from "reactstrap";
import swal from 'sweetalert';
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import {services} from "../../../setup/store"
import {Workbook} from "react-excel-workbook";

const reports = [
    {code: 'ASIGNADOS', description: 'Asignados'},
    {code: 'VACANTES', description: 'Vacantes'},
    {code: 'EXCEDENTES', description: 'Excedentes'}
];

function getSheetName(code) {
    for (const report of reports) {
        if (report.code === code)
            return report.description;
    }
    return "Desconocido"
}


export default class ReportSelectorModal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            reportSelected: null,
            showMessage: false,
            message: null,
            data: []
        };

        this.reportChanged = this.reportChanged.bind(this);
        this.downloadReport = this.downloadReport.bind(this);
        this.close = this.close.bind(this);
    }

    open(params) {
        this.params = params;
        this.setState({modal: true, data: [], reportSelected: null, showMessage: false});
    }

    close() {
        this.setState({modal: false, data: [], reportSelected: null, showMessage: false});
    }

    reportChanged(reportObj) {
        this.setState({...this.state, data: [], reportSelected: reportObj.code});
    }

    getRestCall() {
        switch (this.state.reportSelected) {
            case "ASIGNADOS":
                return services().retrieveAsignados(this.params);
            case "VACANTES":
                return services().retrievesVacantes(this.params);
            case "EXCEDENTES":
                return services().retrieveExcedentes(this.params);
        }
    }

    downloadReport() {
        if (!this.state.reportSelected) {
            swal("!!!Debes seleccionar un tipo de informe!!!");
            return;
        }

        this.setState({...this.state, showMessage: true, message: "Descargando..."});

        this.getRestCall()
            .then((response) => response.json())
            .then((data) => {
                this.setState({...this.state, data: data, showMessage: false});
                if (data && data.length > 0)
                    this.refs.excel.download();
                else
                    swal("!!!No existen datos para generar el reporte!!!");

            });
    }

    render() {
        let hasData = this.state.data && this.state.data.length > 0;
        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}>Seleccione reporte</ModalHeader>
                <ModalBody>
                    <div className="row">
                        <div className="col-md-12">
                            <RemoteCodeSelect id="gradeLevel"
                                              label="Tipo informe"
                                              placeholder=""
                                              codes={reports}
                                              onObjectChange={this.reportChanged}
                                              target="code"
                                              display="description"
                                              value={this.state.reportSelected}
                            />
                        </div>
                    </div>

                    {hasData
                        ? (<div className="row text-center">
                            <Workbook ref="excel" filename={`${getSheetName(this.state.reportSelected)}.xlsx`}
                                      element={() => {
                                      }}>
                                <Workbook.Sheet data={this.state.data} name={getSheetName(this.state.reportSelected)}>
                                    <Workbook.Column label="Región" value="region"/>
                                    <Workbook.Column label="Municipio" value="city"/>
                                    <Workbook.Column label="NumeroDeEscuela" value="extSchoolNumber"/>
                                    <Workbook.Column label="Escuela" value="schoolName"/>
                                    <Workbook.Column label="Puesto" value="puesto"/>
                                    <Workbook.Column label="NumeroDeEmpleado" value="numEmpleado"/>
                                    <Workbook.Column label="NombreDeEmpleado" value="fullName"/>
                                </Workbook.Sheet>
                            </Workbook>
                        </div>)
                        : (null)}

                    {this.state.showMessage
                        ? (<div className="row">
                            <div className="col-md-12">
                                <span className="text-info">{this.state.message}</span>
                            </div>
                        </div>)
                        : (null)
                    }

                </ModalBody>
                <ModalFooter>
                    {this.renderFooter()}
                </ModalFooter>
            </Modal>
        </div>);
    }

    renderFooter() {
        return [<Button color="secondary" onClick={this.close}>Cancelar&nbsp;</Button>,
            <Button color="success" onClick={this.downloadReport}>Descargar&nbsp;</Button>];
    }
}