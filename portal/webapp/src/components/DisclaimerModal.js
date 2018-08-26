import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader, Button} from "reactstrap";
import {saveAgreementAnswer} from "../redux/actions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";

class DisclaimerModal extends Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false
        };
        this.toggle = this.toggle.bind(this);
        this.open = this.open.bind(this);
        this.accept = this.accept.bind(this);
        this.deny = this.deny.bind(this);
        this.close = this.close.bind(this);
    }


    open(params, afterAction) {
        this.action = afterAction;
        this.params = params;
        this.setState({modal: true});
    }

    close() {
        this.setState({modal: false});
        if (this.action) {
            this.action(false);
        }
    }

    accept() {
        this.save(true);
    }

    deny() {
        this.save(false);
    }

    save(answer) {
        let requestObj = {studentId: this.params.studentId, answer: answer};
        this.props.saveAgreementAnswer(requestObj, () => {
            this.setState({modal: false}, () => {
                if (this.action) {
                    this.action(true);
                }
            });

        });
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    render() {
        let enableWhitespaceCss = this.enableWhitespace ? {whiteSpace: "pre"} : {};
        let footer = this.renderConfirmFooter();
        return (<div>
            <Modal size="lg"
                   isOpen={this.state.modal}
                   toggle={this.toggle}
                   className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}> Autorización de cuenta de correo electrónico para los estudiantes del
                    Departamento de Educación.</ModalHeader>
                <ModalBody style={enableWhitespaceCss}>
                    Como parte de los recursos de tecnología que el Departamento de educación ofrece a sus estudiantes,
                    se encuentra la asignación de una cuenta de correo electrónico para uso de cada uno de ellos. Esta
                    cuenta, además de ofrecerle los beneficios de un medio de comunicación electrónica, les da acceso a
                    licencias de Office 365 para uso personal hasta 15 equipos. Esto significa que que podrán instalar
                    el módulo de Office 365 en tabletas, laptops, computadoras personales y teléfonos con la utilización
                    de su cuenta. Así mismo, la cuenta de correo será utilizada para registrarse en otras aplicaciones
                    educativas, ejercicios de práctica y pruebas estandarizadas y de diagnóstico.
                    <br/><br/>
                    Todos y cada uno de nuestros estudiantes tiene asignada una cuenta de correo y todas las cuentas
                    serán activadas con el propósito de poder administrar las cuentas META, entre otros. El correo al
                    que tendrán acceso los niños es de naturaleza institucional.
                    <br/><br/>
                </ModalBody>
                <ModalFooter>
                    {footer}
                </ModalFooter>
            </Modal>
        </div>);
    }

    renderConfirmFooter() {
        return [<Button color="danger" onClick={this.deny}>No Autorizo</Button>,
            <Button color="success" onClick={this.accept}>Autorizo</Button>];
    }
}


function mapDispatchToActions(dispatch) {
    return bindActionCreators({saveAgreementAnswer}, dispatch)
}

export default connect(null, mapDispatchToActions, null, {withRef: true})(DisclaimerModal);

