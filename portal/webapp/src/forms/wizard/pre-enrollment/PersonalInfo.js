/**
 * Created by fran on 1/25/18.
 */
import React, {Component} from "react";
import CodeSelect from "../../../components/CodeSelect";
import TextInput from "../../../components/TextInput";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import {loadPersonalInfo, savePreEnrollment} from "../../../redux/actions";
import SimpleDateInput from "../../../components/SimpleDateInput";
import AnimationHelper from "../../../components/AnimationHelper";
import SocialSecurityInput from "../../../components/SocialSecurityInput";
import ModalHelper from "../../../components/ModalHelper";
import * as UIHelper from "../../../UIHelper";

class PersonalInfo extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.onValidDate = this.onValidDate.bind(this);
    }

    componentWillMount() {
        this.props.loadPersonalInfo(() => {
        }, () => {
            this.refs.modal.open(UIHelper.getText("personalErrorTitle"), UIHelper.getText("personalPageLoadError"), () => {
                this.props.history.push("/home")
            });
        });
    }

    inputHandler(e) {
        let form = this.props.student;
        let element = e.target;
        form[element.id] = element.value;
    }

    onValidDate(date) {
        let form = this.props.student;
        form.dateOfBirth = date;
    }

    onPress(onResult, onError) {
        let form = this.props.student;
        this.props.savePreEnrollment(form, onResult, onError);
    }

    getDescriptionText() {
        let student = this.props.student;
        let studentExists = this.props.found || student.existsOnSie;
        if (studentExists) {
            return (<span className="f30slg">{UIHelper.getText("personalPageMessageOnFoundStart")}<span
                className="f30slb">{UIHelper.getText("personalPageMessageOnFoundHighlight")}</span> {UIHelper.getText("personalPageMessageOnFoundEnd")}</span>)
        }

        return (<span className="f30slg">{UIHelper.getText("personalPageMessageStart")}<span
            className="f30slb">{UIHelper.getText("personalPageMessageHighlight")}</span> {UIHelper.getText("personalPageMessageEnd")}</span>)
    }

    render() {
        let student = this.props.student;
        let studentExists = this.props.found || student.existsOnSie;
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description"><h2
                    className="f90sbg">{UIHelper.getText("personalPageQuestionNumber")}</h2>
                    <div className="violet-line"/>
                </div>
                {this.getDescriptionText()}
            </div>
            <div className="body d-flex flex-column justify-content-end ">
                <form>
                    <div className="row">
                        <div className="col-md-12">
                            {!studentExists
                                ? (<SocialSecurityInput id="ssn"
                                                        mask
                                                        label="Seguro Social"
                                                        value={student.ssn}
                                                        onChange={this.inputHandler}
                                                        required
                                                        disabled={studentExists}/>)
                                : <TextInput id="lastSsn"
                                             type="text"
                                             label="Últimos #4 SSN"
                                             value={student.lastSsnFormatted}
                                             onChange={this.inputHandler}
                                             disabled={studentExists}/>}
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-3 ">
                            <TextInput id="firstName" type="name" label="Nombre"
                                       value={student.firstName}
                                       onChange={this.inputHandler}
                                       required
                                       disabled={studentExists}/>
                        </div>
                        <div className="col-md-3">
                            <TextInput id="middleName" type="name"
                                       label="Segundo Nombre"
                                       value={student.middleName}
                                       onChange={this.inputHandler}
                                       disabled={studentExists}/>
                        </div>
                        <div className="col-md-6">
                            <TextInput id="lastName" type="lastname" label="Apellidos"
                                       value={student.lastName}
                                       onChange={this.inputHandler}
                                       required
                                       disabled={studentExists}/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <CodeSelect id="gender"
                                        label="Género"
                                        codeType="gender"
                                        value={student.gender}
                                        required
                                        grouped
                                        onChange={this.inputHandler}
                                        placeholder="Género"
                                        disabled={studentExists}
                            />

                        </div>
                        <div className="col-md-6">
                            <SimpleDateInput id="dateOfBirth"
                                             label="Fecha de Nac."
                                             required
                                             value={student.dateOfBirth}
                                             onValidDate={this.onValidDate}
                                             disabled={studentExists}/>
                        </div>
                    </div>
                </form>

                <div style={{marginTop: -90}}>
                    {this.props.footer}
                </div>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>, <ModalHelper ref="modal"/>];
    }

}


function mapStateToProps(store) {
    return {
        student: store.studentInfo.student,
        found: store.studentLookup.found
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({loadPersonalInfo, savePreEnrollment}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PersonalInfo);

