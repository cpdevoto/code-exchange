import { combineReducers } from 'redux'
import {
  SELECT_VIEW,
  REQUEST_CUSTOMERS,
  RECEIVE_CUSTOMERS_SUCCESS,
  RECEIVE_CUSTOMERS_ERROR,
  SELECT_CUSTOMER,
  REQUEST_RULE_TEMPLATES,
  RECEIVE_RULE_TEMPLATES_SUCCESS,
  RECEIVE_RULE_TEMPLATES_ERROR,
  SELECT_RULE_TEMPLATE,
  REQUEST_RULE_INSTANCES,
  RECEIVE_RULE_INSTANCES_SUCCESS,
  RECEIVE_RULE_INSTANCES_ERROR,
  TOGGLE_RULE_INSTANCE,
  SELECT_ALL_RULE_INSTANCES,
  DESELECT_ALL_RULE_INSTANCES,
  SAVE_RULE_INSTANCES,
  SAVE_RULE_INSTANCES_SUCCESS,
  SAVE_RULE_INSTANCES_ERROR,
  INVALIDATE_RULE_INSTANCES
} from './actions'
import {
  FILTER_VIEW
} from './views'

function selectedView (state = FILTER_VIEW.id, action) {
  switch (action.type) {
    case SELECT_VIEW:
      return action.view
    default:
      return state
  }
}

function didInvalidateFilter (state = false, action) {
  switch (action.type) {
    case SELECT_CUSTOMER:
    case SELECT_RULE_TEMPLATE:
      return true
    case RECEIVE_RULE_INSTANCES_SUCCESS:
      return false
    default:
      return state
  }
}

function customers (state = {
  selected: null,
  isFetching: false,
  errors: null,
  data: null
}, action) {
  switch (action.type) {
    case REQUEST_CUSTOMERS:
      return {
        ...state,
        isFetching: true
      }
    case RECEIVE_CUSTOMERS_SUCCESS:
      return {
        ...state,
        isFetching: false,
        data: action.data,
        errors: null
      }
    case RECEIVE_CUSTOMERS_ERROR:
      return {
        ...state,
        isFetching: false,
        errors: action.errors,
        data: null
      }
    case SELECT_CUSTOMER:
      return {
        ...state,
        selected: action.id
      }
    default:
      return state
  }
}

function ruleTemplates (state = {
  selected: null,
  isFetching: false,
  errors: null,
  data: null
}, action) {
  switch (action.type) {
    case REQUEST_RULE_TEMPLATES:
      return {
        ...state,
        isFetching: true
      }
    case RECEIVE_RULE_TEMPLATES_SUCCESS:
      return {
        ...state,
        isFetching: false,
        data: action.data,
        errors: null
      }
    case RECEIVE_RULE_TEMPLATES_ERROR:
      return {
        ...state,
        isFetching: false,
        errors: action.errors,
        data: null
      }
    case SELECT_RULE_TEMPLATE:
      return {
        ...state,
        selected: action.id
      }
    default:
      return state
  }
}

function ruleInstances (state = {
  isSaving: false,
  isFetching: false,
  didInvalidate: true,
  errors: null,
  equipment: [],
  candidates: []
}, action) {
  switch (action.type) {
    case INVALIDATE_RULE_INSTANCES:
      return {
        ...state,
        didInvalidate: true
      }
    case SAVE_RULE_INSTANCES:
      return {
        ...state,
        isSaving: true,
        didInvalidate: false
      }
    case REQUEST_RULE_INSTANCES:
      return {
        ...state,
        isFetching: true,
        didInvalidate: false
      }
    case RECEIVE_RULE_INSTANCES_SUCCESS:
      return {
        ...state,
        isFetching: false,
        didInvalidate: false,
        equipment: action.equipment,
        candidates: action.candidates,
        errors: null
      }
    case SAVE_RULE_INSTANCES_SUCCESS:
      return {
        ...state,
        isSaving: false,
        didInvalidate: false,
        errors: null
      }
    case RECEIVE_RULE_INSTANCES_ERROR:
      return {
        ...state,
        isFetching: false,
        didInvalidate: false,
        equipment: [],
        candidates: [],
        errors: action.errors
      }
    case SAVE_RULE_INSTANCES_ERROR:
      return {
        ...state,
        isSaving: false,
        didInvalidate: false,
        errors: action.errors
      }
    case TOGGLE_RULE_INSTANCE:
      return {
        ...state,
        candidates: state.candidates.map(c => {
          if (c.id === action.id) {
            return {
              ...c,
              selected: !c.selected
            }
          }
          return c
        })
      }
    case SELECT_ALL_RULE_INSTANCES:
      return {
        ...state,
        candidates: state.candidates.map(c => {
          return {
            ...c,
            selected: true
          }
        })
      }
    case DESELECT_ALL_RULE_INSTANCES:
      return {
        ...state,
        candidates: state.candidates.map(c => {
          return {
            ...c,
            selected: false
          }
        })
      }
    default:
      return state
  }
}

const rootReducer = combineReducers({
  selectedView,
  didInvalidateFilter,
  customers,
  ruleTemplates,
  ruleInstances
})

export default rootReducer
