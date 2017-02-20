import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {FormGroup} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {FormControl} from 'react-bootstrap';
import {HelpBlock} from 'react-bootstrap';

export default class CategoriesRequestForm extends React.Component {

    constructor(props){
        super(props);
        autobind(this);
    }

    render() {
            return (
              <FormGroup>
                <ControlLabel><b>Select a Category</b></ControlLabel>
                  <HelpBlock>Select up to 3 items.</HelpBlock>
                  <FormControl componentClass="select" placeholder="select">
                      <option value="category1">Category 1</option>
                      <option value="category2">Category 2</option>
                  </FormControl>
              </FormGroup>
            );
    }
}
