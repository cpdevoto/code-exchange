import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import Selector from '../components/Selector'
import Errors from '../components/Errors'
import Spinner from '../components/Spinner'
import {
  selectCustomer,
  selectRuleTemplate,
  selectView,
  fetchCustomersIfNeeded,
  fetchRuleTemplatesIfNeeded,
  invalidateRuleInstances,
  fetchRuleInstancesIfNeeded
} from '../actions'
import { GENERATE_VIEW } from '../views'

class RuleGeneratorFilterView extends Component {
  componentDidMount () {
    const { dispatch } = this.props
    dispatch(fetchCustomersIfNeeded())
    dispatch(fetchRuleTemplatesIfNeeded())
  }

  render () {
    const {
      didInvalidateFilter,
      customers,
      ruleTemplates,
      onCustomerChange,
      onRuleTemplateChange,
      onFilter
    } = this.props

    const componentId = 'filter-view'

    if (customers.isFetching || ruleTemplates.isFetching) {
      return (
        <div id={componentId}>
          <Spinner />
        </div>
      )
    }

    if (customers.errors || ruleTemplates.errors) {
      return (
        <div id={componentId}>
          {customers.errors &&
          <Errors messages={customers.errors} />}
          {ruleTemplates.errors &&
          <Errors messages={ruleTemplates.errors} />}
        </div>
      )
    }

    return (
      <div id={componentId}>
        <Selector id='select-customer' entities={customers} onChange={onCustomerChange}>
          Select customer:
        </Selector>
        <Selector id='select-rule-template' entities={ruleTemplates} onChange={onRuleTemplateChange}>
          Select rule template:
        </Selector>
        <button
          className='btn btn-primary'
          disabled={
            !Number.isInteger(customers.selected) ||
            !Number.isInteger(ruleTemplates.selected)
          }
          onClick={() => onFilter(customers.selected, ruleTemplates.selected, didInvalidateFilter)}
         >
           Filter Rules
         </button>
      </div>
    )
  }
}

RuleGeneratorFilterView.propTypes = {
  didInvalidateFilter: PropTypes.bool.isRequired,
  customers: PropTypes.shape({
    errors: PropTypes.shape({
      header: PropTypes.string.isRequired,
      details: PropTypes.arrayOf(PropTypes.string).isRequired
    }),
    data: PropTypes.object,
    selected: PropTypes.number,
    isFetching: PropTypes.bool.isRequired
  }).isRequired,
  ruleTemplates: PropTypes.shape({
    errors: PropTypes.shape({
      header: PropTypes.string.isRequired,
      details: PropTypes.arrayOf(PropTypes.string).isRequired
    }),
    data: PropTypes.object,
    selected: PropTypes.number,
    isFetching: PropTypes.bool.isRequired
  }).isRequired,
  onCustomerChange: PropTypes.func.isRequired,
  onRuleTemplateChange: PropTypes.func.isRequired,
  onFilter: PropTypes.func.isRequired,
  dispatch: PropTypes.func.isRequired
}

function mapStateToProps (state) {
  const { didInvalidateFilter, customers, ruleTemplates } = state
  return {
    didInvalidateFilter,
    customers,
    ruleTemplates
  }
}

function mapDispatchToProps (dispatch) {
  return {
    dispatch,
    onCustomerChange: (id) => {
      dispatch(selectCustomer(id))
    },
    onRuleTemplateChange: (id) => {
      dispatch(selectRuleTemplate(id))
    },
    onFilter: (selectedCustomerId, selectedRuleTemplateId, didInvalidateFilter) => {
      if (didInvalidateFilter) {
        dispatch(invalidateRuleInstances())
        dispatch(fetchRuleInstancesIfNeeded(selectedCustomerId, selectedRuleTemplateId))
      }
      dispatch(selectView(GENERATE_VIEW))
    }
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RuleGeneratorFilterView)
