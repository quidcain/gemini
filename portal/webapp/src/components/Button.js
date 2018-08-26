import React, {Component} from "react";

export default class Button extends Component {

    constructor(props) {
        super(props);
    }

    componentWillMount() {
        this.sizeCss = {padding: 2};

        switch (this.props.size) {
            case "small":
                this.sizeCss = {padding: 2};
                break;
            case "normal":
                this.sizeCss = {padding: 5};
                break;
            case "large":
                this.sizeCss = {padding: 10};
                break;
            case "xlarge":
                this.sizeCss = {padding: 20};
                break;
        }

        switch (this.props.color) {
            case "primary":
                this.color = "button-blue";
                break;
            default:
                this.color = "button-yellow";

        }
    }

    render() {
        let style = this.props.style ? {...this.props.style, ...this.sizeCss} : {...this.sizeCss};
        return (
            <button {...this.props} className={this.color} style={style} >
                {this.props.children}
            </button>);
    }
}