import React from 'react';
import ReactDOM from 'react-dom';
import {Form, FormGroup, FormControl, Col, Button, Checkbox, ControlLabel} from 'react-bootstrap';
import request from "superagent";
import {Alerts} from "./alerts.jsx";
export default class LoginComponent extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            showAlert : false
        }
        this.login = this.login.bind(this);
    }

    login(e, data){
        e.preventDefault();
        console.log(data);
        let form = ReactDOM.findDOMNode(this.refs.loginform).elements;
        let username = form[0].value;
        let password = form[1].value;
        let payload = {
            username : username,
            password : password
        }
        request
            .post("/authenticate")
            .send(payload)
            .set('Content-Type', 'application/json')
            .set('Accept', 'application/json')
            .end( function (err, res){
               console.log(err);
               console.log(res);
            });

    }

    render() {
        return (
            <form ref="loginform" className = "#loginForm" onSubmit = {this.login}>
                <FormGroup controlId="formHorizontalEmail">
                    <Col componentClass={ControlLabel} sm={2}>
                        Username
                    </Col>
                    <Col sm={10}>
                        <FormControl type="text" placeholder="Please enter username" />
                    </Col>
                </FormGroup>

                <FormGroup controlId="formHorizontalPassword">
                    <Col componentClass={ControlLabel} sm={2}>
                        Password
                    </Col>
                    <Col sm={10}>
                        <FormControl type="password" placeholder="Please enter password" />
                    </Col>
                </FormGroup>

                <FormGroup>
                    <Col smOffset={2} sm={10}>
                        <Checkbox>Remember me</Checkbox>
                    </Col>
                </FormGroup>

                <FormGroup>
                    <Col smOffset={2} sm={10}>
                        <Button type="submit">
                            Sign in
                        </Button>
                    </Col>
                </FormGroup>
            </form>
        )
    }
}
