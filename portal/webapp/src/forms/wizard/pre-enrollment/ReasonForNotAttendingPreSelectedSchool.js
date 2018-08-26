import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import {getReasonForAttendingCodes, saveReasonForNotAttendingSchool, unblockUI} from "../../../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import * as types from "../../../redux/types";
import * as UIHelper from "../../../UIHelper";

class ReasonForNotAttendingPreSelectedSchool extends Component {

    constructor(props) {
        super(props);
        this.state = {reasonSelected: null};
        this.reasonChanged = this.reasonChanged.bind(this);
    }

    componentWillMount() {
        this.props.getReasonForAttendingCodes();
    }

    reasonChanged(reasonObj) {
        this.setState({reasonSelected: reasonObj.name})
    }

    onPress(onResult, onError) {
        if (this.state.reasonSelected === types.MOVE_OUT_OF_COUNTRY)
            this.props.modal.confirm(UIHelper.getText("reasonConfirmTitle"), UIHelper.getText("reasonConfirmMessage"), () => {
                this.props.saveReasonForNotAttendingSchool(this.state.reasonSelected, onResult, onError);
            }, () => {
                this.props.unblockUI()
            });
        else
            this.props.saveReasonForNotAttendingSchool(this.state.reasonSelected, onResult, onError);

    }

    render() {
        let reasons = this.props.reasons;
        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb40"><h2 className="f90sbg">{UIHelper.getText("reasonQuestionNumber")}</h2>
                    <div className="violet-line"/>
                </div>
                <p className="f30sbg text-justify">{UIHelper.getText("reasonQuestion")}</p>
                <RemoteCodeSelect id="reasons"
                                  placeholder={UIHelper.getText("reasonSelection")}
                                  codes={reasons}
                                  target="name"
                                  display="description"
                                  onObjectChange={this.reasonChanged}
                                  value={this.state.reasonSelected}
                />
                <span style={{fontStyle: "italic"}} className="f20slb text-justify">{UIHelper.getText("enrollmentEndByOutOfCountryExplanation")}</span>

            </div>

            {this.props.footer}
        </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={leisureIllustration} alt=""/></div>*/}
                <AnimationHelper type="rest"/>
            </div>];
    }
}

function mapStateToProps(store) {
    return {
        reasons: store.config.reasonsForNotAttending,
    };
}


function mapDispatchToActions(dispatch) {
    return bindActionCreators({getReasonForAttendingCodes, saveReasonForNotAttendingSchool, unblockUI}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(ReasonForNotAttendingPreSelectedSchool);