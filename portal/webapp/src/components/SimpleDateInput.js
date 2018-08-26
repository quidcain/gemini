/**
 * Created by fran on 2/7/18.
 */
import React, {Component} from "react";
import InputMask from 'react-input-mask';
import moment from "moment";
import * as UIHelper from "../UIHelper";

const EMPTY_DATE = "  /  /    ";
export default class SimpleDateInput extends Component {

    static defaultProps = {
        showFormat: true,
    };

    constructor(props) {
        super(props);
        this.state = {dateString: "", valid: false, pristine: true, value: null, displayFormat: false};
        this.inputHandler = this.inputHandler.bind(this);
        this.onFocus = this.onFocus.bind(this);
        this.onBlur = this.onBlur.bind(this);
        this.editing = false;
    }

    componentWillReceiveProps(nextProps) {
        this.populateDate(nextProps.value)
    }

    componentDidMount() {
        this.populateDate(this.props.value)
    }

    valid() {
        return this.state.valid;
    }

    populateDate(date) {
        if (date) {
            let form = this.state;
            date = moment(date);
            if (date.format) {
                form.dateString = date.format("DD/MM/YYYY");
                let result = this.checkValid();
                form.valid = result.valid;
                form.value = result.value;
                this.setState({...form});
            }
        }
    }

    checkValid() {
        let form = this.state;
        let formatted = (form.dateString && form.dateString.replace(/\s/g, '')) || 'empty';
        let valid = moment(formatted, "DD/MM/YYYY").isValid() && formatted.length === 10;
        let value = valid ? moment(formatted, "DD/MM/YYYY") : null;
        return {valid: valid, value: value}
    }

    inputHandler(e) {
        // e.persist();
        this.editing = true;
        let form = this.state;
        let element = e.target;
        form.dateString = element.value;
        form.pristine = false;

        console.log(`value = ${element.value} = ${EMPTY_DATE === element.value}`);

        let result = this.checkValid();
        form.valid = result.valid;
        form.value = result.value;
        this.setState({...this.state}, () => {
            this.editing = false;
            if (this.props.onValidDate && form.valid) {
                this.props.onValidDate(form.value.toDate());
            } else {
                if (this.props.onCleanDate && element.value === EMPTY_DATE)
                    this.props.onCleanDate();
            }
        });


    }

    onFocus(){
        this.setState({...this.state, displayFormat: true})
    }

    onBlur(){
        this.setState({...this.state, displayFormat: false})
    }

    render() {
        let format = this.state.displayFormat ? "(dd/mm/yyyy)" : "";
        let props = Object.assign({}, this.props);
        if (props.showFormat)
            delete props.showFormat;
        if (props.onValidDate)
            delete props.onValidDate;
        let state = this.state;
        let age = `${moment().diff(state.value, 'years')} años`;
        let validHtml = UIHelper.toggleFieldValidHtml(this.state.valid, this.props.required);
        return <div className="group form-group has-feedback">
            <InputMask {...props}
                       onChange={this.inputHandler}
                       className="inputMaterial"
                       mask="99/99/9999"
                       maskChar=" "
                       onFocus={this.onFocus}
                       onBlur={this.onBlur}
                       required
                       value={this.state.dateString}/>

            {/*fa fa-birthday-cake*/}
            <i className="n fa fa-birthday-cake"/>
            <span className="highlight"/>
            <span className="bar"/>
            <label
                htmlFor={this.props.id}>{`${this.props.label} ${state.valid ? `- ${age} ` : format}`}{validHtml}</label>
        </div>;
    }
}