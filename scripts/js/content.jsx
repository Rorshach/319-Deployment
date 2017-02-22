import React from 'react';
import ReactDOM from 'react-dom';
import autobind from 'class-autobind';
import Alerts from "./alerts.jsx";
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {Form} from 'react-bootstrap';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {PageHeader} from 'react-bootstrap';
import {Radio} from 'react-bootstrap';
import ProfileRequestForm from "./profilerequestform.jsx";
import CategoriesRequestForm from "./categoriesrequestform.jsx";

export default class RequestForm extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            selectedOption : null
        }
        autobind(this);
    }

    handleOptionChange(e) {
        this.setState({selectedOption: e});
    }

    render() {
            var form;

            if (this.state.selectedOption === 'categories') {
              form = <CategoriesRequestForm/>
            } else if (this.state.selectedOption === 'profiles') {
              form = <ProfileRequestForm/>
            }

            return (
                <div>
                  <PageHeader>Create a Request</PageHeader>
                <ControlLabel><b>Select a Product Type</b></ControlLabel>
                <Form horizontal>
                    <FormGroup controlId="formControlsSelect">
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

                    </FormGroup>
                </Form>

                {form}
                </div>
            );
    }
}
