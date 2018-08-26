import * as types from "../types";
import * as Utils from "../../Utils";
import services from "../setup";


export const savePreEnrollment = (form, onResult, onError) => (dispatch, getState) => {

    dispatch({type: types.STUDENT_CREATE_PRE_ENROLLMENT_START});
    let found = getState().studentLookup.found;
    let studentInfo = getState().studentInfo;
    let preEnrollment = getState().preEnrollment;
    let gender = Utils.normalizeEnumValue(form.gender);
    let ssn = form.ssn;
    if (found)
        ssn = "xxx-xx-xxxx";

    form.requestId = preEnrollment.requestId;
    form.studentNumber = studentInfo.student.studentNumber;
    form.type = preEnrollment.type;
    let postObj = {type: null, firstName: null, lastName: null, dateOfBirth: null, ...form, ssn: ssn, gender: gender};
    services()
        .savePreEnrollment(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_CREATE_PRE_ENROLLMENT_END, response: response});

            try {
                if (response.successfulOperation) {
                    dispatch({type: types.STUDENT_UPDATED, student: response.content.student});
                    onResult();
                }
                else {
                    if (response.found) {
                        onError(Utils.errorObj(response, dispatch), () => {
                            dispatch({type: types.PRE_ENROLLMENT_ACTIVE_FOUND});
                        });
                    } else if (response.foundOnSIE) {
                        onError(Utils.errorObj(response, dispatch), () => {
                            dispatch({type: types.STUDENT_FOUND_ON_SIE});
                        });
                    }
                    else onError(Utils.errorObj(response, dispatch));
                }
            } catch (e) {
                onError(Utils.errorObj(response, dispatch));
            }
        });
};

export const submitPreEnrollment = (form, onResult, onError) => (dispatch) => {
    dispatch({type: types.PRE_ENROLLMENT_SUBMIT_START});
    services()
        .submitPreEnrollment(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.PRE_ENROLLMENT_SUBMIT_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });

};

export const submitVocationalPreEnrollment = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.VOCATIONAL_PRE_ENROLLMENT_SUBMIT_START});
    let preEnrollment = getState().preEnrollment;
    form = configureSubmitObject(form, preEnrollment);
    services()
        .submitVocationalPreEnrollment(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.VOCATIONAL_PRE_ENROLLMENT_SUBMIT_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });
};

export const submitAlternatePreEnrollment = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.ALTERNATE_PRE_ENROLLMENT_SUBMIT_START});
    let preEnrollment = getState().preEnrollment;
    form = configureSubmitObject(form, preEnrollment);

    services()
        .submitAlternatePreEnrollment(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.ALTERNATE_PRE_ENROLLMENT_SUBMIT_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });
};

export const partialSaveVocationalPreEnrollment = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.PARTIAL_VOC_PRE_ENROLLMENT_SAVE_START});
    let preEnrollment = getState().preEnrollment;
    form = configureSubmitObject(form, preEnrollment);
    services()
        .partialSaveVocationalPreEnrollment(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.PARTIAL_VOC_PRE_ENROLLMENT_SAVE_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });
};

export const partialAlternatePreEnrollmentSave = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.PARTIAL_ALT_PRE_ENROLLMENT_SAVE_START});
    let preEnrollment = getState().preEnrollment;
    form.requestId = preEnrollment.requestId;
    form = configureAlternateSubmitObject(form, preEnrollment);
    services()
        .partialAlternatePreEnrollmentSave(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.PARTIAL_ALT_PRE_ENROLLMENT_SAVE_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });
};

export const retrieveVocationalPreEnrollment = (onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.VOCATIONAL_PRE_ENROLLMENT_RETRIEVE_START});
    let preEnrollment = getState().preEnrollment;
    let requestId = preEnrollment.requestId;

    // services()
    services()
        .getActiveVocationalPreEnrollment(requestId)
        .then((response) => {
            dispatch({type: types.VOCATIONAL_PRE_ENROLLMENT_RETRIEVE_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });


};

export const retrieveAlternatePreEnrollment = (onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.ALTERNATE_PRE_ENROLLMENT_RETRIEVE_START});
    let preEnrollment = getState().preEnrollment;
    let requestId = preEnrollment.requestId;

    // services()
    return services()
        .getActiveAlternatePreEnrollment(requestId)
        .then((response) => {
            dispatch({type: types.ALTERNATE_PRE_ENROLLMENT_RETRIEVE_END, response: response});
            handleResult(response, onResult, onError, dispatch);
        });


};

export const changeCurrentVocationalEnrollment = (currentEnrollment) => (dispatch) => {
    dispatch({type: types.CHANGE_VOCATIONAL_PRE_ENROLLMENT, enrollment: currentEnrollment})
};

export const saveReasonForNotAttendingSchool = (option, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.REASON_FOR_NOT_ATTENDING_SAVE_START});
    let preEnrollment = getState().preEnrollment;
    let reason = Utils.normalizeEnumValue(option);
    let form = {requestId: preEnrollment.requestId, reason: reason};
    services()
        .saveReasonForNotAttending(form)
        .then((response) => response.json())
        .then((response) => {
            let reasonSaved = response.successfulOperation ? reason : null;
            dispatch({
                type: types.REASON_FOR_NOT_ATTENDING_SAVE_END,
                regionResponse: reasonSaved,
                saved: response.successfulOperation
            });
            handleResult(response, onResult, onError, dispatch);
        });
};

export const addSharedSchoolToPreEnrollment = (form, onResult, onError) => (dispatch, getState) => {
    dispatch({type: types.ADD_SHARED_SCHOOL_SAVE_START});
    services()
        .saveSharedSchools(form)
        .then((response) => response.json())
        .then((response) => {
            dispatch({
                type: types.ADD_SHARED_SCHOOL_SAVE_END,
                saved: response.successfulOperation
            });
            handleResult(response, onResult, onError, dispatch);
        });
};


function configureSubmitObject(form, preEnrollment) {
    form.requestId = preEnrollment.requestId;
    form.type = preEnrollment.type;
    return form;
}

function configureAlternateSubmitObject(form, preEnrollment) {
    form.requestId = preEnrollment.requestId;
    preEnrollment.type = form.type;
    return form;
}

function handleResult(response, onResult, onError, dispatch) {
    try {
        if (response.successfulOperation)
            onResult();
        else {
            onError(Utils.errorObj(response, dispatch));
        }
    } catch (e) {
        onError(Utils.errorObj(response, dispatch));
    }
}