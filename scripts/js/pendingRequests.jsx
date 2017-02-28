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
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Mark</td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td colSpan="2">Larry the Bird</td>
                        <td>@twitter</td>
                    </tr>
                    </tbody>
                </Table>
                <div>
                    <p>Click to get the full Modal experience!</p>

                    <Button
                        bsStyle="primary"
                        bsSize="large"
                        onClick={this.open}
                    >
                        Launch demo modal
                    </Button>

                    <Modal show={this.state.showModal} onHide={this.close}>
                        <Modal.Header closeButton>
                            <Modal.Title>Modal heading</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {form}

                            <hr />


                            <h4>Overflowing text to show scroll behavior</h4>
                            <p>Cras mattis consectetur purus sit amet fermentum. </p>

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