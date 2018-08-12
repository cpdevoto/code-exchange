import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class FilterSpec extends Component {
  render () {
    const {
      selectedCustomer,
      selectedRuleTemplate
    } = this.props

    return (
      <div className='filter-spec'>
        <div className='filter-field'>
          <div className='filter-field-label'>Customer:</div>
          <div className='filter-field-value'>{selectedCustomer.text}</div>
        </div>
        <div className='filter-field'>
          <div className='filter-field-label'>Rule Template:</div>
          <div className='filter-field-value'>{selectedRuleTemplate.text}</div>
        </div>
      </div>
    )
  }
}

FilterSpec.propTypes = {
  selectedCustomer: PropTypes.shape({
    id: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired,
  selectedRuleTemplate: PropTypes.shape({
    id: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired
}
