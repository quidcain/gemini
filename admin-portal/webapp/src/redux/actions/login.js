import { createAction } from 'redux-actions'
import * as constants from '../constants'

export const loginUser = createAction(
    constants.LOGIN_START,
    data => ({ data })
)
