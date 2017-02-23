import React from 'react';
import {Table, Tr} from 'reactable';
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

    getTableData(){
        return [
            { Name: 'Griffin Smith', Age: 18 },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 28, Position: 'Developer' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },
            { Age: 23,  Name: 'Lee Salminen' },

        ];
    }
    Modal(){
        console.log("Hello i am le modal");
    }
    // TODO: Need to have real data to work with in order to finalie how the column/rows will appear.
    // TODO: Need to do styling.
    // TODO: Need to handle opening the modal or the page on click.
    // TODO: Need to handle having an appropriate cursor for clicking a link versus sorting. Might be better to have
    // its own column with a button
    render() {
        return (
            <Table className="table hover" data={this.getTableData()}
                   sortable={true} itemsPerPage={10} pageButtonLimit={5} >
                <Tr onClick={this.Modal} className="hover"
                    data={{ name: 'Other Row' , content: 'This is a different row' }} />
            </Table>
        )
    }
}
