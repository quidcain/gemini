import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader, Button} from "reactstrap";
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";
import swal from 'sweetalert';


const reasons = [
    {code: 'EXCEED_CAPACITY', description: 'No hay cupo'},
    {code: 'NO_MEET_ADMISSION_REQUIREMENTS', description: 'No cumple con los criterios de admisión'},
    {code: 'OTHER', description: 'Otro'}
];

export default class ReasonToDenyModal extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.close = this.close.bind(this);
        this.reasonToDenyChanged = this.reasonToDenyChanged.bind(this);
        this.save = this.save.bind(this);

        this.state = {
            modal: false,
            reasonSelected: null,
            showComment: false,
            comment: null
        };
    }

    inputHandler(e) {
        let element = e.target;
        this.state.comment = element.value;
    }


    reasonToDenyChanged(reasonObj) {
        let showComment = reasonObj.code === 'OTHER';
        this.setState({...this.state, reasonSelected: reasonObj.code, showComment: showComment});
    }


    open(afterAction) {
        this.action = afterAction;
        this.setState({modal: true, reasonSelected: null, showComment: false});
    }

    close() {
        this.setState({modal: false, reasonSelected: null, showComment: false});
    }

    save(){

        if(!this.state.reasonSelected) {
            swal("!!!Debe escoger un razon para poder rechazar!!!", {icon: "error"});
            return;
        }

        this.setState({...this.state, modal: false}, () => {
            this.action(this.state.reasonSelected, this.state.comment);
        });
    }

    render() {


        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}>Indique la razón del rechazo</ModalHeader>
                <ModalBody>
                    <div className="row">
                        <div className="col-md-3">
                            Razón:
                        </div>
                        <div className="col-md-9">
                            <RemoteCodeSelect id="gradeLevel"
                                              label="Razón"
                                              placeholder=""
                                              codes={reasons}
                                              onObjectChange={this.reasonToDenyChanged}
                                              target="code"
                                              display="description"
                                              value={this.state.reasonSelected}
                            />
                        </div>
                    </div>

                    {this.state.showComment
                        ? (<div className="row" style={{padding: 10}}>
                            <div className="col-md-3">
                                Comentario:
                            </div>
                            <div className="col-md-9" style={{padding: 20}}>
                                <textarea id="comment" style={{height: 75, width: "100%"}}
                                          onChange={this.inputHandler}/>
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
            <Button color="success" onClick={this.save}>Guardar&nbsp;</Button>];
    }
}