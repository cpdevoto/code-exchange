import React, { Component } from 'react'
import { Provider } from 'react-redux'
import configureStore from '../configureStore'
import RuleGenerator from './RuleGenerator'
import testReducers from '../preloadedState'

const store = configureStore()

export default class Root extends Component {
  render () {
    return (
      <Provider store={store}>
        <RuleGenerator />
      </Provider>
    )
  }
}

// testReducers(store)
