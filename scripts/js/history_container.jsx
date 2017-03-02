/*
Point of this file:
Have 'pre-selected' options for ranges of requests.
----> Last 24 hours. I'm going to use moment to format YYYY-MM-DD -> YYYY-MM-DD where these match
----> Last week. Take the week and - 7
----> Last Month. Take the month and -1
----> Any. No filter on the GET
 */
import React from 'react';
import HistoryTable from './history_table.jsx'
import moment from "moment";
import {Tabs, Tab} from "react-bootstrap";
export default class HistoryContainer extends React.Component {

    constructor(props){
        super(props);
    }

    // Will help to feed props the proper endpoint to hit.
    // TODO: Setup the requests with the appropriate query string.
    getRequestGenerator(date1, date2){
        return "requests?from=" + date1 + "&to=" +  date2;
    }


    componentWillMount(){
        let dateObject = new Date();
        let todayFormatted = moment().format("YYYY-MM-DD"); // can use this to construct range for "today".
        let monthName = "Request's from " + moment().format("MMMM");
        let beginningOfMonth = new moment().date(1).format("YYYY-MM-DD");

        console.log("Here is today: " + todayFormatted);
        console.log("month name : " + monthName);
        console.log("Beginning of month formatted : "  + beginningOfMonth);

        this.setState({
            monthName : monthName,
            todayFormatted : todayFormatted,
            beginningOfMonthFormatted : beginningOfMonth
        })
    }

    render(){

        const tabsInstance = (
            <Tabs defaultActiveKey={1}>
                <Tab eventKey={1} title="Today's Requests"><HistoryTable/></Tab>
                <Tab eventKey={2} title={this.state.monthName}><HistoryTable/></Tab>
                <Tab eventKey={3} title="All Requests"><HistoryTable/></Tab>
            </Tabs>
        );

        return(
           tabsInstance
        )
    }
}