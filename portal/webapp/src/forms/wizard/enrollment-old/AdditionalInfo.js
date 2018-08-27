/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import CodeSelect from "../../components/CodeSelect";
import TextInput from "../../components/TextInput";

export default class AdditionalInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {form: {healthPlanDisabled: true}};
        this.onClick = this.onClick.bind(this);
    }

    onClick(e) {
        this.setState({form: {healthPlanDisabled: !e.target.checked}});
    }

    render() {
        return (<form>
            <div className="row">
                <div className="col-md-6">
                    <div className="form-group">
                        <label htmlFor="insuranceHealthPlan">Seguro Medico:</label>
                        <div className="input-group">
                        <span className="input-group-addon">
                            <input type="checkbox" onClick={this.onClick} aria-label="..."/>
                        </span>
                            <input type="text" className="form-control" aria-label="..." disabled={this.state.form.healthPlanDisabled}/>
                        </div>
                    </div>


                </div>
                <div className="col-md-6">
                    <TextInput id="discussFamilyInfo"
                               label="Discuss Family Info"
                               placeholder="Que significa?"/>
                </div>
            </div>
           
            <div className="row">
                <div className="col-md-6">
                    <div className="form-group">
                        <label>Marca si aplica:</label>
                        <br/>
                        <div style={{paddingTop: 10}}>
                            <div className="checkbox-inline">
                                <label><input type="checkbox" name="leftHanded"/>&nbsp;&nbsp;Es zurdo</label>
                            </div>

                            <div className="checkbox-inline">
                                <label><input type="checkbox" name="leftHanded"/>&nbsp;&nbsp;Requiere Educacion Especial</label>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="col-md-6">
                    <div className="form-group">
                        <label>Marca si aplica:</label>
                        <br/>
                        <div style={{paddingTop: 10}}>

                            <div className="checkbox-inline">
                                <label><input type="checkbox" name="leftHanded"/>&nbsp;&nbsp;Posee Computadora en
                                    casa</label>
                            </div>

                            <div className="checkbox-inline">
                                <label><input type="checkbox" name="leftHanded"/>&nbsp;&nbsp;Posee Internet en
                                    casa</label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div className="row">
                <div className="col-md-6">
                    <CodeSelect codeType="foodOptions"
                                id="foodOptions"
                                label="Opciones de Comida"
                                placeholder="Seleccione su opcion de comida"/>
                </div>
            </div>
        </form>);
    }
}
