import React, {Component} from "react";
import InputMask from 'react-input-mask';
import * as UIHelper from "../UIHelper";

let ssnRegex = /^(?!219-09-9999|078-05-1120)(?!666|000|9\d{2})\d{3}-(?!00)\d{2}-(?!0{4})\d{4}$/;
export default class SocialSecurityInput extends Component {

    static defaultProps = {
        showFormat: true,
    };

    constructor(props) {
        super(props);
        this.state = {valid: false, pristine: true, value: null, displayValue: null, focusLost: props.mask};
        this.inputHandler = this.inputHandler.bind(this);
        this.onFocus = this.onFocus.bind(this);
        this.onBlur = this.onBlur.bind(this);
        this.editing = false;
    }

    componentWillReceiveProps(nextProps) {
        this.populateSSN(nextProps.value)
    }

    componentDidMount() {
        this.populateSSN(this.props.value)
    }

    onFocus(e) {
        let value = this.state.value;
        this.setState({...this.state, focusLost: false, displayValue: value});

    }

    onBlur(e) {
        this.setState({...this.state, focusLost: true, displayValue: this.maskValue(this.state.value)});
    }

    maskValue(value){
        let display = "";
        if (value && value.length === 11) {
            display = `XXX-XX-${ value.substr(value.length - 4)}`;
        }
        return display;
    }


    valid() {
        return this.state.valid;
    }

    populateSSN(value) {
        if (value) {
            let display = this.maskValue(value);
            this.setState({valid: ssnRegex.test(value), value: value, displayValue: display})
        }
    }

    checkValid(value) {
        let valid = ssnRegex.test(value);
        return {valid: valid}
    }

    inputHandler(e) {
        e.persist();
        this.editing = true;
        let form = this.state;
        let element = e.target;
        form.pristine = false;

        let result = this.checkValid(element.value);
        form.valid = result.valid;
        form.value = element.value;
        form.displayValue = element.value;
        this.setState({...form}, () => {
            this.editing = false;
            if (this.props.onChange) {
                this.props.onChange(e);
            }
        });


    }

    render() {
        let format = "(999-99-9999)";
        let props = Object.assign({}, this.props);
        if (props.showFormat)
            delete props.showFormat;
        let validHtml = UIHelper.toggleFieldValidHtml(this.state.valid, this.props.required);
        let cssClass = "inputMaterial";
        cssClass = cssClass.concat(props.disabledBlock ? " block-input " : "");

        return <div className="form-group has-feedback">
            <InputMask {...props}
                       style={{paddingLeft: 10}}
                       onChange={this.inputHandler}
                       onBlur={this.onBlur}
                       onFocus={this.onFocus}
                       className={cssClass}
                       mask={this.state.focusLost ? "XXX-XX-9999" : "999-99-9999"}
                       maskChar=" "
                       required
                       value={this.state.focusLost ? this.state.displayValue : this.state.value}/>
            {/*<i className="n fa fa-birthday-cake"/>*/}
            <span className="highlight"/>
            <span className="bar"/>
            <label style={{left: 10}} htmlFor={this.props.id}>{`${this.props.label} ${format}`}{validHtml}</label>
        </div>;
    }
}