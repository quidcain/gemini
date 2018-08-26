/**
 * Created by fran on 1/26/18.
 */

import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {connect} from "react-redux";
import {getVocationalSchools, loadVocationalCodes, partialSaveVocationalPreEnrollment} from "../../../redux/actions";
import AnimationHelper from "../../../components/AnimationHelper";
import * as Utils from "../../../Utils";
import SimpleSchoolSelector from "../widgets/SimpleSchoolSelector";

class VocationalPreEnrollment extends Component {

    constructor(props) {
        super(props);
        this.fetchSchools = this.fetchSchools.bind(this);
    }

    componentWillMount() {
        this.props.loadVocationalCodes();
    }

    componentDidMount(){
        let form = this.props.currentVocationalEnrollment;
        let preEnrollment = this.props.preEnrollment;
        form.hasPreEnrollment = preEnrollment.hasPreEnrollment;
        form.nextGradeLevel = preEnrollment.nextGradeLevel || form.nextGradeLevel;
    }

    onPress(onResult, onError) {
        let form = this.props.currentVocationalEnrollment;
        form.validateSchoolSelection = true;
        form.validateProgramSchoolSelection = false;
        this.props.partialSaveVocationalPreEnrollment(form, onResult, onError);
    }

    fetchSchools() {
        let form = this.props.currentVocationalEnrollment;
        if (!(Utils.isEmptyValue(form.nextGradeLevel) || Utils.isEmptyValue(form.regionId))) {
            this.props.getVocationalSchools(form.regionId, form.nextGradeLevel);
        }
    }

    render() {
        let regions = this.props.regions;
        let gradeLevels = this.props.gradeLevels;
        let schools = this.props.schools;
        let form = this.props.currentVocationalEnrollment;
        let preEnrollment = this.props.preEnrollment;

        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb30"><h2><span>Registro de</span> Matr&iacute;cula Ocupacional </h2>
                    </div>
                    <span className="f20slg"><span className="f20slb">Vamos a crear una nueva matr&iacute;cula.</span> Por favor seleccione regi&oacute;n, grado y escuela:</span>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form id="enrollment-form" className="mt50">
                        <SimpleSchoolSelector ref="selector"
                                              form={form}
                                              maxSchools={2}
                                              gradeLevels={gradeLevels}
                                              regions={regions}
                                              schools={schools}
                                              fetchSchools={this.fetchSchools}
                        />
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
        student: store.studentInfo.student,
        //this is manage by redux logic
        regions: store.config.regions,
        gradeLevels: store.config.gradeLevels,
        schools: store.config.schools,
        preEnrollment: store.preEnrollment.info,
        currentVocationalEnrollment: store.preEnrollment.currentVocationalEnrollment
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({loadVocationalCodes, getVocationalSchools, partialSaveVocationalPreEnrollment}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(VocationalPreEnrollment);
