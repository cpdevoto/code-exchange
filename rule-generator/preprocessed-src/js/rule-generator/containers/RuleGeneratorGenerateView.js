import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Errors from '../components/Errors'
import Spinner from '../components/Spinner'
import FilterSpec from '../components/FilterSpec'
import Tabs from '../components/Tabs'
import Tab from '../components/Tab'
import Equipment from '../components/Equipment'
import Candidates from '../components/Candidates'
import {
  toggleRuleInstance,
  selectAllRuleInstances,
  deselectAllRuleInstances,
  fetchRuleInstancesIfNeeded,
  postRuleInstances
} from '../actions'

class RuleGeneratorGenerateView extends Component {
  componentDidMount () {
    const {
      dispatch,
      selectedCustomer,
      selectedRuleTemplate
    } = this.props
    dispatch(fetchRuleInstancesIfNeeded(selectedCustomer.id, selectedRuleTemplate.id))
  }

  componentDidUpdate (prevProps) {
    const {
      dispatch,
      selectedCustomer,
      selectedRuleTemplate,
      ruleInstances
    } = this.props
    if (ruleInstances.didInvalidate ||
       selectedCustomer.id !== prevProps.selectedCustomer.id ||
       selectedRuleTemplate.id !== prevProps.selectedRuleTemplate.id) {
      dispatch(fetchRuleInstancesIfNeeded(selectedCustomer.id, selectedRuleTemplate.id))
    }
  }

  render () {
    const {
      selectedCustomer,
      selectedRuleTemplate,
      ruleInstances,
      onToggleRuleInstance,
      onSelectAllRuleInstances,
      onDeselectAllRuleInstances,
      onSaveRuleInstances
    } = this.props

    const componentId = 'filter-view'
    if (ruleInstances.isFetching || ruleInstances.isSaving) {
      return (
        <div id={componentId}>
          <Spinner />
        </div>
      )
    }

    if (ruleInstances.errors) {
      return (
        <div id={componentId}>
          <Errors messages={ruleInstances.errors} />
        </div>
      )
    }

    return (
      <div id={componentId}>
        <FilterSpec
          selectedCustomer={selectedCustomer}
          selectedRuleTemplate={selectedRuleTemplate}
        />
        <Tabs>
          <Tab label='Equipment with Rule Applied'>
            <Equipment items={ruleInstances.equipment} />
          </Tab>
          <Tab label='Candidates for Rule Application'>
            <Candidates
              items={ruleInstances.candidates}
              selectedCustomer={selectedCustomer.id}
              selectedRuleTemplate={selectedRuleTemplate.id}
              onToggleItem={onToggleRuleInstance}
              onSelectAllItems={onSelectAllRuleInstances}
              onDeselectAllItems={onDeselectAllRuleInstances}
              onSave={onSaveRuleInstances}
            />
          </Tab>
        </Tabs>
      </div>
    )
  }
}

RuleGeneratorGenerateView.propTypes = {
  ruleInstances: PropTypes.shape({
    errors: PropTypes.shape({
      header: PropTypes.string.isRequired,
      details: PropTypes.arrayOf(PropTypes.string).isRequired
    }),
    equipment: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number.isRequired,
        text: PropTypes.string.isRequired
      })
    ).isRequired,
    candidates: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number.isRequired,
        text: PropTypes.string.isRequired,
        selected: PropTypes.bool.isRequired
      })
    ).isRequired,
    isFetching: PropTypes.bool.isRequired,
    isSaving: PropTypes.bool.isRequired,
    didInvalidate: PropTypes.bool.isRequired
  }).isRequired,
  selectedCustomer: PropTypes.shape({
    id: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired,
  selectedRuleTemplate: PropTypes.shape({
    id: PropTypes.number.isRequired,
    text: PropTypes.string.isRequired
  }).isRequired,
  onToggleRuleInstance: PropTypes.func.isRequired,
  onSelectAllRuleInstances: PropTypes.func.isRequired,
  onDeselectAllRuleInstances: PropTypes.func.isRequired,
  onSaveRuleInstances: PropTypes.func.isRequired,
  dispatch: PropTypes.func.isRequired
}

function mapStateToProps (state) {
  const { customers, ruleTemplates, ruleInstances } = state
  const selectedCustomer = (customers && customers.selected && customers.data)
    ? customers.data[customers.selected] : null
  const selectedRuleTemplate = (ruleTemplates && ruleTemplates.selected && ruleTemplates.data)
    ? ruleTemplates.data[ruleTemplates.selected] : null
  return {
    selectedCustomer,
    selectedRuleTemplate,
    ruleInstances
  }
}

function mapDispatchToProps (dispatch) {
  return {
    dispatch,
    onToggleRuleInstance: (id) => dispatch(toggleRuleInstance(id)),
    onSelectAllRuleInstances: () => dispatch(selectAllRuleInstances()),
    onDeselectAllRuleInstances: () => dispatch(deselectAllRuleInstances()),
    onSaveRuleInstances: (customerId, ruleTemplateId, candidates) => {
      dispatch(postRuleInstances(customerId, ruleTemplateId, candidates))
    }
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuleGeneratorGenerateView)
