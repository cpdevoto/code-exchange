import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Toggle from './Toggle'

function noItemsSelected (items) {
  for (let i = 0; i < items.length; i++) {
    if (items[i].selected) {
      return false
    }
  }
  return true
}

function getSelectedCandidates (candidates) {
  return candidates
    .filter(candidate => candidate.selected)
    .map(candidate => candidate.id)
}

export default class Candidates extends Component {
  renderButton (selectedCustomer, selectedRuleTemplate, items, onSave) {
    return (
      <button
        className='btn btn-primary generate-rules'
        disabled={noItemsSelected(items)}
        onClick={() => onSave(selectedCustomer, selectedRuleTemplate, getSelectedCandidates(items))}
      >
        Generate Rules
      </button>
    )
  }

  render () {
    const {
      selectedCustomer,
      selectedRuleTemplate,
      items,
      onToggleItem,
      onSelectAllItems,
      onDeselectAllItems,
      onSave
    } = this.props

    if (items.length === 0) {
      return (
        <i>No candidates found.</i>
      )
    }

    const candidates = items.map((item) => (
      <Toggle
        key={item.id}
        item={item}
        onToggle={onToggleItem}
      />
    ))

    return (
      <div className='candidates'>
        <div className='candidate-controls'>
          <a href='#select-all' id='select-all' className='select-all' onClick={() => onSelectAllItems()}>Select All</a>
          <span className='separator'>{'\u00a0\u00a0|\u00a0\u00a0'}</span>
          <a href='#deselect-all' id='deselect-all' className='deselect-all' onClick={() => onDeselectAllItems()}>Deselect All</a>
        </div>
        <div className='entries'>
          {candidates}
        </div>
        <div className='candidate-controls'>
          {this.renderButton(selectedCustomer, selectedRuleTemplate, items, onSave)}
        </div>
      </div>
    )
  }
}

Candidates.propTypes = {
  selectedCustomer: PropTypes.number.isRequired,
  selectedRuleTemplate: PropTypes.number.isRequired,
  items: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number.isRequired,
        text: PropTypes.string.isRequired
      })
    ).isRequired,
  onToggleItem: PropTypes.func.isRequired,
  onSelectAllItems: PropTypes.func.isRequired,
  onDeselectAllItems: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired
}
