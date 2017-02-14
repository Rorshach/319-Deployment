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
                    <NavItem eventKey={1} href="#">Link</NavItem>
                    <NavItem eventKey={2} href="#">Link</NavItem>
                    <NavItem eventKey={3} href="#">Link</NavItem>
                    <NavItem eventKey={4} href="#">Link</NavItem>
                  </Nav>
                </Navbar>);
    }
}
