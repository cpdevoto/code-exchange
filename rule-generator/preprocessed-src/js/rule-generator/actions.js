import fetch from 'cross-fetch'

export const SELECT_VIEW = 'SELECT_VIEW'

export const REQUEST_CUSTOMERS = 'REQUEST_CUSTOMERS'
export const RECEIVE_CUSTOMERS_SUCCESS = 'RECEIVE_CUSTOMERS_SUCCESS'
export const RECEIVE_CUSTOMERS_ERROR = 'RECEIVE_CUSTOMERS_ERROR'
export const SELECT_CUSTOMER = 'SELECT_CUSTOMER'

export const REQUEST_RULE_TEMPLATES = 'REQUEST_RULE_TEMPLATES'
export const RECEIVE_RULE_TEMPLATES_SUCCESS = 'RECEIVE_RULE_TEMPLATES_SUCCESS'
export const RECEIVE_RULE_TEMPLATES_ERROR = 'RECEIVE_RULE_TEMPLATES_ERROR'
export const SELECT_RULE_TEMPLATE = 'SELECT_RULE_TEMPLATE'

export const REQUEST_RULE_INSTANCES = 'REQUEST_RULE_INSTANCES'
export const RECEIVE_RULE_INSTANCES_SUCCESS = 'RECEIVE_RULE_INSTANCES_SUCCESS'
export const RECEIVE_RULE_INSTANCES_ERROR = 'RECEIVE_RULE_INSTANCES_ERROR'

export const TOGGLE_RULE_INSTANCE = 'TOGGLE_RULE_INSTANCE'
export const SELECT_ALL_RULE_INSTANCES = 'SELECT_ALL_RULE_INSTANCES'
export const DESELECT_ALL_RULE_INSTANCES = 'DESELECT_ALL_RULE_INSTANCES'

export const SAVE_RULE_INSTANCES = 'SAVE_RULE_INSTANCES'
export const SAVE_RULE_INSTANCES_SUCCESS = 'SAVE_RULE_INSTANCES_SUCCESS'
export const SAVE_RULE_INSTANCES_ERROR = 'SAVE_RULE_INSTANCES_ERROR'
export const INVALIDATE_RULE_INSTANCES = 'INVALIDATE_RULE_INSTANCES'

function entityMap (json) {
  return Array.isArray(json) ? json.reduce((map, e) => {
    map[e.id] = e
    return map
  }, {}) : {}
}

function errorsObject (header, errors) {
  return {
    header: header,
    details: Array.isArray(errors) ? errors : errors !== undefined ? [ errors ] : []
  }
}

function processErrors (dispatch, error, actionFunction) {
  if (error.json) {
    error.json()
      .then((errors) => {
        if (errors.message) {
          dispatch(actionFunction(errors.message))
        } else if (Array.isArray(errors)) {
          dispatch(actionFunction(errors))
        } else {
          dispatch(actionFunction())
        }
      })
      .catch(() => {
        dispatch(actionFunction(error.statusText))
      })
  } else if (error.message) {
    dispatch(actionFunction(error.message))
  } else {
    dispatch(actionFunction())
  }
}

export function selectView (view) {
  return {
    type: SELECT_VIEW,
    view: view.id ? view.id : view
  }
}

export function requestCustomers () {
  return {
    type: REQUEST_CUSTOMERS
  }
}

export function receiveCustomersSuccess (json) {
  return {
    type: RECEIVE_CUSTOMERS_SUCCESS,
    data: entityMap(json)
  }
}

export function receiveCustomersError (errors) {
  return {
    type: RECEIVE_CUSTOMERS_ERROR,
    errors: errorsObject(
         'A problem occurred while attempting to retrieve customers',
         errors)
  }
}

export function selectCustomer (id) {
  return {
    type: SELECT_CUSTOMER,
    id: Number.isInteger(id) ? id : parseInt(id, 10)
  }
}

export function requestRuleTemplates () {
  return {
    type: REQUEST_RULE_TEMPLATES
  }
}

export function receiveRuleTemplatesSuccess (json) {
  return {
    type: RECEIVE_RULE_TEMPLATES_SUCCESS,
    data: entityMap(json)
  }
}

export function receiveRuleTemplatesError (errors) {
  return {
    type: RECEIVE_RULE_TEMPLATES_ERROR,
    errors: errorsObject(
         'A problem occurred while attempting to retrieve rule templates',
         errors)
  }
}

export function selectRuleTemplate (id) {
  return {
    type: SELECT_RULE_TEMPLATE,
    id: Number.isInteger(id) ? id : parseInt(id, 10)
  }
}

export function requestRuleInstances () {
  return {
    type: REQUEST_RULE_INSTANCES
  }
}

export function receiveRuleInstancesSuccess (json) {
  return {
    type: RECEIVE_RULE_INSTANCES_SUCCESS,
    equipment: json.equipment,
    candidates: json.candidates.map(c => {
      return {
        ...c,
        selected: false
      }
    })
  }
}

export function receiveRuleInstancesError (errors) {
  return {
    type: RECEIVE_RULE_INSTANCES_ERROR,
    errors: errorsObject(
         'A problem occurred while attempting to retrieve rule instances',
         errors)
  }
}

export function toggleRuleInstance (id) {
  return {
    type: TOGGLE_RULE_INSTANCE,
    id
  }
}

export function selectAllRuleInstances () {
  return {
    type: SELECT_ALL_RULE_INSTANCES
  }
}

export function deselectAllRuleInstances () {
  return {
    type: DESELECT_ALL_RULE_INSTANCES
  }
}

export function saveRuleInstances () {
  return {
    type: SAVE_RULE_INSTANCES
  }
}

export function saveRuleInstancesSuccess () {
  return {
    type: SAVE_RULE_INSTANCES_SUCCESS
  }
}

export function saveRuleInstancesError (errors) {
  return {
    type: SAVE_RULE_INSTANCES_ERROR,
    errors: errorsObject(
         'A problem occurred while attempting to save rule instances',
         errors)
  }
}

export function invalidateRuleInstances () {
  return {
    type: INVALIDATE_RULE_INSTANCES
  }
}

function fetchCustomers () {
  return dispatch => {
    dispatch(requestCustomers())
    return fetch('http://localhost:8080/api/customers')
      .then(response => {
        if (!response.ok) {
          throw response
        }
        return response.json()
      })
      .then(json => dispatch(receiveCustomersSuccess(json)))
      .catch(error => {
        processErrors(dispatch, error, receiveCustomersError)
      })
  }
}

function shouldFetchCustomers (state) {
  if (!state.customers.data && !state.customers.isFetching) {
    return true
  }
  return false
}

export function fetchCustomersIfNeeded () {
  return (dispatch, getState) => {
    if (shouldFetchCustomers(getState())) {
      return dispatch(fetchCustomers())
    }
  }
}

function fetchRuleTemplates () {
  return dispatch => {
    dispatch(requestCustomers())
    return fetch('http://localhost:8080/api/rule-templates')
      .then(response => {
        if (!response.ok) {
          throw response
        }
        return response.json()
      })
      .then(json => dispatch(receiveRuleTemplatesSuccess(json)))
      .catch(error => {
        processErrors(dispatch, error, receiveRuleTemplatesError)
      })
  }
}

function shouldFetchRuleTemplates (state) {
  if (!state.ruleTemplates.data && !state.ruleTemplates.isFetching) {
    return true
  }
  return false
}

export function fetchRuleTemplatesIfNeeded () {
  return (dispatch, getState) => {
    if (shouldFetchRuleTemplates(getState())) {
      return dispatch(fetchRuleTemplates())
    }
  }
}

function fetchRuleInstances (customerId, ruleTemplateId) {
  /* TEST CODE
  return dispatch => {
    dispatch(receiveRuleInstancesSuccess({
      equipment: [
        {
          id: 1,
          text: '/Bldg1/Flr1/AHU1'
        },
        {
          id: 2,
          text: '/Bldg1/Flr1/AHU2'
        }
      ],
      candidates: [
        {
          id: 3,
          text: '/Bldg1/Flr1/AHU3'
        },
        {
          id: 4,
          text: '/Bldg1/Flr1/AHU4'
        }
      ]
    }))
  }
  /**/

  /**/
  return dispatch => {
    dispatch(requestRuleInstances())
    return fetch(`http://localhost:8080/api/customers/${customerId}/rule-templates/${ruleTemplateId}/rule-instances`)
      .then(response => {
        if (!response.ok) {
          throw response
        }
        return response.json()
      })
      .then(json => dispatch(receiveRuleInstancesSuccess(json)))
      .catch(error => {
        processErrors(dispatch, error, receiveRuleInstancesError)
      })
  }
  /**/
}

function shouldFetchRuleInstances (state) {
  if (state.ruleInstances.didInvalidate &&
     !state.ruleInstances.isFetching && !state.ruleInstances.isSaving) {
    return true
  }
  return false
}

export function fetchRuleInstancesIfNeeded (customerId, ruleTemplateId) {
  return (dispatch, getState) => {
    if (shouldFetchRuleInstances(getState())) {
      return dispatch(fetchRuleInstances(customerId, ruleTemplateId))
    }
  }
}

export function postRuleInstances (customerId, ruleTemplateId, candidates) {
  /*  TEST CODE
  return (dispatch, getState) => {
    dispatch(invalidateRuleInstances())
  }
  */
  return dispatch => {
    dispatch(saveRuleInstances())
    return fetch(`http://localhost:8080/api/customers/${customerId}/rule-templates/${ruleTemplateId}/rule-instances`, {
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(candidates)
    })
      .then(response => {
        if (!response.ok) {
          throw response
        }
        dispatch(saveRuleInstancesSuccess())
        dispatch(invalidateRuleInstances())
      })
      .catch(error => {
        processErrors(dispatch, error, saveRuleInstancesError)
      })
  }
}
