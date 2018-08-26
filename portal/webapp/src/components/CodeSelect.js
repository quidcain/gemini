/**
 * Created by fran on 1/29/18.
 */
import React, {Component} from "react";
import Codes from "./data/index";
import PropTypes from "prop-types";
import * as UIHelper from "../UIHelper";

class CodeSelect extends Component {

    constructor(props) {
        super(props);
        this.state = {value: "-1", selectedIndex: 0};
        this.onChange = this.onChange.bind(this);
    }

    componentDidMount() {
        this.populateValue(this.props.value);
    }

    componentWillReceiveProps(nextProps) {
        this.populateValue(nextProps.value)
    }

    populateValue(value) {
        if (value) {
            let selectedIndex = 0;
            let options = this.refs.codeSelect.options;
            for (let idx in options) {
                if (options[idx].value === value) {
                    selectedIndex = idx;
                    break;
                }
            }
            this.setState({value: value, selectedIndex: selectedIndex})
        }
    }

    onChange(e) {
        e.persist();
        let value = e.target.value;
        this.setState({value: value, selectedIndex: e.target.selectedIndex}, () => {
            if (this.props.onChange)
                this.props.onChange(e)
        });

    }

    getRawObject() {
        return this.codes[this.state.selectedIndex - 1]
    }

    valid() {
        let selectedIndex = this.state.selectedIndex;
        return selectedIndex > 0;
    }

    componentWillMount() {
        switch (this.props.codeType) {
            case "contactTypes":
                this.codes = Codes.contactTypes;
                break;
            case "countries":
                this.codes = Codes.countries;
                break;
            case "disabilityCodes":
                this.codes = Codes.disabilityCodes;
                break;
            case "educationLevels":
                this.codes = Codes.educationLevels;
                break;
            case "ethnicCodes":
                this.codes = Codes.ethnicCodes;
                break;
            case "gender":
                this.codes = Codes.gender;
                break;
            case "languageCodes":
                this.codes = Codes.languageCodes;
                break;
            case "maritalStatuses":
                this.codes = Codes.maritalStatuses;
                break;
            case "municipios":
                this.codes = Codes.municipios;
                break;
            case "relationTypes":
                this.codes = Codes.relationTypes;
                break;
            case "residentialStatus":
                this.codes = Codes.residentialStatus;
                break;
            case "states":
                this.codes = Codes.states;
                break;
            case "transportationTypes":
                this.codes = Codes.transportationTypes;
                break;
            case "foodOptions":
                this.codes = Codes.foodOptions;
                break;
            case "medicalConditions":
                this.codes = Codes.medicalConditions;
                break;
            case "jobTypes":
                this.codes = Codes.jobTypes;
                break;
            case "incomeTypes":
                this.codes = Codes.incomeTypes;
                break;
            case "registrationRelations":
                this.codes = Codes.registrationRelations;
                break;
        }
    }

    render() {
        let formGroupCss = this.props.grouped ? "form-group has-feedback".concat(" group ") : "form-group has-feedback";
        let elementProps = Object.assign({}, this.props);
        delete elementProps.codeType;
        let validHtml = UIHelper.toggleFieldValidHtml(this.valid(), this.props.required);
        let styleCss = this.props.grouped ? {height: 50} : null;
        return (
            <div className={formGroupCss}>
                <select ref="codeSelect" className="inputMaterial"
                        {...elementProps}
                        required
                        style={styleCss}
                        onChange={this.onChange}
                        value={this.state.value}>
                    <option value="-1">{this.props.placeholder || "Seleccione"}</option>
                    {this.codes.map((code, i) => (
                        <option key={i} value={code.value}>{code.description}</option>
                    ))}
                </select>
                <span className="highlight"/>
                <span className="bar"/>
                <label htmlFor={this.props.id}>{this.props.label}{validHtml}</label>
            </div>);
    }
}

CodeSelect.propTypes = {
    codeType: PropTypes.string
};


export default CodeSelect;


