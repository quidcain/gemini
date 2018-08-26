import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader, Button} from "reactstrap";
import swal from 'sweetalert';
import {services} from "../../../../setup/store"
import TextInput from "../../../../components/TextInput";

export default class TransferRequestModal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false,
            email: null
        };
        this.close = this.close.bind(this);
        this.transfer = this.transfer.bind(this);
        this.inputHandler = this.inputHandler.bind(this);
    }

    open(resultObj, action) {
        this.onCloseAction = action;
        this.params = resultObj;
        this.setState({modal: true, email: null});
    }

    close() {
        this.setState({modal: false, email: null}, () => {
            if (this.onCloseAction)
                this.onCloseAction();
        });
    }

    inputHandler(e) {
        let element = e.target;
        this.state[element.id] = element.value;
    }


    render() {
        let params = this.params || {};
        let iconName = this.state.userExists ? "time-circle" : "check-circle";
        let iconColor = this.state.userExists ? "green" : "red";

        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}>Transferencia de Solicitud</ModalHeader>
                <ModalBody>
                    <div className="row">
                        <div className="col-md-4">
                            Email actual:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{params.email}</span>
                        </div>
                    </div>

                    <div className="row pt-2">
                        <div className="col-md-4">
                            Num. Estudiante:
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

                    <div className="row pt-3">
                        <div className="col-md-12">
                            <TextInput id="email"
                                       type="email"
                                       ref="email"
                                       label="Cuenta de Email Nueva"
                                       onChange={this.inputHandler}
                                       value={this.state.email}
                                       grouped/>
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
        return (<Button color="success" onClick={this.transfer}>Transferir&nbsp;</Button>);
    }

    transfer() {
        if (!this.refs.email.valid()) {
            swal("Debe ingresar un email valido, formato user@domain.com", {icon: "warning"});
            return;
        }

        if (this.refs.email.valid() && this.state.email.toUpperCase() === this.params.email.toUpperCase()) {
            swal(`Debe seleccionar un email distinto, solicitud ya está asociada a ${this.params.email}`, {icon: "warning"});
            return;
        }

        swal({
            title: "Estás Seguro?",
            content: {
                element: "span",
                attributes: {
                    innerHTML: `Desea mover este <b>${this.params.studentName}</b> a la cuenta de <b>${this.state.email}</b>?`
                }
            },
            icon: "warning",
            buttons: true,
            dangerMode: true,
        })
            .then((canSave) => {
                if (canSave) {
                    //saving
                    let requestObj = {
                        email: this.params.email,
                        requestId: this.params.requestId,
                        emailNew: this.state.email
                    };

                    services()
                        .transferRequests(requestObj)
                        .then((response) => response.json())
                        .then((response) => {
                            if (response.success) {
                                swal("La transferencia fue efectuada exitosamente!!!", {icon: "success"})
                                    .then(() => {
                                        this.close();
                                    });
                            }
                            else
                                swal({
                                    title: "Ha ocurrido un error",
                                    text: `${response.message}`,
                                    icon: "error",
                                    button: "Ok",
                                });


                        })
                }
            });

    }
}