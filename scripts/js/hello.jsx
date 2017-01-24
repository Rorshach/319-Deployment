import React from 'react';
import ReactDOM from 'react-dom';

export default class Hello extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            'MEEPO' : true
        }
    Hello.propTypes = {
            handleSomeProp : 'red'
    }
    }
    render() {
        return (<div onChange={this.props.handleSomeProp}> <h1>qwdqwdqwdqwdqdwq</h1> </div>);
    }
}