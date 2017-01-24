/**
 * Created by alekhrycaiko on 2017-01-22.
 */
/* setup.js */
/*
Must be run before test suite executes or things will fail bad.
 */

var jsdom = require('jsdom').jsdom;

global.document = jsdom('');
global.window = document.defaultView;
Object.keys(document.defaultView).forEach((property) => {
    if (typeof global[property] === 'undefined') {
        global[property] = document.defaultView[property];
    }
});

global.navigator = {
    userAgent: 'node.js'
};