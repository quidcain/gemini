import React, { Component} from 'react';
// import PropTypes from 'prop-types';
// import { connect } from 'react-redux';

import { Redirect, Route } from 'react-router-dom';

class AuthRoute extends Component {
  render() {
    return (
      // <Redirect to='/login' />
      <div>
        {this.props.token ? <Route {...this.props} /> : <Redirect to='/login' />}
      </div>
    )
  }
}
// AuthRoute.propTypes = {
//   token: PropTypes.string
// };

// AuthRoute.defaultProps = {
//   token: null
// };

export default AuthRoute
