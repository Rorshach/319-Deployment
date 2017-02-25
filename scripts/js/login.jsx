import React from 'react';
import ReactDOM from 'react-dom';
import {Form, FormGroup, FormControl, Col, Button, Checkbox, ControlLabel} from 'react-bootstrap';
import request from "superagent";
import {Alerts} from "./alerts.jsx";
export default class LoginComponent extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            showAlert : false,
            userNameValue : "",
            passwordValue : ""
        }
        this.login = this.login.bind(this);
        this.userNameValidation = this.userNameValidation.bind(this);
        this.updateUserNameValue = this.updateUserNameValue.bind(this);
        this.passwordValueValidation = this.passwordValueValidation.bind(this);
        this.updatePasswordValue = this.updatePasswordValue.bind(this);
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
    userNameValidation(){
        const length = this.state.userNameValue.length;
        if (length > 6) return 'success';
        else if (length > 5) return 'warning';
        else if (length > 0) return 'error';
    }
    passwordValueValidation(){
        const length = this.state.passwordValue.length;
        if (length > 6) return 'success';
        else if (length > 5) return 'warning';
        else if (length > 0) return 'error';
    }
    updateUserNameValue(e){
        this.setState({ userNameValue: e.target.value });
    }
    updatePasswordValue(e){
        this.setState({ passwordValue: e.target.value });
    }
    render() {
        return (
            <form ref="loginform" className = "#loginForm" onSubmit = {this.login}>
                <FormGroup
                    validationState = {this.userNameValidation()}
                    controlId="formBasicText">
                    <Col componentClass={ControlLabel} sm={2}>
                        Username
                    </Col>
                    <Col sm={10}>
                        <FormControl
                            value={this.state.userNameValue}
                            onChange={this.updateUserNameValue}
                            type="text" placeholder="Please enter username" />
                        <FormControl.Feedback />
                    </Col>
                </FormGroup>

                <FormGroup
                    validationState = {this.passwordValueValidation()}
                    controlId="formBasicText">
                    <Col componentClass={ControlLabel} sm={2}>
                        Password
                    </Col>
                    <Col sm={10}>
                        <FormControl
                            value={this.state.passwordValue}
                            onChange={this.updatePasswordValue}
                            placeholder="Please enter password" />
                        <FormControl.Feedback />
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
