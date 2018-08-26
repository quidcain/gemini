import * as types from "../types";
import services from "../setup";

export const loadHome = () => (dispatch) => {
    dispatch({type: types.HOME_LOAD_START});
    services()
        .home()
        .then((response) => {
            dispatch({type: types.HOME_LOAD_END, response: response});
        })

};