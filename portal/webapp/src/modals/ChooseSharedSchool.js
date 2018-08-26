import React, {Component} from "react";
import {Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import Button from "../components/Button";
import * as UIHelper from "../UIHelper";
import RemoteCodeSelect from "../components/RemoteCodeSelect";
import {addSharedSchoolToPreEnrollment, getSharedSchools} from "../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";

class ChooseSharedSchool extends Component {

    constructor(props) {
        super(props);
        this.state = {
            schoolId: null,
            modal: false,
            errorMessage: "",
            message: "",
            schools: null
        };
        this.toggle = this.toggle.bind(this);
        this.open = this.open.bind(this);
        this.close = this.close.bind(this);
        this.doConfirm = this.doConfirm.bind(this);
        this.schoolChanged = this.schoolChanged.bind(this);
    }

    schoolChanged(schoolObject) {
        this.setState({
            ...this.state,
            schoolId: schoolObject.schoolId,
            schoolAddress: schoolObject.address,
            errorMessage: ""
        });
    }

    doConfirm() {
        let form = {requestId: this.requestId, schoolId: this.state.schoolId};
        this.props.addSharedSchoolToPreEnrollment(form, () => {
            this.setState({...this.state, message: "* Su preferencia fue guardada exitosamente"});
            setTimeout(() => {
                this.setState({...this.state, modal: false});
                this.action(true);
            }, 2000);
        }, () => {
            this.setState({...this.state, errorMessage: "* Ha ocurrido un error guardando su preferencia"});

        });
    }

    open(params, afterCloseAction) {
        this.action = afterCloseAction;
        this.requestId = params.requestId;
        this.studentName = params.studentName;
        this.props.getSharedSchools(params.gradeLevel, () => {
            this.setState({...this.state, modal: true, schools: this.props.schools}, () => {
                let addressFormatted =  params.sharedSchool ? params.sharedSchool.address : "";
                this.setState({...this.state, schoolId: params.schoolId, schoolAddress: addressFormatted});
            });
        });
    }

    close() {
        this.setState({modal: false, schoolId: null});
        this.action(false);
    }

    toggle() {
        this.setState({
            modal: !this.state.modal
        });
    }

    render() {
        let schools = this.state.schools;
        let schoolAddressLine = this.state.schoolAddress ? this.state.schoolAddress.addressFormatted : "";
        return (<div>
            <Modal isOpen={this.state.modal} toggle={this.toggle} className={this.props.className}
                   onClosed={this.close}>
                <ModalHeader toggle={this.close}>¿Escoja la Escuela 'Shared' donde desea matricular el
                    estudiante <b>{this.studentName}</b>?</ModalHeader>
                <ModalBody>
                    <div className="row">
                        <div className="col-md-12">
                            <RemoteCodeSelect id="schools"
                                              label="Escuela a shared matricular"
                                              placeholder="Escuela"
                                              codes={schools}
                                              target="schoolId"
                                              display="displayName"
                                              onObjectChange={this.schoolChanged}
                                              value={this.state.schoolId}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-1">
                            <i className="icon-gps mr5"/>
                        </div>
                        <div className="col-md-11">
                            <h6>{schoolAddressLine}</h6>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-12">
                            <span className={this.state.errorMessage ? "text-danger" : "text-success"}
                                  style={{fontWeight: "bold"}}>{this.state.errorMessage ? this.state.errorMessage : this.state.message}</span>
                        </div>
                    </div>
                </ModalBody>
                <ModalFooter>
                    <Button color="primary" onClick={this.close}>{UIHelper.getText("noButton")}{' '}</Button>
                    <Button color="secondary" onClick={this.doConfirm}>{UIHelper.getText("yesButton")}</Button>
                </ModalFooter>
            </Modal>
        </div>);
    }

}

function mapStateToProps(store) {
    return {
        schools: store.config.schools,
        preEnrollment: store.preEnrollment.info,
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({getSharedSchools, addSharedSchoolToPreEnrollment}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(ChooseSharedSchool);
