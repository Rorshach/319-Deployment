import React from 'react';
import {Table} from 'reactable';
export default class HistoryTable extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <Table className="history-table" data={[
                { Name: 'Griffin Smith', Age: 18 },
                { Age: 23,  Name: 'Lee Salminen' },
                { Age: 28, Position: 'Developer' },
            ]}>
            </Table>
        )
    }
}
