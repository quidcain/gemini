import React, {Component} from "react";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import Button from "../../../components/Button";
import * as Utils from "../../../Utils";
import * as UIHelper from "../../../UIHelper"

export default class SimpleSchoolSelector extends Component {

    constructor(props) {
        super(props);
        this.state = {selectedSchool: null, schools: null};
        this.regionChanged = this.regionChanged.bind(this);
        this.gradeLevelChanged = this.gradeLevelChanged.bind(this);
        this.schoolChanged = this.schoolChanged.bind(this);
        this.fetchSchools = props.fetchSchools.bind(this);
        this.onAdd = this.onAdd.bind(this);
    }

    onAdd(e) {
        e.preventDefault();
        let schoolsSelected = this.props.schoolsSelected;
        let size = schoolsSelected.length || 0;
        let school = this.state.selectedSchool;
        if (size < 2) {
            let object = {
                priority: size + 1,
                school: school,
                schoolName: school.schoolName,
                schoolAddress: school.address
            };
            schoolsSelected.push(object);
            this.cleanSchoolCode()
        } else {
            alert(UIHelper.getText("schoolSelectorMaxExceed"));
        }
    }

    onDelete = (index) => (e) => {
        e.preventDefault();
        let schoolsSelected = this.props.schoolsSelected;
        let schoolsSelectedToDelete = this.props.schoolsSelectedToDelete;

        let schoolDeleted = schoolsSelected[index];
        schoolsSelectedToDelete.push(schoolDeleted);
        schoolsSelected.splice(index, 1);
        this.forceUpdate();
    };

    componentWillReceiveProps(nextProps) {
        if (nextProps.schools) {
            this.setState({...this.state, schools: nextProps.schools})
        }
    }

    limit() {
        let schoolsSelected = this.props.schoolsSelected;
        if (!schoolsSelected)
            return false;
        return this.props.maxSchools ? schoolsSelected.length >= this.props.maxSchools : false;
    }

    cleanSchoolCode() {
        let form = this.props.form;
        form.schoolId = -1;
        this.setState({selectedSchool: null, schools: null});
    }

    regionChanged(e) {
        this.cleanSchoolCode();
        let form = this.props.form;
        let element = e.target;
        form.regionId = element.value;
        this.fetchSchools();
    }

    gradeLevelChanged(gradeLevelObject) {
        console.log(`gradeLevelObject.name = ${gradeLevelObject.name}`);
        this.cleanSchoolCode();
        let form = this.props.form;
        form.nextGradeLevel = gradeLevelObject.name;
        form.nextGradeLevelDescription = gradeLevelObject.displayName;
        this.fetchSchools();
    }

    schoolChanged(schoolObject) {
        let form = this.props.form;
        form.schoolId = schoolObject.schoolId;
        if (!Utils.isEmpty(form.schoolId)) {
            form.schoolName = schoolObject.schoolName;
            form.schoolAddress = schoolObject.address;
        }
        this.setState({selectedSchool: schoolObject});
    }

    render() {
        let form = this.props.form;
        let limit = this.limit();
        let schoolsSelected = this.props.schoolsSelected;
        let gradeLevels = this.props.gradeLevels;
        let regions = this.props.regions;
        let schools = this.state.schools;
        let selectedSchool = this.state.selectedSchool;
        let disabledGradeLevels = (schoolsSelected && schoolsSelected.length > 0) || form.hasPreEnrollment;
        let schoolSelectDisabled = Utils.isEmptyValue(form.regionId)
            || Utils.isEmptyValue(form.nextGradeLevel)
            || limit;

        let schoolName = !selectedSchool || selectedSchool.schoolId === -1
            ? UIHelper.getText("schoolSelectorEmptySchoolMessage")
            : selectedSchool.displayName;

        let schoolAddress = !selectedSchool || selectedSchool.schoolId === -1
            ? UIHelper.getText("schoolSelectorEmptySchoolMessage")
            : selectedSchool.address.addressFormatted;

        console.log(`form.hasPreEnrollment = ${form.hasPreEnrollment}`);
        console.log(`form.nextGradeLevel = ${form.nextGradeLevel}`);
        console.log(`gradeLevels = ${JSON.stringify(gradeLevels)}`);


        return [
            <div className="row">
                <div className="col-md-4">
                    <span>Escuela: <h6>{schoolName}</h6></span>
                </div>
                <div className="col-md-8">
                    <span>{UIHelper.getText("schoolSelectorAddressLabel")} <h6>{schoolAddress}</h6></span>
                </div>
            </div>,
            <div className="row">
                <div className="col-md-2">
                    <RemoteCodeSelect id="gradeLevel"
                                      label="Grado"
                                      placeholder="Grado"
                                      onObjectChange={this.gradeLevelChanged}
                                      codes={gradeLevels}
                                      target="name"
                                      display="displayName"
                                      disabled={disabledGradeLevels}
                                      value={form.nextGradeLevel}/>
                </div>

                <div className="col-md-2">
                    <RemoteCodeSelect id="region"
                                      label="Región"
                                      placeholder="Región"
                                      onChange={this.regionChanged}
                                      codes={regions}
                                      target="regionId"
                                      display="description"
                                      disabled={limit}
                                      value={form.regionId}/>
                </div>
                <div className="col-md-8">
                    <RemoteCodeSelect id="schools"
                                      label="Escuela a matricular"
                                      placeholder="Escuela"
                                      codes={schools}
                                      onObjectChange={this.schoolChanged}
                                      target="schoolId"
                                      display="displayName"
                                      disabled={schoolSelectDisabled}
                                      value={form.schoolId}/>
                </div>
            </div>
        ];
    }

    renderSchoolsSelected() {
        let specializedSchool = this.props.specializedSchool;
        let alternateSchools = this.props.schoolsSelected;
        let span = specializedSchool ? 4 : 3;
        return (
            <table className="table table-striped table-hover ">
                <thead>
                <tr>
                    <th>Prioridad</th>
                    <th>Escuela</th>
                    <th>Dirección</th>
                    <th/>
                </tr>
                </thead>
                <tbody>
                {alternateSchools && alternateSchools.length > 0
                    ? alternateSchools.map((school, index) => (
                        <tr key={index}>
                            <td>{school.priority}</td>
                            <td>{school.schoolName}</td>
                            <td>{school.schoolAddress.addressFormatted}</td>
                            <td>
                                <Button size="sm" color="danger" onClick={this.onDelete(index)}>
                                    <i className="fas fa-trash"/>
                                </Button>
                            </td>
                        </tr>
                    ))
                    : <tr>
                        <td colSpan={span}
                            style={{left: 50, top: 50}}>{UIHelper.getText("schoolSelectorTableEmptyMessage")}</td>
                    </tr>}
                </tbody>
            </table>
        );
    }
}