import React, {Component} from "react";
import {services} from "../setup/store"
import RemoteCodeSelect from "./RemoteCodeSelect";

export default class AdminSchoolSelector extends Component {

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

    clean() {
        this.loadRegions(-1, true);
    }

    getSchoolId() {
        let schoolId = this.state.schools && this.state.schools.length === 1
            ? this.state.schools[0].schoolId
            : this.state.selectedSchoolId;
        return schoolId;
    }

    getRegionId() {
        let regionId = this.state.regions && this.state.regions.length === 1
            ? this.state.regions[0].regionId
            : this.state.selectedRegionId;
        return regionId;
    }

    getCityCode() {
        let cityCode = this.state.cities && this.state.cities.length === 1
            ? this.state.cities[0].cityCode
            : this.state.selectedCityCode;
        return cityCode;
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

    loadRegions(regionIdSelected, cancelCallback = false) {
        try {
            this.setState({...this.state, selectedRegionId: regionIdSelected}, () => {
                services()
                    .retrieveRegions()
                    .then((regions) => {
                        this.setState({...this.state, regions: regions}, () => {
                            this.loadCities(regionIdSelected, -1, cancelCallback);
                        });
                    })
            });
        } catch (e) {
        }
    }

    loadCities(regionId, cityCode, cancelCallback = false) {
        this.setState({...this.state, selectedCityCode: cityCode}, () => {
            services()
                .retrieveCities(regionId)
                .then((cities) => {
                    this.setState({...this.state, cities: cities}, () => {
                        this.loadSchools(this.state.selectedRegionId, cityCode, -1, cancelCallback);
                    });
                })
        });


    }

    loadSchools(regionId, cityCode, schoolId, cancelCallback = false) {
        this.setState({...this.state, selectedSchoolId: schoolId}, () => {
            services()
                .retrieveSchools(regionId, cityCode)
                .then((schools) => {
                    this.setState({...this.state, schools: schools}, () => {
                        if (this.props.callback && !cancelCallback) {
                            this.props.callback();
                        }
                    });

                })
        });

    }

    render() {

        let regions = this.state.regions;
        let cities = this.state.cities;
        let schools = this.state.schools;
        let disabledRegionSelector = false;
        let disabledCitySelector = false;
        let disabledSchoolSelector = false;

        /*
            let selectedRegionId = this.props.regionId ? this.props.regionId
                : (regions && regions.length === 1 ? regions[0].regionId : this.state.selectedRegionId);
            let selectedCityCode = this.props.cityCode ? this.props.cityCode
                : (cities && cities.length === 1 ? cities[0].code : this.state.selectedCityCode);
            let selectedSchoolId = this.props.schoolId ? this.props.schoolId
                : (schools && schools.length === 1 ? schools[0].schoolId : this.state.selectedSchoolId);
        */
        let selectedRegionId = regions && regions.length === 1 ? regions[0].regionId : this.state.selectedRegionId;
        let selectedCityCode = cities && cities.length === 1 ? cities[0].code : this.state.selectedCityCode;
        let selectedSchoolId = schools && schools.length === 1 ? schools[0].schoolId : this.state.selectedSchoolId;

        let showRegions = regions && regions.length > 1 || cities && cities.length > 1;
        let schoolColCss = showRegions ? "col-md-8" : "col-md-12";

        return (<div className="row">

            <div className="col-md-2">
                {showRegions
                    ? (<RemoteCodeSelect id="region"
                                         label="Región"
                                         placeholder="Regiones"
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
                                         placeholder="Municipios"
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
                                  placeholder="Escuelas"
                                  codes={schools}
                                  onObjectChange={this.schoolChanged}
                                  target="schoolId"
                                  display="displayName"
                                  value={selectedSchoolId}
                                  disabled={disabledSchoolSelector}/>
            </div>
        </div>);
    }
}