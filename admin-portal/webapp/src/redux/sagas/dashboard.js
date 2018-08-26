import { delay } from 'redux-saga'
import { put, call, takeEvery, takeLatest } from 'redux-saga/effects'

import * as constants from '../constants'

function* asyncDashboardStart() {
    console.log('asyncDashboardStart')
}

export function* storeDashboard() {
    yield takeLatest(constants.DASHBOARD_START, asyncDashboardStart)
}

export default [
    storeDashboard()
]
