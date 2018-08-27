/**
 * Created by fran on 1/25/18.
 */
import React, {Component} from "react";
import moment from "moment";
import CodeSelect from "../../components/CodeSelect";
import DateInput from "../../components/DateInput";

const PRCode = '1790';
const EUCode = '2310';
const USCitizenship = [PRCode, EUCode];
export default class Demographic extends Component {

    constructor(props) {
        super(props);
        this.state = {form: {startDate: moment(), immigrantYears: 0}};
        this.handleChange = this.handleChange.bind(this);
        this.onValidDate = this.onValidDate.bind(this);

    }

    onValidDate(date) {
        // this.setState({
        //     startDate: date
        // });
        // this.state.form.startDate = e.target.value;
        let form = this.state.form;
        // state.form.startDate = date;
        form.immigrantYears = moment().diff(date, 'years');
        form.startDate = date;
        this.setState(form)
    }

    handleChange(event) {
        let form = this.state.form;
        this.setState({
            form: {
                ...form,
                country: event.target.value
            }
        });

    }


    render() {
        let form = this.state.form;
        let isAmerican = USCitizenship.indexOf(form.country) > -1;
        return (<form>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="country"
                                label="Pais de Nacimiento"
                                value={form.country}
                                onChange={this.handleChange}
                                placeholder="Seleccione su Pais de nacimiento"
                                codeType="countries"/>
                </div>
                <div className="col-md-6">
                    <div className="form-group" style={{paddingTop: 10}}>
                        <br/>
                        <label className="radio-inline"><input type="radio" name="bornPR"
                                                               checked={form.country && form.country === PRCode}
                                                               disabled={true}/>Nacio en
                            PR</label>
                        <label className="radio-inline"><input type="radio" name="bornPR"
                                                               checked={form.country && form.country !== PRCode}
                                                               disabled={true}/>Otro
                            Pais</label>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="ethnicCode"
                                label="Codigo Etnico"
                                placeholder="Seleccione su Codigo Etnico"
                                codeType="ethnicCodes"/>
                </div>
                <div className="col-md-4">
                    <DateInput label="Fecha de Inmigracion"
                               value={form.startDate}
                               onValidDate={this.onValidDate}
                               disabled={isAmerican}/>
                </div>

                <div className="col-md-2">
                    <div className="form-group">
                        <label htmlFor="immigrantYears">A&ntilde;os de Inmigrante</label>
                        <input type="text"
                               className="form-control"
                               id="immigrantYears"
                               placeholder=""
                               disabled={true}
                               value={form.immigrantYears}/>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="residentialStatus"
                                label="Estatus Residencial"
                                placeholder="Seleccione su Estatus Residencial"
                                codeType="residentialStatus"/>
                </div>
                <div className="col-md-6">
                    <CodeSelect id="liveWith"
                                label="Vive con"
                                placeholder="Seleccione vive con"
                                codeType="relationTypes"/>
                </div>
            </div>
        </form>);
    }
}
