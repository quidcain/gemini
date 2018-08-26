import React, {Component, PureComponent} from 'react';
import {withScriptjs, withGoogleMap, GoogleMap, Marker} from "react-google-maps"

const GoogleMapExample = withScriptjs(withGoogleMap(props => (
    <GoogleMap
        defaultCenter={{lat: 18.2204101387711, lng: -66.52507685609078}}
        defaultZoom={11}
        onClick={props.onPositionChange}
        ref={props.onMapMounted}>
        {<Marker draggable={true}
                 onDragEnd={props.onPositionChange}
                 position={props.markerPosition}/>}
    </GoogleMap>
)));


class AddressMap extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {updateFromMap: false};
        this.positionChange = this.positionChange.bind(this);
        this.handleMapMounted = this.handleMapMounted.bind(this);
        this.state = {markerPosition: this.props.position};

        var deferreds = [];
        this._map = new Promise(function (resolve, reject) {
            deferreds.push({resolve: resolve, reject: reject});
        });
        this.deferreds = deferreds;
    }

    doManualLocation(position) {
        if (position) {
            let startMouseEvent = {
                latLng: {
                    lat: () => position.lat,
                    lng: () => position.lng
                },
                locateByAddressString: true
            };
            this.positionChange(startMouseEvent);
        }

    }


    componentDidMount() {

        this._map.then(map => {
            this.map = map;

            let pc = this.positionChange;
            let postionChangeDefaultValue = function () {
                let fackeMouseevent = {
                    latLng: {
                        lat: () => 18.2204101387711,
                        lng: () => -66.52507685609078
                    }
                };
                pc(fackeMouseevent);
            };

            if (!this.props.position && this.props.new) {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        let pos = {
                            lat: position.coords.latitude,
                            lng: position.coords.longitude
                        };
                        let fackeMouseevent = {
                            latLng: {
                                lat: () => pos.lat,
                                lng: () => pos.lng
                            },
                            canUpdateFromMap: true
                        };
                        pc(fackeMouseevent);
                    }, function () {
                        postionChangeDefaultValue();
                    });
                }
            } else {
                let startMouseEvent = {
                    latLng: {
                        lat: () => this.props.position.lat,
                        lng: () => this.props.position.lng
                    }
                };
                pc(startMouseEvent);
            }
        })
    }


    positionChange(mouseEvent) {
        var lat = mouseEvent.latLng.lat();
        var lng = mouseEvent.latLng.lng();
        var url = "https://maps.googleapis.com/maps/api/geocode/json";
        url += "?latlng=" + lat + "," + lng;
        url += "&key=AIzaSyA-FcwDb-mmDebbfTzXl-_tAOYE0YQZ_uA";
        var map = this.map;
        fetch(url).then(result => {
            return result.json();
        }).then(data => {
            console.log(data);
            if (this.props.onResult) {
                var result = prepareResult(data);
                result.lat = lat;
                result.lng = lng;
                if ((this.state.updateFromMap || mouseEvent.canUpdateFromMap) && !mouseEvent.locateByAddressString)
                    this.props.onResult(result);
                this.setState({markerPosition: {lat: lat, lng: lng}, updateFromMap: true});
                map.panTo({lat: lat, lng: lng});
            }
        });
    }

    handleMapMounted = (map) => {
        this.deferreds[0].resolve(map);
    };

    render() {
        const position = this.props.position ? this.props.position : {lat: 18.2204101387711, lng: -66.52507685609078};
        console.log("position = " + this.props.position);

        return (
            <GoogleMapExample
                ref="map"
                googleMapURL="https://maps.googleapis.com/maps/api/js?key=AIzaSyDf-q5eFwF_yAlt24G15hz3wGepiXZKdMo&v=3.exp"
                loadingElement={<div style={{height: `100%`, width: `100%`}}/>}
                containerElement={<div style={{height: `80vh`, width: `100%`}}/>}
                mapElement={<div style={{height: `100%`, width: `100%`}}/>}
                lat={this.props.lat}
                lng={this.props.lng}
                onPositionChange={this.positionChange}
                markerPosition={this.state.markerPosition}
                onMapMounted={this.handleMapMounted}
            />
        );
    }
};

function prepareResult(data) {
    //steet adress is always first
    var postalCodeObj = findField(data.results, "postal_code");
    var postalCode = postalCodeObj ? postalCodeObj.long_name : undefined;

    var cityObj = findField(data.results, "locality");
    var city = cityObj ? cityObj.long_name : undefined;

    var streetObj = findField(data.results, "route");
    var houseNumberObj = findField(data.results, "street_number");
    var addressLine = "";
    if (houseNumberObj) {
        addressLine += houseNumberObj.long_name;
    }
    if (houseNumberObj && streetObj) {
        addressLine += ", ";
    }
    if (streetObj) {
        addressLine += streetObj.long_name;
    }
    return {postalCode: postalCode, city: city, addressLine: addressLine};
}

function findField(results, field) {
    for (var i = 0; i < results.length; i++) {
        var row = results[i];
        var addressComponents = row.address_components;
        var result = addressComponents.find((ac) => ac.types.indexOf(field) != -1);
        if (result) {
            return result;
        }
    }
}

export default AddressMap;