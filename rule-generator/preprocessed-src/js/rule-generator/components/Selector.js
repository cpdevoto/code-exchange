import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Selector extends Component {
  render () {
    const {
      id,
      children,
      entities,
      onChange
    } = this.props

    let sortedEntities = entities.data ? Object.keys(entities.data).map(id => {
      return entities.data[id]
    }) : []

    sortedEntities.sort((e1, e2) => {
      if (e1.text < e2.text) {
        return -1
      } else if (e1.text > e2.text) {
        return 1
      }
      return e1.id - e2.id
    })

    let options = [
      <option key='-1' disabled='true' value='' />
    ]
    options = [
      ...options,
      sortedEntities.map(e => {
        return (
          <option
            key={e.id}
            value={e.id}
        >
            {e.text}
          </option>
        )
      })
    ]

    let selector

    return (
      <div className='form-group'>
        {children && <label htmlFor={id}>{children}</label>}
        <select
          className='form-control'
          id='select-customer'
          value={entities.selected !== null ? entities.selected : ''}
          ref={node => {
            selector = node
          }}
          onChange={() => onChange(selector.value)}
        >
          {options}
        </select>
      </div>
    )
  }
}

Selector.propTypes = {
  id: PropTypes.string.isRequired,
  entities: PropTypes.shape({
    selected: PropTypes.number,
    data: PropTypes.object
  }).isRequired,
  onChange: PropTypes.func.isRequired
}
