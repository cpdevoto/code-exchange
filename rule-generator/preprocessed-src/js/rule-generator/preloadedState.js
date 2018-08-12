import {
  selectView,
  requestCustomers,
  receiveCustomersSuccess,
  receiveCustomersError,
  selectCustomer,
  requestRuleTemplates,
  receiveRuleTemplatesSuccess,
  receiveRuleTemplatesError,
  selectRuleTemplate,
  requestRuleInstances,
  receiveRuleInstancesSuccess,
  receiveRuleInstancesError,
  toggleRuleInstance,
  selectAllRuleInstances,
  deselectAllRuleInstances,
  saveRuleInstances,
  saveRuleInstancesSuccess,
  saveRuleInstancesError,
  invalidateRuleInstances
} from './actions'

import {
  FILTER_VIEW,
  GENERATE_VIEW
} from './views'

export default function testReducers (store) {
  const sampleState = {
    selectedView: FILTER_VIEW,
    didInvalidateFilter: false,
    customers: {
      selected: 1,
      isFetching: false,
      errors: null,
      data: {
        1: {
          id: 1,
          text: 'McLaren'
        },
        2: {
          id: 2,
          text: 'Redico'
        }
      }
    },
    ruleTemplates: {
      selected: 1,
      isFetching: false,
      errors: null,
      data: {
        1: {
          id: 1,
          text: 'Invariant'
        },
        2: {
          id: 2,
          text: 'Simultaneous Heating and Cooling'
        }
      }
    },
    ruleInstances: {
      isSaving: false,
      isFetching: false,
      didInvalidate: false,
      errors: null,
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
          text: '/Bldg1/Flr1/AHU3',
          selected: false
        },
        {
          id: 3,
          text: '/Bldg1/Flr1/AHU3',
          selected: false
        }
      ]
    }
  }
  console.dir(sampleState)

  store.dispatch(selectView(GENERATE_VIEW))
  store.dispatch(selectView(FILTER_VIEW))

  store.dispatch(requestCustomers())
  store.dispatch(receiveCustomersError([
    'The service could not be reached'
  ]))
  store.dispatch(requestCustomers())
  store.dispatch(receiveCustomersSuccess([
    {
      id: 1,
      text: 'McLaren'
    },
    {
      id: 2,
      text: 'Redico'
    }
  ]))
  store.dispatch(selectCustomer(1))

  store.dispatch(requestRuleTemplates())
  store.dispatch(receiveRuleTemplatesError([
    'The service could not be reached'
  ]))
  store.dispatch(requestRuleTemplates())
  store.dispatch(receiveRuleTemplatesSuccess([
    {
      id: 1,
      text: 'Invariant'
    },
    {
      id: 2,
      text: 'Simultaneous Heating and Cooling'
    }
  ]))
  store.dispatch(selectRuleTemplate(1))

  store.dispatch(invalidateRuleInstances())
  store.dispatch(requestRuleInstances())
  store.dispatch(receiveRuleInstancesError(
    'The service could not be reached'
  ))
  store.dispatch(requestRuleInstances())
  store.dispatch(receiveRuleInstancesSuccess({
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
        text: '/Bldg1/Flr1/AHU3'
      }
    ]
  }))

  store.dispatch(toggleRuleInstance(3))
  store.dispatch(toggleRuleInstance(3))
  store.dispatch(toggleRuleInstance(3))

  store.dispatch(selectAllRuleInstances())
  store.dispatch(deselectAllRuleInstances())

  store.dispatch(toggleRuleInstance(4))

  store.dispatch(saveRuleInstances())
  store.dispatch(saveRuleInstancesError([
    'The service could not be reached'
  ]))
  store.dispatch(saveRuleInstances())
  store.dispatch(saveRuleInstancesSuccess())

  store.dispatch(selectView(GENERATE_VIEW))
}
