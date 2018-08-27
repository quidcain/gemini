/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import {Button} from "react-bootstrap";
import MedicalConditionCheckbox from "../../components/MedicalConditionCheckbox";
import CodeSelect from "../../components/CodeSelect";
import TextInput from "../../components/TextInput";

export default class MedicalInfoAdditional extends Component {

    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
        this.onAdd = this.onAdd.bind(this);
        this.handleDelete = this.handleDelete.bind(this);

        this.state = {conditions: [], selectedCondition: '', selectionDescription: ''};
    }

    handleDelete = index => () => {
        this.state.conditions.splice(index, 1);
        this.setState({...this.state});
    };

    onChange(e) {
        let element = e.target;
        this.setState({
            ...this.state,
            selectedCondition: element.value,
            selectionDescription: element[element.selectedIndex].text
        })
    }

    onAdd(e) {
        let conditions = this.state.conditions;
        conditions.push({name: this.state.selectionDescription, type: this.state.selectedCondition});
        this.setState({selectedCondition: '', selectionDescription: '', conditions: conditions});
    }

    render() {
        return (<form>
            <div className="row">
                <div className="col-md-4">
                    <CodeSelect codeType="medicalConditions"
                                label="Otras Condicion Medica"
                                placeholder="Seleccione condicion"
                                onChange={this.onChange}
                                value={this.state.selectedCondition}/>
                </div>
                <div className="col-md-2">
                    <div className="form-group">
                        <label>&nbsp;</label>
                        <Button className="form-control" bsStyle="primary" onClick={this.onAdd}>A&ntilde;adir</Button>
                    </div>
                </div>
                <div className="col-md-6"/>
            </div>
            <div className="row">
                <div className="col-md-12">
                    {this.renderConditionList()}
                </div>
            </div>
        </form>);
    }

    renderConditionList() {
        let conditions = this.state.conditions;
        if (!conditions || conditions.length === 0) {
            return (<div className="panel panel-default">
                <div className="panel-body">
                    <label>No posee condiciones adicionales</label>
                </div>
            </div>);
        }
        return (conditions.map((condition, index) => (
            <MedicalConditionCheckbox key={index}
                                      id={`${condition.type}`}
                                      name={condition.name}
                                      editable={true}
                                      handleDelete={this.handleDelete(index)}
                                      disabled={true}>
                <div className="row">
                    <div className="col-md-6">
                        <TextInput id={`${condition.type}Condition`} label="Condicion" placeholder=""/>
                    </div>
                    <div className="col-md-6">
                        <TextInput id={`${condition.type}Treatment`} label="Condicion" placeholder=""/>
                    </div>
                </div>
            </MedicalConditionCheckbox>
        )));
    }

}
