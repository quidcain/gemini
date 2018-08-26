import * as types from "../types";
import * as Utils from "../../Utils";

const initialState = {
    form: {},
    student: null,
    found: false
};

const studentLookup = (state = Utils.freezeObject(initialState), action) => {
    switch (action.type) {
        case types.STUDENT_SEARCH_START:
            return {...state, form: action.form};
        case types.STUDENT_SEARCH_END:
            return state;
        case types.STUDENT_FOUND:
            return {...state, student: action.result, found: true};
        case types.STUDENT_NOT_FOUND:
            return {...state, student: null, found: false};
        case types.HOME_LOAD_END:
            return Utils.freezeObject(initialState);
        default:
            return state;
    }
};
export default studentLookup;