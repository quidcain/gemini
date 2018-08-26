import * as types from "../types";
import services from "../setup";
import * as Utils from "../../Utils";

//todo fran: please review this preEnrollment needs to load before, in the wizard config start
export const loadPersonalInfo = (onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_PERSONAL_INFO_LOAD_START});

    let studentLookup = getState().studentLookup.student;
    let preEnrollment = getState().preEnrollment;
    let requestId = preEnrollment.requestId = getState().wizard.workingRequestId;
    let editingRequest = preEnrollment.requestId || false;

    if (editingRequest)
        services()
            .getActivePreEnrollment(requestId)
            .then((response) => {
                //todo: change this!!!
                dispatch({type: types.STUDENT_PERSONAL_INFO_LOAD_END, student: response.content});
                try {
                    if (response.successfulOperation) {
                        preEnrollment.type = response.content.type;
                        onResult();
                    }
                    else
                        onError();
                } catch (e) {
                    onError();
                }
            });
    else
        dispatch({
            type: types.STUDENT_PERSONAL_INFO_LOAD_END,
            student: studentLookup
        })


};

export const loadAddress = (requestIdParam, onLoad) => (dispatch, getState) => {
    let requestId = getState().preEnrollment.requestId || requestIdParam;
    dispatch({type: types.STUDENT_LOAD_ADDRESS_START});
    services()
        .getPreEnrollmentAddress(requestId)
        .then((response) => {
            dispatch({type: types.STUDENT_LOAD_ADDRESS_END, response: response});
            if (onLoad)
                onLoad();
        });
};

export const geoCodeAddress = (addressForm, callback) => (dispatch, getState) => {
    dispatch({type: types.GEOCODE_ADDRESS_START});
    services()
        .geoCodeAddress(addressForm)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.GEOCODE_ADDRESS_END, response: response});
            if (response) {
                callback(response.latitude, response.longitude);
            }

        });
};

export const getCountyCode = (addressForm, callback) => (dispatch, getState) => {
    services()
        .getCountyCode(addressForm)
        .then((response) => {
            if (response) {
                callback(response);
            }

        });
};


export const copyPhysicalToPostal = () => (dispatch, getState) => {
    let physical = getState().studentInfo.physicalAddress;
    let postal = getState().studentInfo.postalAddress;
    let postalCpy = Object.assign({}, physical);
    postalCpy.type = postal.type;
    postalCpy.id = postal.id;
    dispatch({type: types.STUDENT_CPY_PHY_TO_POS_ADDRESS, postal: postalCpy})
};

export const saveAddress = (form, onResult, onError, requestIdParam) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_SAVE_ADDRESS_START});
    form.requestId = getState().preEnrollment.requestId || requestIdParam;
    console.log(form.needTransportation);
    services()
        .savePreEnrollmentAddress(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_SAVE_ADDRESS_END, response: response});
            try {
                if (response.successfulOperation)
                    onResult();
                else {
                    onError(Utils.errorObj(response));
                    dispatch({type: types.CANCEL_BLOCK_UI});
                }
            } catch (e) {
                onError(Utils.errorObj());
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};

export const loadDemographics = () => (dispatch, getState) => {
    let student = getState().studentInfo.student;
    dispatch({type: types.STUDENT_DEMOGRAPHICS_LOAD_START});
    services()
        .getStudentDemographics(student.id)
        .then((response) => {
            dispatch({type: types.STUDENT_DEMOGRAPHICS_LOAD_END, response: response});

        });
};

export const saveDemographics = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_DEMOGRAPHICS_SAVE_START});
    let student = getState().studentInfo.student;
    let postObj = {studentId: student.id, ...form};
    services()
        .saveStudentDemographics(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_DEMOGRAPHICS_SAVE_END, response: response});
            try {
                if (response.successfulOperation)
                    onResult();
                else {
                    onError(Utils.errorObj(response));
                    dispatch({type: types.CANCEL_BLOCK_UI});
                }
            } catch (e) {
                onError(Utils.errorObj());
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};

export const saveAgreementAnswer = (requestObj, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_ADDITIONAL_INFO_START});
    services()
        .saveAgreementAnswer(requestObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_ADDITIONAL_INFO_END, response: response});
            try {
                if (response.successfulOperation)
                    onResult();
                else {
                    onError(Utils.errorObj(response));
                    dispatch({type: types.CANCEL_BLOCK_UI});
                }
            } catch (e) {
                onError(Utils.errorObj());
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};


export const saveBornPR = (answer, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_ADDITIONAL_INFO_START});
    let student = getState().studentInfo.student;
    let postObj = {studentId: student.id, answer: answer};
    services()
        .saveBornPr(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_ADDITIONAL_INFO_END, response: response});
            try {
                if (response.successfulOperation)
                    onResult();
                else {
                    onError(Utils.errorObj(response));
                    dispatch({type: types.CANCEL_BLOCK_UI});
                }
            } catch (e) {
                onError(Utils.errorObj());
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};

export const saveHispanic = (answer, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_ADDITIONAL_INFO_START});
    let student = getState().studentInfo.student;
    let postObj = {studentId: student.id, answer: answer};
    services()
        .saveHispanic(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_ADDITIONAL_INFO_END, response: response});
            try {
                if (response.successfulOperation)
                    onResult();
                else {
                    onError(Utils.errorObj(response));
                    dispatch({type: types.CANCEL_BLOCK_UI});
                }
            } catch (e) {
                onError(Utils.errorObj());
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};

export const saveNeedTransportationService = (answer, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.STUDENT_ADDITIONAL_INFO_START});
    let student = getState().studentInfo.student;
    let postObj = {studentId: student.id, answer: answer};
    services()
        .saveNeedTransportationService(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_ADDITIONAL_INFO_END, response: response});
            if (response.successfulOperation)
                onResult();
            else {
                onError(Utils.errorObj(response));
                dispatch({type: types.CANCEL_BLOCK_UI});
            }

        });
};
