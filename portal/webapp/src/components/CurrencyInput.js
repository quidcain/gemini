import React, {Component} from "react";
import NumberFormat from "react-number-format";
import * as UIHelper from "../UIHelper";

const maxValueAllowed = "999,999,999,999.00";
export default class CurrencyInput extends Component {

    constructor(props) {
        super(props);
        this.state = {valid: false};
        this.onChange = this.onChange.bind(this);
        this.isAllowed = this.isAllowed.bind(this);
    }

    onChange(e) {
        let value = e.target.value;
        this.setState({valid: value.length > 0});
        if (this.props.onChange)
            this.props.onChange(e);
    }

    isAllowed(inputValue) {
        let limit = this.props.limit;
        if (inputValue && inputValue.value && limit) {
            return parseInt(inputValue.value) < limit
        }
        return true;
    }

    render() {
        let grouped = this.props.grouped ? "group" : "";
        let groupCss = grouped + " form-group has-feedback";

        let validHtml = UIHelper.toggleFieldValidHtml(this.state.valid, this.props.required);

        return (
            <div className={groupCss}>
                <NumberFormat {...this.props}
                              onChange={this.onChange}
                              maxLength={maxValueAllowed.length}
                              className="inputMaterial"
                              isAllowed={this.isAllowed}
                              decimalScale={2}
                              displayType='input'
                              thousandSeparator={true}
                              prefix={'$'}/>
                <span className="highlight"/>
                <span className="bar"/>
                <label
                    htmlFor={this.props.id}>{this.props.label || this.props.placeholder}{validHtml}</label>
            </div>);
    }
}