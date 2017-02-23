module.exports = {
    plugins: [
        require('precss'),
        require('postcss-import'),
        require('autoprefixer')({browsers : ['ie >= 11']})
    ]
}