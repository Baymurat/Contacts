import * as React from "react";
import RenderContacts from "./RenderContacts";

class Search extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            contacts: [],
            selectedContacts: [],
            offSet: 0,
            isLast: true,
            isFirst: true,
            selectedContactId: -1,
            selectedIndex: -1,
            showCheckbox: false,
            isSelected: false,
            searchQuery: "",
            fixedSearchQuery: "",
        };

        this.nextPage = this.nextPage.bind(this);
        this.previousPage = this.previousPage.bind(this);

        document.title = "Поиск контакта";
    }

    componentDidMount() {

    }

    nextPage() {
        let page = this.state.offSet + 1;
        this.getContactList(page);
    }

    previousPage() {
        let page = this.state.offSet - 1;
        this.getContactList(page);
    }

    getContactList(page) {
        let refThis = this;
        let text = this.state.fixedSearchQuery;

        console.log(text);
        return fetch("/searchContact?page=" + page + "&size=" + 4 + "&text=" + text)
            .then(function (response) {
                return response.json();
            }).then(function (result) {
                return result;
            }).then(function (data) {
                console.log(data);
                refThis.setState({
                    contacts: data.content,
                    offSet: data.number,
                    isFirst: data.first,
                    isLast: data.last,
                    selectedIndex: -1,
                    showCheckbox: false,
                    selectedContacts: [],
                })
            }).catch(err => {
                console.log(err.messageText);
            });
    }

    handleSearchButtonClick() {
        this.setState({
            fixedSearchQuery: this.state.searchQuery,
        });

        console.log(this.state.fixedSearchQuery + "   handle method");
        this.getContactList(0);
    }

    handleSearchInputChange(e) {
        this.setState({
            searchQuery: e.target.value,
        });
    }

    render() {
        return <div className="row">
            <div>
                <button type="button" className="btn btn-primary" onClick={() => {this.handleSearchButtonClick()}}>Search</button>
                <div>
                    <input type="text" className="form-control" onChange={(e) => {this.handleSearchInputChange(e)}}/>
                </div>
            </div>

            <RenderContacts contacts={this.state.contacts}/>
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <button disabled={this.state.isFirst} className="btn btn-primary" onClick={this.previousPage}>Previous
                </button>
                <button disabled={this.state.isLast} className="btn btn-primary" onClick={this.nextPage}>Next</button>
                <select className="btn btn-primary">
                    <option selected={true}>5</option>
                    <option>10</option>
                    <option>15</option>
                    <option>20</option>
                </select>
            </div>
        </div>
    }
}

export default Search;