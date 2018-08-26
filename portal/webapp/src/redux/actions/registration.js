import services from "../setup";
import * as types from "../types";
import * as Utils from "../../Utils";

export const registerUser = (userForm, success, error) => (dispatch, getState) => {
    let token = getState().registration.form.token;
    dispatch({type: types.REGISTER_START});
    services()
        .registerAccount(userForm, token)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.REGISTER_END});
            response.successfulOperation ? success() : error()
        })

};

export const existsCode = (code, onInvalidCode) => (dispatch) => {
    dispatch({type: types.ACTIVATION_USER_CLEAN});
    dispatch({type: types.USER_LOCATION_START});
    services()
        .existsCode(code)
        .then((response) => {
            dispatch({type: types.USER_LOCATION_END, result: response === "true"});
            if (response !== "true")
                onInvalidCode()
        });

};

export const activateAccount = (activationForm, success, error) => (dispatch, getState) => {
    let token = getState().registration.activationForm.token;
    dispatch({type: types.ACTIVATION_USER_START});
    services()
        .activateAccount(activationForm, token)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.ACTIVATION_USER_END, result: response.successfulOperation});
            response.successfulOperation ? success() : error()
        });
};

export const cleanRegistration = () => (dispatch) => {
    dispatch({type: types.REGISTER_CLEAN});

};
