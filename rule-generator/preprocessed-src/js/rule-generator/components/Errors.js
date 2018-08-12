import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Errors extends Component {
  render () {
    const { messages } = this.props

    let key = 0
    let items = messages.details.map(e => (
      <li key={key++}>{e}</li>
    ))
    return (
      <div className='errors'>
        <h2>{messages.header + (items.length > 0 ? ':' : '.')}</h2>
        {items.length > 0 && (
          <ul>
            {items}
          </ul>
        )}
      </div>
    )
  }
}

Errors.propTypes = {
  messages: PropTypes.shape({
    header: PropTypes.string.isRequired,
    details: PropTypes.arrayOf(PropTypes.string.isRequired).isRequired
  }).isRequired
}
