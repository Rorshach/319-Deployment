import React from 'react';
import ReactDOM from 'react-dom';
import {Grid} from 'react-bootstrap';
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {Form} from 'react-bootstrap';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {FormControl} from 'react-bootstrap';
import {Checkbox} from 'react-bootstrap';
import {Button} from 'react-bootstrap';
import {PageHeader} from 'react-bootstrap';
import Alerts from './alerts.jsx'

export default class Content extends React.Component {
    constructor(props){
        super(props);
    }
    render() {
        return (
          <Grid fluid  className="contentGrid">
            <PageHeader><HeaderName/></PageHeader>
            <Row className="show-grid">
              <Col><RequestForm/></Col>
            </Row>
            <Hello/>
          </Grid>
      );
    }
}

class HeaderName extends React.Component {
    render() {
        return (
            <p>Create a Request</p>
        );
    }
}


class Hello extends React.Component {



    render() {
        return (
            <div>
            <Alerts title="success" />
            <Alerts title="noConnection" />
            <Alerts title="timeout" />
            </div>
        );
    }
}
// TODO: This is basically copied from React Bootstrap, edit this to the form we need
class RequestForm extends React.Component {
    render() {
        return (
          <Form horizontal>
            <FormGroup controlId="formHorizontalEmail">
              <Col componentClass={ControlLabel} sm={2}>
                Email
              </Col>
              <Col sm={8}>
                <FormControl type="email" placeholder="Email" />
              </Col>
            </FormGroup>

            <FormGroup controlId="formHorizontalPassword">
              <Col componentClass={ControlLabel} sm={2}>
                Password
              </Col>
              <Col sm={8}>
                <FormControl type="password" placeholder="Password" />
              </Col>
            </FormGroup>

            <FormGroup>
              <Col smOffset={2} sm={8}>
                <Checkbox>Remember me</Checkbox>
              </Col>
            </FormGroup>

            <FormGroup>
              <Col smOffset={2} sm={8}>
                <Button type="submit">
                  Sign in
                </Button>
              </Col>
            </FormGroup>
          </Form>
        );
    }
}
