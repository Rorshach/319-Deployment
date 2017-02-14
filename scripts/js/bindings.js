/**
 * Created by alekhrycaiko on 2017-01-24.
 */
import React from 'react';
import ReactDOM from 'react-dom';
import Content from './content.jsx';
import Toolbar from './toolbar.jsx';
import TabsComponent from "./tabs.jsx";

module.exports.bindings
{
    ReactDOM.render(<Content/>, document.getElementById('content'));
    ReactDOM.render(<Toolbar/>, document.getElementById('toolbar'));
    ReactDOM.render(<TabsComponent/>, document.getElementById('tabs'));

}/**
 * Created by alekhrycaiko on 2017-01-24.
 */
