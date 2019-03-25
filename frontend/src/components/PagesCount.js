import React from "react";

class PagesCount extends React.Component {

    render() {
        let totalPages = this.props.totalPages;
        let element = [];

        for (let i = 0; i < totalPages; i++) {
            element.push(<span className="btn btn-primary" onClick={() => this.props.handleSelectedPageChange(i)}
                               key={i}>{i}</span>);
        }

        return (<span>{element}</span>);
    }
}

export default PagesCount;