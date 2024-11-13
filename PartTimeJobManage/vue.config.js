const path = require('path')
module.exports = {
  pluginOptions: {
    'style-resources-loader': {
      preProcessor: 'less',
      patterns: [path.resolve(__dirname, './src/theme/colors.less')]
    }
  },

  productionSourceMap: false,
  devServer: {
    port: 8082,
  },
  css: {
    extract: false
  }
}
