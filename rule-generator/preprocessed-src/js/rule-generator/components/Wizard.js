import React, { Component } from 'react'
import PropTypes from 'prop-types'
import WizardBreadcrumbTrail from './WizardBreadcrumbTrail'

function getBreadcrumbViews (views, currentView) {
  const idx = views.map(v => v.id).indexOf(currentView)
  return idx === -1 ? [] : views.slice(0, idx + 1)
}

export default class Wizard extends Component {
  render () {
    const { views, viewMap, currentView, onBreadcrumbClick } = this.props
    const breadcrumbViews = getBreadcrumbViews(views, currentView)
    return (
      <div className='wizard'>
        <WizardBreadcrumbTrail views={breadcrumbViews} onClick={onBreadcrumbClick} />
        <div className='view'>
          {viewMap[currentView]}
        </div>
      </div>
    )
  }
}

Wizard.propTypes = {
  views: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.string.isRequired,
    text: PropTypes.shape.isRequired
  })).isRequired,
  viewMap: PropTypes.objectOf(PropTypes.element.isRequired).isRequired,
  currentView: PropTypes.string.isRequired,
  onBreadcrumbClick: PropTypes.func.isRequired
}
