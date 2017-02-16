import React from 'react'
import ReactDOM from 'react-dom';
import AlertContainer from 'react-alert';

export default class Alerts extends React.Component {
    constructor(props) {
        super(props);
        this.alertOptions = {
            offset: 14,
            position: 'top left',
            theme: 'dark',
            time: 10000,
            transition: 'scale'
        };
        this.showAlert = this.showAlert.bind(this);
    }
    showAlert(){
        this.msg.show(this.props.title, {
            time: 2000,
            type: 'success'
        });
    }
    componentDidMount(){
        this.showAlert();
    }

    render(){
        return(
            <div>
                <AlertContainer ref={a => this.msg = a} {...this.alertOptions} />
            </div>
        );
    }
}