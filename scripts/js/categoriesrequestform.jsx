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

export default class CategoriesRequestForm extends React.Component {

    constructor(props){
        super(props);
        autobind(this);
        var selectedCheckboxesSet = null;
        var categories = null;
        var selectedProducts = null;

        this.state = {
          isCheckboxSelected : false,
          categoryOption : null,
          hasOverThreeItems : false,
          canSubmit : false,
          statusDisplay : false,
          statusMessage : null
        }
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

    getProductsInCategories(e) {
      var checkboxes = new Array();
      for (var i = 0; i < 6; i++) {
        if (e.target.options[i].selected) {
          var label = "Category" + i;
          checkboxes.push(<option value={label} key={label}>{label}</option>);
          this.setState({categoryOption: e.target.options[i].value});
        }
      }
      this.categories = checkboxes;
    }

    componentWillMount() {
      this.selectedCheckboxesSet = new Set();
    }

    createCheckbox(label) {
      return <Checkbox onChange={() => this.toggleCheckbox(label)} checked={true} key={label}>{label}</Checkbox>
    }

    createCheckboxes(e) {
        if (e.target.value !== null && e.target.value !== "") {
          this.toggleCheckbox(e.target.value);
        }
    }

    toggleCheckbox(label) {
      if (this.selectedCheckboxesSet.has(label)) {
        this.selectedCheckboxesSet.delete(label);
        this.setState({isCheckboxSelected: false});
      } else {
        this.selectedCheckboxesSet.add(label);
        this.setState({isCheckboxSelected: true});
      }

      if (this.selectedCheckboxesSet.size > 0) {
        this.setState({canSubmit: true});
      } else {
        this.setState({canSubmit: false});
      }

      if (this.selectedCheckboxesSet.size > 2) {
        this.setState({hasOverThreeItems: true});
      } else {
        this.setState({hasOverThreeItems: false});
      }

      var array = [];
      var data = this;
      this.selectedCheckboxesSet.forEach(function(i) {array.push(i);});
      this.selectedProducts = array.map(function(a) {return data.createCheckbox(a, true)});
    }


    render() {
          var alert;

          if (this.state.statusDisplay === true) {
              alert = <Alerts title={this.state.statusMessage}/>
          }

            return (
              <Form onSubmit={this.callRequest}>
              {alert}
              <FormGroup>
                <ControlLabel><b>Select a Category</b></ControlLabel>
                  <HelpBlock>Select up to 3 items.</HelpBlock>
                  <Row>
                    <Col sm={4}>
                    <FormControl componentClass="select" multiple style={{ height : 200 }} onChange={this.getProductsInCategories} disabled={this.state.hasOverThreeItems}>
                       <option value="category1">Category 1</option>
                       <option value="category2">Category 2</option>
                       <option value="category3">Category 3</option>
                       <option value="category4">Category 4</option>
                       <option value="category5">Category 5</option>
                       <option value="category6">Category 6</option>
                    </FormControl>
                  </Col>
                  <Col sm={8}>
                  <FormControl componentClass="select" multiple style={{ height : 200 }} onClick={this.createCheckboxes} disabled={this.state.hasOverThreeItems}>
                      {this.categories}
                  </FormControl>
                  </Col>
                </Row>
                <p/>
                <ControlLabel><b>Selected Items</b></ControlLabel>
                  <Well style={{height : 135}}>
                    {this.selectedProducts}
                  </Well>

                  <Col>
                      <ControlLabel><b>Description</b></ControlLabel>
                      <HelpBlock>Enter an optional short description about your request (max 250 characters).</HelpBlock>
                      <FormControl componentClass="textarea" placeholder="Description" maxLength='250'/>
                  </Col>
                  <p/>
                  <Button type="submit" disabled={!this.state.canSubmit}>
                        Submit Request
                  </Button>
              </FormGroup>
            </Form>
            );
    }
}
