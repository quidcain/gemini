import React, {Component} from "react";
import RemoteCodeSelect from "../../../components/RemoteCodeSelect";
import {services} from "../../../setup/store"
import TotalComponent from "./TotalComponent";
import LineChartComponent from "./LineChartComponent";


export default class MainComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selectedRegionId: "-1",
            selectedCityCode: "-1",
            selectedSchoolId: "-1",
            regions: [],
            cities: [],
            schools: []
        };
        this.regionChanged = this.regionChanged.bind(this);
        this.cityChanged = this.cityChanged.bind(this);
        this.schoolChanged = this.schoolChanged.bind(this);
    }

    componentWillMount() {
        this.loadRegions(-1);
    }

    loadRegions(regionIdSelected) {
        try {
            this.setState({...this.state, selectedRegionId: regionIdSelected});
            services()
                .retrieveRegions()
                .then((regions) => {
                    this.setState({...this.state, regions: regions});
                    this.loadCities(regionIdSelected, -1);
                })
        } catch (e) {
        }
    }

    loadCities(regionId, cityCode) {
        this.setState({...this.state, selectedCityCode: cityCode});

        services()
            .retrieveCities(regionId)
            .then((cities) => {
                this.setState({...this.state, cities: cities});
                this.loadSchools(this.state.selectedRegionId, -1);
            })
    }

    loadSchools(regionId, cityCode, schoolId) {
        this.setState({...this.state, selectedSchoolId: schoolId});

        services()
            .retrieveSchools(regionId, cityCode)
            .then((schools) => {
                this.setState({...this.state, schools: schools});
                this.fetchDashboardData(this.state.selectedRegionId, this.state.selectedCityCode, this.state.selectedSchoolId);
            })
    }

    fetchDashboardData(regionId, cityCode, schoolId) {
        let criteria = {regionId: regionId, cityCode: cityCode, schoolId: schoolId};
        services()
            .retrieveSummary(criteria)
            .then((response) => response.json())
            .then((data) => {
                this.setState({...this.state, data: data});
            });


    }

    regionChanged(regionObj) {
        this.loadRegions(regionObj.regionId);
    }

    cityChanged(cityObj) {
        this.loadCities(this.state.selectedRegionId, cityObj.cityCode)
    }

    schoolChanged(schoolObj) {
        this.loadSchools(this.state.selectedRegionId, this.state.selectedCityCode, schoolObj.schoolId)

    }

    render() {
        let regions = this.state.regions;
        let cities = this.state.cities;
        let schools = this.state.schools;
        let disabledRegionSelector = false;
        let disabledCitySelector = false;
        let disabledSchoolSelector = false;

        let selectedRegionId = regions && regions.length === 1 ? regions[0].regionId : this.state.selectedRegionId;
        let selectedCityCode = cities && cities.length === 1 ? cities[0].code  : this.state.selectedCityCode;
        let selectedSchoolId = schools && schools.length === 1 ? schools[0].schoolId  : this.state.selectedSchoolId;

        let showRegions = regions && regions.length > 1 || cities && cities.length > 1;
        let schoolColCss = showRegions ? "col-md-8" : "col-md-12";

        return [<div className="row">

            <div className="col-md-2">
                {showRegions
                    ? (<RemoteCodeSelect id="region"
                                         label="Región"
                                         placeholder="Todas las Regiones"
                                         onObjectChange={this.regionChanged}
                                         codes={regions}
                                         target="regionId"
                                         display="description"
                                         value={selectedRegionId}
                                         disabled={disabledRegionSelector}/>)
                    : null
                }
            </div>

            <div className="col-md-2">
                {showRegions
                    ? (<RemoteCodeSelect id="city"
                                         label="Municipio"
                                         placeholder="Todos los municipios"
                                         onObjectChange={this.cityChanged}
                                         codes={cities}
                                         target="cityCode"
                                         display="city"
                                         value={selectedCityCode}
                                         disabled={disabledCitySelector}/>)
                    : (null)
                }
            </div>

            <div className={schoolColCss}>
                <RemoteCodeSelect id="schools"
                                  label="Escuela"
                                  placeholder="Todas las escuelas"
                                  codes={schools}
                                  onObjectChange={this.schoolChanged}
                                  target="schoolId"
                                  display="displayName"
                                  value={selectedSchoolId}
                                  disabled={disabledSchoolSelector}/>
            </div>
        </div>
            , <TotalComponent data={this.state.data}/>
            , <LineChartComponent data={this.state.data}/>];
    }
}