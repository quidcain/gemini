import React, {Component} from "react";
import AdminSchoolSelector from "../../../../components/AdminSchoolSelector";
import {withRouter} from "react-router-dom";
import * as store from "../../../../setup/store";
import swal from 'sweetalert';
import TextInput from "../../../../components/TextInput";
import RemoteCodeSelect from "../../../../components/RemoteCodeSelect";

const grades = [
    {value: "PK", display: "PK-PreKinder"},
    {value: "KG", display: "KG-Kindergarden"},
    {value: "01", display: "01-Primero"},
    {value: "02", display: "02-Segundo"},
    {value: "03", display: "03-Tercero"},
    {value: "04", display: "04-Cuarto"},
    {value: "05", display: "05-Quinto"},
    {value: "06", display: "06-Sexto"},
    {value: "07", display: "07-Septimo"},
    {value: "08", display: "08-Octavo"},
    {value: "09", display: "09-Noveno"},
    {value: "10", display: "10-Decimo"},
    {value: "11", display: "11-Undecimo"},
    {value: "12", display: "12-Duodécimo"},


];

class SearchSchoolMaxCapacity extends Component {

    constructor(props) {
        super(props);
        this.goToDetail = this.goToDetail.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.showEditCapField = this.showEditCapField.bind(this);
        this.gradeLevelChanged = this.gradeLevelChanged.bind(this);
        this.state = ({result: [], schoolId: null, newMaxCapacity: [], canEdit: [], gradeLevel: -1})
    }

    //No longer use
    goToDetail = (resultObj) => (e) => {
        // store.setSessionData({regionId: this.refs.selector.getRegionId, cityCode: this.refs.selector.getCityCode, schoolId: this.refs.selector.schoolId});
        this.props.history.push(`/dashboard/request/edit/school/capacity/${resultObj.schoolGradeLimitId}`);

    };

    gradeLevelChanged(gradeLevelObj) {
        this.setState({...this.state, gradeLevel: gradeLevelObj.value})
    }

    inputHandler = (i) => (e) => {
        let element = e.target;
        let form = this.state;
        form[element.id][i] = element.value;
    };


    saveConfirmedCap = (obj, i) => (e) => {
        let schoolId = this.refs.selector.getSchoolId();
        let schoolCap = obj;
        let form = {
            schoolId: schoolCap.schoolId,
            gradeLevel: schoolCap.gradeLevel,
            newMaxCapacity: this.state.newMaxCapacity[i]
        };

        if (!this.state.newMaxCapacity[i]) {
            swal("!!!Debe ingresar una capacidad valida para poder guardar!!!", {icon: "error"});
            return;
        }

        if (!schoolId || parseInt(schoolId) < 0) {
            swal("!!!Debes seleccionar una escuela para poder guarda el cupo!!!");
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
                this.doSearch();
            })
    };

    doSearch() {
        let regionId = this.refs.selector.getRegionId();
        let cityCode = this.refs.selector.getCityCode();
        let schoolId = this.refs.selector.getSchoolId();

        let criteria = {regionId: regionId, cityCode: cityCode, schoolId: schoolId, gradeLevel: this.state.gradeLevel};
        store.services()
            .doSchoolCapsSearch(criteria)
            .then((response) => response.json())
            .then((result) => {
                let canEdit = [];
                let newMaxCapacity = [];

                if (result && result.length > 0) {
                    for (const r of result) {
                        canEdit.push(false);
                        newMaxCapacity.push(r.confirmedMaxCapacity);
                    }
                }

                this.setState({...this.state, result: result, canEdit: canEdit});
                if (!result || result.length < 1)
                    swal("!!!La búsqueda no produjo resultados!!!", {icon: "error"});
            })
    }


    render() {
        let result = this.state.result;
        let isDirectorUser = store.getUser() ? store.getUser().directorUserOverride : false;
        let schoolId = this.refs.selector ? this.refs.selector.getSchoolId() : null;
        let schoolNotSelected = !schoolId || parseInt(schoolId) < 0;

        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Espacios Disponibles para Estudiantes por Grado</h3>
                </div>
            </div>,

            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">

                    <div className="row" style={{paddingTop: 20}}>
                        <div className="col-md-8">
                            <AdminSchoolSelector ref="selector"/>
                        </div>

                        <div className="col-md-2">
                            <RemoteCodeSelect id="gradeLevel"
                                              label="Grados"
                                              placeholder="Grados"
                                              codes={grades}
                                              onObjectChange={this.gradeLevelChanged}
                                              target="value"
                                              display="display"/>
                        </div>
                        <div className="col-md-2">
                            <button className="button-green" style={{paddingLeft: 10}} onClick={this.doSearch}>
                                Buscar
                            </button>
                        </div>
                    </div>

                    <div className="row">
                        <table className="table table-striped table-hover ">
                            <thead>
                            <tr>
                                <th>Escuela</th>
                                <th>Grado</th>
                                <th>Capacidad Máxima</th>
                                <th>Total de Matrícula</th>
                                <th>Capacidad Restante</th>
                                {schoolNotSelected ? (null) : (<th>Capacidad Máxima Confirmada por el Director</th>)}
                                {schoolNotSelected ? (null) : (<th/>)}
                            </tr>
                            </thead>
                            <tbody>
                            {result && result.length > 0
                                ?
                                result.map((sgl, i) => (
                                    <tr style={{background: "white"}}>
                                        <td>{sgl.schoolName}</td>
                                        <td>{sgl.gradeLevelDescription}</td>
                                        <td>{sgl.maxCapacity}</td>
                                        <td>{sgl.enrollmentTotal}</td>
                                        <td>{sgl.remainCap}</td>
                                        {schoolNotSelected ? (null) : (
                                            <td onClick={this.toggleEdit(i)}>{this.showEditCapField(sgl, i)}</td>)}
                                        {/*<td>{sgl.isOvercapacity ? "Sí" : "No"}</td>*/}
                                        {isDirectorUser && !schoolNotSelected ? (
                                                <td onClick={this.saveConfirmedCap(sgl, i)}><i className="fa fa-save"
                                                                                               style={{fontSize: 32}}/>
                                                </td>)
                                            : (<td/>)}
                                    </tr>
                                ))
                                : (<tr style={{background: "white"}}>
                                    <td colSpan={3}>
                                        <span>No existen datos para los criterios de b&uacute;squeda ingresado</span>
                                    </td>
                                </tr>)
                            }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        ];
    }

    toggleEdit = (i) => (e) => {
        this.state.canEdit[i] = !this.state.canEdit[i];
        this.setState({...this.state, canEdit: this.state.canEdit})
    };

    showEditCapField(sgl, i) {
        let isDirectorUser = store.getUser() ? store.getUser().directorUserOverride : false;
        if (this.state.canEdit[i] && isDirectorUser)
            return (<TextInput id="newMaxCapacity"
                               plain
                               style={{width: "15%", padding: "0 0 0 0"}}
                               type="number"
                               ref="newMaxCapacity"
                               includeLabel={false}
                               label=""
                               value={this.state.newMaxCapacity[i] || sgl.confirmedMaxCapacity}
                               onChange={this.inputHandler(i)}/>);

        if (sgl.confirmedMaxCapacity)
            return sgl.confirmedMaxCapacity;

        return (<span style={{color: isDirectorUser ? "#BDBDBD" : "#000"}}>Sin Confirmar</span>);
    }

}

export default withRouter(SearchSchoolMaxCapacity);