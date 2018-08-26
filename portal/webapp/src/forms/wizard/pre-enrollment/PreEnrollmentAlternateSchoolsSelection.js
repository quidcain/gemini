/**
 * Created by fran on 1/26/18.
 */

import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {
    getSchools,
    loadCodes,
    partialAlternatePreEnrollmentSave,
    retrieveAlternatePreEnrollment
} from "../../../redux/actions";
import AnimationHelper from "../../../components/AnimationHelper";
import * as Utils from "../../../Utils";
import * as types from "../../../redux/types";
import * as UIHelper from "../../../UIHelper";
import MultipleSchoolSelector from "../widgets/MultipleSchoolSelector";

class PreEnrollmentAlternateSchoolsSelection extends Component {

    constructor(props) {
        super(props);
        this.state = {schools1: null, schools2: null};
        this.fetchSchools = this.fetchSchools.bind(this);
    }

    componentWillMount() {
        this.props.loadCodes(() => {
            this.props.retrieveAlternatePreEnrollment(() => {
            }, () => {
                alert(UIHelper.getText("alternateEnrollmentError"))
            });
        });
    }

    onPress(onResult, onError) {
        let form = this.props.alternateEnrollment;
        let preEnrollment = this.props.preEnrollment;
        form.nextGradeLevel = preEnrollment.nextGradeLevel;
        form.nextGradeLevelDescription = preEnrollment.nextGradeLevelDescription;
        form.type = types.REGULAR_ALTERNATE_SCHOOLS;
        this.props.partialAlternatePreEnrollmentSave(form, onResult, onError);
    }

    fetchSchools(priorityId, regionId) {
        let form = this.props.preEnrollment;
        if (!(Utils.isEmptyValue(form.nextGradeLevel) || Utils.isEmptyValue(regionId))) {
            this.props.getSchools(regionId, form.nextGradeLevel, () => {
                let stateChanged = this.state;
                stateChanged[`schools${priorityId}`] = this.props.schools;
                this.setState({...this.state})
            });
        }
    };

    render() {
        let schoolsSelected = this.props.alternateEnrollment.alternateSchools;
        let schoolsSelectedToDelete = this.props.alternateEnrollment.alternateSchoolsToDelete;
        let regions = this.props.regions;
        let gradeLevels = this.props.gradeLevels;
        let schools1 = this.state.schools1;
        let schools2 = this.state.schools2;
        let form = this.props.preEnrollment;

        return [<div className="col-md-7 content-section">
            <div className="title">
                <div className="description mb30"><h2>{UIHelper.getText("alternateEnrollmentTitleStart")}
                    <span>{UIHelper.getText("alternateEnrollmentTitleEnd")}</span></h2>
                </div>
                <span className="f20slg">
                    {UIHelper.getText("alternateEnrollmentMessage")}
                    <span className="f20slb">{UIHelper.getText("enrollmentYear")}</span>:
                </span>
            </div>
            <div className="body d-flex flex-column justify-content-end">
                <form>
                    <MultipleSchoolSelector ref="selector"
                                    loadPreEnrollment={this.props.alternatePreEnrollmentLoad}
                                    form={form}
                                    schoolsSelected={schoolsSelected}
                                    schoolsSelectedToDelete={schoolsSelectedToDelete}
                                    gradeLevels={gradeLevels}
                                    regions={regions}
                                    schools1={schools1}
                                    schools2={schools2}
                                    fetchSchools={this.fetchSchools}
                    />
                </form>
                <div style={{marginTop: -20}}>
                    {this.props.footer}
                </div>
            </div>
        </div>, <div className="col-md-4 illustration-section d-flex align-items-center text-center">
            {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
            <AnimationHelper type="blackboard"/>
        </div>];
    }
}

function mapStateToProps(store) {
    return {
        student: store.studentInfo.student,
        regions: store.config.regions,
        gradeLevels: store.config.gradeLevels,
        schools: store.config.schools,
        preEnrollment: store.preEnrollment.info,
        alternateEnrollment: store.preEnrollment.alternateSchoolEnrollment,
        alternatePreEnrollmentLoad: store.preEnrollment.alternatePreEnrollmentLoad
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({
        loadCodes,
        getSchools,
        retrieveAlternatePreEnrollment,
        partialAlternatePreEnrollmentSave
    }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PreEnrollmentAlternateSchoolsSelection);
