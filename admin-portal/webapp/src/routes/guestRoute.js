import React, { Component} from 'react';
// import PropTypes from 'prop-types';
// import { connect } from 'react-redux';

import { Redirect, Route } from 'react-router-dom';

class GuestRoute extends Component {
  render() {
    return (
      // <Redirect to='/login' />
      <div>
        {!this.props.token ? <Route {...this.props} /> : <Redirect to='/' />}
      </div>
    )
  }
}
// GuestRoute.propTypes = {
//   token: PropTypes.string
// };

// GuestRoute.defaultProps = {
//   token: null
// };

export default GuestRoute
