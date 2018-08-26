import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader, Button} from "reactstrap";
import swal from 'sweetalert';
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";
import {services} from "../../../../setup/store"

export default class ApprovalCentralModal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            selectedSchoolId: null,
        };
        this.close = this.close.bind(this);
        this.schoolChanged = this.schoolChanged.bind(this);
        this.approve = this.approve.bind(this);
    }

    open(resultObj, action) {
        this.onCloseAction = action;
        this.params = resultObj;
        this.setState({modal: true, selectedSchoolId: null});
    }

    close() {
        this.setState({modal: false, selectedSchoolId: null}, () => {
            if (this.onCloseAction) {
                this.onCloseAction();
            }
        });
    }

    schoolChanged(schoolObj) {
        this.setState({...this.state, selectedSchoolId: schoolObj.schoolId})
    }

    render() {
        let params = this.params || {};
        let schools = (this.params && this.params.schoolsSelected) ? this.params.schoolsSelected : [];
        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}>Aprobaci&oacute;n</ModalHeader>
                <ModalBody>
                    <div className="row">
                        <div className="col-md-4">
                            Num. SIE:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{params.sieStudentId}</span>
                        </div>
                    </div>

                    <div className="row pt-2">
                        <div className="col-md-4">
                            Estudiante:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{params.studentName}</span>
                        </div>
                    </div>

                    <div className="row pt-2">
                        <div className="col-md-4">
                            Grado:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{params.gradeLevel}</span>
                        </div>
                    </div>

                    <div className="row pt-2">
                        <div className="col-md-12">
                            Escuelas:
                        </div>
                    </div>

                    <div className="row pt-2">
                        <div className="col-md-12">
                            <RemoteCodeSelect id="schools"
                                              label="Escuelas"
                                              placeholder=""
                                              codes={schools}
                                              onObjectChange={this.schoolChanged}
                                              target="schoolId"
                                              display="displayName"
                                              value={this.state.selectedSchoolId}
                            />
                        </div>
                    </div>

                </ModalBody>
                <ModalFooter>
                    {this.renderFooter()}
                </ModalFooter>
            </Modal>
        </div>);
    }

    renderFooter() {
        return (<Button color="success" onClick={this.approve}>Aprobar&nbsp;</Button>);
    }

    approve() {
        if (!this.state.selectedSchoolId && this.state.selectedSchoolId <= 0) {
            swal("Debe seleccionar una escuela para realizar la aprobación", {icon: "warning"});
            return;
        }

        let requestObj = {requestId: this.params.requestId, schoolId: this.state.selectedSchoolId};

        services()
            .approveRequest(requestObj)
            .then((response) => {

                if (response.status === 200) {
                    swal("!!!Solicitud fue aprobada correctamente!!!", {icon: "success"})
                        .then(() => {
                            this.close();
                        });
                } else {
                    swal("!!!Ocurrio un error realizando la aprobación!!!", {icon: "error"});
                }

            })

    }
}