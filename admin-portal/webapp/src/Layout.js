import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import './index.css'
import './assets/css/dashboard.css'
import './assets/css/navDashboard.css'
import './assets/css/styles.css'
import './assets/css/login.css'
import './assets/css/components.css'

class Layout extends Component {
    render() {
        return (
            <div className='app'>
                {this.props.children}
            </div>
        )
    }
}

// Layout.propTypes = {
//   children: PropTypes.node.isRequired
// }

export default Layout
