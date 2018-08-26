import React, {Component} from "react";
import * as store from "../../../../setup/store"
import swal from "sweetalert";
import * as Utils from "../../../../Utils";
import TextInput from "../../../../components/TextInput";

export default class EditUserForm extends Component {

    constructor(props) {
        super(props);
        this.state = {user: null, comment: null, password: null};
        this.inputHandler = this.inputHandler.bind(this);
        this.save = this.save.bind(this);
    }

    componentWillMount() {
        let userId = this.props.match.params.id;
        store.services()
            .getEditUser(userId)
            .then((result) => {
                this.setState({...this.state, user: result});
            })
    }

    save() {
        let userId = this.props.match.params.id;
        let form = {userId: userId, comment: this.state.comment, password: this.state.password};
        let isActive = this.state.user.enabled;

        if(!isActive){
            if(!this.state.password) {
                swal("!!!Debe ingresar una contraseña para poder activar la cuenta!!!", {icon: "error"});
                return;
            }
        }

        if(this.state.password && this.state.password.length < 4){
            swal("!!!Debe ingresar una contraseña de al menos 4 caracteres!!!", {icon: "error"});
            return;
        }


        store.services()
            .activateUser(form)
            .then((response) => {
                if (response.status === 200) {
                    swal("!!!Los datos del Usario fueron grabados correctamente!!!", {icon: "info"});
                    this.props.history.push(`/dashboard/users/manage/${this.state.user.userId}`)
                } else {
                    swal("!!!Los datos del Usario NO fueron grabados correctamente!!!", {icon: "error"});
                }
            })

    }

    inputHandler(e) {
        let element = e.target;
        let form = this.state;
        form[element.id] = element.value;
    }


    render() {
        let user = this.state.user;
        if (!user)
            return (null);

        let actionLabel = user.enabled ? "Cambiar Contraseña" : "Activar";
        return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Editar Usario</h3>
                </div>
            </div>,
            <div className="row" style={{backgroundColor: "white", padding: 40}}>
                <div className="col-md-12">
                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Email del Padre/Madre:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{user.email}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Activada?
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{user.activated}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Completo Perfil?
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{user.profileCompleted}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Nombre del Padre/Madre:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{user.fatherName}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Fecha de nacimiento:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{Utils.format(user.dateOfBirth, 'DDMMMMYYYY')}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Ultimo acceso:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{Utils.format(user.lastLogin, 'DDMMMMYYYY h:mm:ss a')}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Fecha de creaci&oacute;n:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{Utils.format(user.creationDate, 'DDMMMMYYYY h:mm:ss a')}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Ultimo Modificaci&oacute;n del Admin:
                        </div>
                        <div className="col-md-8">
                            <span className="text-info">{Utils.format(user.revisionDate, 'DDMMMMYYYY h:mm:ss a')}</span>
                        </div>
                    </div>

                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Contrase&ntilde;a:
                        </div>
                        <div className="col-md-8" style={{padding: 10}}>
                            <TextInput id="password"
                                       type="password"
                                       ref="password"
                                       label="Password"
                                       onChange={this.inputHandler}/>
                        </div>
                    </div>


                    <div className="row" style={{padding: 10}}>
                        <div className="col-md-4">
                            Comentario:
                        </div>
                        <div className="col-md-8" style={{padding: 20}}>
                            <textarea id="comment" style={{height: 75, width: "100%"}}
                                      onChange={this.inputHandler}></textarea>
                        </div>
                    </div>
                    <div className="clearfix mt-4">
                        <div className="float-right">
                            <button className="button-green" style={{paddingLeft: 10}}
                                    onClick={this.save}>{actionLabel}
                            </button>
                        </div>
                    </div>
                </div>
            </div>];
    }

}