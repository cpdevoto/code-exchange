import React, { Component } from 'react'
import PropTypes from 'prop-types'

export default class Tabs extends Component {
  constructor (props, context) {
    super(props, context)
    const {defaultTabIndex} = this.props
    this.state = {
      activeTabIndex: defaultTabIndex !== undefined ? defaultTabIndex : 0
    }
    this.handleTabClick = this.handleTabClick.bind(this)
  }

    // Toggle currently active tab
  handleTabClick (tabIndex) {
    const { activeTabIndex } = this.state
    if (tabIndex === activeTabIndex) {
      return
    }
    this.setState({
      activeTabIndex: tabIndex
    })
  }

    // Encapsulate <Tabs/> component API as props for <Tab/> children
  renderChildrenWithTabsApiAsProps () {
    const { activeTabIndex } = this.state
    const { children } = this.props
    return React.Children.map(children, (child, index) => {
      return React.cloneElement(child, {
        onClick: this.handleTabClick,
        tabIndex: index,
        isActive: index === activeTabIndex
      })
    })
  }

    // Render current active tab content
  renderActiveTabContent () {
    const {children} = this.props
    const {activeTabIndex} = this.state
    if (children[activeTabIndex]) {
      return children[activeTabIndex].props.children
    }
  }

  render () {
    return (
      <div className='tabs'>
        <ul className='tabs-nav'>
          {this.renderChildrenWithTabsApiAsProps()}
        </ul>
        <div className='tabs-active-content'>
          {this.renderActiveTabContent()}
        </div>
      </div>
    )
  }
};

Tabs.propTypes = {
  defaultTabIndex: PropTypes.number
}
