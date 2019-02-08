import React from "react";
import UserData from "./UserData";
import Phone from "./Phone";
import Attachment from "./Attachment";
import Link from "react-router-dom/es/Link";

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

    renderButtons() {
        return <div className="offset-lg-4 col-lg-4 superuserform_companylist">
            <Link to={"/index"}>
                <button type="button" className="btn btn-primary">Home</button>
            </Link>
        </div>
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
        })
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

    render() {
        return <div className="row">
            <UserData userData={this.state.userData} addMode={false}/>
            <Phone numbers={this.state.numbers} addMode={false}/>
            <Attachment attachments={this.state.attachments} addMode={false}/>
            {this.renderButtons()}
        </div>
    }
}

export default About;