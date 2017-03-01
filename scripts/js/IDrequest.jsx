import React from 'react';
import ReactDOM from 'react-dom';
import request from 'superagent';
import autobind from 'class-autobind';
import {Row} from 'react-bootstrap';
import {Col} from 'react-bootstrap';
import {FormGroup, FormControl, Table} from 'react-bootstrap';
import {ControlLabel, Label} from 'react-bootstrap';
import {Button} from 'react-bootstrap';
import JsonTable from 'react-json-table';

export default class IDRequest extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            isCheckboxSelected : false,
            categoryOption : null,
            hasOverThreeItems : false,
            canSubmit : false,
            canApprove : false,
            canDeny : false,
            canPend : false,
            statusDisplay : false,
            statusMessage : null,
            categoriesResponse : null,
            requestResponse : null,
            ID : "",
            ide: "",
            dateCreated: "",
            submittedBy: "",
            statusCode: "",
            changedStatusCode: ""

        }
        autobind(this);
    }

    getRequest() {
        request
            .get("/requests/" + this.state.ID)
            .end((err, res) => {
                if (err) {
                    this.setState({
                        statusMessage: err,
                        statusDisplay: true
                    });
                } else {
                    try {
                        var jsonResponse = JSON.parse(res.text);
                        this.setState({
                            ide : jsonResponse.id,
                            dateCreated : jsonResponse.dateCreated,
                            submittedBy : jsonResponse.submittedBy,
                            statusCode : jsonResponse.statusCode

                        });
                        if (this.state.statusCode === 'PEND'){
                            this.setState({
                                canApprove : true,
                                canDeny : true,
                                canPend : false
                            });
                        }
                        else if (this.state.statusCode === 'APPR'){
                            this.setState({
                                canApprove : false,
                                canDeny : true,
                                canPend : true
                            });
                        }
                        else if (this.state.statusCode === 'DEN'){
                            this.setState({
                                canApprove : true,
                                canDeny : false,
                                canPend : true
                            });
                        }
                        console.log(jsonResponse)
                    } catch (e) {
                        console.log(e);
                    }
                }
            });
    }

    editRequest(e) {
        var payload = {"statusCode": e};
        request
            .put("/requests/" + this.state.ID)
            .send(payload)
            .end((err, res)=> {
                if (err){
                    this.setState({
                        statusMessage: "noConnection",
                        statusDisplay: true
                    });
                }
                else{
                    this.setState({
                        statusMessage: "Success",
                        statusDisplay: true
                    });
                    this.getRequest();
                }
            });
    }

    handleID(e) {
        this.setState({ID : e.target.value});
    }


    render() {
        var status;

        if (this.state.statusCode === 'PEND'){
            status = <Label bsStyle="warning">PENDING</Label>
        }
        else if (this.state.statusCode === 'APPR'){
            status = <Label bsStyle="success">APPROVED</Label>
        }
        else if (this.state.statusCode === 'DEN'){
            status = <Label bsStyle="danger">DENIED</Label>
        }

        return (
        <div>
            <FormControl className="ID" componentClass="textarea" placeholder="ID" maxLength='5' onChange={this.handleID}/>
            <Button type="submit" className="submitBtn" onClick={this.getRequest}>
                Submit
            </Button>
            {this.state.requestResponse}
            <Table striped bordered condensed hover>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Date Created</th>
                    <th>submittedBy</th>
                    <th>statusCode</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>{this.state.ide}</td>
                    <td>{this.state.dateCreated}</td>
                    <td>{this.state.submittedBy}</td>
                    <td>{status}</td>
                    <td>{this.state.statusCode}</td>
                </tr>
                </tbody>
            </Table>
            <Button type="submit" className="ApproveBtn" disabled={!this.state.canApprove} onClick={ () => this.editRequest('APPR')} >
                Approve
            </Button>
            <Button type="submit" className="DenyBtn" disabled={!this.state.canDeny} onClick={ () => this.editRequest('DEN')} >
                Deny
            </Button>
            <Button type="submit" className="PendBtn" disabled={!this.state.canPend} onClick={ () => this.editRequest('PEND')} >
                Set To Pend
            </Button>
        </div>

        );
    }
}
