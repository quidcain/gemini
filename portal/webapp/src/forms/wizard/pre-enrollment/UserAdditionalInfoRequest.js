import React, {Component} from "react";
import TextInput from "../../../components/TextInput";
import {completeProfile} from "../../../redux/actions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import CurrencyInput from "../../../components/CurrencyInput";
import AnimationHelper from "../../../components/AnimationHelper";
import CodeSelect from "../../../components/CodeSelect";
import * as UIHelper from "../../../UIHelper";

class UserAdditionalInfoRequest extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
    }

    onPress(onResult, onError) {
        let form = this.props.form;
        this.props.completeProfile(form, onResult, onError);
    }


    render() {
        let form = this.props.form;

        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description"><h2>{UIHelper.getText("userAdditionalInfoRequestTitle")}</h2>
                    <div className="violet-line"></div>
                </div>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <form id="profile-form">
                    <div className="row">
                        <div className="col-md-6">
                            <CurrencyInput id="income"
                                           ref="income"
                                           label="Ingreso Anual Familiar"
                                           onChange={this.inputHandler}
                                           value={form.income}
                                           limit={250001}
                                           required
                                           grouped/>
                        </div>
                        <div className="col-md-6">
                            <TextInput id="totalFamilyMembers"
                                       type="totalMembers"
                                       ref="totalFamilyMembers"
                                       label="Total de Miembros de la Familia"
                                       onChange={this.inputHandler}
                                       alignCenter
                                       value={form.totalFamilyMembers}
                                       max={15}
                                       required
                                       grouped/>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <CodeSelect id="educationLevel"
                                        label="Escolaridad"
                                        codeType="educationLevels"
                                        value={form.educationLevel}
                                        onChange={this.inputHandler}
                                        placeholder=""
                                        grouped
                            />
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
    return bindActionCreators({completeProfile}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(UserAdditionalInfoRequest);