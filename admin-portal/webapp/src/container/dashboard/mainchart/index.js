import React, {Component} from 'react'
import MainComponent from "./MainComponent";
import * as store from "../../../setup/store";


class MainChart extends Component {
    render() {
        let mainObj = store.getUser() && store.getUser().userId ? (<MainComponent/>) : (null);
        return (
            <div className='mainchart'>
                {mainObj}
            </div>
        )
    }
}

export default MainChart
