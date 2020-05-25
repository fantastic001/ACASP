const path = require('path');

const { VueLoaderPlugin } = require('vue-loader')
const { FileLoaderPlugin } = require('file-loader')
const { ImgLoaderPlugin } = require('img-loader')
const { SassLoaderPlugin } = require('sass-loader')



module.exports = {
  entry: './index.js',
  mode: "development",
  output: {
    filename: 'main.js',
    path: path.resolve(__dirname, 'dist')
  },
  module: {
    rules: [
      {
        test: /\.vue$/,
        use: [
          'vue-loader',
        ]
      },
      {
        test: /\.(png|jpe?g|gif)$/i,
        use: [
          'file-loader',
        ]
      },
      {
        test: /\.scss$/,
        use: [
          'vue-style-loader',
          'css-loader',
          'sass-loader'
        ]
      },
      {
        test: /\.svg$/,
        use: [
          'svg-loader',
        ]
      },
      {
        test: /\.css$/,
        use: [
          'vue-style-loader',
          'css-loader',
          //'sass-loader'
        ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    // new SassLoaderPlugin(), 
    // new FileLoaderPlugin(),
    // new ImgLoaderPlugin()
  ]
};
