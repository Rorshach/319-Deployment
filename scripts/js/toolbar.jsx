import React from 'react';
import ReactDOM from 'react-dom';
import {Navbar} from 'react-bootstrap';
import {Nav} from 'react-bootstrap';
import {NavItem} from 'react-bootstrap';

export default class Toolbar extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (<Navbar fluid bsStyle="default" className="navbar">
                  <Navbar.Header>
                    <Navbar.Text>
                    <img id="logo" src={require('./../../images/coastcapitallogo.png')}/>
                    </Navbar.Text>
                  </Navbar.Header>
                  <h4><Navbar.Text className="navbarText">
                    <b>Coast Capital Savings Ordering System</b>
                  </Navbar.Text></h4>
                  <Nav bsStyle="pills" pullRight className="navbarLinks">
                    <NavItem eventKey={1} href="/history">History</NavItem>
                    <NavItem eventKey={3} href="/logout">Logout</NavItem>
                  </Nav>
                </Navbar>);
    }
}
