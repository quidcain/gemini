import React, {Component} from "react";
import AdminSchoolSelector from "../../../components/AdminSchoolSelector";
import ReportSelectorModal from "./ReportSelectorModal";

export default class B2SReports extends Component{

    constructor(props){
        super(props);
        this.export = this.export.bind(this);

    }

    export(){
        let criteriaObj = {
            regionId: this.refs.selector.getRegionId(),
            cityCode: this.refs.selector.getCityCode(),
            schoolId: this.refs.selector.getSchoolId(),
        };

        this.refs.reportModal.open(criteriaObj);
    }

    render(){
      return [
            <div className="row">
                <div className="col-md-12">
                    <h3>Back to School Reports</h3>
                </div>
            </div>,

            <div className="row" style={{backgroundColor: "white"}}>
                <div className="col-md-12">

                    <div className="row" style={{paddingTop: 20}}>
                        <div className="col-md-10">
                            <AdminSchoolSelector ref="selector"/>
                        </div>

                        <div className="col-md-2">
                            <button className="button-green" style={{paddingLeft: 10}} onClick={this.export}>
                                Exportar
                            </button>
                        </div>
                    </div>
                </div>
            </div>,
          <ReportSelectorModal ref="reportModal"/>

        ];
    }
}