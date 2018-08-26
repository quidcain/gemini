import React, {Component} from "react";
import AnimationHelper from "../../components/AnimationHelper";
import * as UIHelper from "../../UIHelper";

export default class Instructions extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return [

            <div key="inst" className="col-md-7 content-section">
                <div className="title">
                    <div className="description"><h2>{UIHelper.getText("instructionPageTitle")}</h2>
                        <div className="violet-line"/>
                    </div>
                    {/*<p className="f22slg text-justify mt-3">*/}
                    {/*{UIHelper.getText("instructionPageMessageStart")}*/}
                    {/*&nbsp;&nbsp;&nbsp;&nbsp;{UIHelper.getText("instructionPageMessageFragment")}*/}
                    {/*<li style={{paddingLeft: 10}}>Confirmar matrícula</li>*/}
                    {/*<li style={{paddingLeft: 10}}>Solicitar matrícula nuevo ingreso</li>*/}
                    {/*{UIHelper.getText("instructionPageMessageEnd")}*/}
                    {/*<span className="f20slb">{UIHelper.getText("enrollmentYear")}</span>.&nbsp;&nbsp;&nbsp;&nbsp;*/}
                    {/*/!*{UIHelper.getText("instructionFragmentStart")}*!/*/}
                    {/*Este proceso solo te tomará unos minutos. &nbsp;&nbsp;&nbsp;&nbsp;Presiona el botón de&nbsp;*/}
                    {/*<span className="f20slb">{UIHelper.getText("instructionFragmentHighlight")}</span>*/}
                    {/*{UIHelper.getText("instructionFragmentEnd")}*/}
                    {/*</p>*/}
                    <p className="f22slg text-justify mt-3">
                        Seleccione una de las siguientes opciones para comenzar el proceso de confirmación de
                        matrícula. <br/>Una vez seleccionada la opción, usted contestará una serie de preguntas con la
                        intención de confirmar la matrícula para el próximo año escolar <span className="f20slb">2018-2019.</span></p>
                </div>

                {this.props.footer}

            </div>,
            <div key="picture" className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={profileIlustration} alt=""/></div>*/}
                <AnimationHelper type="girlsTable"/>
            </div>

        ];
    }
}