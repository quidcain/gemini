import React, {Component} from "react";
import TextInput from "../../../../components/TextInput";
import {withRouter} from "react-router-dom";
import * as store from "../../../../setup/store";
import swal from 'sweetalert';

class EditSchoolMaxCapacity extends Component {

    constructor(props) {
        super(props);
        this.state = {schoolCap: null, newMaxCapacity: null};
        this.inputHandler = this.inputHandler.bind(this);
        this.save = this.save.bind(this);
    }

    componentWillMount() {
        let schoolGradeLimitId = this.props.match.params.id;
        store.services()
            .getEditSchoolCap(schoolGradeLimitId)
            .then((result) => {
                this.setState({...this.state, schoolCap: result});
            })
    }

    inputHandler(e) {
        let element = e.target;
        let form = this.state;
        form[element.id] = element.value;
    }


    save() {
        let userId = this.props.match.params.id;
        let schoolCap = this.state.schoolCap;
        let form = {
            userId: userId,
            schoolId: schoolCap.schoolId,
            gradeLevel: schoolCap.gradeLevel,
            newMaxCapacity: this.state.newMaxCapacity
        };

        if (!this.state.newMaxCapacity) {
            swal("!!!Debe confirmar una capacidad para poder guardar!!!", {icon: "error"});
            return;
        }


        store.services()
            .saveEditCapRequest(form)
            .then((response) => {
                if (response.status === 200) {
                    swal("!!!Los datos sometidos fueron grabados correctamente!!!", {icon: "info"});
                    this.props.history.push(`/dashboard/school/capacity/manage/${schoolCap.schoolId}`)
                } else {
                    swal("!!!Los datos sometidos NO fueron grabados correctamente!!!", {icon: "error"});
                }
            })

    }

    render() {
        let isDirectorUser = store.getUser() ? store.getUser().directorUser : false;
        let schoolCap = this.state.schoolCap;
        if (!schoolCap)
            return (null);
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Solicitar Revisi&oacute;n de Cupo</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white", padding: 40}}>
                <div className="col-md-12">
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Escuela:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{schoolCap.schoolName}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Grado:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{schoolCap.gradeLevelDescription}</span>
                        </div>
                    </div>
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Capacidad:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{schoolCap.maxCapacity}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Confirmar Capacidad:
                        </div>
                        <div className="col-md-8">
                            <TextInput id="newMaxCapacity"
                                       type="number"
                                       ref="newMaxCapacity"
                                       label="Confirmar Capacidad"
                                       value={this.state.newMaxCapacity || schoolCap.confirmedMaxCapacity}
                                       onChange={this.inputHandler}/>
                        </div>
                    </div>

                    <div className="clearfix mt-4">
                        {
                            isDirectorUser ?
                                (<div className="float-right">
                                    <button className="button-green" style={{paddingLeft: 10}} onClick={this.save}>
                                        Guardar
                                    </button>
                                </div>)
                                : (null)
                        }
                    </div>


                </div>
            </div>];
    }
}

export default withRouter(EditSchoolMaxCapacity);