import React, {Component} from "react";
// import * as env from "../env";


export default class AnimationHelper extends Component {

    constructor(props) {
        super(props);
    }

    componentWillMount() {
        let base = `/${process.env.REACT_APP_BASE_CONTEXT}-animations`;
        // if ("srs" === env.default.baseContext) {
        //     base = "https://prdesietest.dde.pr/schoolmax_tmax2-animations";
        // }

        // if ("registro-pruebas" === env.default.baseContext) {
        //     base = "https://prdesieprod.dde.pr/registro-animations";
        // }

        let style = {border: 0, overflow: "hidden"};
        let animRegistration = (<div className="illustration anim-full">
            <iframe width="1000" height="856" src={`${base}/anim-registration/index.html`} style={style}/>
        </div>);
        let animSearch = (<div className="illustration anim-offset d-flex align-items-center">
            <iframe width="1000" height="946" src={`${base}/anim-search/index.html`}
                    style={style}/>
        </div>);
        let animGirlsTable = (<div className="illustration anim-offset d-flex align-items-center">
            <iframe width="1000" height="1041" src={`${base}/anim-girls-table/index.html`} style={style}/>
        </div>);
        let animTable = (<div className="illustration anim-offset d-flex align-items-center">
                <iframe width="1000" height="1041" src={`${base}/anim-table/index.html`}
                        style={style}/>
            </div>
        );
        let animRest = (
            <div className="illustration anim-offset d-flex align-items-center">
                <iframe width="1000" height="1041" src={`${base}/anim-rest/index.html`} style={style}/>
            </div>
        );
        switch (this.props.type) {
            case "home":
                this.animation = animRegistration;
                break;
            case "search":
                this.animation = animSearch;
                break;
            case "girlsTable":
                this.animation = animGirlsTable;
                break;
            case "blackboard":
                this.animation = animTable;
                break;
            case "rest":
                this.animation = animRest;
                break;
            default:
                this.animation = animRest;

        }

    }

    render() {
        return (this.animation);
    }
}