import React from "react";
import UserData from "./UserData";
import Phone from "./Phone";
import Attachment from "./Attachment";
import Link from "react-router-dom/es/Link";
import {FormattedMessage} from "react-intl";
import {request} from "./Utils";
import {ACCESS_TOKEN} from "./constants";

class About extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            userData: {},
            numbers: [],
            attachments: [],
        };

        document.title = "Контакт";
    }

    componentDidMount() {
        let link = window.location.href.split("/");
        let id = link[link.length - 1];

        this.getUserData(id).then(data => {
            if (data) {
                this.setState({
                    numbers: data.phones,
                    attachments: data.attachments,
                    userData: data,
                })
            }
        });

        this.getUserPhoto(id).then(data => {
            if (data !== undefined && data.length > 0) {
                let newUserData = this.state.userData;
                newUserData.photo = 'data:image/png;base64,' + data;

                this.setState({
                    userData: newUserData,
                });
            }
        });
    }

    getUserData(id) {
        return request({
            url: "/api/contact/" + id,
            method: 'GET'
        }).then(result => {
            return result;
        }).catch(err => {
            console.log(err.message);
        });
    }

    getUserPhoto(id) {
        return fetch("/api/photo/" + id, {
            method: 'GET',
            headers: {'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)}
        }).then(response => {
            return response.text();
        }).catch(err => {
            console.log(err.message);
        });
    }

    render() {
        return <div className="row">
            <UserData userData={this.state.userData} addMode={false}/>
            <Phone numbers={this.state.numbers} addMode={false}/>
            <Attachment attachments={this.state.attachments} addMode={false}/>
            <div className="offset-lg-4 col-lg-4 superuserform_companylist">
                <Link to={"/index"}>
                    <button type="button" className="btn btn-primary">
                        <FormattedMessage id={"detail.buttons.back"}/>
                    </button>
                </Link>
            </div>
        </div>
    }
}

export default About;