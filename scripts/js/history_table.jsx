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
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },

            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 33,  Name: 'bob', Status: "fail" },
            { Age: 53,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 1,  Name: 'Lee Salminen', Status: "Pending" },
            { Age: 23,  Name: 'Lee Salminen', Status: "Pending" },

        ];
    }
    Modal(e){
        console.log("Hello i am le modal");
        console.log(e);
    }


    // TODO: Need to have real data to work with in order to finalie how the column/rows will appear.
    // TODO: Need to do styling.
    // TODO: Need to handle opening the modal or the page on click.
    // TODO: Need to handle having an appropriate cursor for clicking a link versus sorting. Might be better to have
    // its own column with a button
    // TODO: ... need to inject HTML into the input field for the placeholder..the stupid open source plugin doesnt have a config for that.


    render() {
        return (
            <Table  className="table hover" data={this.getTableData()}
                  itemsPerPage={10} pageButtonLimit={5} filterable={['Status', 'Age', "Name"]}>
                <Tr onClick={this.Modal} className="hover"
                    data={{ name: 'Other Row' , content: 'This is a different row' }} />
            </Table>
        )
    }
}
