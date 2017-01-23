module.exports = {
    plugins: [
        require('precss'),
        require('autoprefixer')({browsers : ['ie >= 11']})
    ]
}