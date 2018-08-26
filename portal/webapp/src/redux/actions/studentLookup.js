import services from "../setup";
import * as types from "../types";
import * as Utils from "../../Utils";

export const searchStudent = (criteria, onResult, onError) => (dispatch) => {
    dispatch({type: types.STUDENT_SEARCH_START, form: criteria});
    let dob = Utils.toDateString(criteria.dateOfBirth);
    let postObj = {firstName: null, lastName: null, studentNumber: null, lastSsn: null, ...criteria, dateOfBirth: dob};
    services()
        .searchStudent(postObj)
        .then((response) => response.json())
        .then((response) => {
            dispatch({type: types.STUDENT_SEARCH_END});
            try {
                if (response.found) {
                    dispatch({type: types.STUDENT_FOUND, result: response});
                    onResult(types.ON_FOUND_CALLBACK);
                } else {
                    if (response.errorOperation && response.validationMessages) {
                        onError(Utils.errorObj(response, dispatch));
                        dispatch({type: types.CANCEL_BLOCK_UI});
                    }
                    else {
                        // onError(Utils.errorObj(response, dispatch));
                        dispatch({type: types.STUDENT_NOT_FOUND});
                        onResult(types.ON_NOT_FOUND_CALLBACK);
                    }
                }
            } catch (e) {
                onError();
            }

        });
};
