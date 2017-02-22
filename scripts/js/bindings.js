/**
 * Created by alekhrycaiko on 2017-01-24.
 */
import React from 'react';
import ReactDOM from 'react-dom';
import Content from './content.jsx';
import Toolbar from './toolbar.jsx';
import TabsComponent from "./tabs.jsx";
import LoginComponent from "./login.jsx";
import HistoryContainer from "./history_container.jsx";

module.exports.bindings
{
    ReactDOM.render(<Toolbar/>, document.getElementById('toolbar'));

    if (document.getElementById("content")){
        ReactDOM.render(<Content/>, document.getElementById('content'));
    }
    if (document.getElementById("tabs")) {
        ReactDOM.render(<TabsComponent/>, document.getElementById('tabs'));
    }
    if (document.getElementById("login")) {
        ReactDOM.render(<LoginComponent/>, document.getElementById("login"));
    }
    if (document.getElementById("history")){
        ReactDOM.render(<HistoryContainer/>, document.getElementById("history"));
    }
}/**
 * Created by alekhrycaiko on 2017-01-24.
 */
