import React, {Component} from "react";
import {bindActionCreators} from "redux";
import {
    getSpecializedSchools,
    loadSpecializedCodes,
    partialAlternatePreEnrollmentSave,
    retrieveAlternatePreEnrollment
} from "../../../redux/actions";
import {connect} from "react-redux";
import AnimationHelper from "../../../components/AnimationHelper";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import * as Utils from "../../../Utils";
import * as UIHelper from "../../../UIHelper";
import * as types from "../../../redux/types";
import MultipleSchoolSelector from "../widgets/MultipleSchoolSelector";

class PreEnrollmentSpecializedSchoolsSelections extends Component {

    constructor(props) {
        super(props);
        this.state = {selectedCategory: "-1", selectedCategoryObject: null};
        this.categoryChanged = this.categoryChanged.bind(this);
        this.fetchSchools = this.fetchSchools.bind(this);
    }

    componentWillMount() {
        this.props.loadSpecializedCodes(() => {
            this.props.retrieveAlternatePreEnrollment(() => {
            }, () => {
                alert("Error loading")
            });
        });
    }


    onPress(onResult, onError) {
        let form = this.props.alternateEnrollment;
        let preEnrollment = this.props.preEnrollment;
        form.nextGradeLevel = preEnrollment.nextGradeLevel;
        form.nextGradeLevelDescription = preEnrollment.nextGradeLevelDescription;
        form.type = types.SPECIALIZED_SCHOOLS_ENROLLMENT;
        this.props.partialAlternatePreEnrollmentSave(form, onResult, onError);
    }


    categoryChanged(categoryObject) {
        this.refs.selector.cleanSchoolCode();
        this.setState({
            selectedCategory: categoryObject ? categoryObject.name : "-1",
            selectedCategoryObject: categoryObject
        })
    }

    getSelectedCategoryCode() {
        return this.state.selectedCategory ? this.state.selectedCategory.name : "-1";
    }


    fetchSchools() {
        let form = this.props.preEnrollment;
        if (!(Utils.isEmptyValue(form.nextGradeLevel) || Utils.isEmptyValue(form.regionId))) {
            this.props.getSpecializedSchools(form.regionId, form.nextGradeLevel, this.getSelectedCategoryCode());
        }
    }

    render() {
        let schoolsSelected = this.props.alternateEnrollment.alternateSchools;
        let schoolsSelectedToDelete = this.props.alternateEnrollment.alternateSchoolsToDelete;
        let specializedCategories = this.props.specializedCategories;
        let regions = this.props.regions;
        let gradeLevels = this.props.gradeLevels;
        let schools = this.props.schools;
        let form = this.props.preEnrollment;
        let selectorDisabled = this.refs.selector && this.refs.selector.limit();

        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb30"><h2>{UIHelper.getText("alternateSpecializedEnrollmentTitleStart")}
                        <span>{UIHelper.getText("alternateSpecializedEnrollmentTitleEnd")} </span></h2></div>
                    <span className="f20slg"><span
                        className="f20slb">{UIHelper.getText("alternateSpecializedEnrollmentMessageStart")}</span> {UIHelper.getText("alternateSpecializedEnrollmentMessageEnd")}</span>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form>
                        <div className="row">
                            <div className="col-md-12">
                                <RemoteCodeSelect id="specializedCategory"
                                                  placeholder="Categorías"
                                                  onObjectChange={this.categoryChanged}
                                                  codes={specializedCategories}
                                                  target="name"
                                                  display="description"
                                                  disabled={selectorDisabled}
                                                  value={this.state.selectedCategory}
                                />
                            </div>
                        </div>
                        <MultipleSchoolSelector ref="selector"
                                        form={form}
                                        schoolsSelected={schoolsSelected}
                                        schoolsSelectedToDelete={schoolsSelectedToDelete}
                                        maxSchools={2}
                                        gradeLevels={gradeLevels}
                                        regions={regions}
                                        schools={schools}
                                        fetchSchools={this.fetchSchools}
                                        specializedSchool
                        />
                    </form>
                    <div style={{marginTop: -60}}>
                        {this.props.footer}
                    </div>
                </div>

            </div>, <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>
        ];
    }

}

function mapStateToProps(store) {
    return {
        student: store.studentInfo.student,
        regions: store.config.regions,
        gradeLevels: store.config.gradeLevels,
        schools: store.config.schools,
        specializedCategories: store.config.specializedCategories,
        preEnrollment: store.preEnrollment.info,
        alternateEnrollment: store.preEnrollment.alternateSchoolEnrollment
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({
        loadSpecializedCodes,
        getSpecializedSchools,
        retrieveAlternatePreEnrollment,
        partialAlternatePreEnrollmentSave
    }, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(PreEnrollmentSpecializedSchoolsSelections);
