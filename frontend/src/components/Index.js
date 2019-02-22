import React from "react";
import RenderContacts from "./RenderContacts";
import PagesCount from "./PagesCount";
import {FormattedMessage} from "react-intl";

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
            pageSize: 5,
            totalPages: 0,
            selectedPage: 0,
        };

        document.title = "Список контактов";
    }

    componentDidMount() {
        this.getContactList(0);
    }

    handleSelectedPageChange = (newSelectedPage) => {
        this.getContactList(newSelectedPage);
    };

    inputPageSizeChange = (e) => {
        this.setState({
            pageSize: e.target.value,
        });

        this.getContactList(this.state.offSet)
    };

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

        return fetch("/contacts?page=" + page + "&size=" + this.state.pageSize)
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
                    totalPages: data.totalPages,
                })
            })
    }

    render() {
        return <div className="row">
            <RenderContacts contacts={this.state.contacts}/>
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <button disabled={this.state.isFirst} className="btn btn-primary" onClick={this.previousPage}>
                    <FormattedMessage id={"detail.buttons.prev"}/>
                </button>
                <PagesCount totalPages={this.state.totalPages} handleSelectedPageChange={this.handleSelectedPageChange}/>
                <button disabled={this.state.isLast} className="btn btn-primary" onClick={this.nextPage}>
                    <FormattedMessage id={"detail.buttons.next"}/>
                </button>
                <select className="btn btn-primary"
                defaultValue={5}
                onChange={this.inputPageSizeChange}>
                    <option value={5}>5</option>
                    <option value={10}>10</option>
                    <option value={15}>15</option>
                    <option value={20}>20</option>
                </select>
            </div>
        </div>
    }
}

export default Index;