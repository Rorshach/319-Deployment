import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import Alerts from "./alerts.jsx";
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

/*
// TODO: need to move all of these components into separate classes.
export default class Content extends React.Component {
    constructor(props){
        super(props);
        autobind(this);
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
    constructor (props){
        super(props);
        autobind(this);
    }
    render() {
        return (<p>Create a Request</p>);
    }
}
*/
export default class RequestForm extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            statusDisplay : false,
            statusMessage : null
        }
        autobind(this);
    }

    callRequest(e) {
        e.preventDefault();
        request
            .post("/requests")
            .send({ "category_id": 11})
            .end((err, res) => {
                console.log(res);
                if (err){
                    this.setState({
                        statusMessage: "noConnection",
                        statusDisplay: true
                    });
                }
                else{
                    this.setState({
                        statusMessage: 'success',
                        statusDisplay: true

                    });
                }
            });
    }

    // TODO:  I think we just want to set the prop based on theState, then
    // TODO:    feed it into a component that optionally renders.
    render() {
        if (this.state.statusDisplay === true){
            return (
                <div class ="has-status">
                <Alerts title={this.state.statusMessage}/>
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
                            <Button onSubmit = {this.callRequest}  type="submit">
                                Submit Request
                            </Button>
                        </Col>
                    </FormGroup>
                </Form>
                </div>
            );
        }else {
            return (
                <div class ="no-status">
                <Form horizontal>
                    <FormGroup controlId="formControlsSelect">
                        <ControlLabel>Select a product type</ControlLabel>
                        <FormControl componentClass="select" placeholder="select">
                            <option value ="null" disabled> None </option>
                            <option value="select">Pencil</option>
                            <option value="other">Laptop</option>
                        </FormControl>
                    </FormGroup>
                    <FormGroup>
                        <Col smOffset={2} sm={8}>
                            <Button onClick={this.callRequest} type="button">
                                Submit Request
                            </Button>
                        </Col>
                    </FormGroup>
                </Form>
                </div>
            );
        }
    }
}
