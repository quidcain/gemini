import React, {Component} from "react";
import AdminSchoolSelector from "../../../../components/AdminSchoolSelector";
import {withRouter} from "react-router-dom";
import * as store from "../../../../setup/store";
import swal from 'sweetalert';
import TextInput from "../../../../components/TextInput";

class SearchSchoolMaxCapacity extends Component {

    constructor(props) {
        super(props);
        this.goToDetail = this.goToDetail.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.showEditCapField = this.showEditCapField.bind(this);
        this.state = ({result: [], schoolId: null, newMaxCapacity: [], canEdit: []})
    }

    //No longer use
    goToDetail = (resultObj) => (e) => {
        // store.setSessionData({regionId: this.refs.selector.getRegionId, cityCode: this.refs.selector.getCityCode, schoolId: this.refs.selector.schoolId});
        this.props.history.push(`/dashboard/request/edit/school/capacity/${resultObj.schoolGradeLimitId}`);

    };

    inputHandler = (i) => (e) => {
        let element = e.target;
        let form = this.state;
        form[element.id][i] = element.value;
    };


    saveConfirmedCap = (obj, i) => (e) => {

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
        let schoolId = this.refs.selector.getSchoolId();
        if (!schoolId || parseInt(schoolId) < 0) {
            swal("!!!Debes seleccionar una escuela para poder buscar los cupos!!!");
            return;
        }

        let criteria = {schoolId: schoolId};
        store.services()
            .doSchoolCapsSearch(criteria)
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

        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Validaci&oacute;n de Cupos</h3>
                </div>
            </div>,

            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">

                    <div className="row" style={{paddingTop: 20}}>
                        <div className="col-md-10">
                            <AdminSchoolSelector ref="selector"/>
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
                                <th>Grado</th>
                                <th>Capacidad Máxima</th>
                                <th>Capacidad Máxima Confirmada por el Director</th>
                                {/*<th>Sobrecapacidad</th>*/}
                                <th/>
                            </tr>
                            </thead>
                            <tbody>
                            {result && result.length > 0
                                ?
                                result.map((sgl, i) => (
                                    <tr style={{background: "white"}}>
                                        <td>{sgl.gradeLevelDescription}</td>
                                        <td>{sgl.maxCapacity}</td>
                                        <td onClick={this.toggleEdit(i)}>{this.showEditCapField(sgl, i)}</td>
                                        {/*<td>{sgl.isOvercapacity ? "Sí" : "No"}</td>*/}
                                        {isDirectorUser ? (
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