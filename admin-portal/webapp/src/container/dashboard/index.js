import React, {Component} from 'react'
import {Link, Route, Switch, withRouter} from 'react-router-dom'
import NavDashboard from '../../components/NavDashboard'

import * as store from "../../setup/store";
import MainChart from "./mainchart";
import Report from "./reports";
import ManageEntryPoint from "./manage";
import EditRequestForm from "./manage/request/EditRequestForm";
import ManageUsersEntryPoint from "./users";
import EditUserForm from "./users/manage/EditUserForm";
import userPlaceholder from '../../assets/img/userPlaceholder.png'
import {Collapse} from "reactstrap";

import * as env from "../../env";
import SchoolCapsEntryPoint from "./caps";
import EditSchoolMaxCapacity from "./caps/manage/EditSchoolMaxCapacity";
import ApprovalsEntryPoint from "./approvals";
import EditStudentRequestEnrolled from "./approvals/manage/EditStudentRequestEnrolled";
import SchedulingDashboard from "./scheduling";
import StaffReports from "./staff";
import B2SReports from "./b2s";

class Dashboard extends Component {

    constructor(props) {
        super(props);
        this.state = {activeIndex: 0, collapseOpen: "1"};
        this.handleLogout = this.handleLogout.bind(this);
        this.toggle = this.toggle.bind(this);

    }

    async handleLogout() {
        try {
            await store.services().logout();
            this.props.history.push(`/`);
        } catch (e) {
            console.log("error at logout")
        }
    }

    selectLink = (idx) => (e) => {
        store.clearSessionData();
        this.setState({activeIndex: idx, activeKey: '1'});
    };

    toggle(t) {
        this.setState((prevState, props) => ({
            collapseOpen: prevState.collapseOpen === t ? '' : t
        }));
    }

    iconMore() {
        return (
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                 viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round"
                 class="feather feather-plus-circle">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="12" y1="8" x2="12" y2="16"></line>
                <line x1="8" y1="12" x2="16" y2="12"></line>
            </svg>
        )
    }

    iconLess() {
        return (
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                 viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round"
                 class="feather feather-plus-circle">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="8" y1="12" x2="16" y2="12"></line>
            </svg>
        )
    }


    render() {
        let userFullName = store.getUser() ? store.getUser().fullName : "";
        let activeIndex = this.state.activeIndex;
        let counter = 0;
        let isSIEUser = store.getUser() ? store.getUser().sieUser : false;
        let isTransportationUser = store.getUser() ? store.getUser().transportationUser : false;
        let isDirectorUser = store.getUser() ? store.getUser().directorUser : false;
        let isRHUser = store.getUser() ? store.getUser().rhUser : false;
        let isSAPDEUser = store.getUser() ? store.getUser().sapdeUser : false;
        let isRegionalUser = store.getUser() ? store.getUser().regionalUser : false;
        let isPlanificacionUser = store.getUser() ? store.getUser().regionalUser : false;

        return (


            <div className='dashboard'>
                <div className='main'>
                    <NavDashboard/>
                    <div className="container-fluid">

                        <div className="row">
                            <nav className="d-none d-md-block bg-light sidebar">
                                <div className='userInfo flexCenter'>
                                    <img className='userlogo' src={userPlaceholder} style={{height: "40px"}} alt=''/>
                                    <div className='userName'>{userFullName}</div>
                                </div>
                                <div className="sidebar-sticky">

                                    <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1">
                                        <span style={{color: 'white'}}> Admin </span>
                                        <a className="d-flex align-items-center text-muted" href="#"
                                           onClick={() => this.toggle("1")}>
                                            {this.state.collapseOpen === '1' ? this.iconLess() : this.iconMore()}
                                        </a>
                                    </h6>

                                    <Collapse isOpen={this.state.collapseOpen === '1'}>
                                        <ul className="nav flex-column">
                                            <li className="nav-item">
                                                <Link onClick={this.selectLink(counter)}
                                                      className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                      href='#mainchart'
                                                      to='/dashboard/mainchart'>
                                                    Inicio
                                                </Link>
                                            </li>

                                            <li className="nav-item">
                                                <Link onClick={this.selectLink(counter)}
                                                      className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                      href='#mainchart'
                                                      to='/dashboard/staff/reports'>
                                                    Enrollment and Staffing Summary
                                                </Link>
                                            </li>

                                            <li className="nav-item">
                                                <Link onClick={this.selectLink(counter)}
                                                      className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                      href='#mainchart'
                                                      to='/dashboard/school/capacity/manage'>
                                                    Espacios Disponibles para Estudiantes por Grado
                                                </Link>
                                            </li>

                                            {
                                                (isSIEUser || isPlanificacionUser || isRegionalUser) ?
                                                    (
                                                        <li className="nav-item">
                                                            <Link onClick={this.selectLink(counter)}
                                                                  className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                                  href='#mainchart'
                                                                  to='/dashboard/request/manage'>
                                                                Manejo de solicitudes
                                                            </Link></li>)
                                                    : (null)
                                            }

                                            {
                                                isSIEUser ?
                                                    (<li className="nav-item">
                                                        <Link onClick={this.selectLink(counter)}
                                                              className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                              href='#mainchart'
                                                              to='/dashboard/users/manage/'>
                                                            Manejo de Cuentas Portal Público
                                                        </Link></li>)
                                                    : (null)}

                                            {
                                                // isSIEUser || isDirectorUser ?
                                                //     (<li className="nav-item">
                                                //         <Link onClick={this.selectLink(counter)}
                                                //               className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                //               href='#mainchart'
                                                //               to='/dashboard/approvals/manage/'>
                                                //             Manejo de Aprobaciones
                                                //         </Link></li>)
                                                //     : (null)
                                            }

                                            {
                                                isSIEUser || isSAPDEUser || isRHUser || isRegionalUser || isDirectorUser ?
                                                    (<li className="nav-item">
                                                        <Link onClick={this.selectLink(counter)}
                                                              className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                              href='#mainchart'
                                                              to='/dashboard/scheduling/'>
                                                            Dashboard de RH
                                                        </Link></li>)
                                                    : (null)
                                            }

                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#mainchart'*/}
                                                      {/*to='/dashboard/b2s/reports/'>*/}
                                                    {/*Reportes Back to School*/}
                                                {/*</Link></li>*/}
                                        </ul>
                                    </Collapse>
                                    <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 ">
                                        <span style={{color: 'white'}}> Reportes SIE-Registro</span>
                                        <a className="d-flex align-items-center text-muted" href="#"
                                           onClick={() => this.toggle("2")}>
                                            {this.state.collapseOpen === '2' ? this.iconLess() : this.iconMore()}
                                        </a>
                                    </h6>
                                    <Collapse isOpen={this.state.collapseOpen === '2'}>
                                        <ul className="nav flex-column">

                                            {
                                                isTransportationUser || isSIEUser ?
                                                    (<li className="nav-item">
                                                        <Link onClick={this.selectLink(counter)}
                                                              className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                              href='#reports'
                                                              to='/dashboard/report/transportation_requested'>
                                                            Estudiantes Solicitando Transportaci&oacute;n
                                                        </Link>
                                                    </li>)
                                                    : (null)
                                            }

                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#reports'*/}
                                                      {/*to='/dashboard/report/confirmed'>*/}
                                                    {/*Estudiantes Confirmados*/}
                                                {/*</Link>*/}
                                            {/*</li>*/}
                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#reports'*/}
                                                      {/*to='/dashboard/report/denied'>*/}
                                                    {/*Estudiantes Rechazados*/}
                                                {/*</Link>*/}
                                            {/*</li>*/}
                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#reports'*/}
                                                      {/*to='/dashboard/report/alternate_enrollment'>*/}
                                                    {/*Estudiantes con matricula alterna*/}
                                                {/*</Link>*/}
                                            {/*</li>*/}
                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#reports'*/}
                                                      {/*to='/dashboard/report/pending_enrollment'>*/}
                                                    {/*Estudiantes pendientes por matricular*/}
                                                {/*</Link>*/}
                                            {/*</li>*/}
                                            <li className="nav-item">
                                                <Link onClick={this.selectLink(counter)}
                                                      className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}
                                                      href='#reports'
                                                      to='/dashboard/report/new_entry_enrollment'>
                                                    Estudiantes Nuevo Ingresos
                                                </Link>
                                            </li>
                                            {/*<li className="nav-item">*/}
                                                {/*<Link onClick={this.selectLink(counter)}*/}
                                                      {/*className={`nav-link ${activeIndex === counter++ ? "active" : ""}`}*/}
                                                      {/*href='#reports'*/}
                                                      {/*to='/dashboard/report/vocational_enrollment'>*/}
                                                    {/*Estudiantes Ocupacionales*/}
                                                {/*</Link>*/}
                                            {/*</li>*/}
                                        </ul>
                                    </Collapse>
                                </div>
                                <div className='logout ' style={{fontSize: "10px"}}>
                                    <span className={"text-right"}> © {this.currentYear} Dpto. de Educaci&oacute;n de Puerto Rico</span>
                                    <span className="text-right"
                                          style={{marginLeft: 20}}>v{env.default.buildVersion}</span>
                                </div>
                            </nav>
                        </div>
                    </div>
                    <div className="display">
                        <Switch>
                            <Route
                                path="/dashboard/mainchart"
                                component={MainChart}
                            />
                            <Route
                                path="/dashboard/school/capacity/manage"
                                component={SchoolCapsEntryPoint}
                            />
                            <Route
                                path="/dashboard/request/edit/school/capacity/:id"
                                component={EditSchoolMaxCapacity}
                            />
                            <Route
                                path="/dashboard/report/:reportType"
                                component={Report}
                            />
                            <Route
                                path="/dashboard/request/manage"
                                component={ManageEntryPoint}
                            />
                            <Route
                                path="/dashboard/edit/request/:id"
                                component={EditRequestForm}
                            />

                            <Route
                                path="/dashboard/users/manage/:id?"
                                component={ManageUsersEntryPoint}
                            />
                            <Route
                                path="/dashboard/edit/user/:id"
                                component={EditUserForm}
                            />

                            <Route
                                path="/dashboard/approvals/manage/"
                                component={ApprovalsEntryPoint}
                            />
                            <Route
                                path="/dashboard/approvals/edit/manage/:id/school/:schoolId"
                                component={EditStudentRequestEnrolled}
                            />

                            <Route
                                path="/dashboard/scheduling/"
                                component={SchedulingDashboard}/>
                            <Route
                                path="/dashboard/staff/reports"
                                component={StaffReports}
                            />
                            <Route
                                path="/dashboard/b2s/reports"
                                component={B2SReports}
                            />

                        </Switch>
                    </div>
                </div>
            </div>
        )
    }
}

export default withRouter(Dashboard);
