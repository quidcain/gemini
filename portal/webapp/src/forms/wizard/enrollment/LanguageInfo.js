/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import CodeSelect from "../../components/CodeSelect";
import {ToggleButton, ToggleButtonGroup} from "react-bootstrap";
import TextInput from "../../components/TextInput";

export default class LanguageInfo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            spanishSkills: [],
            englishSkills: []
        };
        this.handleSpanishChange = this.handleSpanishChange.bind(this);
        this.handleEnglishChange = this.handleEnglishChange.bind(this);
    }

    handleSpanishChange(e) {
        this.setState({...this.state, spanishSkills: e});
    }

    handleEnglishChange(e) {
        this.setState({...this.state, englishSkills: e});
    }


    render() {
        let form = {...this.state};
        return (<form>
            <div className="row">

                <div className="col-md-6">
                    <CodeSelect id="schoolLanguage"
                                label="Lenguaje de la Escuela"
                                codeType="languageCodes"
                                placeholder="Seleccione lenguaje de la escuela"/>
                </div>
                <div className="col-md-6">
                    <CodeSelect id="schoolLanguageOther"
                                label="Otro Lenguaje de la Escuela"
                                codeType="languageCodes"
                                placeholder="Seleccione otro lenguaje de la escuela"/>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="homeLanguage"
                                label="Lenguaje de la Casa"
                                codeType="languageCodes"
                                placeholder="Seleccione lenguaje de la casa"/>
                </div>

                <div className="col-md-6">
                    <CodeSelect id="homeLanguageOther"
                                label="Otro Lenguaje de la Cas"
                                codeType="languageCodes"
                                placeholder="Seleccione otro lenguaje de la casa"/>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <CodeSelect id="responsibleLanguage"
                                label="Lenguaje del padre o encargado"
                                codeType="languageCodes"
                                placeholder="Seleccione otro lenguaje del padre o encargado"/>
                </div>
                <div className="col-md-6">
                    <CodeSelect id="responsibleLanguageOther"
                                label="Otro Lenguaje del padre o encargado"
                                codeType="languageCodes"
                                placeholder="Seleccione otro lenguaje del padre o encargado"/>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <div className="form-group">
                        <label htmlFor="spanishLanguageSkills">Habilidades del Idioma Español:</label>
                        <br/>
                        <ToggleButtonGroup id="spanishLanguageSkills" type="checkbox"
                                           onChange={this.handleSpanishChange} value={form.spanishSkills}>
                            <ToggleButton value={1} bsStyle={form.spanishSkills.indexOf(1) > -1 ? "primary" : "default"}>Puedo Hablar</ToggleButton>
                            <ToggleButton value={2} bsStyle={form.spanishSkills.indexOf(2) > -1 ? "primary" : "default"}>Puedo Leer</ToggleButton>
                            <ToggleButton value={3} bsStyle={form.spanishSkills.indexOf(3) > -1 ? "primary" : "default"}>Puedo Escribir</ToggleButton>
                            <ToggleButton value={4} bsStyle={form.spanishSkills.indexOf(4) > -1 ? "primary" : "default"}>Puedo Comprender</ToggleButton>
                        </ToggleButtonGroup>
                    </div>
                </div>
                <div className="col-md-6">
                    <div className="form-group">
                        <label htmlFor="englishLanguageSkills">Habilidades del Idioma Ingles:</label>
                        <br/>
                        <ToggleButtonGroup id="englishLanguageSkills" type="checkbox"
                                           onChange={this.handleEnglishChange} value={form.englishSkills}>
                            <ToggleButton value={1} bsStyle={form.englishSkills.indexOf(1) > -1 ? "primary" : "default"} >Puedo Hablar</ToggleButton>
                            <ToggleButton value={2} bsStyle={form.englishSkills.indexOf(2) > -1 ? "primary" : "default"} >Puedo Leer</ToggleButton>
                            <ToggleButton value={3} bsStyle={form.englishSkills.indexOf(3) > -1 ? "primary" : "default"} >Puedo Escribir</ToggleButton>
                            <ToggleButton value={4} bsStyle={form.englishSkills.indexOf(4) > -1 ? "primary" : "default"} >Puedo Comprender</ToggleButton>
                        </ToggleButtonGroup>
                    </div>
                </div>
            </div>
            <div className="row">
                <div className="col-md-6">
                    <TextInput id="speakSpanishIssues" label="Problemas con idioma Espa&ntilde;ol" placeholder="Problemas con idioma Espa&ntilde;ol"/>
                </div>
            </div>
        </form>);
    }
}
