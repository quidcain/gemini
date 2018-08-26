import React, {Component} from 'react'
import Routes from './Routes'
import BlockUi from 'react-block-ui';
import 'react-block-ui/style.css';
import * as store from "./setup/store";
import moment from "moment";
import esLocale from "moment/locale/es";
import { withRouter } from 'react-router-dom'
import * as env from "./env";

moment.updateLocale('es', esLocale);

class App extends Component {

    constructor(props){
        super(props);
        this.state = {blocking: false};
        this.sessionCheck = false;
    }

    componentWillMount() {
        if (this.props.location.pathname !== "/" && !this.sessionCheck) {
            this.session();
        }

    }


    async session(){
        let sessionResp;
        try {
            sessionResp = await store.services().session();
        } catch (e) {
            store.setSession({});
        }

        switch (sessionResp && sessionResp.status) {
            case 200:
                let user = await sessionResp.json();
                store.setSession(user);
                this.forceUpdate();
                break;
            case 401:
            case 403:
                this.state.sessionCheck = true;
        }
        this.sessionCheck = true;
    }


    render() {
        return (
            <BlockUi tag="div" blocking={store.blockUI()}>
                <div>
                    <Routes/>
                </div>
            </BlockUi>
        )
    }
}
export default withRouter(App);
