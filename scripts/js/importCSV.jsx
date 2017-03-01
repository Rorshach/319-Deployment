import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import Dropzone from 'react-dropzone';

export default class ImportCSV extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            imageFiles: []
        }
    }

    onImageDrop(files) {
        this.setState({
            uploadedFile: files[0]
        });
        console.log(this.state.uploadedFile);
    }

    render() {
        return (
            <div className="dropzonePad">
                <Dropzone
                    accept="text/csv"
                    onDrop={this.onImageDrop.bind(this)}>
                    <p>Drop a CSV file or click to select a file to upload.</p>
                </Dropzone>
            </div>

        );
    }
}

