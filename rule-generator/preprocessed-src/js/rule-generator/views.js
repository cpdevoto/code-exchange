export const FILTER_VIEW = {
  id: 'FILTER_VIEW',
  text: 'Step 1: Filter Rules'
}

export const GENERATE_VIEW = {
  id: 'GENERATE_VIEW',
  text: 'Step 2: Generate Rules'
}

export const VIEW_SEQUENCE = [
  FILTER_VIEW,
  GENERATE_VIEW
]

export const VIEWS = VIEW_SEQUENCE.map((map, view) => {
  map[view.id] = view
}, {})
