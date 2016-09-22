const webpack = require('webpack');

module.exports = {
    entry: "./entry.js",
    output: {
        path: __dirname,
        filename: "dist/bundle.js"
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: "style!css" },
			{ test: /\.js$/, exclude: /(node_modules|bower_components)/, loader: 'babel', query: {presets: ['es2015']} },
			{ test: /\.jsx?$/, exclude: /node_modules/, loader: "babel",query:{presets:['react']}},
			{ test: /\.json$/, loader: "json-loader"}
        ]
    }
};