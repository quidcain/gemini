import React, {Component} from "react";
import AnimationHelper from "./components/AnimationHelper";
import * as UIHelper from "./UIHelper";

export default class DisableMessage extends Component {

    constructor(props) {
        super(props);
    }

    render() {

        return [<div className="col-md-6 content-section">
            <div className="title" style={{height: "10%"}}>
                <div className="description"/>
                <span className="f30sbg"> Enhorabuena!!!</span>
                <div className="violet-line"/>
            </div>
            <div className="body d-flex flex-column " style={{height: "80%"}}>
                <div className="row">
                    <div className="col-md-12">
                        <div className="row">
                            <div className="col-md-12">
                                <p className="disabled-message">
                                    Ha concluido el proceso de Confirmaci&oacute;n de Matr&iacute;cula <span
                                    className="f20slb">{UIHelper.getText("enrollmentYear")}</span>,
                                    pr&oacute;ximamente estar&aacute; recibiendo un correo electr&oacute;nico donde
                                    notificaremos la determinaci&oacute;n relacionada a su solicitud.
                                </p>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <p className="disabled-message">
                                    Agradecemos su colaboraci&oacute;n en este proceso.
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style={{marginBottom: 300}}/>
        </div>,
            <div className="col-md-5 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="home"/>
            </div>,

        ];
    }

}