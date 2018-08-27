import React, {Component} from "react";
import * as store from "../../../setup/store";
import AdminSchoolSelector from "../../../components/AdminSchoolSelector";
import * as Utils from "../../../Utils";
import {Bar, Pie, Polar, HorizontalBar, Chart} from 'react-chartjs-2';
import {Button, Nav, NavItem, TabContent, TabPane} from "react-bootstrap";
import ReportSelectorModal from "./ReportSelectorModal";
import chartLabels from "chart.piecelabel.js";

const analysis = [
    // {code: 'REGULAR', description: 'Maestro Regulares'},
    {code: 'REGULAR_AFTER_PLACEMENT', description: 'Maestros'},
    // {code: 'SPECIAL_EDUCATION', description: 'Maestro de Educacion Especial '},
    // {code: 'SPECIAL_EDUCATION_AFTER_PLACEMENT', description: 'Maestro de Educacion Especial'},
    // {code: 'OCCUPATIONAL_PLACEMENT', description: 'Maestro de Ocupacional'}

];

const colors = [
    "rgba(0, 128, 128, 0.45)"
    , "rgba(0, 128, 128, 0.50)"
    , "rgba(0, 128, 128, 0.55)"
    , "rgba(0, 128, 128, 0.60)"
    , "rgba(0, 128, 128, 0.65)"
    , "rgba(0, 128, 128, 0.70)"
    , "rgba(0, 128, 128, 0.75)"
    , "rgba(0, 128, 128, 0.80)"
    , "rgba(0, 128, 128, 0.85)"
    , "rgba(0, 128, 128, 0.90)"
    , "rgba(0, 128, 128, 0.95)"
    , "rgba(0, 128, 128, 1)"
    , "rgba(0, 128, 128, 0.40)"
    , "rgba(0, 128, 128, 0.35)"
    , "rgba(0, 128, 128, 0.30)"
    , "rgba(0, 128, 128, 0.25)"
    , "rgba(0, 128, 128, 0.20)"
    , "rgba(0, 128, 128, 0.15)"
    , "rgba(0, 128, 128, 0.10)"
    , "rgba(70, 240, 240, 0.7)"
    , "rgba(70, 240, 240, 0.8)"
];

const newColors = [
    "rgba(138,161, 54,0.75)",
    "rgba(118, 40,106,0.75)",
    "rgba(168,120, 57,0.75)",
    "rgba(240,220, 60,0.75)",
    "rgba(240,121, 60,0.75)",
    "rgba( 41, 80,108,0.75)",
    "rgba( 40,159,120,0.75)",
    "rgba( 95, 52,163,0.75)",
    "rgba(138,161, 54,0.75)",
    "rgba(118, 40,106,0.75)",
    "rgba(168,120, 57,0.75)",
    "rgba(240,220, 60,0.75)",
    "rgba(240,121, 60,0.75)",
    "rgba( 41, 80,108,0.75)",
    "rgba( 95, 52,163,0.75)",
    "rgba( 40,159,120,0.75)",
    "rgba(138,161, 54,0.25)",
    "rgba(118, 40,106,0.25)",
    "rgba(168,120, 57,0.25)",
    "rgba(240,220, 60,0.25)",
    "rgba(240,121, 60,0.25)",
    "rgba( 41, 80,108,0.25)",
    "rgba( 95, 52,163,0.25)",
    "rgba( 40,159,120,0.25)"

];


function getColors(arr) {
    let counter = 0;
    let colorsRet = [];
    let length = arr.length;
    while (length > 0) {
        colorsRet.push(colors[counter++]);
        length--;
        if (counter >= colors.length) {
            counter = 0;
        }
    }
    return colorsRet;
}

function getNewColors(arr) {
    let counter = 0;
    let colorsRet = [];
    let length = arr.length;
    while (length > 0) {
        colorsRet.push(newColors[counter++]);
        length--;
        if (counter >= newColors.length) {
            counter = 0;
        }
    }
    return colorsRet;
}


const stackedBarOptions = {
    scales: {
        xAxes: [{
            stacked: true,
            ticks: {
                callback: function (label, index, labels) {
                    return label;
                },
                fontSize: 12,
                fontColor: 'black'
            }
        }],
        yAxes: [{
            display: true,
            ticks: {
                callback: function (label, index, labels) {
                    return label;
                },
                fontSize: 12,
                fontColor: 'black'
            },
            stacked: true,
        }]
    }
};

export default class SchedulingDashboard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedAnalysis: null,
            chartType: "overview",
            redraw: false,
            summary: {
                labels: [],
                asignadosTotales: [],
                vacantesTotales: [],
                excedentesTotales: [],
                cancelSelectorAction: false,
                dataChanged: false
            },
            categories: {
                labels: [],
                categoryTotals: [],
                backgroundColor: []
            },
            vacantAvailable: {
                labels: [],
                categoryTotals: [],
                backgroundColor: []
            },
            vacantAvailableRaw: {},
            detailTitleDescription: "Todas",
            activeTab: 1
        };
        this.analysisChanged = this.analysisChanged.bind(this);
        this.exportReport = this.exportReport.bind(this);
        this.selectorChanged = this.selectorChanged.bind(this);
        this.clickEvent = this.clickEvent.bind(this);
        this.toggleStaffOverview = this.toggleStaffOverview.bind(this);
        this.toggleFullyStaffed = this.toggleFullyStaffed.bind(this);
        this.toggleTotalNeededStaff = this.toggleTotalNeededStaff.bind(this);
        this.toggleVacantAvailable = this.toggleVacantAvailable.bind(this);

    }

    toggleStaffOverview() {
        this.setState({...this.state, chartType: "overview", redraw: true}, () => {
            this.setState({...this.state, redraw: false})
        })
    }

    toggleFullyStaffed() {
        this.setState({...this.state, chartType: "fullyStaffed", redraw: true}, () => {
            this.setState({...this.state, redraw: false})
        })
    }

    toggleTotalNeededStaff() {
        this.setState({...this.state, chartType: "totalNeeded", redraw: true}, () => {
            this.setState({...this.state, redraw: false})
        })
    }

    toggleVacantAvailable() {

        let criteriaObj = {
            analysisType: this.state.selectedAnalysis,
            selectedType: 1,
            regionId: this.refs.selector.getRegionId(),
            cityCode: this.refs.selector.getCityCode(),
            schoolId: this.refs.selector.getSchoolId(),
        };

        store.services()
            .retrieveVacantCategories(criteriaObj)
            .then((response) => response.json())
            .then((data) => {

                let categories = {
                    labels: data.labels || [],
                    categoryTotals: data.categoryTotals || [],
                    backgroundColor: getNewColors((data.labels || []))
                };

                this.setState({
                    ...this.state,
                    chartType: "vacantAvailable",
                    vacantAvailable: categories,
                    vacantAvailableRaw: data,
                    redraw: true
                }, () => {
                    this.setState({...this.state, redraw: false})
                })
            });
    }

    componentDidMount() {
        this.analysisChanged(analysis[0]);

        Chart.pluginService.register({
            afterDraw: (chart, easing) => {
                let ctx = chart.chart.ctx;
                ctx.font = "12px Verdana";
                ctx.fillStyle = "#777";
                ctx.textAlign = "center";
                ctx.textBaseline = "bottom";

                chart.chart.config.data.datasets.forEach(function (dataset) {
                    const dataArray = dataset.data;
                    if (dataset && dataset._meta) {
                        let bar;
                        Object.keys(dataset._meta).forEach(function (key) {
                            let bar = dataset._meta[key];
                            if (bar.type === "bar" && !bar.hidden)
                                bar.data.forEach(function (bar, index) {
                                    let offset = Math.ceil(dataArray[index] * .035);
                                    ctx.fillText(dataArray[index], bar._view.x, bar._view.y + offset);
                                });
                        });

                    }
                })
            }
        });
    }

    analysisChanged(obj) {
        this.setState({
            ...this.state,
            selectedAnalysis: obj.code,
            regionId: null,
            cityCode: null,
            schoolId: null,
            cancelSelectorAction: true
        }, () => {

            this.refs.selector.clean();
            let criteriaObj = {
                analysisType: obj.code,
                regionId: null,
                cityCode: null,
                schoolId: null
            };
            /*
                store.services()
                    .retrieveSchedulingSummary(criteriaObj)
                    .then((response) => response.json())
                    .then((data) => {
                        this.setState({
                            ...this.state,
                            cancelSelectorAction: false,
                            dataChanged: true,
                            summary: data
                        }, () => {
                            this.setState({...this.state, dataChanged: false});

                            let criteriaCategoryObj = {
                                analysisType: obj.code,
                                selectedType: -1,
                                regionId: null,
                                cityCode: null,
                                city: null,
                                schoolId: null
                            };
                            store.services()
                                .retrieveSchedulingCategories(criteriaCategoryObj)
                                .then((response) => response.json())
                                .then((data) => {
                                    let categories = {
                                        labels: data.labels || [],
                                        categoryTotals: data.categoryTotals || [],
                                        backgroundColor: getColors((data.labels || []))
                                    };
                                    this.setState({
                                        ...this.state,
                                        categories: categories,
                                        detailTitleDescription: this.getText(-1)
                                    });
                                });
                        });
                    });
            */
        });
    }

    exportReport() {
        let criteriaObj = {
            analysisType: this.state.selectedAnalysis,
            regionId: this.refs.selector.getRegionId(),
            cityCode: this.refs.selector.getCityCode(),
            schoolId: this.refs.selector.getSchoolId(),
        };

        this.refs.reportModal.open(criteriaObj);
    }

    selectorChanged() {
        if (this.state.cancelSelectorAction)
            return;
        let criteriaObj = {
            analysisType: this.state.selectedAnalysis,
            regionId: this.refs.selector.getRegionId(),
            cityCode: this.refs.selector.getCityCode(),
            schoolId: this.refs.selector.getSchoolId(),
        };
        /*
        store.services()
            .retrieveSchedulingSummary(criteriaObj)
            .then((response) => response.json())
            .then((data) => {
                this.setState({...this.state, dataChanged: true, summary: data}, () => {
                    this.setState({...this.state, dataChanged: false});


                        let criteriaCategoryObj = {
                            analysisType: this.state.selectedAnalysis,
                            selectedType: -1,
                            regionId: this.refs.selector.getRegionId(),
                            cityCode: this.refs.selector.getCityCode(),
                            schoolId: this.refs.selector.getSchoolId(),
                            city: null,
                        };

                        store.services()
                            .retrieveSchedulingCategories(criteriaCategoryObj)
                            .then((response) => response.json())
                            .then((data) => {
                                let categories = {
                                    labels: data.labels || [],
                                    categoryTotals: data.categoryTotals || [],
                                    backgroundColor: getColors((data.labels || []))
                                };
                                this.setState({
                                    ...this.state,
                                    categories: categories,
                                    detailTitleDescription: this.getText(-1)
                                });

                                // if (this.state.chartType === "vacantAvailable")
                                //     this.toggleVacantAvailable()
                            });

                });
            });
             */

    }

    render() {

        return [
            /*<div className="row">
                <div className="col-md-12">
                    <RemoteCodeSelect id="gradeLevel"
                                      label="Analisis"
                                      placeholder="Seleccione analisis"
                                      codes={analysis}
                                      onObjectChange={this.analysisChanged}
                                      target="code"
                                      display="description"
                                      value={this.state.selectedAnalysis}/>
                </div>
            </div>,*/
            <div className="row">
                <div className="col-md-12">
                    <h3>Dashboard de Recurso Humanos</h3>
                </div>
            </div>,

            <div className="row">
                <div className="col-md-10">
                    <AdminSchoolSelector ref="selector" callback={this.selectorChanged}/>
                </div>
                <div className="col-md-2">
                    <button className="button-green" style={{paddingLeft: 10}} onClick={this.exportReport}>
                        Exportar
                    </button>
                </div>

                <ReportSelectorModal ref="reportModal"/>
            </div>
        ];
    }

    renderGraph() {
        let summaryObj = this.state.summary;
        let categories = this.state.categories;
        let vacantAvailable = this.state.vacantAvailable;


        let pieOptions = {
            legend: {
                display: false
            },
            tooltips: {
                callbacks: {
                    label: function (tooltipItem, data) {
                        return `${data.labels[tooltipItem.index]} = ${data.datasets[0].data[tooltipItem.index]}`;
                    }
                }
            }
        };

        let polarData = {
            datasets: [
                {
                    data: categories.categoryTotals,
                    backgroundColor: categories.backgroundColor,
                    label: 'Categorias' // for legend
                    // hoverBackgroundColor: categories.backgroundColor
                }
            ],
            labels: categories.labels
        };


        let pieData = {
            datasets: [
                {
                    data: categories.categoryTotals,
                    backgroundColor: categories.backgroundColor,
                    hoverBackgroundColor: categories.backgroundColor
                }
            ],
        };
        let datasetsArray = [

            {
                label: "Asignados",
                backgroundColor: "rgba(96,207,103,0.5)",
                borderColor: "rgba(96,207,103,1)",
                borderWidth: 1,

                // pointColor: "rgba(96,207,103,1)",
                // pointHighlightStroke: "rgba(96,207,103,1)",
                // pointStrokeColor: "#fff",
                // pointHighlightFill: "#fff",
                data: summaryObj.asignadosTotales
            },
            {
                label: "Vacantes",
                backgroundColor: "rgba(96,177,207,0.5)",
                borderColor: "rgba(96,177,207,1)",
                borderWidth: 1,
                // pointColor: "rgba(96,177,207,1)",
                // pointHighlightStroke: "rgba(96,177,207,1)",
                // pointStrokeColor: "#fff",
                // pointHighlightFill: "#fff",
                data: summaryObj.vacantesTotales
            }];
        let options = stackedBarOptions;
        let horizontalData;

        if (this.state.chartType === "overview") {
            options = {
                scales: {
                    xAxes: [{ticks: {mirror: true}}]
                }
            };
            datasetsArray.push({
                label: "Excedentes",
                backgroundColor: "rgba(147,96,207,0.5)",
                borderColor: "rgba(147,96,207,1)",
                borderWidth: 1,
                // pointColor: "rgba(147,96,207,1)",
                // pointHighlightStroke: "rgba(147,96,207,1)",
                // pointStrokeColor: "#fff",
                // pointHighlightFill: "#fff",
                data: summaryObj.excedentesTotales
            });
        }
        else if (this.state.chartType === "totalNeeded") {
            let colors = getNewColors((summaryObj.necesidadesTotales || []));
            datasetsArray = [{
                label: "Necesidades",
                // "rgba(96,207,103,0.5)"
                backgroundColor: colors,
                borderColor: colors,
                borderWidth: 1,

                // pointColor: "rgba(96,207,103,1)",
                // pointHighlightStroke: "rgba(96,207,103,1)",
                // pointStrokeColor: "#fff",
                // pointHighlightFill: "#fff",
                data: summaryObj.necesidadesTotales
            }];
        } else if (this.state.chartType === "vacantAvailable") {
            horizontalData = {
                labels: vacantAvailable.labels,
                datasets: [
                    {
                        label: 'Categorias',
                        backgroundColor: 'rgba(0, 128, 128, 0.5)',
                        borderColor: 'rgba(0, 128, 128, 1)',
                        borderWidth: 1,
                        hoverBackgroundColor: 'rgba(0, 128, 128, 0.4))',
                        hoverBorderColor: 'rgba(0, 128, 128, 1)',
                        data: vacantAvailable.categoryTotals
                    }
                ]
            };
        }

        let chartData = {
            labels: this.state.summary.labels,
            datasets: datasetsArray
        };

        return
        [<div className="row">
            <div className="col-md-3"/>
            <div className="col-md-6">
                <Button color="secondary" size="sm" style={{height: 40, paddingTop: 10}}
                        onClick={this.toggleStaffOverview}>Staff Overview</Button>
                <Button color="secondary" size="sm" style={{height: 40, paddingTop: 10}}
                        onClick={this.toggleFullyStaffed}>Fully Staffed</Button>
                <Button color="secondary" size="sm" style={{height: 40, paddingTop: 10}}
                        onClick={this.toggleTotalNeededStaff}>Total Needed</Button>
                <Button color="secondary" size="sm" style={{height: 40, paddingTop: 10}}
                        onClick={this.toggleVacantAvailable}>Vacant Categories</Button>
            </div>
            <div className="col-md-3"/>
        </div>,
            <div className="row" style={{paddingTop: 10}}>
                <div
                    className={this.state.chartType === "totalNeeded" || this.state.chartType === "vacantAvailable" ? "col-md-12" : "col-md-6"}>
                    {this.renderMainChart(chartData, options)}

                </div>
                {this.state.chartType === "totalNeeded" || this.state.chartType === "vacantAvailable"
                    ? (null)
                    : (<div className="col-md-6">
                        {/*<Pie data={pieData} height={200}/>*/}
                        <label style={{paddingLeft: 100}}> Clasificacion de
                            Maestro: {this.state.detailTitleDescription}</label>
                        <div style={{paddingTop: 50}}/>
                        <Polar data={polarData} height={175} options={pieOptions}/>

                        {/*<HorizontalBar data={horizontalData}/>*/}

                    </div>)}

            </div>];
    }

    clickEvent(elems) {

        if (elems && elems[0]) {
            let summaryObj = this.state.summary;

            // elems[0]._index -- means x axis
            //elems[0]._datasetIndex  -- means vacantes, asignados o excedentes
            let regionId = this.refs.selector.getRegionId();
            let cityCode = this.refs.selector.getCityCode();
            let schoolId = this.refs.selector.getSchoolId();
            let city = null;


            if (Utils.isEmptyValue(regionId) && Utils.isEmptyValue(cityCode) && Utils.isEmptyValue(schoolId)) {
                regionId = summaryObj.labelIds[elems[0]._index];
            } else if (!Utils.isEmptyValue(regionId) && Utils.isEmptyValue(cityCode) && Utils.isEmptyValue(schoolId)) {
                cityCode = summaryObj.labelIds[elems[0]._index];
                city = elems[0]._view.label;
            } else if (!Utils.isEmptyValue(regionId) && !Utils.isEmptyValue(cityCode) && !Utils.isEmptyValue(cityCode)) {
                schoolId = summaryObj.labelIds[elems[0]._index];
            }


            let criteriaCategoryObj = {
                analysisType: this.state.selectedAnalysis,
                selectedType: elems[0]._datasetIndex,
                regionId: regionId,
                cityCode: cityCode,
                schoolId: schoolId,
                city: city
            };
            store.services()
                .retrieveSchedulingCategories(criteriaCategoryObj)
                .then((response) => response.json())
                .then((data) => {
                    let categories = {
                        labels: data.labels || [],
                        categoryTotals: data.categoryTotals || [],
                        backgroundColor: getColors((data.labels || []))
                    };
                    this.setState({
                        ...this.state,
                        categories: categories,
                        detailTitleDescription: this.getText(elems[0]._datasetIndex)
                    });
                });

        }
    }

    getText(val) {
        let text = "";
        switch (val) {
            case 0:
                text = "Asignados";
                break;
            case 1:
                text = "Vacantes";
                break;
            case 2:
                text = "Excedentes";
                break;
            default:
                text = "Todas";
                break;
        }
        return text;
    }

    renderMainChart(chartData, options) {
        if (this.state.chartType === "totalNeeded")
            return (<Pie data={chartData}
                         options={{
                             responsive: true,
                             pieceLabel: {
                                 render: function (args) {
                                     return args.label + ' = ' + args.value;
                                 },
                                 fontColor: '#000',
                                 fontSize: 14,
                             }
                         }}
                         legend={{display: false}}
            />);
        else if (this.state.chartType === "vacantAvailable")
            return (
                <table className="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th>Categoria</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.vacantAvailableRaw && this.state.vacantAvailableRaw.labels && this.state.vacantAvailableRaw.labels.length > 0
                        ?
                        this.state.vacantAvailableRaw.labels.map((label, i) => (
                            <tr style={{background: "white"}}>
                                <td>{label}</td>
                                <td>{this.state.vacantAvailableRaw.categoryTotals[i]}</td>
                            </tr>
                        ))
                        : (<tr style={{background: "white"}}>
                            <td colSpan={2}>
                                <span>No existen datos para los criterios de b&uacute;squeda ingresado</span>
                            </td>
                        </tr>)
                    }
                    </tbody>
                </table>
            );
        else
            return (<Bar ref='barchart'
                         data={chartData}
                         options={options}
                         height={200}
                         getElementAtEvent={this.clickEvent}
                         redraw={this.state.dataChanged || this.state.redraw}
            />);
    }
}