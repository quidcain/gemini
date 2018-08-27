/**
 * Created by fran on 1/26/18.
 */
import React, {Component} from "react";
import {Button} from "react-bootstrap";
import CodeSelect from "../../components/CodeSelect";
import PhoneInput from "../../components/PhoneInput";
import TextInput from "../../components/TextInput";

export default class EmergencyContacts extends Component {

    constructor(props) {
        super(props);
        this.state = {
            form: {name: "", lastname: "", relationType: "", relationTypeDesc: "", phoneNumber: ""},
            contacts: []
        };

        this.inputHandler = this.inputHandler.bind(this);
        this.save = this.save.bind(this);
        this.cancel = this.cancel.bind(this);
    }

    clear(form) {
        let cloneObj = Object.assign({}, form);
        for (let prop in cloneObj) {
            if (cloneObj.hasOwnProperty(prop))
                cloneObj[prop] = '';
        }
        return cloneObj;
    }

    save() {
        let form = this.state.form;
        let contacts = this.state.contacts;
        contacts.push(form);
        this.setState({form: this.clear(form), contacts: contacts});
    }

    cancel() {
        let form = this.state.form;
        this.setState({form: this.clear(form), contacts: this.state.contacts,});

    }

    inputHandler(e) {
        let form = this.state.form;
        let element = e.target;
        form[element.id] = element.value;
        if (element.id === "relationType") {
            let index = element.selectedIndex;
            form.relationTypeDesc = element[index].text;
        }
        this.setState({...this.state, form: form});
    }

    render() {
        return (
            <div className="row">
                <div className="col-md-6">
                    {this.renderForm()}
                </div>

                <div className="col-md-6">
                    {this.renderList()}
                </div>
            </div>
        );
    }

    renderList() {
        let contacts = this.state.contacts;
        return (<div>
            <div className="panel panel-primary">
                <div className="panel-heading">
                    <h3 className="panel-title">Contactos de emergencia</h3>
                </div>
                <div className="panel-body">
                    {contacts && contacts.length > 0
                        ? contacts.map((contact, i) => (
                            <div key={i} style={{padding: 10, borderBottomColor: 'gray', borderBottomWidth: 1}}>
                                <div className="row">
                                    <div className="col-md-6">
                                        {contact.name + ' ' + contact.lastname}
                                    </div>
                                    <div className="col-md-6">
                                        {contact.phoneNumber}
                                    </div>

                                </div>
                                <div className="row">
                                    <div className="col-md-12">
                                        <span className="label label-primary">{contact.relationTypeDesc}</span>
                                    </div>
                                </div>
                            </div>

                        ))
                        : (<div style={{padding: 10}}>
                            <label>No posee ningun contatcto de emergencia</label>
                        </div>)}


                </div>
            </div>
        </div>);
    }

    renderForm() {
        let form = this.state.form;
        return (<form id="contact-form">
            <div className="row">
                <div className="col-md-6">
                    <TextInput id="name"
                               type="name"
                               label="Nombre"
                               placeholder="Nombre"
                               value={form.name}
                               onChange={this.inputHandler}
                    />

                </div>
                <div className="col-md-6">
                    <TextInput id="lastname"
                               type="lastname"
                               label="Apellido"
                               placeholder="Apellido"
                               value={form.lastname}
                               onChange={this.inputHandler}
                    />
                </div>
            </div>

            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="relationType"
                                value={form.relationType}
                                label="Relacion"
                                onChange={this.inputHandler}
                                placeholder="Seleccione relación con el estudiante"
                                codeType="relationTypes"/>
                </div>

                <div className="col-md-6">
                    <PhoneInput id="phoneNumber"
                                label="Teléfono"
                                placeholder="Teléfono"
                                value={form.phoneNumber}
                                onChange={this.inputHandler}/>
                </div>
            </div>

            <div className="row">
                <div className="col-md-12">
                    <div className=" pull-right">
                        <Button onClick={this.cancel} style={{marginRight: 5}} bsStyle="primary">Cancelar</Button>
                        <Button onClick={this.save} bsStyle="primary">Guardar</Button>
                    </div>
                </div>
            </div>
        </form>);
    }
}

