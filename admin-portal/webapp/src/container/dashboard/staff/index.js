import React, {Component} from "react";
import * as store from "../../../setup/store";
import AdminSchoolSelector from "../../../components/AdminSchoolSelector";
import {Nav, NavItem, NavLink, TabContent, TabPane} from "reactstrap";
import classnames from 'classnames';
import {toggleBlockVariable} from "../../../setup/store";

export default class StaffReports extends Component {

    constructor(props) {
        super(props);
        this.doSearch = this.doSearch.bind(this);
        this.state = {
            activeTab: '1',
            regionStaff: [],
            cityStaff: [],
            schoolStaff: [],
            enrollmentAndStaff: [],
            vacantStaff: []
        };
    }

    doSearch() {
        let criteriaObj = {
            regionId: this.refs.selector.getRegionId(),
            cityCode: this.refs.selector.getCityCode(),
            schoolId: this.refs.selector.getSchoolId(),
        };

        switch (this.state.activeTab) {
            case '1':
                store.services()
                    .retrieveStaffRegionSummary(criteriaObj)
                    .then((response) => response.json())
                    .then((data) => {
                        this.setState({...this.state, regionStaff: data});
                    });
                break;
            case '2':
                store.services()
                    .retrieveStaffCitySummary(criteriaObj)
                    .then((response) => response.json())
                    .then((data) => {
                        this.setState({...this.state, cityStaff: data});
                    });
                break;
            case '3':
                store.services()
                    .retrieveStaffSchoolSummary(criteriaObj)
                    .then((response) => response.json())
                    .then((data) => {
                        this.setState({...this.state, schoolStaff: data});
                    });
                break;
            // case '4':
            //     store.services()
            //         .retrieveEnrollmentStaffingSummary(criteriaObj)
            //         .then((response) => response.json())
            //         .then((data) => {
            //             this.setState({...this.state, enrollmentAndStaff: data});
            //         });
            //     break;
            case '5':
                store.services()
                    .retrieveVacantSummary(criteriaObj)
                    .then((response) => response.json())
                    .then((data) => {
                        this.setState({...this.state, vacantStaff: data});
                    });
                break;
        }


    }

    render() {
        // let result = this.state.result;
        // let isDirectorUser = store.getUser() ? store.getUser().directorUserOverride : false;
        // let schoolId = this.refs.selector ? this.refs.selector.getSchoolId() : null;
        // let schoolNotSelected = !schoolId || parseInt(schoolId) < 0;

        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Enrollment and Staffing Summary</h3>
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
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">
                    {this.renderReport()}
                </div>
            </div>
        ];
    }

    toggle(tabNum) {
        this.setState({
            activeTab: tabNum
        });
    }

    renderReport() {
        return [

            <Nav tabs>
                <NavItem>
                    <NavLink className={classnames({active: this.state.activeTab === '1'})} onClick={() => {
                        this.toggle('1');
                    }}>
                        Regi&oacute;n
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className={classnames({active: this.state.activeTab === '2'})} onClick={() => {
                        this.toggle('2');
                    }}>
                        Municipios
                    </NavLink>
                </NavItem>
                <NavItem>
                    <NavLink className={classnames({active: this.state.activeTab === '3'})} onClick={() => {
                        this.toggle('3');
                    }}>
                        Escuelas
                    </NavLink>
                </NavItem>
                {/*<NavItem>*/}
                    {/*<NavLink className={classnames({active: this.state.activeTab === '4'})} onClick={() => {*/}
                        {/*this.toggle('4');*/}
                    {/*}}>*/}
                        {/*Enrollment And Staff Analysis*/}
                    {/*</NavLink>*/}
                {/*</NavItem>*/}
                <NavItem>
                    <NavLink className={classnames({active: this.state.activeTab === '5'})} onClick={() => {
                        this.toggle('5');
                    }}>
                        Vacantes
                    </NavLink>
                </NavItem>
            </Nav>,

            <TabContent activeTab={this.state.activeTab}>
                <TabPane tabId="1">
                    <div className="row">
                        <div className="col-md-12">
                            {this.renderRegionReport()}
                        </div>
                    </div>
                </TabPane>
                <TabPane tabId="2">
                    <div className="col-md-12">
                        {this.renderCityReport()}
                    </div>
                </TabPane>
                <TabPane tabId="3">
                    <div className="col-md-12">
                        {this.renderSchoolReport()}
                    </div>
                </TabPane>
                {/*<TabPane tabId="4">*/}
                    {/*<div className="col-md-12">*/}
                        {/*{this.renderEnrollmentAndStaffReport()}*/}
                    {/*</div>*/}
                {/*</TabPane>*/}
                <TabPane tabId="5">
                    <div className="col-md-12">
                        {this.renderVancatReport()}
                    </div>
                </TabPane>

            </TabContent>

        ];
    }

    renderRegionReport() {
        let result = this.state.regionStaff;

        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Regi&oacute;n</th>
                    <th>Matr&iacute;cula Estudiantes</th>
                    <th>Necesidad</th>
                    <th>Maestro Asignados</th>
                    <th>Transitorios Pendientes</th>
                    <th>Puestos Vacantes</th>
                </tr>
                </thead>
                <tbody>
                {result && result.length > 0
                    ?
                    result.map((sgl, i) => (
                        <tr style={{background: "white"}}>
                            <td>{sgl.regionName}</td>
                            <td>{sgl.enrollmentTotal}</td>
                            <td>{sgl.needs}</td>
                            <td>{sgl.assigns}</td>
                            <td>{sgl.assignTransit}</td>
                            <td>{sgl.vacants}</td>
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
        )
    }

    renderCityReport() {
        let result = this.state.cityStaff;

        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Municipio</th>
                    <th>Matr&iacute;cula Estudiantes</th>
                    <th>Necesidad</th>
                    <th>Maestro Asignados</th>
                    <th>Transitorios Pendientes</th>
                    <th>Puestos Vacantes</th>
                </tr>
                </thead>
                <tbody>
                {result && result.length > 0
                    ?
                    result.map((sgl, i) => (
                        <tr style={{background: "white"}}>
                            <td>{sgl.city}</td>
                            <td>{sgl.enrollmentTotal}</td>
                            <td>{sgl.needs}</td>
                            <td>{sgl.assigns}</td>
                            <td>{sgl.assignTransit}</td>
                            <td>{sgl.vacants}</td>
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
        )
    }

    renderSchoolReport() {
        let result = this.state.schoolStaff;

        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Escuela</th>
                    <th>Matr&iacute;cula Estudiantes</th>
                    <th>Necesidad</th>
                    <th>Maestro Asignados</th>
                    <th>Transitorios Pendientes</th>
                    <th>Puestos Vacantes</th>
                </tr>
                </thead>
                <tbody>
                {result && result.length > 0
                    ?
                    result.map((sgl, i) => (
                        <tr style={{background: "white"}}>
                            <td>{sgl.schoolName}</td>
                            <td>{sgl.enrollmentTotal}</td>
                            <td>{sgl.needs}</td>
                            <td>{sgl.assigns}</td>
                            <td>{sgl.assignTransit}</td>
                            <td>{sgl.vacants}</td>
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
        )
    }

    renderEnrollmentAndStaffReport() {
        let result = this.state.enrollmentAndStaff;

        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Grado</th>
                    <th>Materia</th>
                    <th>Matr&iacute;cula Estudiantes</th>
                    <th>Necesidad de Maestros</th>
                    <th>Maestro Asignados</th>
                    <th>Transitorios Pendientes</th>
                    <th>Puestos Vacantes</th>
                </tr>
                </thead>
                <tbody>
                {result && result.length > 0
                    ?
                    result.map((sgl, i) => (
                        <tr style={{background: "white"}}>
                            <td>{sgl.gradeLevel}</td>
                            <td>{sgl.category}</td>
                            <td>{sgl.enrollmentTotal}</td>
                            <td>{sgl.needs}</td>
                            <td>{sgl.assigns}</td>
                            <td>{sgl.assignTransit}</td>
                            <td>{sgl.vacants}</td>
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
        )
    }

    renderVancatReport() {
        let result = this.state.vacantStaff;

        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Municipio</th>
                    <th>K-3</th>
                    <th>4-6</th>
                    <th>English Superior</th>
                    <th>Matemática Superior</th>
                    <th>Español Superior</th>
                    <th>Historia</th>
                    <th>Ciencia Superior</th>
                    <th>Otros</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {result && result.length > 0
                    ?
                    result.map((sgl, i) => (
                        <tr style={{background: "white"}}>
                            <td>{sgl.city}</td>
                            <td>{sgl.k_3}</td>
                            <td>{sgl.g4_6}</td>
                            <td>{sgl.englishSuperior}</td>
                            <td>{sgl.mathSuperior}</td>
                            <td>{sgl.spanishSuperior}</td>
                            <td>{sgl.historySuperior}</td>
                            <td>{sgl.scienceSuperior}</td>
                            <td>{sgl.other}</td>
                            <td>{sgl.total}</td>

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
        )
    }

}