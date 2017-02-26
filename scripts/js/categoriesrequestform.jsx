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
        var selectedItemsList = null;
        var selectedItemsCheckboxes = null;

        this.state = {
          isCheckboxSelected : false,
          categoryOption : null,
          hasOverThreeItems : false,
          canSubmit : false,
          statusDisplay : false,
          statusMessage : null,
          categoriesResponse : null,
          categoryResponse : null,
          description : ""
        }
    }

    getProducts(id) {
        request
           .get("/categories/"+ id)
           .end((err, res) => {
                if (err) {
                    this.setState({
                        statusMessage: err,
                        statusDisplay: true
                    });
                } else {
                  try {
                    var categoryOptions = [];
                    var jsonResponse = JSON.parse(res.text).products;
                    for (var i = 0; i < jsonResponse.length; i++) {
                        categoryOptions.push(<option value={jsonResponse[i].code} key={jsonResponse[i].code}>{jsonResponse[i].description}</option>);
                    }
                    this.setState({
                        categoryResponse : categoryOptions
                    });
                  } catch (e) {
                    console.log(e);
                  }
                }
      });
    }

    submitRequest(e) {
        e.preventDefault();
        var selectedItemsKeys = this.selectedItemsList.map(function(e) {return e.key});
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
                }
                else{
                    this.setState({
                          statusMessage: "Success",
                          statusDisplay: true
                    });
                    window.location.reload();
                }
            });
    }

    getProductsInCategories(e) {
        this.getProducts(e.target.options[e.target.selectedIndex].value);
        this.setState({categoryOption: e.target.options[e.target.selectedIndex].value});
    }

    componentWillMount() {
      this.selectedItemsList = [];
      request
         .get("/categories")
         .end((err, res) => {
              if (err) {
                  this.setState({
                      statusMessage: err,
                      statusDisplay: true
                  });
              } else {
                  var categoriesOptions = [];
                  try {
                    var jsonResponse = JSON.parse(res.text);
                    for (var i = 0; i < jsonResponse.length; i++) {
                        categoriesOptions.push(<option value={jsonResponse[i].code} key={jsonResponse[i].code}>{jsonResponse[i].description}</option>);
                    }
                    this.setState({
                        categoriesResponse : categoriesOptions
                    });
                  } catch (e) {
                    console.log(e);
                  }
              }
          });
    }

    createCheckbox(label, key) {
      return <Checkbox onChange={() => this.toggleCheckbox(label, key)} checked={true} key={key}>{label}</Checkbox>
    }

    createCheckboxes(e) {
        var selected;
        try {
          selected = e.target.options[e.target.selectedIndex];
        } catch (err) {
          selected = e.target;
        }
        if (selected.value !== null && selected.value !== "") {
          this.toggleCheckbox(selected.text, selected.value);
        }
    }

    toggleCheckbox(label, key) {
      var isInSelectionList = false;
      for (var i = 0; i < this.selectedItemsList.length; i++) {
        if (this.selectedItemsList[i].key === key) {
          isInSelectionList = true;
          this.selectedItemsList.splice(i, 1);
          this.setState({isCheckboxSelected: false});
        }
      }
      if (!isInSelectionList) {
        this.selectedItemsList.push({key: key, value: label});
        this.setState({isCheckboxSelected: true});
      }

      if (this.selectedItemsList.length > 0) {
        this.setState({canSubmit: true});
      } else {
        this.setState({canSubmit: false});
      }

      if (this.selectedItemsList.length > 2) {
        this.setState({hasOverThreeItems: true});
      } else {
        this.setState({hasOverThreeItems: false});
      }

      var data = this;
      this.selectedItemsCheckboxes = this.selectedItemsList.map(function(a) {return data.createCheckbox(a.value, a.key)});
    }

    handleDescription(e) {
        this.setState({description : e.target.value});
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
                <ControlLabel><b>Select a Category</b></ControlLabel>
                  <HelpBlock>Select up to 3 items.</HelpBlock>
                  <Row>
                    <Col sm={4}>
                    <FormControl className="categoriesResponse" componentClass="select" multiple style={{ height : 200 }} onChange={this.getProductsInCategories} disabled={this.state.hasOverThreeItems}>
                      {this.state.categoriesResponse}
                    </FormControl>
                  </Col>
                  <Col sm={8}>
                    <FormControl className="categoryResponse" componentClass="select" multiple style={{ height : 200 }} onClick={this.createCheckboxes} disabled={this.state.hasOverThreeItems}>
                      {this.state.categoryResponse}
                    </FormControl>
                  </Col>
                </Row>
                <p/>
                <ControlLabel><b>Selected Items</b></ControlLabel>
                  <Well style={{height : 135}} className="selectedItems">
                    {this.selectedItemsCheckboxes}
                  </Well>

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
