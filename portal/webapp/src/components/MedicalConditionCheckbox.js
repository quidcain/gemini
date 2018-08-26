/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import {Button, Glyphicon} from "react-bootstrap";

export default class MedicalConditionCheckbox extends Component {

    constructor(props) {
        super(props);
        this.state = {showBody: false};
        this.onClick = this.onClick.bind(this);
    }

    onClick(e) {
        this.setState({showBody: e.target.checked});
    }

    render() {
        let panelClass = "panel-body ".concat(!this.state.showBody && !this.props.editable && "hidden");
        let checkProps = {checked: this.props.editable || this.state.showBody};
        return (   <div className="panel panel-primary">
            <div className="panel-heading">
                <h3 className="panel-title">
                    <label><input type="checkbox"
                                  onClick={this.onClick}
                                  id={this.props.id}
                                  name={this.props.id}
                                  {...checkProps}/>&nbsp;&nbsp;{this.props.name}</label>
                    {this.renderEditableDiv()}
                </h3>
            </div>
            <div className={panelClass}>
                {this.props.children}
            </div>
        </div>);
    }

    renderEditableDiv() {
        let props = {...this.props};
        if (props.editable)
            return (<div className="pull-right">
                <Button bsSize="small" bsStyle="danger" onClick={() => props.handleDelete()}>
                    <Glyphicon glyph="glyphicon glyphicon-trash"/>
                </Button>
            </div>);
        return (null);
    }
}
