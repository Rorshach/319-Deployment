import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {Table, Modal, Button} from 'react-bootstrap';
import IDRequest from "./IDrequest.jsx";

export default class PendingRequests extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            showModal: false
        }
        autobind(this);
    }

    close() {
        this.setState({ showModal: false });
    }

    open() {
        this.setState({ showModal: true });
    }


    render() {
        var form;

        form = <IDRequest/>
        return (
            <div>
                <Table striped bordered condensed hover>
                    <thead>
                    <tr>
                        <th>Request ID</th>
                        <th>Author</th>
                        <th>Date Created</th>
                        <th>Status</th>
                        <th>Date Expected</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                    </tr>
                    <tr>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                    </tr>
                    <tr>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                    </tr>
                    <tr>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                    </tr>
                    <tr>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                        <td>Temp Data</td>
                    </tr>
                    </tbody>
                </Table>
                <div>
                    <p>Change The status of A Request</p>

                    <Button
                        bsStyle="primary"
                        bsSize="large"
                        onClick={this.open}
                    >
                        Edit A Request
                    </Button>

                    <Modal show={this.state.showModal} onHide={this.close}>
                        <Modal.Header closeButton>
                            <Modal.Title>Edit Status Of a Request</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {form}
                        </Modal.Body>
                        <Modal.Footer>
                            <Button onClick={this.close}>Close</Button>
                        </Modal.Footer>
                    </Modal>
                </div>
            </div>
        );
    }
}