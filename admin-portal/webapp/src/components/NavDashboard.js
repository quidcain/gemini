import React from 'react'
import {Link, withRouter} from 'react-router-dom'
import logo from '../assets/img/logo.png'
import * as store from "../setup/store";

class NavDashboard extends React.Component {
    state = {
        isVisible: false
    };

    constructor(props) {
        super(props);
        this.handleLogout = this.handleLogout.bind(this);
    }

    async handleLogout() {
        try {
            console.log("before")
            await store.services().logout();
            console.log("after")
            this.props.history.push(`/`);
        } catch (e) {
            console.log("error at logout")
        }
    }


    render() {
        let style = {};
        return (
            <nav className='navbar navbar-default' style={style}>
                <div className='navbar-header'>
                    <Link className='navbar-brand' to='/home'>
                        <img src={logo} alt=''/>
                    </Link>
                </div>
                <ul className='nav navbar-nav navbar-right'>
                    <li className=''>
                        <a className='logout' onClick={this.handleLogout}>
                            Salir
                        </a>
                    </li>
                </ul>
            </nav>
        )
    }
}

export default withRouter(NavDashboard);

