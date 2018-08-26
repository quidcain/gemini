import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import CodeSelect from "../../../components/CodeSelect";
import {saveProfile} from "../../../redux/actions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import SimpleDateInput from "../../../components/SimpleDateInput";
import AnimationHelper from "../../../components/AnimationHelper";
import CurrencyInput from "../../../components/CurrencyInput";
import * as UIHelper from "../../../UIHelper";


class UserInfoRequest extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.onValidDate = this.onValidDate.bind(this);
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
    }

    onValidDate(date) {
        let form = this.props.form;
        form.dateOfBirth = date;
    }


    onPress(onResult, onError) {
        let form = this.props.form;
        this.props.saveProfile(form, onResult, onError);
    }


    render() {
        let form = this.props.form;

        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description"><h2>{UIHelper.getText("userInfoRequestTitle")}</h2>
                    <div className="violet-line"></div>
                </div>
                <span>Ingrese la informaci&oacute;n requerida para completar su perfil.</span>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <form id="profile-form">
                    <div className="row plr15">
                        <div className="col-md-3">
                            <TextInput id="firstName" type="name"
                                       label="Nombre"
                                       ref="firstName"
                                       onChange={this.inputHandler}
                                       value={form.firstName}
                                       required
                                       grouped/>
                        </div>
                        <div className="col-md-4">
                            <TextInput id="middleName" type="name"
                                       label="Segundo Nombre"
                                       onChange={this.inputHandler}
                                       value={form.middleName}
                                       grouped/>
                        </div>
                        <div className="col-md-5">
                            <TextInput id="lastName"
                                       type="lastname"
                                       ref="lastName"
                                       label="Apellidos"
                                       onChange={this.inputHandler}
                                       value={form.lastName}
                                       required
                                       grouped/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <SimpleDateInput label="Fecha de Nac."
                                             ref="dob"
                                             onValidDate={this.onValidDate}
                                             onInvalidDate={this.onInvalidDate}
                                             value={form.dateOfBirth}
                                             required
                                             grouped/>
                        </div>
                        <div className="col-md-6">
                            <CodeSelect id="relationType"
                                        mandatory
                                        label="Seleccione relación con el estudiante"
                                        ref="registrationRelations"
                                        codeType="registrationRelations"
                                        value={form.relationType}
                                        onChange={this.inputHandler}
                                        required
                                        grouped/>
                        </div>
                    </div>
                    {this.props.footer}
                </form>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={profileIllustration} alt=""/></div>*/}
                <AnimationHelper type="girlsTable"/>
            </div>];
    }

}

function mapStateToProps(store) {
    return {
        form: store.profile.form
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({saveProfile}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(UserInfoRequest);