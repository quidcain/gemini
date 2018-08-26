import * as types from "../types";
import * as Utils from "../../Utils";

const initialState = {
    preEnrollments: [],
    canAddPreEnrollments: true
};

const home = (state = Utils.freezeObject(initialState), action) => {
    switch (action.type) {
        case types.HOME_LOAD_START:
            return  Utils.freezeObject(initialState);
        case types.HOME_LOAD_END:
            return {...state, preEnrollments: action.response.preEnrollments};
        default:
            return state;
    }
};

export default home;