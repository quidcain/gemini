/**
 * Created by fran on 1/30/18.
 */
import React, {Component} from "react";
import MedicalConditionCheckbox from "../../components/MedicalConditionCheckbox";
import TextInput from "../../components/TextInput";

export default class MedicalInfo extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (<form>
            <div className="row">
                <div className="col-md-12">
                    <MedicalConditionCheckbox name="Posee Condicion Visual" id="visual">
                        <div className="row">
                            <div className="col-md-6">
                                <TextInput id="visualCondition" label="Condicion" placeholder=""/>
                            </div>
                            <div className="col-md-6">
                                <TextInput id="visualTreatment" label="Tratamiento" placeholder=""/>
                            </div>
                        </div>
                    </MedicalConditionCheckbox>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <MedicalConditionCheckbox name="Posee Condicion Auditiva" id="hearing">
                        <div className="row">
                            <div className="col-md-6">
                                <TextInput id="hearingCondition" label="Condicion" placeholder=""/>
                            </div>
                            <div className="col-md-6">
                                <TextInput id="hearingTreatment" label="Condicion" placeholder=""/>
                            </div>
                        </div>
                    </MedicalConditionCheckbox>
                </div>
            </div>
            <div className="row">
                <div className="col-md-12">
                    <MedicalConditionCheckbox name="Posee Condicion Dental" id="dental">
                        <div className="row">
                            <div className="col-md-6">
                                <TextInput id="dentalCondition" label="Condicion" placeholder=""/>
                            </div>
                            <div className="col-md-6">
                                <TextInput id="dentalTreatment" label="Tratamiento" placeholder=""/>
                            </div>
                        </div>
                    </MedicalConditionCheckbox>
                </div>
            </div>
        </form>);
    }
}
