import fetch from 'safe-fetch'
import {buildUrl} from '../Utils'
import {toggleBlockVariable} from "./store";
import * as env from "../env";
import swal from 'sweetalert';

// import {triggerErrorOn, triggerSessionExpiredOn} from '../actions'
fetch.cookieName = 'XSRF-TOKEN';
fetch.headerName = 'X-XSRF-TOKEN';

const DEFAULT_HEADERS = {
    Accept: 'application/json',
    'Content-Type': 'application/json'
};

/*
    localhost:3000/srs-admin-api/auth -> POST use basic http authentication -> Basic ${btoa(credentials.username + ‘:’ + credentials.password)}
    localhost:3000/srs-admin-api/dashboard/retrieve/regions - GET
    localhost:3000/srs-admin-api/dashboard/retrieve/cities/region/${regionIdParam} - GET
    localhost:3000/srs-admin-api/dashboard/retrieve/schools/region/${regionIdParam}/city/${cityCodeParam} - GET
    localhost:3000/srs-admin-api/dashboard/retrieve/summary - POST request body json object {regionId: null, cityCode: null, schoolId: null};
    localhost:3000/srs-admin-api/report/confirmed/students - POST request body json object {regionId: null, cityCode: null, schoolId: null};

*/
export default class Services {

    constructor() {
    }


    //authentication
    token() {
        return this._getRaw("/token")
    }

    async authenticate(credentials) {
        await this.token();
        return this._login("/auth", credentials);
    }


    session() {
        return this._getPromise("/auth");
    }

    logout() {
        return this._securedPost("/logout", {});
    }

    // main dashboard
    retrieveRegions() {
        toggleBlockVariable(true);
        return this._get(`/dashboard/retrieve/regions`);
    };

    retrieveCities(regionId) {
        return this._get(`/dashboard/retrieve/cities/region/${regionId}`);
    };

    retrieveSchools(regionId, cityCode) {
        return this._get(`/dashboard/retrieve/schools/region/${regionId}/city/${cityCode}`);
    };

    retrieveGradeLevels(schoolId) {
        return this._get(`/dashboard/retrieve/grade/levels/${schoolId}`);
    };

    retrieveSummary(criteria) {
        return this._post(`/dashboard/retrieve/summary`, criteria);
    };

    //reports
    retrieveReportData(type, schoolId) {
        // localhost:3000/srs-admin-api/report/confirmed/students
        return this._get(`/report/${type}/school/${schoolId}/students`);
    }

    retrieveAsignados(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/scheduling/asignados`, criteria);
    }

    retrievesVacantes(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/scheduling/vacantes`, criteria);
    }

    retrieveExcedentes(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/scheduling/excedentes`, criteria);
    }

    retrieveRoster(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/detailed/roster`, criteria);
    }

    retrieveSchoolRoster(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/school/roster`, criteria);
    }

    retrieveClassGroupUnassigned(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/class/group/unassigned/teacher`, criteria);
    }

    retrieveStudentAddressSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/student/address/summary`, criteria);
    }


    //manage pre-enrollments
    doRequestSearch(criteria) {
        toggleBlockVariable(true);
        return this._post(`/request/manage/search`, criteria);
    }

    getEditRequest(preEnrollmentId) {
        return this._get(`/request/manage/edit/${preEnrollmentId}`);
    }

    reactivateRequest(form) {
        return this._post(`/request/manage/reactivate`, form);
    }

    approveRequest(form){
        toggleBlockVariable(true);
        return this._posWithFeedback(`/request/manage/manual/approval`, form);
    }

    transferRequests(form){
        toggleBlockVariable(true);
        return this._post(`/request/manage/transfer`, form);
    }

    //manage portal users
        doUserSearch(criteria) {
        return this._post(`/portal/user/manage/search`, criteria);
    }

    getEditUser(userId) {
        return this._get(`/portal/user/manage/edit/${userId}`);
    }

    activateUser(form) {
        return this._post(`/portal/user/manage/save`, form);
    }

    //manage school caps
    doSchoolCapsSearch(criteria) {
        toggleBlockVariable(true);
        return this._post(`/school/caps/manage/search`, criteria);
    }

    getEditSchoolCap(schoolGradeLimitId) {
        return this._get(`/school/caps/manage/edit/${schoolGradeLimitId}`);
    }

    saveEditCapRequest(form) {
        return this._post(`/school/caps/manage/save`, form);
    }

    //manage students for approvals
    retrieveApprovalSchools() {
        toggleBlockVariable(true);
        return this._get(`/student/approval/manage/schools`);
    }

    doStudentSearch(criteria) {
        toggleBlockVariable(true);
        return this._get(`/student/approval/manage/search/${criteria.schoolId}/gradeLevel/${criteria.gradeLevel}`);
    }

    getStudentApprovalInfo(preEnrollmentId, schoolId) {
        return this._get(`/student/approval/manage/edit/${preEnrollmentId}/school/${schoolId}`);
    }

    saveStudentApproval(form) {
        return this._post(`/student/approval/manage/save`, form);
    }

    saveMontessoriInfo(form) {
        return this._post(`/student/approval/manage/montessori/check/save`, form);
    }

    //manage scheduling dashboard
    retrieveSchedulingSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/dashboard/scheduling/retrieve/summary`, criteria);
    }

    retrieveVacantCategories(criteria) {
        toggleBlockVariable(true);
        return this._post(`/dashboard/scheduling/retrieve/vacants/categories`, criteria);
    }

    retrieveSchedulingCategories(criteria) {
        toggleBlockVariable(true);
        return this._post(`/dashboard/scheduling/retrieve/categories`, criteria);
    }

    //staff report
    retrieveStaffRegionSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/staff/region/summary`, criteria);
    }

    retrieveStaffCitySummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/staff/city/summary`, criteria);
    }

    retrieveStaffSchoolSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/staff/school/summary`, criteria);
    }

    retrieveEnrollmentStaffingSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/staff/enrollment/staffing/summary`, criteria);
    }

    retrieveVacantSummary(criteria) {
        toggleBlockVariable(true);
        return this._post(`/report/staff/city/vacant/staffing/summary`, criteria);
    }

    _login(path, credentials) {
        toggleBlockVariable(true);
        let authorization = `Basic ${btoa(credentials.username + ':' + credentials.password)}`;
        return fetch(buildUrl(path), {method: "POST", ...this._addHeader(authorization), credentials: "same-origin"})
            .then((response) => this._handleHttpCode(response, false))
    }

    _getPromise(path) {
        return fetch(buildUrl(path), this._addHeader())
            .then((response) => this._handleHttpCode(response));
    }

    _get(path) {
        return fetch(buildUrl(path), this._addHeader())
            .then((response) => this._handleHttpCode(response))
            .then((response) => response.json())
            .catch((e) => {
            })
    }

    _getRaw(path) {
        return fetch(buildUrl(path), this._addHeader())
            .then((response) => this._handleHttpCode(response))
            .then((response) => response.text())
            .catch((e) => {
            })
    }

    _publicPost(path, body, token) {
        return fetch(buildUrl(path), {
            method: "POST",
            body: JSON.stringify(body), ...this._addHeaderOnPublicPOST(token)
        })
            .then((response) => this._handleHttpCode(response, false))
            .catch((e) => {
            });
    }


    //todo: POST Methods can be improve
    _securedPost(path, body) {
        return fetch(buildUrl(path), {
            method: "POST",
            ...this._addHeader(),
            credentials: "same-origin",
            body: JSON.stringify(body)
        })
            .then((response) => this._handleHttpCode(response))
            .catch((e) => {
            });
    }

    _post(path, body) {
        return fetch(buildUrl(path), {method: "POST", body: JSON.stringify(body), ...this._addHeader()})
            .then((response) => this._handleHttpCode(response))
            .catch((e) => {
            });
    }

    _posWithFeedback(path, body) {
        return fetch(buildUrl(path), {method: "POST", body: JSON.stringify(body), ...this._addHeader()})
            .then((response) => this._handleHttpCode(response, false))
            .catch((e) => {
            });
    }

    _put(path, body) {
        return fetch(buildUrl(path), {method: "PUT", body: JSON.stringify(body), ...this._addHeader()})
            .then((response) => this._handleHttpCode(response))
            .catch((e) => {
            });
    }

    _addHeaderOnPublicPOST(token) {
        let headersObj = {headers: {...DEFAULT_HEADERS}};
        headersObj.headers["recaptcha-token"] = token;
        return headersObj;
    }

    _addHeader(authorization) {
        let headersObj = {headers: {...DEFAULT_HEADERS}};
        if (authorization)
            headersObj.headers.Authorization = authorization;
        return headersObj;
    }

    _handleHttpCode(response, manageException = true) {
        let httpStatus = response.status;
        if ((httpStatus >= 200 && httpStatus < 300) || (httpStatus >= 400 && httpStatus <= 402)) {
            toggleBlockVariable(false);
            return response;
        } else if (httpStatus === 403) {
            toggleBlockVariable(false);
            if (manageException) {
                swal("Su session ha expirado.").then(() => {
                    window.location = "/" + env.default.baseContext;
                });
            }
            return response;
            // if (manageException) {
            // this.store.dispatch(triggerSessionExpiredOn("Su sesión ha expirado"));
            // }
        } else {
            if (manageException)
            // this.store.dispatch(triggerErrorOn("Occurió un error interno, disculpe el inconveniente"));
                swal("Occurió un error interno, disculpe el inconveniente");
            console.log(`internal server error, error info: ${response && response.statusText}`);
            let message = (response && response.statusText) || "unknown error";

            if (manageException) {
                console.log(message);
            } else {
                return response;
            }

        }
    }

}

