import React from 'react'
// import PropTypes from 'prop-types'

class FormInput extends React.Component {
  state = {
    type: 'text'
  }
  render() {
    const {
      input,
      placeholder,
      className,
      // type,
      label,
      meta: { touched, error, warning }
    } = this.props
    return (
      <div>
        <label className='sr-only'>
          {label}
        </label>
        <input
          {...input}
          onFocus={this.handleFocus}
          onBlur={this.handleBlur}
          className='form-control'
          placeholder={placeholder}
          type={this.state.type}
          id={input.name}
        />
      </div>
    )
  }
}

// FormInput.propTypes = {
//   input: PropTypes.object.isRequired,
//   placeholder: PropTypes.string.isRequired,
//   type: PropTypes.string,
//   label: PropTypes.string.isRequired,
//   className: PropTypes.string,
//   meta: PropTypes.object.isRequired
// }
// FormInput.defaultProps = {
//   className: '',
//   type: 'text'
// }

export default FormInput
