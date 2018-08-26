import services from "../setup";
import * as types from "../types";

export const cleanLogin = () => (dispatch, getState) => {
    if (!getState().profile.cleanTimeoutId) {
        let timeoutId = setTimeout(() => {
            dispatch({type: types.CLEAN_LOGIN, username: getState().profile.authentication.username});
        }, 10000);
        toggleCleanTimeout(timeoutId)(dispatch, getState);
    }
};

export const toggleCleanTimeout = (timeoutId) => (dispatch, getState) => {
    //first we check if we has active timeoutId to cancel
    let currentTimeoutId = getState().profile.cleanTimeoutId;
    if (currentTimeoutId) {
        clearTimeout(currentTimeoutId);
    }
    dispatch({type: types.TOGGLE_LOGIN_CLEAN_TIMEOUT, cleanTimeoutId: timeoutId});
};

export const login = (form, onSuccess, onError) => (dispatch, getState) => {
    dispatch({type: types.AUTHENTICATING_START, username: form.username});
    //do nothing with this promise on front-end side
    return _login(form, dispatch, getState, onSuccess, onError);
};

export const logout = (onResult) => (dispatch) => {
    dispatch({type: types.LOGOUT_START});
    return _logout(onResult, dispatch);
};

export const checkSession = () => (dispatch) => {
    dispatch({type: types.SESSION_CHECK_START});
    return _session(dispatch);
};

async function _session(dispatch) {
    let sessionResp;

    try {
        sessionResp = await services().session();
    } catch (e) {
        dispatch({type: types.SESSION_CHECK_END, authenticated: false, user: {}});
    }

    switch (sessionResp && sessionResp.status) {
        case 200:
            let user = await sessionResp.json();
            dispatch({type: types.SESSION_CHECK_END, authenticated: true, user: user});
            break;
        case 401:
        case 403:
            dispatch({type: types.SESSION_CHECK_END, authenticated: false, user: {}});
    }


}

async function _login(form, dispatch, getState, onSuccess, onError) {
    let authResponse;
    let user;

    authResponse = await services().authenticate(form);
    dispatch({type: types.AUTHENTICATING_END});

    if (authResponse && authResponse.status)
        switch (authResponse.status) {
            case 401:
            case 403:
                dispatch({type: types.INVALID_CREDENTIALS});
                break;
            case 200:
                try {
                    user = await authResponse.json();
                    toggleCleanTimeout()(dispatch, getState);
                    dispatch({type: types.AUTHENTICATED, response: user});
                    await services().token();
                    let canGoHome = user.canGoHome;
                    let requestId = user.workingPreEnrollmentId && parseInt(user.workingPreEnrollmentId) > 0 ? user.workingPreEnrollmentId : "";
                    let nextPath = canGoHome ? "/home" : `/wizard/${requestId}`;
                    onSuccess(nextPath);
                } catch (e) {
                    dispatch({type: types.UNKNOWN_LOGIN_ERROR})
                }
                break;
            default:
                dispatch({type: types.UNKNOWN_LOGIN_ERROR})
        }


}

async function _logout(onResult, dispatch) {
    try {
        await services().logout();
    } catch (e) {
        console.log("error on logout")
    }

    onResult();
    dispatch({type: types.LOGOUT_END});
}

export const forgotPassword = (form, onSuccess, onError) => (dispatch, getState) => {
    let token = getState().loginHelp.token;
    dispatch({type: types.FORGOT_PASSWORD_REQUEST_START});
    services().forgotPassword(form, token)
        .then((response) => response.json())
        .then((response) => {
            dispatch({
                type: types.FORGOT_PASSWORD_REQUEST_END,
                result: response.successfulOperation,
                email: form.email
            });
            try {
                if (response.successfulOperation)
                    onSuccess();
                else
                    onError();
            } catch (e) {
                onError();
            }
        })
};

export const existsKey = (key, onError) => (dispatch) => {
    dispatch({type: types.VALIDATE_CRED_LOST_KEY_START});
    services()
        .existsKey(key)
        .then((response) => {
            dispatch({type: types.VALIDATE_CRED_LOST_KEY_END, result: response === "true"});
            if (response === "false")
                onError();
        })

};

export const resetPassword = (form, onSuccess, onError) => (dispatch, getState) => {
    let token = getState().loginHelp.token;
    dispatch({type: types.RESET_PASSWORD_START});
    services().resetPassword(form, token)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.RESET_PASSWORD_END, result: response.successfulOperation});
            try {
                if (response.successfulOperation)
                    onSuccess();
                else
                    onError();
            } catch (e) {
                onError();
            }
        })
};

export const cancelResetPassword = (key) => (dispatch) => {
    dispatch({type: types.VALIDATE_CRED_LOST_KEY_START});
    services()
        .cancelResetPassword(key)
        .then((response) => {
            dispatch({type: types.VALIDATE_CRED_LOST_KEY_END, result: response.successfulOperation});
        })
};

export const clean = () => (dispatch) => {
    dispatch({type: types.CLEAN_FORM});
};

export const triggerSessionExpiredOn = (message) => (dispatch) => {
    dispatch({type: types.TRIGGER_SESSION_EXPIRED_ON, message: message});
};

export const triggerSessionExpiredOff = (message) => (dispatch) => {
    dispatch({type: types.TRIGGER_SESSION_EXPIRED_OFF, message: message});
    unblockUI()(dispatch);
};

export const triggerErrorOn = (message) => (dispatch) => {
    dispatch({type: types.TRIGGER_ERROR_ON, message: message});

};

export const triggerErrorOff = () => (dispatch) => {
    dispatch({type: types.TRIGGER_ERROR_OFF});
    unblockUI()(dispatch);
};

export const unblockUI = () => (dispatch) => {
    let times = 0;
    while (times < 3) {
        dispatch({type: types.CANCEL_BLOCK_UI});
        times++;
    }
};

