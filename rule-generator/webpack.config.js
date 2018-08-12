var path = require('path')

module.exports = {
  entry: {
    '/js/todo-list.bundle.js': './preprocessed-src/js/todo-list/index.js',
    '/js/reddit-api.bundle.js': './preprocessed-src/js/reddit-api/index.js',
    '/js/rule-generator.bundle.js': './preprocessed-src/js/rule-generator/index.js'
  },
  output: {
    filename: '[name]',
    path: path.resolve(__dirname, 'src/main/resources/assets')
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        exclude: /node_modules/
      }
    ]
  },
  watch: true
}
