/**
 * Created by fran on 2/1/18.
 */
import React, {Component} from "react";
import TextInput from "../../components/TextInput";
import {connect} from "react-redux";
import * as store from "../../setup/store";
import * as UIHelper from "../../UIHelper";
import swal from 'sweetalert';

import * as Actions from '../../redux/actions'

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            valid: false,
            token: null,
            username: '',
            password: ''
        };
        this.loginUser = this.loginUser.bind(this);
        this.inputHandlerUsername = this.inputHandlerUsername.bind(this);
        this.inputHandlerPass = this.inputHandlerPass.bind(this);
    }

    async loginUser(e) {
        e.preventDefault();
        let crendentials = {username: this.state.username, password: this.state.password};
        let authResponse;
        let user;

        try {
            authResponse = await store.services().authenticate(crendentials);
            user = await authResponse.json();
        } catch (e) {
            swal("Error al autenticar");
            return;
        }

        if (authResponse && authResponse.status)
            if (authResponse.status === 200) {
                store.setSession(user);
                this.props.history.push(`/dashboard/mainchart`);
            } else {
                swal("Error autenticando verifique sus credenciales");
            }


        // this.props.loginUser()

    }

    inputHandlerUsername(e) {
        let element = e.target;
        this.setState({
            username: element.value
        })
    }

    inputHandlerPass(e) {
        let element = e.target;
        this.setState({
            password: element.value
        })
    }

    render() {
        let form = this.props.form;
        return (
            <div className='dashboard'>
                <div className='main'>
                    <div className='display full'>
                        <div className='panel panel-default panel-small'>
                            <div className='panel-heading'>
                                <h2>Login</h2>
                            </div>
                            <form className='form login-form'>
                                <div className='panel-body'>
                                    <div className="col-md-12 content-section">
                                        <div className="title">
                                            <div className="description"><h2>{UIHelper.getText("projectName")}</h2>
                                                <div className="violet-line"/>
                                            </div>
                                            <span className="f20slg">{UIHelper.getText("loginPage")}</span>
                                        </div>
                                        <div className="body d-flex align-items-center flex-column justify-content-end">
                                            <form>
                                                <div className="row plr15 ">
                                                    <div className="col-md-12">
                                                        <TextInput id="username"
                                                                   type="text"
                                                                   ref="username"
                                                                   label="Usuario SIE"
                                                                   onChange={this.inputHandlerUsername}
                                                                   value={this.state.username}
                                                                   iconname="icon-human"
                                                                   grouped/>
                                                    </div>
                                                </div>
                                                <div className="row plr15">
                                                    <div className="col-md-12">
                                                        <TextInput id="password"
                                                                   type="password"
                                                                   ref="password"
                                                                   label="Password"
                                                                   onChange={this.inputHandlerPass}
                                                                   value={this.state.password}
                                                                   iconname="icon-eye"
                                                                   grouped/>
                                                    </div>
                                                </div>

                                                <div className="row mt50">
                                                    <div className="col-md-12 ">
                                                        <button
                                                            style={{paddingLeft: 20}}
                                                            className="button-green mr30 mob-mb30px"
                                                            // type="submit"
                                                            onClick={this.loginUser}
                                                        >
                                                            {UIHelper.getText("loginButton")}
                                                        </button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

function mapStateToProps(store) {
    return {form: store.login.form}
}

function mapDispatchToActions(dispatch) {
    return {
        loginUser: function (data) {
            return dispatch(Actions.loginUser(data))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToActions)(Login)
