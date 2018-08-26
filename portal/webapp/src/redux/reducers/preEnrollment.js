import * as types from "../types";
import * as Utils from "../../Utils";

const initialState = {
    alternatePreEnrollmentLoad: false,
    requestId: null,
    type: "REGULAR",
    info: {
        regionId: null,
        schoolId: null,
        schoolName: null,
        schoolAddress: {addressFormatted: "Test"},
        nextGradeLevel: null,
        nextGradeLevelDescription: null
    },
    //this for the form being editing
    currentVocationalEnrollment: {
        id: null,
        nextGradeLevel: null,
        schoolId: null,
        programs: [],
        programsToDelete: []
    },
    alternateSchoolEnrollment: {
        nextGradeLevel: null,
        alternateSchools: [],
        alternateSchoolsToDelete: []
    },
    //validations flags
    foundPreviousEnrollment: false,
    initialPreEnrollmentSaved: false,
    completePreEnrollment: false,
    activePreEnrollmentFound: false,
    foundOnSie: false,
    reasonSaved: false,
    reasonSelected: null
};

const preEnrollment = (state = Utils.freezeObject(initialState), action) => {
    switch (action.type) {

        case types.STUDENT_CREATE_PRE_ENROLLMENT_START:
        case types.VOCATIONAL_PRE_ENROLLMENT_RETRIEVE_START:
        case types.PARTIAL_ALT_PRE_ENROLLMENT_SAVE_START:
        case types.REASON_FOR_NOT_ATTENDING_SAVE_START:
            return state;
        case types.STUDENT_CREATE_PRE_ENROLLMENT_END:
        case types.VOCATIONAL_PRE_ENROLLMENT_RETRIEVE_END:
            let resp = action.response.content;
            return {
                ...state,
                info: resp,
                requestId: action.response.requestId || state.requestId,
                initialPreEnrollmentSaved: !resp
            };
        case types.PRE_ENROLLMENT_ACTIVE_FOUND:
            return {
                ...state,
                activePreEnrollmentFound: true
            };
        case types.STUDENT_FOUND_ON_SIE:
            return {
                ...state,
                foundOnSie: true
            };
        case types.ON_WIZARD_LOAD_START:
            return {
                ...state,
                activePreEnrollmentFound: false
            };
            return {...state, activePreEnrollmentFound: false};
        case types.PRE_ENROLLMENT_SUBMIT_START:
            return state;
        case types.PRE_ENROLLMENT_SUBMIT_END:
            return {...state, completePreEnrollment: action.response.successfulOperation, type: "REGULAR"};
        case types.CHANGE_VOCATIONAL_PRE_ENROLLMENT:
            return {
                ...state, currentVocationalEnrollment: action.enrollment || {
                    id: null,
                    nextGradeLevel: null,
                    schoolId: null,
                    programs: [],
                    programsToDelete: []
                }
            };
        case types.ALTERNATE_PRE_ENROLLMENT_RETRIEVE_START:
            return {...state, alternatePreEnrollmentLoad: false};
        case types.ALTERNATE_PRE_ENROLLMENT_RETRIEVE_END:
            let altResp = action.response.content;
            return {
                ...state,
                info: altResp,
                requestId: action.response.requestId,
                alternatePreEnrollmentLoad: true,
                alternateSchoolEnrollment: {
                    alternateSchools: altResp.alternateSchools,
                    alternateSchoolsToDelete: []
                }
            };
        case types.REASON_FOR_NOT_ATTENDING_SAVE_END:
            return {...state, reasonSaved: action.saved, reasonSelected: action.regionResponse};
        case types.STUDENT_SEARCH_START:
            return {...state, foundOnSie: false};
        case types.HOME_LOAD_END:
            return Utils.freezeObject(initialState);
        default:
            return state;
    }
};

export default preEnrollment;