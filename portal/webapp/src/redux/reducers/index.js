import * as types from "../types";
import {combineReducers} from "redux";
import registration from "./registration";
import loginHelp from "./loginHelp";
import profile from "./profile"
import home from "./home";
import wizard from "./wizard";
import studentLookup from "./studentLookup";
import studentInfo from "./studentInfo";
import preEnrollment from "./preEnrollment";
import config from "./config";

const appReducer = combineReducers({
    registration,
    loginHelp,
    profile,
    home,
    wizard,
    studentLookup,
    studentInfo,
    preEnrollment,
    config
});


const rootReducer = (state, action) => {
    switch (action.type) {
        case types.LOGOUT_END:
        case types.RESET_PASSWORD_END:
        case types.FORGOT_PASSWORD_REQUEST_END:
        case types.TRIGGER_SESSION_EXPIRED_OFF:
            console.log('cleaning');
            state = undefined;
            break;
    }

    return appReducer(state, action)
};

export default rootReducer;

