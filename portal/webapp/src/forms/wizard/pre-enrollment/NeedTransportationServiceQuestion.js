import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {saveNeedTransportationService} from "../../../redux/actions";
import * as UIHelper from "../../../UIHelper";

class NeedTransportationServiceQuestion extends Component {

    constructor(props) {
        super(props);
    }

    onPress(onResult, onError) {
        this.save(true, onResult, onError);
    }

    onBack(onResult, onError) {
        this.save(false, onResult, onError);
    }

    save(answer, onResult, onError) {
        this.props.saveNeedTransportationService(answer, onResult, onError);
    }

    render() {
        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb40"><h2
                        className="f90sbg">{UIHelper.getText("transportationPageQuestionNumber")}</h2>
                        <div className="violet-line"/>
                    </div>
                    {/*<p className="f30sbg text-justify">{UIHelper.getText("transportationPageTitle")}</p>*/}
                    <span className="f30sbb"
                          style={{zIndex: 2000}}>{UIHelper.getText("transportationPageQuestion")}</span>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <span className="f20slg" style={{zIndex: 1000}}>
                        * Si la respuesta es <span className="f20sbb">Sí</span>, la misma será referida para el debido análisis
                    </span>
                    <span style={{
                        fontSize: 10,
                        zIndex: 1000,
                        color: "black",
                        textIndent: -20,
                        textAlign: "justify",
                        marginLeft: 40
                    }}>
                        <li><b>Programa Regular</b> - los estudiantes deberán cumplir con los requisitos establecidos de
                            distancia e ingreso familiar. El estudiante deberá residir a una distancia no menor de seis
                            (6)
                            kilómetros de la escuela en la que se haya matriculado. El ingreso familiar no puede exceder
                            de
                            dieciocho mil dólares ($18,000.00) anuales.
                        </li>
                        <li>
                            <b>Programa de Educación Especial</b> - los requisitos de distancia e ingreso familiar NO
                            aplican a
                            estudiantes registrados en el Programa de Educación Especial por disposición de la
                            reglamentación federal aplicable a estudiantes pertenecientes a este programa. Criterios:
                            que el servicio de transportación responda a la necesidad particular de estos estudiantes.
                        </li>
                    </span>

                    <div style={{marginTop: -20}}>
                        {this.props.footer}
                    </div>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>
        ];
    }
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({saveNeedTransportationService}, dispatch)
}

export default connect(null, mapDispatchToActions, null, {withRef: true})(NeedTransportationServiceQuestion);