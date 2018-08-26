import React from "react";
import ReactDOM from "react-dom";
import {Provider} from "react-redux";
import {setupStore} from "./redux/setup";
import {BrowserRouter as Router} from "react-router-dom";
import { unregister } from './registerServiceWorker';
//css
import 'react-block-ui/style.css';
import App from "./App";
import * as env from "./env";
import ErrorCatcher from "./ErrorCatcher";

let store = setupStore();

ReactDOM.render(
    <Provider store={store}>
        <Router basename={`/${env.default.baseContext}`}>
            <ErrorCatcher>
                <App/>
            </ErrorCatcher>
        </Router>
    </Provider>,
    document.getElementById('root'));

unregister();
