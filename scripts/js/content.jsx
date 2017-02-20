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
import {Radio} from 'react-bootstrap';
import {HelpBlock} from 'react-bootstrap';
import ProfileRequestForm from "./profilerequestform.jsx";
import CategoriesRequestForm from "./categoriesrequestform.jsx";

export default class RequestForm extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            statusDisplay : false,
            statusMessage : null,
            selectedOption : null
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

    handleOptionChange(e) {
        this.setState({selectedOption: e});
    }

    render() {
            var form;
            var alert;
            var description;
            var submit;

            if (this.state.selectedOption !== null) {
              description = <Col>
                <ControlLabel><b>Description</b></ControlLabel>
                <HelpBlock>Enter an optional short description about your request (max 250 characters).</HelpBlock>
                <FormControl componentClass="textarea" placeholder="Description" maxLength='250'/></Col>;
              submit = <Button onSubmit = {this.callRequest}  type="submit" disabled={true}>
                    Submit Request
                </Button>;
            }

            if (this.state.selectedOption === 'categories') {
              form = <CategoriesRequestForm/>
            } else if (this.state.selectedOption === 'profiles') {
              form = <ProfileRequestForm/>
            }

            if (this.state.statusDisplay === true) {
                alert = <Alerts title={this.state.statusMessage}/>
            }

            return (
                <div>
                  <PageHeader>Create a Request</PageHeader>
                {alert}
                <Form horizontal>
                    <FormGroup controlId="formControlsSelect">
                        <Col smOffset={1}><ControlLabel><b>Select a Product Type</b></ControlLabel></Col>
                          <FormGroup>
                            <Row>
                            <Col smOffset={4} sm={3}>
                                <Radio inline name="groupOptions" onChange={ () => this.handleOptionChange('categories')}>
                                Categories
                                </Radio>
                            </Col>
                            <Col sm={3}>
                                <Radio inline name="groupOptions" onChange={() => this.handleOptionChange('profiles')}>
                                  Profiles
                                </Radio>
                            </Col>
                            </Row>
                          </FormGroup>

                          <Col smOffset={1} sm={10}>
                          {form}
                          <p/>
                          {description}
                          <p/>
                          {submit}
                          </Col>
                    </FormGroup>
                </Form>
                </div>
            );
    }
}
