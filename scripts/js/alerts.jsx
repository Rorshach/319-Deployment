import React from 'react'
import ReactDOM from 'react-dom';
import AlertContainer from 'react-alert';

export default class Alerts extends React.Component {
    constructor(props) {
        super(props);
        this.alertOptions = {
            offset: 14,
            position: 'bottom Left',
            theme: 'dark',
            time: 10000,
            transition: 'scale'
        };
    }

    showAlert() {
        msg.success('Connected To API', {
            time: 5000,
            type: 'success',
            transition: 'fade',
            position: 'top left'
        });
    }

    showAlert2(){
        msg.error('Did Not Connect to API', {
            time: 5000,
            type: 'success',
            transition: 'fade',
            position: 'top left'
        });
    }

    showAlert3(){
        msg.error('API Timed Out', {
            time: 5000,
            type: 'success',
            transition: 'fade',
            position: 'top left'
        });
    }


    renderUserMessage(){
        if (this.props.title === 'success') {
            return (
                <span>
                <AlertContainer ref={(a) => global.msg = a} {...this.alertOptions} />
                <button onClick={this.showAlert}>{this.props.title}</button>
                </span>
            );
        } else if (this.props.title === 'noConnection'){
            return (
            <span>
            <AlertContainer ref={(a) => global.msg = a} {...this.alertOptions} />
            <button onClick={this.showAlert2}>{this.props.title}</button>
            </span>
            );
        } else if (this.props.title === 'timeout') {
            return (
                <span>
            <AlertContainer ref={(a) => global.msg = a} {...this.alertOptions} />
            <button onClick={this.showAlert3}>{this.props.title}</button>
            </span>
            );
        }
    }

    render(){
        return(
            <div>
                {this.renderUserMessage()}
            </div>
        );
    }
}
