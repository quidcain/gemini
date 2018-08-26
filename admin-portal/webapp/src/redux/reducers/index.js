import { combineReducers } from 'redux'
import loginStore from './login'
import dashboardStore from './dashboard'

const rootReducer = combineReducers({
    login: loginStore,
    dashboard: dashboardStore
})

// const rootReducer = (state, action) => {
//     return appReducer(state, action)
// }

export default rootReducer
