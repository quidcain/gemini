import React, {Component} from "react";
import * as env from "./env";

export default class Footer extends Component {

    constructor(props) {
        super(props);
        this.currentYear = new Date().getFullYear();
    }

    render() {
        return (<div className="row footer d-flex align-items-center">
            <div className="col-md-1"/>
            <div className="col-md-8">
                <span>© {this.currentYear} Departamento de Educaci&oacute;n de Puerto Rico</span>
            </div>
            <div className="col-md-3 text-right">
                <span>Build: {env.default.buildVersion}</span>
            </div>
        </div>);
    }
}