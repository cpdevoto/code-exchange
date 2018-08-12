import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Toggle extends Component {
  render () {
    const {
      item,
      onToggle
    } = this.props

    return (
      <div className='toggle'>
        <span className='input'>
          <input
            type='checkbox'
            checked={item.selected}
            onChange={() => onToggle(item.id)}
          />
        </span>
        <label>
          {item.text}
        </label>
      </div>
    )
  }
}

Toggle.propTypes = {
  item: PropTypes.shape({
    id: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired,
  onToggle: PropTypes.func.isRequired
}
