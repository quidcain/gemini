import React, {Component} from "react";
import * as env from "./env";

export default class Footer extends Component{

    constructor(props){
        super(props);
        this.currentYear = new Date().getFullYear();
    }

    render(){
        return(
            <footer className="footer">
                <div className="container">
                    <div className="col align-self-end">
                        <span>© {this.currentYear} Departamento de Educaci&oacute;n de Puerto Rico</span>
                        <span className={"text-right"} style={{marginRight: 10}}>Build: {env.default.buildVersion}</span>
                    </div>
                </div>
            </footer>

            );
    }
}

{/*
<div className="row footer d-flex align-items-center">
    <div className="col-md-1"/>
    <div className="col-md-8">
        <span>© {this.currentYear} Departamento de Educaci&oacute;n de Puerto Rico</span>
    </div>
    <div className="col-md-3 text-right">
        <span style={{marginRight: 20}}>Build: {env.default.buildVersion}</span>
    </div>
</div>*/}
