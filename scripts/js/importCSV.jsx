import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import Dropzone from 'react-dropzone';

export default class ImportCSV extends React.Component {

    constructor(props){
        super(props);
        autobind(this);
    }

    onImageDrop(files) {
        this.setState({
        uploadedFile: files[0]
        });
    }

    render() {
      return (
        <div class="dropzonePad">
        <Dropzone
            multiple={false}
            accept="image/*"
            onDrop={this.onImageDrop.bind(this)}>
            <p>Drop an image or click to select a file to upload.</p>
        </Dropzone>
        </div>
      );
    }
}