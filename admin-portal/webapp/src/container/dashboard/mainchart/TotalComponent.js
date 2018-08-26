import React, {Component} from "react";
import Categorys from "../../../components/Categorys";

export default class TotalComponent extends Component {

    constructor(props) {
        super(props);
        // private int totalPreEnrollments;
        // private int totalNewEntryEnrollments;
        // private int totalConfirmed;
        // private int totalDenied;
        // private List<ProgressSummary> progress;
        // this.state= {data: {totalPreEnrollments: 0, totalNewEntryEnrollments: 0, totalConfirmed: 0, totalDenied: 0}}
    }

    getData(attr) {
        let data = this.props.data;
        return data && data[attr] ? data[attr] : 0

    }

    render() {
        let totalPreEnrollments = this.getData("totalPreEnrollments");
        let totalConfirmed = this.getData("totalConfirmed");
        let totalDenied = this.getData("totalDenied");
        let totalNewEntryEnrollments = this.getData("totalNewEntryEnrollments");

        return (<div className="row">
            <div className="col-lg-3 col-md-6 col-sm-6">
                <Categorys
                    category={'Pre-Matriculados'}
                    value={totalPreEnrollments}
                    icon={'fa fa-graduation-cap'}
                    smallIconColor={'red'}
                    // smallIcon={'fa fa-exclamation-triangle'}
                    iconDesc={''}
                    iconDescColor={'#cdcdcd'}
                    markColor={'#19bad0'}
                />
            </div>
            <div className="col-lg-3 col-md-6 col-sm-6">
                <Categorys
                    category={'Confirmados'}
                    value={totalConfirmed}
                    icon={'fa fa-check'}
                    // smallIcon={'fa fa-calendar-o'}
                    iconDesc={''}
                    iconDescColor={'#cdcdcd'}
                    markColor={'#52ac55'}
                />
            </div>
            <div className="col-lg-3 col-md-6 col-sm-6">
                <Categorys
                    category={'Rechazados'}
                    value={totalDenied}
                    icon={'fa fa-ban'}
                    // smallIcon={'fa fa-tag'}
                    iconDesc={''}
                    iconDescColor={'#cdcdcd'}
                    markColor={'#e22d6c'}
                />

            </div>
            <div className="col-lg-3 col-md-6 col-sm-6">
                <Categorys
                    category={'Nuevo Ingresos'}
                    value={totalNewEntryEnrollments}
                    icon={'fa fa-plus'}
                    smallIcon={''}
                    iconDesc={''}
                    iconDescColor={'#b65fc5'}
                    markColor={'#fc960e'}
                />

            </div>
        </div>);
    }
}