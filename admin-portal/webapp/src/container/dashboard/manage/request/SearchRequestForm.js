import React, {Component} from "react";
import TextInput from "../../../../components/TextInput";
import SocialSecurityInput from "../../../../components/SocialSecurityInput";
import * as store from "../../../../setup/store"
import * as Utils from "../../../../Utils";
import {withRouter} from "react-router-dom";
import swal from 'sweetalert';
import ApprovalCentralModal from "./ApprovalCentralModal";
import TransferRequestModal from "./TransferRequestModal";

class SearchRequestForm extends Component {

    constructor(props) {
        super(props);
        this.inputHandler = this.inputHandler.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.goToDetail = this.goToDetail.bind(this);
        this.state = ({result: [], ssn: null, studentNumber: null, email: null})
    }

    componentWillMount() {

        if (store.getDataByKey("email") || store.getDataByKey("studentNumber")) {

            this.setState({
                ...this.state,
                email: store.getDataByKey("email"),
                studentNumber: store.getDataByKey("studentNumber")
            }, () => {
                let criteria = {email: this.state.email, ssn: this.state.ssn, studentNumber: this.state.studentNumber};
                store.services()
                    .doRequestSearch(criteria)
                    .then((response) => response.json())
                    .then((result) => {
                        this.setState({...this.state, result: result});
                        if (!result || result.length < 1)
                            swal("!!!La búsqueda no produjo resultados!!!", {icon: "error"});
                    })
            });
        }
    }

    inputHandler(e) {
        let element = e.target;
        this.state[element.id] = element.value;
    }

    doSearch() {
        let criteria = {email: this.state.email, studentNumber: this.state.studentNumber, ssn: this.state.ssn};
        store.services()
            .doRequestSearch(criteria)
            .then((response) => response.json())
            .then((result) => {
                this.setState({...this.state, result: result});
                if (!result || result.length < 1)
                    swal("!!!La búsqueda no produjo resultados!!!", {icon: "error"});
            })
    }

    goToDetail = (resultObj) => (e) => {
        store.setSessionData({email: this.state.email, ssn: this.state.ssn, studentNumber: this.state.studentNumber});
        this.props.history.push(`/dashboard/edit/request/${resultObj.requestId}`);
    };


    approveRequest = (resultObj) => (e) => {
        this.refs.approvalModal.open(resultObj, () => {
            this.doSearch();
        })
    };

    transferRequest = (resultObj) => (e) => {
        this.refs.transferModal.open(resultObj, () => {
            this.doSearch();
        })
    };

    render() {
        let isSIEUser = store.getUser() ? store.getUser().sieUser : false;
        let isPlanificacionUser = store.getUser() ? store.getUser().regionalUser : false;
        let isRegionalUser = store.getUser() ? store.getUser().regionalUser : false;

        let result = this.state.result;
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Búsqueda de Solicitudes</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">
                    <div className="row mt-4">
                        <div className="col-md-3">
                            <SocialSecurityInput
                                id="ssn"
                                ref="ssn"
                                label="Seguro Social"
                                onChange={this.inputHandler}
                                value={this.state.ssn}
                                grouped/>
                        </div>

                        <div className="col-md-3">
                            <TextInput id="studentNumber"
                                       type="text"
                                       ref="studentNumber"
                                       label="# Estudiante SIE"
                                       onChange={this.inputHandler}
                                       value={this.state.studentNumber}
                                       grouped/>
                        </div>

                        <div className="col-md-3">
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
                                    <th>Nombre</th>
                                    <th>Email del Padre/Madre</th>
                                    <th>Estatus</th>
                                    <th>Sometido?</th>
                                    <th/>
                                    <th/>
                                    <th/>
                                </tr>
                                </thead>
                                <tbody>
                                {result && result.length > 0
                                    ?
                                    result.map((r, i) => (
                                        <tr style={{background: "white"}}>
                                            <td>{r.studentName}</td>
                                            <td>{r.email}</td>
                                            <td><span className={r.cssStatusColor}>{r.status}</span></td>
                                            <td>{r.submitted}</td>

                                            {r.canApprove && (isSIEUser || isPlanificacionUser || isRegionalUser) ? (<td onClick={this.approveRequest(r)}>
                                                <i className="fa fa-check-square" style={{fontSize: 20}}/></td>) : (
                                                <td/>)}

                                            {(isSIEUser || isPlanificacionUser) ? (<td onClick={this.transferRequest(r)}><i className="fa fa-exchange"
                                                                                     style={{fontSize: 20}}/></td>)
                                            : (<td/>)}
                                            <td onClick={this.goToDetail(r)}><i className="fa fa-edit"
                                                                                style={{fontSize: 20}}/></td>
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

                            <ApprovalCentralModal ref="approvalModal"/>
                            <TransferRequestModal ref="transferModal"/>

                        </div>
                    </div>
                </div>
            </div>
        ];
    }
}

export default withRouter(SearchRequestForm);