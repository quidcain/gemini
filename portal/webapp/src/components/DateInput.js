/**
 * Created by fran on 2/7/18.
 */
import React, {Component} from "react";
import InputMask from 'react-input-mask';
import moment from "moment";

export default class DateInput extends Component {

    static defaultProps = {
        showFormat: true,
    };

    constructor(props) {
        super(props);
        this.state = {day: '', month: '', year: '', valid: false, pristine: true, value: null};
        this.inputHandler = this.inputHandler.bind(this);
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
                form.day = date.format("DD");
                form.month = date.format("MM");
                form.year = date.format("YYYY");
                let result = this.checkValid();
                form.valid = result.valid;
                form.value = result.value;
                this.setState({...form});
            }
        }
    }

    checkValid() {
        let form = this.state;
        let valid = moment(`${form.day || '99'}-${form.month || '99'}-${form.year || '9999'}`, "DD-MM-YYYY").isValid() && parseInt(form.year || '0') >= 1900;
        let value = valid ? moment(`${form.day || '99'}-${form.month || '99'}-${form.year || '9999'}`, "DD-MM-YYYY") : null;
        return {valid: valid, value: value}
    }

    inputHandler(e) {
        // e.persist();
        this.editing = true;
        let form = this.state;
        let element = e.target;
        let id = element.id;
        form[id] = element.value;
        form.pristine = false;

        let result = this.checkValid();
        form.valid = result.valid;
        form.value = result.value;
        this.setState({...this.state}, () => {
            this.editing = false;
            if (this.props.onValidDate && form.valid) {
                this.props.onValidDate(form.value.toDate());
            } else {
                if (this.props.onInvalidDate)
                    this.props.onInvalidDate();
            }
        });


    }

    render() {
        let format = this.props.showFormat ? "(dd/mm/yyyy)" : "";
        // if (this.state.valid || this.props.value) {
        //     let date = moment(this.state.value || this.props.value);
        //     let day = date.format("DD");
        //     let month = date.format("MMMM");
        //     let year = date.format("YYYY");
        //     format = `(${day}/${month}/${year})`;
        // }
        return <div className="form-group">
            <label htmlFor={this.props.id}>{`${this.props.label} ${format}:`}</label>
            {this.renderDateInput()}
        </div>;
    }

    renderDateInput() {
        let formGroup = "group form-group has-feedback".concat(!this.state.valid ? "has-error" : "");
        let props = {disabled: this.props.disabled};
        return (
            <div className="row ">
                <div className="col-xs-4" style={{textAlign: "center", paddingRight: 0}}>
                    <div className={formGroup} style={{display: "inline-flex"}}>
                        <InputMask {...props}
                                   mask="99" maskChar=" " placeholder="Dia" className="inputMaterial" id="day"
                                   value={this.state.day}
                                   onChange={this.inputHandler}/>
                        <div style={{textAlign: "center", paddingLeft: 10}}>
                            <span style={{fontSize: "xx-large"}}>/</span>
                        </div>

                    </div>
                </div>

                <div className="col-xs-4" style={{textAlign: "center", paddingRight: 0}}>
                    <div className={formGroup} style={{display: "inline-flex"}}>
                        <InputMask {...props}
                                   mask="99" maskChar=" " placeholder="Mes" className="inputMaterial" id="month"
                                   value={this.state.month}
                                   onChange={this.inputHandler}/>
                        <div style={{textAlign: "center", paddingLeft: 10}}>
                            <span style={{fontSize: "xx-large"}}>/</span>
                        </div>
                    </div>
                </div>

                <div className="col-xs-4">
                    <div className={formGroup}>
                        <InputMask {...props}
                                   mask="9999" maskChar=" " className="inputMaterial" id="year" placeholder="Año"
                                   value={this.state.year}
                                   onChange={this.inputHandler}/>
                    </div>
                </div>
            </div>
        );
    }
}