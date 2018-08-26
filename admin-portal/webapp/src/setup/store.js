import {applyMiddleware, compose, createStore} from 'redux'
import createSagaMiddleware from 'redux-saga'

import createReducer from './reducer'
import rootSaga from './sagas'
import Services from './standalone-services'
import {mainComponent} from "../index";

const sagaMiddleware = createSagaMiddleware();

let instance = null;
let user = null;
let blocking = false;

let blockingCount = 0;
let unblockingCount = 0;

let data = {};


export default function configureStore(initialState = {}, history) {
    const middlewares = [
        sagaMiddleware,
    ];
    const enhancers = [
        applyMiddleware(...middlewares),
    ];

    const composeEnhancers =
        process.env.NODE_ENV !== 'production' &&
        typeof window === 'object' &&
        window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
            window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ : compose;

    const store = createStore(
        createReducer(),
        initialState,
        composeEnhancers(...enhancers),
    );

    instance = new Services(store);

    sagaMiddleware.run(rootSaga);
    return store
}

export function services() {
    return instance
}

export function setSession(sessionUser) {
    user = sessionUser;
}

export function clearSessionData() {
    data = {};
}

export function setSessionData(keyValue) {
    data = {...data, ...keyValue};
}

export function getUser() {
    return user;
}

export function getDataByKey(key) {
    return data[key];
}

export function toggleBlockVariable(action) {
    console.log("blocking " + action);
    blocking = action;
    // if (action)
    //     blockingCount++;
    // else
    //     blockingCount--;

    mainComponent()
        ? mainComponent().setState({blocking: blocking})
        : null;
}

export function blockUI() {
    return blocking;
}
