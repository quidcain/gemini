import React, {Component} from "react";
import TextInput from "../../../../components/TextInput";
import * as store from "../../../../setup/store"
import * as Utils from "../../../../Utils";
import {withRouter} from "react-router-dom";
import swal from 'sweetalert';

class SearchUserForm extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.goToDetail = this.goToDetail.bind(this);
        this.state = ({result: [], email: null, userId: null})
    }

    inputHandler(e) {
        let element = e.target;
        this.state[element.id] = element.value;
    }

    componentWillMount() {
        let userId = this.props.match.params.id;
        console.log(`userId = ${userId}`);
        if (userId) {
            this.setState({...this.state, userId: userId}, () => {
                this.doSearch();
            })
        }
    }

    doSearch() {
        let criteria = {email: this.state.email, userId: this.state.userId};
        store.services()
            .doUserSearch(criteria)
            .then((response) => response.json())
            .then((result) => {
                this.setState({...this.state, result: result});
                if (!result || result.length < 1)
                    swal("!!!La búsqueda no produjo resultados!!!", {icon: "error"});
            })
    }

    goToDetail = (resultObj) => (e) => {
        this.props.history.push(`/dashboard/edit/user/${resultObj.userId}`);
    };

    render() {
        let result = this.state.result;
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Búsqueda de Usuarios Portal Público</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">
                    <div className="row mt-4">
                        <div className="col-md-9">
                            <TextInput id="email"
                                       type="email"
                                       ref="email"
                                       label="Email del padre"
                                       onChange={this.inputHandler}
                                       value={this.state.email}
                                       grouped/>
                        </div>

                        <div className="col-md-3">
                            <button className="button-green" style={{paddingLeft: 10}} onClick={this.doSearch}>
                                Buscar
                            </button>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <table className="table table-striped table-hover ">
                                <thead>
                                <tr>
                                    <th>Email del Padre/Madre</th>
                                    <th>Creada</th>
                                    <th>Activada?</th>
                                    <th/>
                                </tr>
                                </thead>
                                <tbody>
                                {result && result.length > 0
                                    ?
                                    result.map((u, i) => (
                                        <tr style={{background: "white"}}>
                                            <td>{u.email}</td>
                                            <td>{Utils.format(u.created, 'DDMMMMYYYY h:mm:ss a')}</td>
                                            <td>{u.activated}</td>
                                            <td onClick={this.goToDetail(u)}><i className="fa fa-edit"
                                                                                style={{fontSize: 32}}/></td>
                                        </tr>
                                    ))
                                    : (<tr style={{background: "white"}}>
                                        <td colSpan={2}>
                                            <span>No existen datos para los criterios de b&uacute;squeda ingresado</span>
                                        </td>
                                    </tr>)
                                }
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        ];
    }
}

export default withRouter(SearchUserForm);