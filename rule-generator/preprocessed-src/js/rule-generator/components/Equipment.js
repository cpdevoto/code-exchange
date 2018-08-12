import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Equipment extends Component {
  render () {
    const { items } = this.props

    if (items.length === 0) {
      return (
        <i>No equipment found.</i>
      )
    }

    const equipment = items.map((e) => (
      <li key={e.id}>
        {e.text}
      </li>
    ))

    return (
      <ul className='equipment'>
        {equipment}
      </ul>
    )
  }
}

Equipment.propTypes = {
  items: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number.isRequired,
        text: PropTypes.string.isRequired
      })
    ).isRequired
}
