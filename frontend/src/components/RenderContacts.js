import React from "react";
import Link from "react-router-dom/es/Link";
import {FormattedMessage} from "react-intl";
import {request} from "./Utils";

class RenderContacts extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
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

    setSelectedContact(index) {
        if (this.state.selectedIndex !== index) {
            this.setState({
                selectedIndex: index,
                selectedContactId: this.props.contacts[index].id,
            });
        } else {
            this.setState({
                selectedIndex: -1,
                selectedContactId: -1,
            })
        }
    }

    isActive(index) {
        return this.state.selectedIndex === index ?
            'row table_row order_row animated fadeInUp selected' : 'row table_row order_row animated fadeInUp';
    }

    toggleCheckBoxesState() {
        this.setState(prevState => ({
            showCheckbox: !prevState.showCheckbox,
            selectedContacts: [],
        }));
    }

    handleCheckBoxChange(e, index) {
        let newSelectedContacts = this.state.selectedContacts.slice();
        let id = this.props.contacts[index].id;

        if (e.target.checked) {
            newSelectedContacts.push(id);
        } else {
            newSelectedContacts = newSelectedContacts.filter((element) => {
                return element !== id;
            });
        }

        this.setState({
            selectedContacts: newSelectedContacts,
        });
    }

    handleDeleteButtonClick() {
        let deleteContacts = this.state.selectedContacts.slice();

        if (deleteContacts.length === 0 && this.state.selectedContactId !== -1) {
            this.requestDeleteOneContact(this.state.selectedContactId);
        } else {
            let ids = JSON.stringify(deleteContacts);
            this.requestDeleteContacts(ids);
        }
    }

    requestDeleteContacts(data) {
        request({
            url: "/api/deleteContacts",
            method: "POST",
            body: data,
        }).then(response => {
            if (response.status === 200) {
                window.location.reload();
            } else {
                console.log("PROBLEMS");
            }
        }).catch(err => {
            console.log(err);
        });
    }

    requestDeleteOneContact(id) {
        request({
            url: "/api/delete/" + id,
            method: "GET",
        }).then(() => {
            window.location.reload();
        }).catch(err => {
            console.log(err.message);
        });
    }

    renderTable(contact, index) {
        return <div key={index} className={this.isActive(index)} onClick={() => this.setSelectedContact(index)}>
            <input type={"checkbox"} onChange={(e) => this.handleCheckBoxChange(e, index)}
                   hidden={!this.state.showCheckbox}/>
            <div className="col-md-3">{contact.name}</div>
            <div className="col-md-3">{contact.surName}</div>
            <div className="col-md-3">{contact.email}</div>
            <div className="col-md-3">{contact.webSite}</div>
        </div>
    }

    renderButtons() {
        return <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
            <Link to={"/add"}>
                <button id="add-button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.add"}/>
                </button>
            </Link>

            <Link to={"/edit/" + this.state.selectedContactId}>
                <button id="edit-button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.edit"}/>
                </button>
            </Link>

            <Link to={"/about/" + this.state.selectedContactId}>
                <button id="delete-button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.about"}/>
                </button>
            </Link>

            <Link to={"/search"}>
                <button id="about-button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.search"}/>
                </button>
            </Link>

            <Link to={{
                pathname: "/email",
                state: {
                    selectedContacts: (() => {
                        let array = [];
                        if (this.state.selectedContacts.length === 0 && this.state.selectedContactId !== -1) {
                            array.push(this.state.selectedContactId);
                        } else {
                            array = this.state.selectedContacts.slice();
                        }
                        return array;
                    })()
                }
            }}>
                <button id="send-button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.email"}/>
                </button>
            </Link>

            <Link to={'/index'}>
                <button id="go-over-search-button" className="btn btn-danger"
                        onClick={() => this.handleDeleteButtonClick()}>
                    <FormattedMessage id={"detail.buttons.delete"}/>
                </button>
            </Link>
            <button id="check-boxes-button" className="btn btn-primary"
                    onClick={() => this.toggleCheckBoxesState()}>
                <FormattedMessage id={"detail.buttons.multiSelect"}/>
            </button>
        </div>
    }

    render() {
        return <div className="row">
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <div className="row table_header">
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.name"}/>
                    </div>
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.surname"}/>
                    </div>
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.email"}/>
                    </div>
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.site"}/>
                    </div>
                </div>
                {
                    this.props.contacts.map((element, index) => {
                        return this.renderTable(element, index)
                    })
                }
            </div>

            {this.renderButtons()}
        </div>
    }
}

export default RenderContacts;