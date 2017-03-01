import React from 'react';
import ReactTable from 'react-table'
import {request} from 'superagent';
export default class HistoryTable extends React.Component {
    constructor(props){
        super(props);
        this.state = {data : null}
    }
    /*
    componentWillMount(){
        request
            .get("/requests")
            .end(function(err, res){
                // process the data.
                this.setState({data : res});
                // set the data into state.
            });
    }
    */
    // TODO: Need to have real data to work with in order to finalie how the column/rows will appear.
    // TODO: Need to do styling.
    // TODO: Need to handle opening the modal or the page on click.
    // TODO: Need to handle having an appropriate cursor for clicking a link versus sorting. Might be better to have
    // TODO: ... need to inject HTML into the input field for the placeholder..the stupid open source plugin doesnt have a config for that.


    render() {
        const data = [
            {
                name: 'Tanner Linsley',
                age: 26,
            },
            {
                name: 'beep Linsley',
                age: 26,
            }
        ]

        const columns = [
                {
                    header: 'Name',
                    accessor: 'name' // String-based value accessors!
                },
                {
                    header: 'Age',
                    accessor: 'age',
                }
            ]

        return (
            <ReactTable
                getTrProps={(state, rowInfo, column) => {
                    return {
                        onClick: e => {
                            console.log("hi");
                            return true;
                            }
                        }
                    }
                }
                data={data}
                columns={columns}
            />
        )
    }
}
