import React from 'react'
import ReactDOM from 'react-dom'
import { Provider } from 'react-redux'
import { fromJS } from 'immutable'
import configureStore from './setup/store'
import { BrowserRouter as Router} from 'react-router-dom'
import {unregister} from './registerServiceWorker'
import Footer from "./Footer";
//css
import App from './App'
import Layout from './Layout'
import * as env from "./env";

const initialState = fromJS({});
const store = configureStore(initialState);

const component = ReactDOM.render(
    <Provider store={store}>
        <Router basename={`/${env.default.baseContext}`}>
            <Layout>
                <App/>
                {/*<Footer/>*/}
            </Layout>
        </Router>
    </Provider>,
    document.getElementById('root'));

unregister();

export function mainComponent(){
    return component;
}
