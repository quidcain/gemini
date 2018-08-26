import React, {Component} from "react";
import SearchStudentEnrolled from "./manage/SearchStudentEnrolled";


export default class ApprovalsEntryPoint extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (<SearchStudentEnrolled/>);
    }
}