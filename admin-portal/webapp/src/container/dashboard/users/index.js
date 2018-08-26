import React, {Component} from "react";
import SearchUserForm from "./manage/SearchUserForm";


export default class ManageUsersEntryPoint extends Component{

    constructor(props){
        super(props);
    }

    render(){
        return(<SearchUserForm/>);
    }
}