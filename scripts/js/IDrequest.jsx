import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {FormGroup, FormControl} from 'react-bootstrap';
import {ControlLabel} from 'react-bootstrap';
import {Button} from 'react-bootstrap';

export default class IDRequest extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            isCheckboxSelected : false,
            categoryOption : null,
            hasOverThreeItems : false,
            canSubmit : false,
            statusDisplay : false,
            statusMessage : null,
            categoriesResponse : null,
            categoryResponse : null,
            ID : ""
        }
        autobind(this);
    }

    getRequest() {
        request
            .get("/requests/"+ 8)
            .end((err, res) => {
                if (err) {
                    this.setState({
                        statusMessage: err,
                        statusDisplay: true
                    });
                } else {
                    try {
                        var categoryOptions = [];
                        var jsonResponse = JSON.parse(res.text);
                        this.setState({
                            categoryResponse : JSON.stringify(jsonResponse, null, 4)
                        });
                    } catch (e) {
                        console.log(e);
                    }
                }
            });
    }

    handleID(e) {
        this.setState({ID : e.target.value});
    }

    render() {
        return (
        <div>
            <FormControl className="ID" componentClass="textarea" placeholder="ID" maxLength='5' onChange={this.handleID}/>
            <Button type="submit" className="submitBtn" onClick={this.getRequest}>
                Submit
            </Button>
            {this.state.categoryResponse}
        </div>

        );
    }
}
