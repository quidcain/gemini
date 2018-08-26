import React, {Component} from "react";
import SearchRequestForm from "./request/SearchRequestForm";


export default class ManageEntryPoint extends Component{

    constructor(props){
        super(props);
    }

    render(){
        return(<SearchRequestForm/>);
    }
}