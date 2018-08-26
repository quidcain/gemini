import services from "../setup";
import * as types from "../types";
import * as Utils from "../../Utils";

export const saveProfile = (form, onResult, onError) => (dispatch) => {
    dispatch({type: types.PROFILE_UPDATE_START});
    let relation = Utils.normalizeEnumValue(form.relationType);
    let postObj = {
        firstName: null,
        middleName: null,
        lastName: null,
        dateOfBirth: null, ...form,
        relationType: relation
    };
    services()
        .saveProfile(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.PROFILE_UPDATE_END, user: response.content || {}});
            if (response.successfulOperation)
                onResult();
            else {
                onError(Utils.errorObj(response));
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        })
};

export const completeProfile = (form, onResult, onError) => (dispatch) => {
    dispatch({type: types.PROFILE_UPDATE_START});
    let income = form.income ? form.income.replace(/[^0-9\.-]+/g, "") : null;
    let postObj = {totalFamilyMembers: null, educationLevel: null, ...form, income: income};
    services()
        .saveCompleteProfile(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.PROFILE_UPDATE_END, user: response.content || {}});
            if (response.successfulOperation)
                onResult();
            else {
                onError(Utils.errorObj(response));
                dispatch({type: types.CANCEL_BLOCK_UI});
            }
        });
};
