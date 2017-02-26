/**
 * Created by alekhrycaiko on 2017-01-22.
 */

var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: ['babel-polyfill', './scripts/js/entry.js'],
    output: { path: __dirname + "/src/main/resources/public", filename: 'bundle.js' },
    module: {
        loaders: [
            {
                test: /.jsx?$/,
                loader: 'babel',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015', 'react', 'stage-1']
                }
            },
            {
                test: /\.css$/,
                loaders: [
                    'style-loader',
                    'css-loader?importLoaders=1',
                    'postcss-loader'
                ]
            },
            {
                test: /\.(png|jpg)$/,
                loader: 'url?limit=25000'
            },
            
        ],
        externals: {
            'react/addons': true,
            'react/lib/ExecutionEnvironment': true,
            'react/lib/ReactContext': true
        }
    }
};
