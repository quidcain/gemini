import React, {Component} from "react";
// import {Line} from "react-chartjs";
import {Line} from 'react-chartjs-2';
import Chart from "chart.js";

Chart.defaults.global.responsive = true;

const data1 = {
    labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
        {
            label: 'My First dataset',
            fill: false,
            lineTension: 0.1,
            backgroundColor: 'rgba(75,192,192,0.4)',
            borderColor: 'rgba(75,192,192,1)',
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',
            pointBorderColor: 'rgba(75,192,192,1)',
            pointBackgroundColor: '#fff',
            pointBorderWidth: 1,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: 'rgba(75,192,192,1)',
            pointHoverBorderColor: 'rgba(220,220,220,1)',
            pointHoverBorderWidth: 2,
            pointRadius: 1,
            pointHitRadius: 10,
            data: [65, 59, 80, 81, 56, 55, 40]
        }
    ]
};


export default class LineChartComponent extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        let dataProps = this.props.data;

        let confirmedLine = {
            label: "Confirmadas",
            backgroundColor: "rgba(80,172,86,0.2)",
            borderColor: "rgba(80,172,86,1)",
            borderCapStyle: 'butt',
            borderDash: [],
            borderDashOffset: 0.0,
            borderJoinStyle: 'miter',

            pointColor: "rgba(80,172,86,1)",
            pointHighlightStroke: "rgba(80,172,86,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            data: dataProps && dataProps.confirmedEnrollments ? dataProps.confirmedEnrollments : []
        };

        let deniedLine = {
            label: "Denegadas",
            backgroundColor: "rgba(226,43,107,0.2)",
            borderColor: "rgba(226,43,107,1)",

            pointColor: "rgba(226,43,107,1)",
            pointHighlightStroke: "rgba(226,43,107,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            data: dataProps && dataProps.deniedEnrollments ? dataProps.deniedEnrollments : []
        };

        let newEntryLine = {
            label: "Nuevo Ingresos",
            backgroundColor: "rgba(252,149,56,0.2)",
            borderColor: "rgba(252,149,56,1)",
            
            pointColor: "rgba(252,149,56,1)",
            pointHighlightStroke: "rgba(252,149,56,1)",
            pointStrokeColor: "#fff",
            pointHighlightFill: "#fff",
            data: dataProps && dataProps.newEntryEnrollments ? dataProps.newEntryEnrollments : []
        };
        let data = {
            labels: dataProps && dataProps.labels ? dataProps.labels : [],
            datasets: [confirmedLine, deniedLine, newEntryLine]
        };

        let empty = (dataProps && dataProps.empty);
        if (empty)
            return (<div style={{width: "50%", height: "50%", margin: "auto", display: "block", padding: "100"}}><span>No existen datos para la gr&aacute;fica</span>
            </div>);
        return (
            <div className="row">
                <div className="col-md-12">
                    <Line data={data}  height={100}/>
                </div>
            </div>
        );
    }
}