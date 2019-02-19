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
            searchText: "",
            currJobSearchText: "",
            phoneNumSearchText: "",
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
        let text = this.state.searchText;
        let currentJob = this.state.currJobSearchText;
        let phoneNumber = this.state.phoneNumSearchText;

        console.log(text);
        return fetch("/searchContact?page=" + page + "&size=" + 4 + "&firstAndLastName=" + text +
            "&currentJob=" + currentJob + "&phoneNumber=" + phoneNumber)
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
        this.getContactList(0);
    }

    handleSearchInputChange(e) {
        this.setState({
            searchText: e.target.value,
        });
    }

    handleCurrJobSearchInputChange(e) {
        this.setState({
            currJobSearchText: e.target.value,
        });
    }

    handlePhoneNumSearchInputChange(e) {
        this.setState({
            phoneNumSearchText: e.target.value,
        });
    }
    render() {
        return <div className="row">
            <div className="offset-lg-4 col-lg-16 superuserform_companylist animated fadeIn">
                <div className={"d-inline-block"}>
                    <label>Name or Surname</label>
                    <input type="text" className="form-control" onChange={(e) => {this.handleSearchInputChange(e)}}/>
                </div>
                <div className={"d-inline-block"}>
                    <label>Current Job</label>
                    <input type="text" className="form-control" onChange={(e) => {this.handleCurrJobSearchInputChange(e)}}/>
                </div>
                <div className={"d-inline-block"}>
                    <label>Phone number</label>
                    <input type="number" className="form-control" onChange={(e) => {this.handlePhoneNumSearchInputChange(e)}}/>
                </div>
                <div>
                    <button type="button" className="btn btn-primary" onClick={() => {this.handleSearchButtonClick()}}>Search</button>
                </div>
            </div>

            <RenderContacts contacts={this.state.contacts}/>
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <button disabled={this.state.isFirst} className="btn btn-primary" onClick={this.previousPage}>Previous
                </button>
                <button disabled={this.state.isLast} className="btn btn-primary" onClick={this.nextPage}>Next</button>
                <select className="btn btn-primary" defaultValue={5}>
                    <option>5</option>
                    <option>10</option>
                    <option>15</option>
                    <option>20</option>
                </select>
            </div>
        </div>
    }
}

export default Search;