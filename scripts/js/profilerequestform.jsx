import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {FormControl} from 'react-bootstrap';
import {HelpBlock} from 'react-bootstrap';
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {Checkbox} from 'react-bootstrap';
import {Well} from 'react-bootstrap';
import {Button} from 'react-bootstrap';
import {Form} from 'react-bootstrap';
import Alerts from "./alerts.jsx";

export default class ProfileRequestForm extends React.Component {

    constructor(props){
        super(props);
        autobind(this);

        this.state = {
          canSubmit : false,
          statusDisplay : false,
          statusMessage : null,
          description : "",
          profilesResponse : null,
          profileResponse : null,
          profileOption : null
        }
    }

    componentWillMount() {
      request
         .get("/profiles")
         .end((err, res) => {
              if (err) {
                  this.setState({
                      statusMessage: err,
                      statusDisplay: true
                  });
              } else {
                  var profilesOptions = [];
                  try {
                    var jsonResponse = JSON.parse(res.text);
                    for (var i = 0; i < jsonResponse.length; i++) {
                        profilesOptions.push(<option value={jsonResponse[i].code} key={jsonResponse[i].code}>{jsonResponse[i].description}</option>);
                    }
                    this.setState({
                        profilesResponse : profilesOptions
                    });
                  } catch (e) {
                    console.log(e);
                  }
              }
          });
    }

    getProductsInProfiles(e) {
        this.getProducts(e.target.options[e.target.selectedIndex].value);
        this.setState({profileOption: e.target.options[e.target.selectedIndex].value});
    }

    getProducts(id) {
        request
           .get("/profiles/"+ id)
           .end((err, res) => {
                if (err) {
                    this.setState({
                        statusMessage: err,
                        statusDisplay: true
                    });
                } else {
                  var profileOptions = [];
                  try {
                    var jsonResponse = JSON.parse(res.text).products;
                    for (var i = 0; i < jsonResponse.length; i++) {
                        profileOptions.push(<Checkbox style={{margin:15}} checked={true} readOnly key={jsonResponse[i].code}>{jsonResponse[i].description}</Checkbox>);
                    }
                    this.setState({
                        profileResponse : profileOptions,
                        canSubmit : true
                    });
                  } catch (e) {
                    console.log(e);
                  }
                }
      });
    }

    handleDescription(e) {
        this.setState({description : e.target.value});
    }


    submitRequest(e) {
        e.preventDefault();
        var selectedItemsKeys = this.state.profileResponse.map(function(e) {return e.key});
        var payload = {"products": selectedItemsKeys, "notes": this.state.description};
        request
            .post("/requests")
            .send(payload)
            .end((err, res) => {
                if (err){
                    this.setState({
                        statusMessage: "noConnection",
                        statusDisplay: true
                    });
                } else {
                    this.setState({
                        statusMessage: "Success",
                        statusDisplay: true
                    });
                    window.location.reload();
                }
            });
        }

    render() {
          var alert;

          if (this.state.statusDisplay === true) {
              alert = <Alerts title={this.state.statusMessage}/>
          }

          return (
              <Form onSubmit={this.submitRequest}>
              {alert}
              <FormGroup>
                <ControlLabel><b>Select a Profile</b></ControlLabel>
                <HelpBlock>Select a profile with predefined items.</HelpBlock>
                  <Row>
                    <Col sm={4}>
                      <FormControl className="profilesResponse" componentClass="select" multiple style={{ height : 200 }} onChange={this.getProductsInProfiles}>
                          {this.state.profilesResponse}
                      </FormControl>
                    </Col>
                    <Col sm={8}>
                        <Well className="profileResponse" style={{ height : 200 }}>
                          {this.state.profileResponse}
                        </Well>
                    </Col>
                  </Row>
                  <p/>
                  <Col>
                      <ControlLabel><b>Description</b></ControlLabel>
                      <HelpBlock>Enter an optional short description about your request (max 250 characters).</HelpBlock>
                      <FormControl className="description" componentClass="textarea" placeholder="Description" maxLength='250' onChange={this.handleDescription}/>
                  </Col>
                  <p/>
                  <Button type="submit" className="submitBtn" disabled={!this.state.canSubmit}>
                        Submit Request
                  </Button>
              </FormGroup>
            </Form>
        );
    }
}
