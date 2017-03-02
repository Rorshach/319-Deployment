import React from 'react';
import ReactTable from 'react-table'
import {request} from 'superagent';
export default class HistoryTable extends React.Component {
    constructor(props){
        super(props);
        this.state = {data : null}
    }
    // TODO: Make this request dynamic and provide the data of interest.
    /*
    componentWillMount(){
        request
            .get("this.props.request")
            .end(function(err, res){
                // process the data.
                this.setState({data : res});
                // set the data into state.
            });
    }
    */
    render() {
        const data = [
            {
                id: 22,
                notes: "I am a note",
            },
            {
                name: 23,
                notes: "Hello i am a note",
            }
        ]

        const columns = [
                {
                    header: 'Request Number',
                    accessor: 'id' // String-based value accessors!
                },
                {
                    header: 'Notes',
                    accessor: 'notes',
                }
            ]

        return (
            <ReactTable
                getTrProps={(state, rowInfo, column) => {
                    return {
                        onClick: e => {
                            console.log("hi we will load a page here");
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
