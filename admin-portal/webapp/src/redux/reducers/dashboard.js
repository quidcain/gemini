import * as constants from '../constants'

const initialState = {
    data: []           
}

export default function dashboardStore(state = initialState, action) {
    switch (action.type) {
        case constants.DASHBOARD_START:
            return state
        default:
            return state
    }
}