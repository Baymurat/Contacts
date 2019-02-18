import {Component} from "react";
import UserData from "./UserData";
import React from "react";
import Attachment from "./Attachment";
import Phone from "./Phone";
import Link from "react-router-dom/es/Link";

class Edit extends Component {

    constructor(props) {
        super(props);

        this.state = {
            numbers: [],
            attachments: [],
            userData: {},
            deleteNumbers: [],
            deleteAttaches: [],
        };

        /*this.sendData = this.sendData.bind(this);*/
        this.numbersChanged = this.numbersChanged.bind(this);
        this.attachmentsChanged = this.attachmentsChanged.bind(this);
        this.userDataChanged = this.userDataChanged.bind(this);
        this.numberDeleted = this.numberDeleted.bind(this);
        this.attachmentDeleted = this.attachmentDeleted.bind(this);

        document.title = "Редактировать контакт";
    }

    componentDidMount() {
        this.getUserData().then(data => {
            if (data) {
                this.setState({
                    numbers: data.phones,
                    attachments: data.attachments,
                    userData: data,
                })
            }
        });

        this.getUserPhoto().then(data => {
            if (data !== undefined && data.length > 0) {
                let newUserData = this.state.userData;
                newUserData.photo = 'data:image/png;base64,' + data;

                this.setState({
                    userData: newUserData,
                });
            }
        });
    }

    getUserData() {
        let link = window.location.href.split("/");
        let id = link[link.length - 1];


        return fetch("/contact/" + id, {method: 'GET'}).then(response => {
            return response.json();
        }).then(result => {
            return result;
        }).catch(err => {
            console.log(err.message);
        });
    }

    getUserPhoto() {
        let link = window.location.href.split("/");
        let id = link[link.length - 1];


        return fetch("/photo/" + id, {
            method: 'GET',
        }).then(response => {
            return response.text();
        }).catch(err => {
            console.log(err.message);
        });
    }

    sendUserData() {
        let link = window.location.href.split("/");
        let id = link[link.length - 1];

        let contact = {};
        contact.name = this.state.userData.name;
        contact.surName = this.state.userData.surName;
        contact.middleName = this.state.userData.middleName;
        contact.gender = this.state.userData.gender;
        contact.citizenship = this.state.userData.citizenship;
        contact.familyStatus = this.state.userData.familyStatus;
        contact.webSite = this.state.userData.webSite;
        contact.email = this.state.userData.email;
        contact.currentJob = this.state.userData.currentJob;
        contact.birthDate = this.state.userData.birthDate;
        contact.country = this.state.userData.country;
        contact.city = this.state.userData.city;
        contact.streetHouseApart = this.state.userData.streetHouseApart;
        contact.index = this.state.userData.index;

        contact.phones = this.state.numbers.slice();
        contact.deleteAttaches = this.state.deleteAttaches.slice();
        contact.deletePhones = this.state.deleteNumbers.slice();

        let formData = new FormData();
        let attachments = this.state.attachments.slice();

        attachments = attachments.map(element => {
            formData.append("files", element.selectedFile);
            delete element.selectedFile;
            delete element.isNew;
            return element;
        });

        contact.attachments = attachments;

        formData.append("person", JSON.stringify(contact));
        formData.append("photo", this.state.userData.photoFile);

        fetch("http:/updateRecord/" + id,
            {
                method: "POST",
                body: formData,
            }).then(result => {
            if (result.status === 200) {

            }
        }).catch(err => {
            console.log(err.message);
        })
    }

    renderButtons() {
        return <div className="offset-lg-4 col-lg-4 superuserform_companylist">
            <Link to={"/index"}>
                <button type="button" className="btn btn-primary" onClick={() => {
                    this.sendUserData()
                }}>
                    Update
                </button>
            </Link>
            <Link to={"/index"}>
                <button type="button" className="btn btn-primary">Cancel</button>
            </Link>
        </div>
    }

    numbersChanged(newNumbers) {
        this.setState({
            numbers: newNumbers,
        });
    }

    attachmentsChanged(newAttachments) {
        this.setState({
            attachments: newAttachments,
        });
    }

    userDataChanged(newUserData) {
        this.setState({
            userData: newUserData,
        })
    }

    numberDeleted(newDeleteNumber) {
        let numbers = this.state.deleteNumbers.slice();
        numbers.push(newDeleteNumber);

        this.setState({
            deleteNumbers: numbers,
        });
    }

    attachmentDeleted(newDeleteAttache) {
        let attaches = this.state.deleteAttaches.slice();
        attaches.push(newDeleteAttache);

        this.setState({
            deleteAttaches: attaches,
        });
    }

    render() {
        return <div className="row">
            <UserData userData={this.state.userData} addMode={true} onUserDataChange={this.userDataChanged}/>
            <Phone numbers={this.state.numbers} onNumberDelete={this.numberDeleted} addMode={true}
                   onNumbersChange={this.numbersChanged}/>
            <Attachment attachments={this.state.attachments} addMode={true}
                        onAttachmentDelete={this.attachmentDeleted}
                        onAttachmentsChange={this.attachmentsChanged}/>
            {this.renderButtons()}
        </div>
    }
}

export default Edit;