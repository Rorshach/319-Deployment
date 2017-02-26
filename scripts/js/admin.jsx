import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import Alerts from "./alerts.jsx";
import {ListGroup, ListGroupItem, Col} from 'react-bootstrap';
import ProfileRequestForm from "./profilerequestform.jsx";
import CategoriesRequestForm from "./categoriesrequestform.jsx";
import ImportCSV from "./importCSV.jsx";
import PendingRequests from "./pendingRequests.jsx";


export default class AdminComponent extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            statusDisplay : false,
            statusMessage : null,
            selectedOption : null
        }
    }

    callRequest(e) {
        e.preventDefault();
        request
            .post("/requests")
            .send({ "category_id": 11})
            .end((err, res) => {
                console.log(res);
                if (err){
                    this.setState({
                        statusMessage: "noConnection",
                        statusDisplay: true
                    });
                }
                else{
                    this.setState({
                        statusMessage: 'success',
                        statusDisplay: true

                    });
                }
            });
    }
    handleOptionChange(e) {
        this.setState({selectedOption: e});
    }

    render() {
            var page;
            var state;
            var alert;
            var description;
            var submit;

            if (this.state.selectedOption === 'AllPending') {
              page = <PendingRequests/>
            } else if (this.state.selectedOption === 'Add') {
              page = <text>Product</text>
            } else if (this.state.selectedOption === 'import') {
              page = <ImportCSV/>
            } else if (this.state.selectedOption === 'reports') {
              page = <text>reports</text>
            }


            return (
                <div>
                <Col sm={2}>
                <ListGroup>
                <ListGroupItem onClick={() => this.handleOptionChange('AllPending')}>All Pending Requests</ListGroupItem>
                <ListGroupItem onClick={() => this.handleOptionChange('Add')}>Add a Product/Category</ListGroupItem>
                <ListGroupItem onClick={() => this.handleOptionChange('import')}>Import CSV</ListGroupItem>
                <ListGroupItem onClick={() => this.handleOptionChange('reports')}>Reports</ListGroupItem>
                </ListGroup>
                </Col>
                <Col sm={10}>
                {page}
                </Col>
                </div>
            );
    }
}
