import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class WizardBreadcrumbTrail extends Component {
  render () {
    const { views, onClick } = this.props
    if (views.length === 0) {
      return null
    }
    let links = []
    let key = 0
    for (let i = 0; i < views.length; i++) {
      if (i > 0) {
        links.push(<span key={key++} className='separator'>{'\u00a0\u00a0>\u00a0\u00a0'}</span>)
      }
      links.push(
        i === views.length - 1
        ? <span key={key++} className='item'>{views[i].text}</span>
        : <a href='#' key={key++} onClick={() => onClick(views[i].id)} className='item'>{views[i].text}</a>
      )
    }

    return (
      <div className='wizardBreadcrumbTrail'>
        {links}
      </div>
    )
  }
}

WizardBreadcrumbTrail.propTypes = {
  views: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string.isRequired,
    text: PropTypes.shape.isRequired
  })).isRequired,
  onClick: PropTypes.func.isRequired
}
