import React from "react";
import RenderContacts from "./RenderContacts";

class Index extends React.Component {
    constructor(props) {
        super(props);

        this.nextPage = this.nextPage.bind(this);
        this.previousPage = this.previousPage.bind(this);

        this.state = {
            contacts: [],
            selectedContacts: [],
            offSet: 0,
            isLast: false,
            isFirst: false,
            selectedContactId: -1,
            selectedIndex: -1,
            showCheckbox: false,
            isSelected: false,
        };

        document.title = "Список контактов";
    }

    componentDidMount() {
        this.getContactList(0);
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

        return fetch("/contacts?page=" + page)
            .then(function (response) {
                return response.json();
            }).then(function (result) {
                return result;
            }).then(function (data) {
                refThis.setState({
                    contacts: data.content,
                    offSet: data.number,
                    isFirst: data.first,
                    isLast: data.last,
                    selectedIndex: -1,
                    showCheckbox: false,
                    selectedContacts: [],
                })
            })
    }

    render() {
        return <div className="row">
            <RenderContacts contacts={this.state.contacts}/>
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <button disabled={this.state.isFirst} className="btn btn-primary" onClick={this.previousPage}>Previous
                </button>
                <button disabled={this.state.isLast} className="btn btn-primary" onClick={this.nextPage}>Next</button>
            </div>
        </div>
    }
}

export default Index;