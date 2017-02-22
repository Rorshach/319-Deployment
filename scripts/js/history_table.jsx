import React from 'react';
import {Table, Tr, Td, Th} from 'reactable';

export default class HistoryTable extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <Table className="table" data = { [{JSON: "BOB THE JSON DOG"}, {JSON: "RALPH THE JSON CAT"}]}>
                <Th>
                    HeLO bob
                </Th>
                <Tr>
                    HI RALPH
                </Tr>
            </Table>


        )
    }
}
