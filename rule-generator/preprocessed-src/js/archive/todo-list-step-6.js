import React from 'react'
import ReactDOM from 'react-dom'
import { combineReducers, createStore } from 'redux'
import { Provider, connect } from 'react-redux'

// Redux Reducers

const todo = (state, action) => {
  switch (action.type) {
    case 'ADD_TODO':
      return {
        id: action.id,
        text: action.text,
        completed: false
      }
    case 'TOGGLE_TODO':
      if (state.id !== action.id) {
        return state
      }
      return {
        ...state,
        completed: !state.completed
      }
    default:
      return state
  }
}

const todos = (state = [], action) => {
  switch (action.type) {
    case 'ADD_TODO':
      return [
        ...state,
        todo(undefined, action)
      ]
    case 'TOGGLE_TODO':
      return state.map(t => todo(t, action))
    default:
      return state
  }
}

const visibilityFilter = (
  state = 'SHOW_ALL',
  action
) => {
  switch (action.type) {
    case 'SET_VISIBILITY_FILTER':
      return action.filter
    default:
      return state
  }
}

// const todoApp = (state = {}, action) => {
//   return {
//     todos: todos(state.todos, action),
//     visibilityFilter: visibilityFilter(state.visibilityFilter, action)
//   }
// }

const todoApp = combineReducers({
  todos,
  visibilityFilter
})

// Action Creators

const addTodo = (function () {
  let nextTodoId = 0

  return (text) => {
    return {
      type: 'ADD_TODO',
      id: nextTodoId++,
      text: text
    }
  }
})()

const setVisibilityFilter = (filter) => {
  return {
    type: 'SET_VISIBILITY_FILTER',
    filter
  }
}

const toggleTodo = (id) => {
  return {
    type: 'TOGGLE_TODO',
    id
  }
}

// REACT Components (Presentional Components, and Container Components created with connect())

const { Component } = React

const Todo = ({
  onClick,
  completed,
  text
}) => (
  <li
    onClick={onClick}
    style={{
      textDecoration:
        completed
          ? 'line-through'
          : 'none'
    }}>
    {text}
  </li>
)

const TodoList = ({
  todos,
  onTodoClick
}) => (
  <ul>
    {todos.map(todo =>
      <Todo
        key={todo.id}
        {...todo}
        onClick={() => onTodoClick(todo.id)}
      />
    )}
  </ul>
)

const VisibleTodoList = (function () {
  const mapStateToProps = (
    state
  ) => {
    return {
      todos: getVisibleTodos(
      state.todos,
      state.visibilityFilter
    )
    }
  }
  const mapDispatchToProps = (
    dispatch
  ) => {
    return {
      onTodoClick: (id) => {
        dispatch(toggleTodo(id))
      }
    }
  }
  return connect(
    mapStateToProps,
    mapDispatchToProps
  )(TodoList)
})()

let AddTodo = ({
  dispatch
}) => {
  let input

  return (
    <div>
      <input ref={node => {
        input = node
      }} />
      <button onClick={() => {
        dispatch(addTodo(input.value))
        input.value = ''
      }}>
        Add Todo
      </button>
    </div>
  )
}
AddTodo = connect()(AddTodo) // Default behavior: does not subscribe component to the store and passes the dispatch object as a prop

const Link = ({
   active,
   onClick,
   children
}) => {
  if (active) {
    return <span>{children}</span>
  }

  return (
    <a href='#'
      onClick={e => {
        e.preventDefault()
        onClick()
      }}>
      {children}
    </a>
  )
}

const FilterLink = (function () {
  const mapStateToProps = (
    state,
    ownProps
  ) => {
    return {
      active: ownProps.filter === state.visibilityFilter
    }
  }

  const mapDispatchToProps = (
    dispatch,
    ownProps
  ) => {
    return {
      onClick: () => {
        dispatch(
          setVisibilityFilter(ownProps.filter)
        )
      }
    }
  }

  return connect(
    mapStateToProps,
    mapDispatchToProps
  )(Link)
})()

const Footer = () => (
  <p>
    Show:
    {' '}
    <FilterLink
      filter='SHOW_ALL'>
      All
    </FilterLink>
    {' '}
    <FilterLink
      filter='SHOW_ACTIVE'>
      Active
    </FilterLink>
    {' '}
    <FilterLink
      filter='SHOW_COMPLETED'>
      Completed
    </FilterLink>
  </p>
)

const getVisibleTodos = (
  todos,
  filter
) => {
  switch (filter) {
    case 'SHOW_ALL':
      return todos
    case 'SHOW_COMPLETED':
      return todos.filter(
        t => t.completed
      )
    case 'SHOW_ACTIVE':
      return todos.filter(
        t => !t.completed
      )
    default:
      return todos
  }
}

const TodoApp = () => (
  <div>
    <AddTodo />
    <VisibleTodoList />
    <Footer />
  </div>
)

ReactDOM.render(
  <Provider store={createStore(todoApp)}>
    <TodoApp />
  </Provider>,
  document.getElementById('root')
)
