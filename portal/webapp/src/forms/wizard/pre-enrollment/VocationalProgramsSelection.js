import React, {Component} from "react";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import {getVocationalPrograms, partialSaveVocationalPreEnrollment} from "../../../redux/actions";
import {connect} from "react-redux";
import {bindActionCreators} from "redux";
import Immutable from "immutable";
import * as types from "../../../redux/types";
import AnimationHelper from "../../../components/AnimationHelper";
import Button from "../../../components/Button";

class VocationalProgramsSelection extends Component {

    constructor(props) {
        super(props);
        this.state = {selectedProgram: null, formPrograms: null};
        this.onProgramChange = this.onProgramChange.bind(this);
        this.onAdd = this.onAdd.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.programs) {
            this.setState({...this.state, formPrograms: Immutable.fromJS(nextProps.programs).toJS()})
        }
    }

    componentWillMount() {
        let form = this.props.currentVocationalEnrollment;
        form.programsToDelete = [];
        this.schoolId = form.schoolId;
        this.props.getVocationalPrograms(this.schoolId)
    }

    onPress(onResult, onError) {
        let form = this.props.currentVocationalEnrollment;
        form.validateSchoolSelection = false;
        form.validateProgramSchoolSelection = true;
        this.props.partialSaveVocationalPreEnrollment(form, onResult, onError);
    }

    onProgramChange(programObject) {
        this.setState({selectedProgram: programObject})
    }

    onAdd(e) {
        e.preventDefault();
        let form = this.props.currentVocationalEnrollment;
        let program = this.state.selectedProgram;
        program.schoolId = this.schoolId;
        form.programs.push(program);

        let Map = Immutable.fromJS(this.state.formPrograms);
        let index = this.state.formPrograms.indexOf(program);
        let result = Map.delete(index);
        this.setState({...this.state, selectedProgram: null, formPrograms: result.toJS()})
    }

    onDelete = (index) => (e) => {
        e.preventDefault();
        let form = this.props.currentVocationalEnrollment;
        let programDeleted = form.programs[index];
        form.programsToDelete.push(programDeleted);
        form.programs.splice(index, 1);

        let list = Immutable.List(this.state.formPrograms);
        list = list.push(programDeleted);
        list = list.sort((a, b) => a.programDescription.localeCompare(b.programDescription));
        this.setState({...this.state, selectedProgram: null, formPrograms: list.toJS()})

    };

    render() {
        let enrollment = this.props.currentVocationalEnrollment;
        let programs = this.state.formPrograms;
        let vocationalType = this.props.preEnrollment.type === types.TECHNIQUE_ENROLLMENT
            ? "Institutos"
            : "Ocupacional";

        return [
            <div className="col-md-7 content-section">
                <div className="title">
                    <div className="description mb30"><h2>Matr&iacute;cula <span>{vocationalType}</span></h2></div>
                    <p className="f30slg">Seleccione los programas ocupacionales, que desea <span
                        className="f30slb">matricularse.</span></p>
                </div>
                <div className="body d-flex flex-column justify-content-end">
                    <form>
                        <div className="row" style={{marginTop: -120}}>
                            <div className="col-md-12">
                                <div className="row">
                                    <div className="col-md-4">
                                        <span>Escuela: </span>
                                    </div>
                                    <div className="col-md-8">
                                        <h5>{enrollment && enrollment.schoolName}</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12">
                                <div className="row">
                                    <div className="col-md-4">
                                        <span>Direccion Escuela: </span>
                                    </div>
                                    <div className="col-md-8">
                                        <h6>{enrollment && enrollment.schoolAddress.addressFormatted}</h6>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-8">
                                <RemoteCodeSelect id="program"
                                                  label="Programa"
                                                  placeholder="Programa"
                                                  codes={programs}
                                                  onObjectChange={this.onProgramChange}
                                                  target="programCode"
                                                  display="programDescription"
                                                  value={this.state.selectedProgram}
                                />
                            </div>
                            <div className="col-md-4">
                                <div className="form-group">
                                    <label>&nbsp;</label>
                                    <Button size="normal" onClick={this.onAdd}
                                            disabled={!programs || programs.length === 0}>A&ntilde;adir</Button>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-md-12 tableScrollable">
                                {this.renderProgramsSelections()}
                            </div>
                        </div>
                    </form>
                    <div style={{marginTop: -20}}>
                        {this.props.footer}
                    </div>
                </div>
            </div>,
            <div className="col-md-4 illustration-section d-flex align-items-center text-center">
                {/*<div className="illustration"><img src={entrollmentIllustration} alt=""/></div>*/}
                <AnimationHelper type="blackboard"/>
            </div>
        ];
    }

    renderProgramsSelections() {
        let form = this.props.currentVocationalEnrollment;
        let programs = form.programs;
        return (<table className="table table-striped table-hover ">
            <thead>
            <tr>
                <th>#</th>
                <th>Programa</th>
                <th>Remover</th>
            </tr>
            </thead>
            <tbody>
            {programs && programs.length > 0
                ? programs.map((prog, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{prog.programDescription}</td>
                        <td>
                            <Button size="small" onClick={this.onDelete(index)}>
                                <i className="fas fa-trash"/>
                            </Button>
                        </td>
                    </tr>
                ))
                : <tr>
                    <td colSpan={3} style={{left: 50, top: 50}}>No posee ningun programa a&uacute;n</td>
                </tr>}
            </tbody>
        </table>)
    }
}

function mapStateToProps(store) {
    return {
        preEnrollment: store.preEnrollment.info,
        currentVocationalEnrollment: store.preEnrollment.currentVocationalEnrollment,
        programs: store.config.vocationalPrograms
    };
}

function mapDispatchToActions(dispatch) {
    return bindActionCreators({getVocationalPrograms, partialSaveVocationalPreEnrollment}, dispatch)
}

export default connect(mapStateToProps, mapDispatchToActions, null, {withRef: true})(VocationalProgramsSelection);