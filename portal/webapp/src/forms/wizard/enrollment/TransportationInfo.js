/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import CodeSelect from "../../components/CodeSelect";
import TextInput from "../../components/TextInput";

export default class TransportationInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {form: {}};
    }

    render() {
        let form = this.state.form;
        return (<form>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="transportationType"
                                onChange={(e) => {
                                    this.setState({
                                        form: {
                                            transportationType: e.target.value
                                        }
                                    });
                                }}
                                codeType="transportationTypes"
                                label="Tipo de transportaci&oacute;n"
                                placeholder="Seleccione su tipo de transaportacion"/>
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <label>Transportaci&oacute;n del Estudiante</label>
                        <br/>
                        <label className="radio-inline">
                            <input type="radio" name="studentTransportation" id="transportationNeed"
                                   checked={form.transportationType && form.transportationType !== 'PRVD'} disabled/>Necesita
                            Transportacion
                        </label>
                        <label className="radio-inline">
                            <input type="radio" name="studentTransportation" id="haveTransportation"
                                   checked={form.transportationType && form.transportationType === 'PRVD'}
                                   disabled/> Posee
                            Transportacion
                        </label>
                    </div>
                </div>
            </div>

            <div className="row">
                <div className="col-md-6">
                    <TextInput id="transportationDistance"
                               type="number"
                               placeholder="Distancia en Millas"/>
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <label>Necesidad del Estudiante</label>
                        <br/>
                        <label className="checkbox-inline">
                            <input type="checkbox" id="studentSpecialNeed"/> Requiere transportacion Especial
                        </label>
                    </div>
                </div>
            </div>
        </form>);
    }
}
