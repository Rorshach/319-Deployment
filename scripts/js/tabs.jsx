import React from 'react';
import ReactDOM from 'react-dom';
import {Tabs} from 'react-bootstrap';
import {Tab} from 'react-bootstrap';

export default class TabsComponent extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <Tabs defaultActiveKey={2} id="cc-tabs">
                <Tab eventKey={1} title="Needs Approval"> List of  requests needing approval (approvers only) </Tab>
                <Tab eventKey={2} title="In Processing"> List of active requests (ownership) </Tab>
                <Tab eventKey={3} title="Approved">list of approved requests (approver)</Tab>
            </Tabs>
        )
    }
}
