import fetch from "safe-fetch";
import {buildUrl} from "../../Utils";

import Cookies from 'cookies-js';
import {triggerErrorOn, triggerSessionExpiredOn} from "../actions";

fetch.cookieName = 'XSRF-TOKEN';
fetch.headerName = 'X-XSRF-TOKEN';

const DEFAULT_HEADERS = {
    Accept: 'application/json',
    "Content-Type": 'application/json'
};
export default class Services {

    constructor(store) {
        this.store = store;
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

    //user
    saveProfile(form) {
        return this._securedPost(`/user/save`, form);
    }

    saveCompleteProfile(form) {
        return this._securedPost(`/user/complete/profile`, form);
    }

    //home
    home() {
        return this._get("/home");
    }

    //accounts
    registerAccount(user, token) {
        return this._publicPost("/account/register", user, token);
    }

    activateAccount(activationForm, token) {
        return this._publicPost(`/account/activate`, activationForm, token);
    }

    existsCode(code) {
        return this._getRaw(`/account/activate/${code}`);
    }

    forgotPassword(form, token) {
        return this._publicPost(`/account/forgot/password`, form, token);
    }

    existsKey(key) {
        return this._getRaw(`/account/reset/password/${key}`);
    }

    resetPassword(form, token) {
        return this._publicPost(`/account/reset/password/`, form, token);
    }

    cancelResetPassword(key) {
        return this._get(`/account/cancel/reset/password/${key}`);
    }

    //smax interface
    searchStudent(criteria) {
        return this._securedPost(`/smax/interface/student/search`, criteria);
    }

    getRegions() {
        return this._get(`/smax/interface/retrieve/regions`);
    }

    getVocationalRegions() {
        return this._get(`/smax/interface/retrieve/occupational/regions`);
    }

    getGradeLevels(category) {
        let param = category ? `?category=${category}` : "";
        return this._get(`/smax/interface/retrieve/grade/levels/school/category${param}`);
    }

    getSchoolsByRegionAndGradeLevel(regionId, gradeLevel) {
        return this._get(`/smax/interface/retrieve/school/${regionId}/grade/level/${gradeLevel}`);
    }

    getSchoolsByRegionAndGradeLevel(regionId, gradeLevel) {
        return this._get(`/smax/interface/retrieve/school/${regionId}/grade/level/${gradeLevel}`);
    }

    getSpecializedSchoolCategories() {
        return this._get(`/smax/interface/retrieve/specialized/school/categories`);
    }

    getSpecializedSchoolsByRegionAndGradeLevel(regionId, gradeLevel, category) {
        let param = category ? `?category=${category}` : "";
        return this._get(`/smax/interface/retrieve/specialized/school/${regionId}/grade/level/${gradeLevel}/category${param}`);
    }

    getVocationalSchoolsByRegionAndGradeLevel(regionId, gradeLevel) {
        return this._get(`/smax/interface/retrieve/occupational/school/${regionId}/grade/level/${gradeLevel}`);
    }

    getVocationalProgramsBySchool(schoolId) {
        return this._get(`/smax/interface/retrieve/occupational/programs/school/${schoolId}`);
    }

    getTechnicalSchools() {
        return this._get(`/smax/interface/retrieve/technical/schools`);
    }

    getSharedSchools(gradeLevel) {
        return this._get(`/smax/interface/retrieve/shared/schools/grade/level/${gradeLevel}`);
    }

    saveSharedSchools(form) {
        return this._securedPost(`/pre/enrollment/add/shared/school/save`, form);
    }

    //pre-enrollment

    getActivePreEnrollment(requestId) {
        return this._get(`/pre/enrollment/${requestId}`);
    }

    getActiveVocationalPreEnrollment(requestId) {
        return this._get(`/pre/enrollment/vocational/${requestId}`);
    }

    getActiveAlternatePreEnrollment(requestId) {
        return this._get(`/pre/enrollment/alternate/${requestId}`);
    }

    savePreEnrollment(form) {
        return this._securedPost(`/pre/enrollment/save`, form);
    }

    getPreEnrollmentAddress(requestId) {
        return this._get(`/pre/enrollment/${requestId}/address`);
    }

    partialSaveVocationalPreEnrollment(form) {
        return this._securedPost(`/pre/enrollment/vocational/partial/save`, form)
    }

    partialAlternatePreEnrollmentSave(form) {
        return this._securedPost(`/pre/enrollment/alternate/partial/save`, form)
    }

    savePreEnrollmentAddress(addressForm) {
        return this._securedPost(`/pre/enrollment/${addressForm.requestId}/address/save`, addressForm);
    }

    submitPreEnrollment(form) {
        return this._securedPost(`/pre/enrollment/submit`, form);
    }

    submitVocationalPreEnrollment(form) {
        return this._securedPost(`/pre/enrollment/vocational/submit`, form);
    }

    submitAlternatePreEnrollment(form) {
        return this._securedPost(`/pre/enrollment/alternate/submit`, form);
    }

    getReasonsForNotAttendingSchools() {
        return this._get(`/pre/enrollment/reasons/for/not/attending/school`);
    }

    saveReasonForNotAttending(form) {
        return this._securedPost(`/pre/enrollment/reason/for/not/attending/school/save`, form);
    }

    //student additional info

    getStudentDemographics(studentId) {
        return this._get(`/student/${studentId}/demographics/retrieve`)
    }

    saveStudentDemographics(form) {
        return this._securedPost(`/student/${form.studentId}/demographics/save`, form);
    }

    saveBornPr(form) {
        return this._securedPost(`/student/${form.studentId}/born/pr/save`, form);
    }

    saveAgreementAnswer(form) {
        return this._securedPost(`/student/${form.studentId}/agreement/save`, form);
    }

    saveHispanic(form) {
        return this._securedPost(`/student/${form.studentId}/hispanic/save`, form);
    }

    saveNeedTransportationService(form) {
        return this._securedPost(`/student/${form.studentId}/request/transportation/save`, form);
    }

    geoCodeAddress(form) {
        return this._securedPost(`/address/helper/geocode`, form);
    }

    getCountyCode(zipcode) {
        return this._getRaw(`/address/helper/retrieve/county/code/${zipcode}`);
    }

    loadStudentSchedule(studentId) {
        return this._get(`/student/${studentId}/schedule/view`);
    }

    _login(path, credentials) {
        let authorization = `Basic ${btoa(credentials.username + ':' + credentials.password)}`;
        return fetch(buildUrl(path), {method: "POST", ...this._addHeader(authorization), credentials: "same-origin"});
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
            return response;
        } else if (httpStatus === 403) {
            if (manageException) {
                this.store.dispatch(triggerSessionExpiredOn("Su sesión ha expirado"));
            }
        } else {
            if (manageException)
                this.store.dispatch(triggerErrorOn("Occurió un error interno, disculpe el inconveniente"));
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