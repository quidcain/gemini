import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import Button from "./Button";
import * as UIHelper from "../UIHelper";

export default class ModalHelper extends Component {

    constructor(props) {
        super(props);
        this.state = {
            type: "info",
            modal: false,
            title: null,
            message: null
        };
        this.toggle = this.toggle.bind(this);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.doConfirm = this.doConfirm.bind(this);
    }

    confirm(title, messages, confirm, onClose, enableWhitespace = false) {
        this.confirmAction = confirm;
        this.onClose = onClose;
        this.enableWhitespace = enableWhitespace;
        this.setState({modal: true, title: title, message: messages, type: "confirm"});
    }

    doConfirm() {
        if (this.confirmAction) {
            setTimeout(() => {
                this.confirmAction();
            }, 300);
        }

        this.setState({modal: false, title: null, message: null});
    }

    open(title, messages, afterCloseAction, enableWhitespace = false) {
        this.action = afterCloseAction;
        this.enableWhitespace = enableWhitespace;
        this.setState({modal: true, title: title, message: messages, type: "info"});
    }

    close() {
        if (this.action) {
            setTimeout(() => {
                this.action();
            }, 300);
        }

        if (this.onClose) {
            setTimeout(() => {
                this.onClose()
            }, 300);
        }


        this.setState({modal: false, title: null, message: null});
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    render() {
        let footer;
        let enableWhitespaceCss = this.enableWhitespace ? {whiteSpace: "pre"} : {};
        switch (this.state.type) {
            case 'confirm':
                footer = this.renderConfirmFooter();
                break;
            default:
                footer = this.renderInfoFooter();
        }

        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className} onClosed={this.close}>
                <ModalHeader toggle={this.close}>{this.state.title}</ModalHeader>
                <ModalBody style={enableWhitespaceCss}>
                    {this.state.message}
                </ModalBody>
                <ModalFooter>
                    {footer}
                </ModalFooter>
            </Modal>
        </div>);
    }

    renderInfoFooter() {
        return (<Button color="secondary" onClick={this.close}>OK</Button>)
    }

    renderConfirmFooter() {
        return [<Button color="primary" onClick={this.close}>{UIHelper.getText("noButton")}{' '}</Button>,
            <Button color="secondary" onClick={this.doConfirm}>{UIHelper.getText("yesButton")}</Button>];
    }
}
