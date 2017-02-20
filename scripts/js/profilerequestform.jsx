import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {FormControl} from 'react-bootstrap';

export default class ProfileRequestForm extends React.Component {

    constructor(props){
        super(props);
        autobind(this);
    }

    render() {
            return (
                <FormGroup>
                    <ControlLabel><b>Select a Profile</b></ControlLabel>
                            <Row>
                              <Col sm={4}>
                              <FormControl componentClass="select" multiple style={{ height : 200 }}>
                                 <option value="profile1">Profile 1</option>
                                 <option value="profile2">Profile 2</option>
                                 <option value="profile3">Profile 3</option>
                                 <option value="profile4">Profile 4</option>
                                 <option value="profile5">Profile 5</option>
                                 <option value="profile6">Profile 6</option>
                              </FormControl>
                            </Col>
                            <Col sm={8}>
                              <FormControl componentClass="textarea" readOnly style={{ height : 200 }}>
                                Test test test
                              </FormControl>
                            </Col>
                          </Row>
                </FormGroup>
            );
    }
}
