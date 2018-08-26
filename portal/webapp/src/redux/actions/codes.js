import * as types from "../types";
import services from "../setup";

export const loadCodes = (promise) => (dispatch) => {
    Promise.all([getRegions(dispatch), getGradeLevels(dispatch), promise()]).then(() => {
        //can enable school select
        // alert("regions");
    });
};

//todo: fran validate this!!!
export const loadVocationalCodes = () => (dispatch) => {
    Promise.all([getVocationalRegions(dispatch), getGradeLevels(dispatch, types.OCCUPATIONAL)]).then(() => {
    });
};

export const loadSpecializedCodes = () => (dispatch) => {
    Promise.all([getRegions(dispatch), getGradeLevels(dispatch, types.SPECIALIZED), getSpecializedSchoolCategories(dispatch)]).then(() => {
    });
};


function getVocationalRegions(dispatch) {
    dispatch({type: types.REGION_LOAD_START});
    return services().getVocationalRegions().then((response) => {
        dispatch({type: types.REGION_LOAD_END, response: response});
    });
}

function getRegions(dispatch) {
    dispatch({type: types.REGION_LOAD_START});
    return services().getRegions().then((response) => {
        dispatch({type: types.REGION_LOAD_END, response: response});
    });
}

function getGradeLevels(dispatch, category) {
    dispatch({type: types.GRADELEVEL_LOAD_START});
    return services().getGradeLevels(category).then((response) => {
        dispatch({type: types.GRADELEVEL_LOAD_END, response: response});
    });
}

function getSpecializedSchoolCategories(dispatch) {
    dispatch({type: types.CATEGORIES_LOAD_START});
    return services().getSpecializedSchoolCategories().then((response) => {
        dispatch({type: types.CATEGORIES_LOAD_END, response: response});
    });
}

export const getSchools = (regionId, gradeLevel, onResult) => (dispatch) => {
    dispatch({type: types.SCHOOL_LOAD_START});
    return services().getSchoolsByRegionAndGradeLevel(regionId, gradeLevel).then((response) => {
        dispatch({type: types.SCHOOL_LOAD_END, response: response});
        if (onResult) {
            onResult();
        }
    });
};

export const getSpecializedSchools = (regionId, gradeLevel, category) => (dispatch) => {
    dispatch({type: types.SCHOOL_LOAD_START});
    return services().getSpecializedSchoolsByRegionAndGradeLevel(regionId, gradeLevel, category).then((response) => {
        dispatch({type: types.SCHOOL_LOAD_END, response: response});
    });
};

export const getVocationalSchools = (regionId, gradeLevel) => (dispatch) => {
    dispatch({type: types.SCHOOL_LOAD_START});
    return services().getVocationalSchoolsByRegionAndGradeLevel(regionId, gradeLevel).then((response) => {
        dispatch({type: types.SCHOOL_LOAD_END, response: response});
    });
};

export const getVocationalPrograms = (schoolId) => (dispatch) => {
    dispatch({type: types.VOCATIONAL_PROGRAMS_START});
    return services().getVocationalProgramsBySchool(schoolId).then((response) => {
        dispatch({type: types.VOCATIONAL_PROGRAMS_END, response: response});

    });
};

export const getTechnicalSchools = () => (dispatch) => {
    dispatch({type: types.SCHOOL_LOAD_START});
    return services().getTechnicalSchools().then((response) => {
        dispatch({type: types.SCHOOL_LOAD_END, response: response});
    });
};

export const getSharedSchools = (gradeLevel, onResult) => (dispatch) => {
    dispatch({type: types.SCHOOL_LOAD_START});
    return services().getSharedSchools(gradeLevel).then((response) => {
        dispatch({type: types.SCHOOL_LOAD_END, response: response});
        if (onResult)
            onResult();
    });
};

export const getReasonForAttendingCodes = () => (dispatch) => {
    dispatch({type: types.REASONS_LOAD_START});
    return services().getReasonsForNotAttendingSchools().then((response) => {
        dispatch({type: types.REASONS_LOAD_END, response: response});
    });
};