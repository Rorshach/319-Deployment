import React from 'react';
import ReactDOM from 'react-dom';
import {Form, FormGroup, FormControl, Col, Button, Checkbox, ControlLabel} from 'react-bootstrap';

export default class AdminComponent extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <Form horizontal>
                <FormGroup controlId="formHorizontalEmail">
                    <Col componentClass={ControlLabel} sm={2}>
                        Email
                    </Col>
                    <Col sm={10}>
                        <FormControl type="email" placeholder="Email" />
                    </Col>
                </FormGroup>
            </Form>
        )
    }
}