import React, {Component} from "react";
import SearchSchoolMaxCapacity from "./manage/SearchSchoolMaxCapacity";


export default class SchoolCapsEntryPoint extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (<SearchSchoolMaxCapacity/>);
    }
}