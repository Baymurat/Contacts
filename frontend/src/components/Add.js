import React from "react";
import Phone from "./Phone";
import Attachment from "./Attachment";
import UserData from "./UserData";
import Link from "react-router-dom/es/Link";
import {FormattedMessage} from "react-intl";

class Add extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            numbers: [],
            attachments: [],
            userData: {},
        };

        this.sendData = this.sendData.bind(this);
        this.numbersChanged = this.numbersChanged.bind(this);
        this.attachmentsChanged = this.attachmentsChanged.bind(this);
        this.userDataChanged = this.userDataChanged.bind(this);

        document.title = "Добавить контакт";
    }


    renderButtons() {
        return <div className="offset-lg-4 col-lg-4 superuserform_companylist animated fadeIn">
            <Link to={"/index"}>
                <button type="button" className="btn btn-primary" onClick={() => {
                    this.sendData()
                }}>
                    <FormattedMessage id={"detail.buttons.accept"}/>
                </button>
            </Link>
            <Link to={"/index"}>
                <button id="back-to-index" type="button" className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.back"}/>
                </button>
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

    getFiles(formData) {
        let usersAttachment = document.querySelectorAll('input[name = users-attach]');
        let userPhoto = document.querySelector('#photo-input');

        for (let i = 0; i < usersAttachment.length; i++) {
            formData.append('files', usersAttachment[i].files[0]);
        }

        if (userPhoto.files.length > 0) {
            formData.append('photo', userPhoto.files[0]);
        }
    }

    sendData() {
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
        contact.attachments = this.state.attachments.slice();

        let formData = new FormData();
        let attachments = this.state.attachments.slice();

        attachments = attachments.map((element) => {
            formData.append("files", element.selectedFile);
            delete element.selectedFile;
            delete element.isNew;
            return element;
        });

        contact.attachments = attachments;

        formData.append("person", JSON.stringify(contact));
        formData.append("photo", this.state.userData.photoFile);


        fetch("/addRecord",
            {
                method: 'POST',
                body: formData,
            }).then(res => {
            if (res.status === 200) {

            }
        }).catch((e) => {
            console.log(e.message);
        })
    }

    render() {
        return <div className="row">
            <UserData userData={this.state.userData} addMode={true} onUserDataChange={this.userDataChanged}/>
            <Phone numbers={this.state.numbers} addMode={true} onNumbersChange={this.numbersChanged}/>
            <Attachment attachments={this.state.attachments} addMode={true}
                        onAttachmentsChange={this.attachmentsChanged}/>
            {this.renderButtons()}
        </div>
    }
}

export default Add;