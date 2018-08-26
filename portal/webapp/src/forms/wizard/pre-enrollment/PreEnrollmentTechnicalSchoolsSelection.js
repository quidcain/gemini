import React, {Component} from "react";
import AnimationHelper from "../../../components/AnimationHelper";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import * as Utils from "../../../Utils";
import {getTechnicalSchools, partialSaveVocationalPreEnrollment} from "../../../redux/actions";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import * as UIHelper from "../../../UIHelper";

class PreEnrollmentTechnicalSchoolsSelection extends Component{

    constructor(props){
        super(props);
        this.state = {selectedSchool: null};
        this.schoolChanged = this.schoolChanged.bind(this);
    }

    componentWillMount(){
        this.props.getTechnicalSchools();
    }

    schoolChanged(schoolObject) {
        let form = this.props.currentVocationalEnrollment;
        form.schoolId = schoolObject.schoolId;
        if (!Utils.isEmpty(form.schoolId)) {
            form.schoolName = schoolObject.schoolName;
            form.schoolAddress = schoolObject.address;
        }
        this.setState({selectedSchool: schoolObject});
    }

    render(){
        let form = this.props.currentVocationalEnrollment;
        let schools = this.props.schools;
        let selectedSchool = this.state.selectedSchool;

        let schoolName = !selectedSchool || selectedSchool.schoolId === -1
            ? "No ha selecionado escuela aun"
            : selectedSchool.displayName;

        let schoolAddress = !selectedSchool || selectedSchool.schoolId === -1
            ? "No ha selecionado escuela aun"
            : selectedSchool.address.addressFormatted;

        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb30"><h2><span>{UIHelper.getText("institutePageTitleStart")}</span>{UIHelper.getText("institutePageTitleEnd")}</h2></div>
                    <span className="f20slg"><span className="f20slb">{UIHelper.getText("institutePageMessageStart")}</span> {UIHelper.getText("institutePageMessageEnd")}</span>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form id="enrollment-form" className="mt50">
                        <div className="row pt-2" style={{margin: 2, marginBottom: 15}}>
                            <div className="col-md-4">
                                <span>Escuela: <h6>{schoolName}</h6></span>
                            </div>
                            <div className="col-md-8">
                                <span>Direcci&oacute;n: <h6>{schoolAddress}</h6></span>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <RemoteCodeSelect id="schools"
                                                  label="Escuela a matricular"
                                                  placeholder="Escuela"
                                                  codes={schools}
                                                  onObjectChange={this.schoolChanged}
                                                  target="schoolId"
                                                  display="displayName"
                                                  value={form.schoolId}/>
                            </div>
                        </div>
                        {this.props.footer}
                    </form>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>
        ];
    }
}

function mapStateToProps(store) {
    return {
        schools: store.config.schools,
        preEnrollment: store.preEnrollment.info,
        currentVocationalEnrollment: store.preEnrollment.currentVocationalEnrollment
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({getTechnicalSchools, partialSaveVocationalPreEnrollment}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PreEnrollmentTechnicalSchoolsSelection);