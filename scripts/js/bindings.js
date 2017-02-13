/**
 * Created by alekhrycaiko on 2017-01-24.
 */
import React from 'react';
import ReactDOM from 'react-dom';
import Content from './content.jsx';
import Toolbar from './toolbar.jsx';

module.exports.bindings
{
    ReactDOM.render(<Content/>, document.getElementById('content'));
    ReactDOM.render(<Toolbar/>, document.getElementById('toolbar'));
}/**
 * Created by alekhrycaiko on 2017-01-24.
 */
