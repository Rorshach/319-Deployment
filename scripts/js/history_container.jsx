import React from 'react';

export default class HistoryContainer extends React.Component {
    constructor(props){
        super(props);
    }

    render() {
        return (
            <div>
                <HistoryTable></HistoryTable>
            </div>

        )
    }
}
