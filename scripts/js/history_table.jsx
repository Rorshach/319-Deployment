import React from 'react';
import {Table} from 'reactable';
import {request} from 'superagent';
export default class HistoryTable extends React.Component {
    constructor(props){
        super(props);
        this.state = {data : null}
    }

    componentWillMount(){
        request
            .get("/requests")
            .end(function(err, res){
                // process the data.
                this.setState({data : res});
                // set the data into state.
            });
    }

    getTableData(){
        return [
            { Name: 'Griffin Smith', Age: 18 },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 28, Position: 'Developer' },
        ];
    }

    render() {
        return (
            <Table className="history-table" data={this.getTableData()}>
            </Table>
        )
    }
}
