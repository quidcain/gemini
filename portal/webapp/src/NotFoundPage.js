/**
 * Created by fran on 1/24/18.
 */
import React, {Component} from "react";
import potImage from "./assets/img/pot.png";
import searchGuy from "./assets/img/search-guy.png";
import AnimationHelper from "./components/AnimationHelper";

export default class NotFoundPage extends Component {

    render() {
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40">
                    <h2>404 - </h2>
                    {/*<div className="violet-line"/>*/}
                    <span className="f30slb">P&aacute;gina no fue encontrada</span>

                </div>
            </div>
            <div className="body d-flex align-items-center flex-column justify-content-end">
                <div className="search-empty visible" style={{overflow: "inherit"}}>
                    <img className="search-guy" src={searchGuy} alt=""/>
                    <img className="pot" src={potImage} alt=""/>
                    <div className="row">
                        <div className="col-md-12 text-center">
                            <span>Lo sentimos este recurso no existe en sistema.</span>
                        </div>
                        <div className="col-md-12 text-center pt-5">
                            <a className="button-white" onclick="window.open('pre-register.html', '_self')">
                                <span>n</span>Salir
                            </a>
                        </div>
                    </div>
                </div>
                <div style={{marginTop: 100}}/>

            </div>
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                <AnimationHelper type="search"/>
            </div>];
    }

}
