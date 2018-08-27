import * as types from "../types";
import * as Utils from "../../Utils";

const initialState = {
    student: {},
    demographics: {
        language: null
        , citizenship: null
        , ethnicCodes: []
        , ethnicCodesToDelete: []
        , saved: false
    },
    geoCodeAddress: {latitude: null, longitude: null},
    physicalAddress: {line1: '', line2: '', city: '', country: '', zipcode: ''},
    postalAddress: {line1: '', line2: '', city: '', country: '', zipcode: ''},
    needTransportation: false,
    addressLoad: false,
    studentScheduleView: [],
    fullName: null,
    gradeLevel: null,
    school: null
};

const studentInfo = (state = Utils.freezeObject(initialState), action) => {
    switch (action.type) {
        case types.STUDENT_PERSONAL_INFO_LOAD_START:
            return state;
        case types.STUDENT_PERSONAL_INFO_LOAD_END:
            return {...state, student: action.student || {}};
        case types.STUDENT_LOAD_ADDRESS_START:
            return {...state, addressLoad: false};
        case types.STUDENT_LOAD_ADDRESS_END:
            return {
                ...state,
                physicalAddress: action.response && action.response.physical,
                postalAddress: action.response && action.response.postal,
                needTransportation: action.response && action.response.needTransportation,
                addressLoad: true
            };
        case types.GEOCODE_ADDRESS_START:
            return {...state, geoCodeAddress: {latitude: null, longitude: null}};
        case types.GEOCODE_ADDRESS_END:
            return {
                ...state,
                geoCodeAddress: action.response
            };
        case types.STUDENT_CPY_PHY_TO_POS_ADDRESS:
            return {...state, postalAddress: action.postal};
        case types.STUDENT_UPDATED:
            return {...state, student: action.student};
        case types.STUDENT_DEMOGRAPHICS_SAVE_END:
            return {...state, demographics: {...state.demographics, saved: true}};
        case types.STUDENT_DEMOGRAPHICS_LOAD_END:
            return {...state, demographics: action.response.content || state.demographics};
        case types.STUDENT_SCHEDULE_START:
            return {...state, studentScheduleView: []};
        case types.STUDENT_SCHEDULE_END:
            return {...state, studentScheduleView: action.response};
        case types.SET_STUDENT_SELECT:
            let resp = action.response;
            return {
                ...state, fullName: resp.fullName,
                gradeLevel: resp.gradeLevel,
                school: resp.school
            };
        case types.HOME_LOAD_END:
            return Utils.freezeObject(initialState);

        default:
            return state;
    }
};

export default studentInfo;