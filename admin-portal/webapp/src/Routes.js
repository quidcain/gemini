import React, {Component} from 'react'
import { Redirect, Route, Switch, withRouter } from 'react-router-dom'
import AuthRoute from './routes/authRoute'
import GuestRoute from './routes/guestRoute'

import Dashboard from './container/dashboard'
import Login from './container/login'

class Routes extends Component {
    render() {
        return (
            <Switch>
                {/* <AuthRoute exact path='/' component={Dashboard}/>
                <GuestRoute path='/login' component={Login}/> */}
                <GuestRoute exact path='/' component={Login}/>
                <Route path='/dashboard' component={Dashboard}/>
            </Switch>
        );
    }
}

export default withRouter(Routes);
