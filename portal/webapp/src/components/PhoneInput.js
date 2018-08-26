/**
 * Created by fran on 2/7/18.
 */
import React, {Component} from "react";
import InputMask from 'react-input-mask';

const phoneRegex = /^\([0-9]{3}\)-[0-9]{3}-[0-9]{4}$/;
export default class PhoneInput extends Component {

    constructor(props) {
        super(props);
        this.state = {valid: false};
        this.onChange = this.onChange.bind(this);
    }

    onChange(e) {
        let value = e.target.value;
        this.setState({valid: phoneRegex.test(value)});
        if (this.props.onChange)
            this.props.onChange(e);
    }

    render() {
        let groupCss = "form-group ".concat(!this.state.valid ? "has-error" : "");
        return <div className={groupCss}>
            <label htmlFor={this.props.id}>{this.props.label || this.props.placeholder}:</label>
            <InputMask {...this.props}
                       onChange={this.onChange}
                       className="form-control"
                       mask="(999)-999-9999"
                       maskChar=" "/>
        </div>
    }
}