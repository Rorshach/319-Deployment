import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
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
          </Grid>
      );
    }
}

class HeaderName extends React.Component {
    render() {
        return (<p>Create a Request</p>);
    }
}

class RequestForm extends React.Component {

    constructor(props){
        super(props);
    }

    callRequest(e){
        e.preventDefault();
        request
            .post("/requests")
            .send({ categoryid: '1' })
            .end(function(err, res) {
                console.log(recs);
            });

    }

    render() {
        return (
              <Form horizontal>
                  <FormGroup controlId="formControlsSelect">
                      <ControlLabel>Select a product type</ControlLabel>
                      <FormControl componentClass="select" placeholder="select">
                          <option value="select">Pencil</option>
                          <option value="other">Laptop</option>
                      </FormControl>
                  </FormGroup>
                <FormGroup>
                  <Col smOffset={2} sm={8}>
                    <Button onClick = {this.callRequest}  type="click">
                      Submit Request
                    </Button>
                  </Col>
                </FormGroup>
              </Form>
        );
    }
}
