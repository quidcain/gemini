import { createAction } from 'redux-actions'
import * as constants from '../constants'

export const dashboard = createAction(
    constants.DASHBOARD_START
);


export const loadRegions = createAction(
    constants.REGIONS_START
);


export const loadCities = createAction(
    constants.CITIES_START
);


export const loadSchools = createAction(
    constants.SCHOOLS_START
);

export const fetchDashboardData = createAction(
    constants.FETCH_DASHBOARD_DATA_START
);

