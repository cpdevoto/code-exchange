import React from 'react'
import { connect } from 'react-redux'
import { selectView } from '../actions'
import { FILTER_VIEW, GENERATE_VIEW } from '../views'
import Wizard from '../components/Wizard'
import RuleGeneratorFilterView from './RuleGeneratorFilterView'
import RuleGeneratorGenerateView from './RuleGeneratorGenerateView'

function mapStateToProps (state) {
  const { selectedView } = state
  return {
    views: [
      FILTER_VIEW,
      GENERATE_VIEW
    ],
    viewMap: {
      FILTER_VIEW: <RuleGeneratorFilterView />,
      GENERATE_VIEW: <RuleGeneratorGenerateView />
    },
    currentView: selectedView
  }
}

function mapDispatchToProps (dispatch) {
  return {
    onBreadcrumbClick: (view) => dispatch(selectView(view))
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Wizard)
