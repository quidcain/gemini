import React, {Component} from "react";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import Button from "../../../components/Button";
import * as UIHelper from "../../../UIHelper"
import * as Utils from "../../../Utils";

export default class MultipleSchoolSelector extends Component {

    constructor(props) {
        super(props);
        this.initialLoad = true;
        this.userChangedRegion = false;
        this.state = {regionId1: null, regionId2: null, schools1: null, schools2: null, fetching: false};
        this.fetchSchools = props.fetchSchools.bind(this);
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

    limit() {
        let schoolsSelected = this.props.schoolsSelected;
        if (!schoolsSelected)
            return false;
        return this.props.maxSchools ? schoolsSelected.length >= this.props.maxSchools : false;
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.schools1 || nextProps.schools2 || nextProps.schoolsSelected || nextProps.loadPreEnrollment) {
            if (nextProps.loadPreEnrollment && this.initialLoad) {
                this.initialLoad = false;
                let school1 = this.getSchoolPriorityFromProps(1, nextProps);
                let school2 = this.getSchoolPriorityFromProps(2, nextProps);
                if (school1) {
                    this.state.regionId1 = school1.regionId;
                    this.fetchSchools(school1.priority, school1.regionId);
                }

                if (school2) {
                    this.state.regionId2 = school2.regionId;
                    this.fetchSchools(school2.priority, school2.regionId);
                }
            }

            this.setState({
                ...this.state,
                schools1: nextProps.schools1,
                schools2: nextProps.schools2
            })
        }
    }

    cleanSchoolCode(priorityId) {
        let stateChanged = this.state;
        stateChanged[`schools${priorityId}`] = null;
        let cleanSchool = {schoolId: "-1",};
        this.setSchool(priorityId, cleanSchool);
        this.setState(stateChanged);
    }

    gradeLevelChanged = (priorityId) => (gradeLevelObject) => {
        this.cleanSchoolCode(1);
        this.cleanSchoolCode(2);
        let form = this.props.form;
        form.nextGradeLevel = gradeLevelObject.name;
        form.nextGradeLevelDescription = gradeLevelObject.displayName;
        this.fetchSchools(1, this.state[`regionId${1}`]);
        this.fetchSchools(2, this.state[`regionId${2}`]);

    };

    regionChanged = (priorityId) => (e) => {
        this.userChangedRegion = true;
        this.cleanSchoolCode(priorityId);
        let element = e.target;
        this.state[`regionId${priorityId}`] = element.value;
        this.fetchSchools(priorityId, this.state[`regionId${priorityId}`]);
    };

    schoolChanged = (priorityId) => (schoolObject) => {
        this.setSchool(priorityId, schoolObject);
    };

    setSchool(priority, school) {
        let schoolsSelected = this.props.schoolsSelected;
        let schoolsSelectedToDelete = this.props.schoolsSelectedToDelete;
        let schoolToSet = this.getSchoolPriority(priority);


        if (schoolToSet) {
            schoolsSelectedToDelete.push(schoolToSet);
            schoolsSelected.splice(this.getSchoolPriorityIndex(priority), 1);
        }

        schoolToSet = {
            priority: priority,
            regionId: this.state[`regionId${priority}`],
            schoolId: school.schoolId,
            school: school,
            schoolName: school.schoolName,
            schoolAddress: school.address
        };

        schoolsSelected.push(schoolToSet);
        this.forceUpdate();

    }

    getSchoolPriority(priority) {
        return this.getSchoolPriorityFromProps(priority, this.props);
    }

    getSchoolPriorityFromProps(priority, props) {
        for (let school of props.schoolsSelected) {
            if (school.priority === priority)
                return school;
        }
        return null;
    }


    getSchoolPriorityIndex(priority) {
        for (let idx in this.props.schoolsSelected) {
            if (this.props.schoolsSelected[idx].priority === priority)
                return idx;
        }
        return null;
    }


    render() {
        let schools1 = this.state.schools1;
        let schools2 = this.state.schools2;

        let selectedSchool1 = this.getSchoolPriority(1);
        let selectedSchool2 = this.getSchoolPriority(2);

        return [
            this.renderSchoolPanel(1, schools1, selectedSchool1),
            this.renderSchoolPanel(2, schools2, selectedSchool2)
        ];
    }

    renderSchoolPanel(priority, schools, selectedSchool) {
        let form = this.props.form;
        let regions = this.props.regions;
        let gradeLevels = this.props.gradeLevels;
        let bypassDisabledGradeLevels = ['EE', 'EI', 'ES', 'PK', 'PKE', 'PKM', 'KG', 'AND'];
        let canBypass = false;
        for (let gradeLevel of bypassDisabledGradeLevels) {
            canBypass = canBypass || gradeLevel === form.nextGradeLevel;
        }

        if (canBypass && form.hasPreEnrollment) {
            gradeLevels = [
                {name: 'AND', description: 'Andarín', displayName: "AND-Andarín"},
                {
                    name: 'PKE',
                    description: 'Educación Especial- Pre-Kinder',
                    displayName: "PKE-Educación Especial- Pre-Kinder"
                },
                {name: 'PKM', description: 'Pre-Kinder Montessori', displayName: "PKM-Pre-Kinder Montessori"},
                {name: 'PK', description: 'Pre-Kinder', displayName: "PK-Pre-Kinder"},
                {name: 'KG', description: 'Kindergarten', displayName: "KG-Kindergarten"},
                {
                    name: 'EE',
                    description: 'Educación Especial - Elemental',
                    displayName: "EE-Educación Especial - Elemental"
                },
                {
                    name: 'EI',
                    description: 'Educación Especial - Intermedia',
                    displayName: "EI-Educación Especial - Intermedia"
                },
                {
                    name: 'ES',
                    description: 'Educación Especial - Superior',
                    displayName: "ES-Educación Especial - Superior"
                },
                {name: '01', description: 'Primero', displayName: "01-Primero"},

            ]
        }
        let disabledGradeLevels = (canBypass && form.hasPreEnrollment) ? false : form.hasPreEnrollment;

        let schoolAddress = !selectedSchool || Utils.isEmptyValue(selectedSchool.schoolId)
            ? UIHelper.getText("schoolSelectorEmptySchoolMessage")
            : selectedSchool.schoolAddress.addressFormatted;

        let regionId = !this.userChangedRegion
            ? (selectedSchool && selectedSchool.regionId) || "-1"
            : this.state[`regionId${priority}`];
        console.log(`regionId${priority} = ` + regionId);
        console.log(`this.state regionId${priority} = ` + this.state[`regionId${priority}`]);


        let schoolComponentCss = "col-md-8";

        let colStyle = {paddingLeft: 0};
        let rowAddressStyle = {marginTop: -10};
        let schoolRow = priority === 2 ? {marginTop: 20} : {};

        return [<div className="row" style={schoolRow}>
            <div className="col-md-1">
                <span className="f20sbb">{priority}.</span>
            </div>
            <div className="col-md-11">
                <div className="row">
                    <div className="col-md-2" style={colStyle}>
                        <RemoteCodeSelect id="gradeLevel"
                                          label="Grado"
                                          placeholder="Grado"
                                          onObjectChange={this.gradeLevelChanged(priority)}
                                          codes={gradeLevels}
                                          target="name"
                                          display="displayName"
                                          disabled={disabledGradeLevels}
                                          value={form.nextGradeLevel}/>
                    </div>
                    <div className="col-md-2" style={colStyle}>
                        <RemoteCodeSelect id="region"
                                          label="Región"
                                          placeholder="Región"
                                          onChange={this.regionChanged(priority)}
                                          codes={regions}
                                          target="regionId"
                                          display="description"
                                          value={regionId}/>
                    </div>
                    <div className={schoolComponentCss} style={colStyle}>
                        <RemoteCodeSelect id="schools"
                                          label="Escuela a matricular"
                                          placeholder="Escuela"
                                          codes={schools}
                                          onObjectChange={this.schoolChanged(priority)}
                                          target="schoolId"
                                          display="displayName"
                                          value={(selectedSchool && selectedSchool.schoolId) || "-1"}/>
                    </div>
                </div>
                <div className="row" style={rowAddressStyle}>
                    <div className="col-md-1">
                        {/*{UIHelper.getText("schoolSelectorAddressLabel")}*/}
                        <i className="icon-gps mr5"/>
                    </div>
                    <div className="col-md-11">
                        <h6>{schoolAddress}</h6>
                    </div>
                </div>
            </div>

        </div>];
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