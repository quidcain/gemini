import React, {Component} from "react";
import CodeSelect from "../../../components/CodeSelect";
import AnimationHelper from "../../../components/AnimationHelper";
import Button from "../../../components/Button";
import {loadDemographics, saveDemographics} from "../../../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import * as UIHelper from "../../../UIHelper";

class PersonalAdditionalInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {selectedCode: null};
        this.addEthnicCode = this.addEthnicCode.bind(this);
        this.inputHandler = this.inputHandler.bind(this);
    }

    componentWillMount() {
        this.props.loadDemographics();
    }

    inputHandler(e) {
        let form = this.props.form;
        let element = e.target;
        form[element.id] = element.value;
    }


    onPress(onResult, onError) {
        let form = this.props.form;
        this.props.saveDemographics(form, onResult, onError);
    }

    addEthnicCode(e) {
        e.preventDefault();

        let form = this.props.form;
        let size = form.ethnicCodes.length || 0;
        let ethnicCode = this.refs.ethnicCodes.getRawObject();
        if (size < 5) {
            let object = {
                value: ethnicCode.value,
                description: ethnicCode.description
            };
            form.ethnicCodes.push(object);
            this.cleanEthnicCode()
        } else {
            alert(UIHelper.getText("personalAdditionalEthnicCodeValidation"))
        }
    }

    deleteEthnicCode = (index) => (e) => {
        e.preventDefault();

        let form = this.props.form;
        form.ethnicCodesToDelete = form.ethnicCodesToDelete || [];
        let ethnicCodeDeleted = form.ethnicCodes[index];
        form.ethnicCodesToDelete.push(ethnicCodeDeleted);
        form.ethnicCodes.splice(index, 1);
        this.forceUpdate();
    };

    cleanEthnicCode() {
        this.setState({selectedCode: null});
    }

    render() {
        let form = this.props.form;
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description"><h2
                    className="f90sbg">{UIHelper.getText("personalAdditionalPageQuestionNumber")}</h2>
                    <div className="violet-line"></div>
                </div>
                <span className="f30slg">
                    {UIHelper.getText("personalAdditionalPageMessageStart")}
                    <span className="f30slb">{UIHelper.getText("personalAdditionalPageMessageHighlight")}</span>
                    {UIHelper.getText("personalAdditionalPageMessageEnd")}
                </span>
            </div>
            <div className="body d-flex flex-column justify-content-end">
                <form>
                    <div className="row ">
                        <div className="col-md-6 ">
                            <CodeSelect id="citizenship"
                                        label="Cuidadanía"
                                        codeType="residentialStatus"
                                        value={form.citizenship}
                                        required
                                        onChange={this.inputHandler}
                                        placeholder=""
                            />
                        </div>
                        <div className="col-md-6">
                            <CodeSelect id="language"
                                        label="Idioma"
                                        codeType="languageCodes"
                                        value={form.language}
                                        required
                                        onChange={this.inputHandler}
                                        placeholder=""
                            />
                        </div>
                    </div>
                    <div className="row pt-4">
                        <div className="col-md-10">
                            <CodeSelect id="ethnicCodes"
                                        ref="ethnicCodes"
                                        label="Códigos Étnicos"
                                        codeType="ethnicCodes"
                                        required
                                        value={this.state.selectedCode}
                                        placeholder=""
                            />
                        </div>
                        <div className="col-md-2">
                            <Button color="primary" size="small" onClick={this.addEthnicCode}><i
                                className="fa fa-plus"/></Button>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12 tableScrollable">
                            {this.renderEthnicCodesSelected()}
                        </div>
                    </div>
                </form>
                <div style={{marginTop: -40}}>
                    {this.props.footer}
                </div>
            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>];
    }

    renderEthnicCodesSelected() {
        let form = this.props.form;
        let ethnicCodes = form.ethnicCodes;
        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Raza</th>
                    <th>Acci&oacute;n</th>
                </tr>
                </thead>
                <tbody>
                {ethnicCodes && ethnicCodes.length > 0
                    ? ethnicCodes.map((code, index) => (
                        <tr key={index}>
                            <td>{code.description}</td>
                            <td>
                                <Button size="sm" color="danger" onClick={this.deleteEthnicCode(index)}>
                                    <i className="fas fa-trash"/>
                                </Button>
                            </td>
                        </tr>
                    ))
                    : <tr>
                        <td colSpan={3}
                            style={{left: 50, top: 50}}>{UIHelper.getText("personalAdditionalNoEthnicCode")}</td>
                    </tr>}
                </tbody>
            </table>
        );
    }

}


function mapStateToProps(store) {
    return {
        form: store.studentInfo.demographics,
        found: store.studentLookup.found
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({loadDemographics, saveDemographics}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PersonalAdditionalInfo);
