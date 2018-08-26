import React, {Component} from "react";
import AnimationHelper from "../components/AnimationHelper";
import * as UIHelper from "../UIHelper";

export default class Welcome extends Component {

    constructor(props) {
        super(props);
        this.goAuthentication = this.goAuthentication.bind(this);
        this.goRegister = this.goRegister.bind(this);
    }

    goAuthentication() {
        this.props.history.push("/login");
    }

    goRegister() {
        this.props.history.push("/registration");
    }


    render() {
        return [<div className="col-md-6 content-section">
            <div className="title">
                <h2 style={{fontSize: 50}}>{UIHelper.getText("projectName")}</h2>
                <div className="description"><p>{UIHelper.getText("projectNameFragment")}</p>
                    <div className="green-line"/>
                </div>
                <p className="f20slg text-justify">
                    {UIHelper.getText("instructionPageMessageStart")}
                </p>
                <p className="f20slg text-justify">
                    {UIHelper.getText("instructionPageMessageFragment")}
                    <li style={{paddingLeft: 10}}><span className="f20slb">Confirmar matrícula y solicitar transportación</span></li>
                    <li style={{paddingLeft: 10}}><span className="f20slb">Solicitar matrícula nuevo ingreso</span></li>
                    {UIHelper.getText("instructionPageMessageEnd")}
                    <span className="f20slb">{UIHelper.getText("enrollmentYear")}</span>.
                </p>
                <p className="f20slg text-justify">
                    Presione el botón de <span className="f20slb">Regístrate</span> para registrarte por primera vez o
                    presione el botón <span className="f20slb">Conéctate</span> para iniciar sesión.
                </p>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <div className="row">
                    <div className="col-md-12 text-center text-lg-left p-0">
                        <a onClick={this.goRegister} className="button-green-login mr30 mob-mb30px">
                            Regístrate
                        </a>
                        <a onClick={this.goAuthentication} className="button-green-login mob-mb30px">
                            Conéctate
                        </a>
                    </div>
                </div>
            </div>
        </div>,
            <div className="col-md-5 illustration-section d-flex align-items-center text-center">
                <AnimationHelper type="home"/>
            </div>
        ];
    }
}