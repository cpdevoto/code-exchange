import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Tab extends Component {
  render () {
    const {
      linkClassName,
      iconClassName,
      isActive,
      label,
      tabIndex,
      onClick
    } = this.props
    return (
      <li className={`tab${linkClassName ? ' ' + linkClassName : ''}${isActive ? ' active' : ''}`}
        onClick={(event) => {
          event.preventDefault()
          onClick(tabIndex)
        }}>
        {iconClassName &&
          <i className={`tab-icon ${iconClassName}`} />
        }
        {label}
      </li>
    )
  }
}

Tab.propTypes = {
  linkClassName: PropTypes.string,
  iconClassName: PropTypes.string,
  isActive: PropTypes.bool,
  label: PropTypes.string.isRequired,
  tabIndex: PropTypes.number,
  onClick: PropTypes.func
}
