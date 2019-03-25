import React from "react";
import Link from "react-router-dom/es/Link";
import {FormattedMessage} from "react-intl";
import {request} from "./Utils";
import {ACCESS_TOKEN} from "./constants";

class Email extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            messageText: "",
            messageTheme: "",
            messagePatterns: {},
            selectedContacts: props.location.state.selectedContacts,
            recipientsEmail: [],
        };

        document.title = "Email";
    }

    componentDidMount() {
        this.getMessagePatterns().then(data => {
            for (let i = 0; i < data.length; i++) {
                let newTheme = data[i].theme;
                let newMessageText = data[i].content;
                let newMessagePatterns = this.state.messagePatterns;

                newMessagePatterns[newTheme] = newMessageText;

                this.setState({
                    messagePatterns: newMessagePatterns,
                });
            }
        });

        this.getRecipientsEmail().then(data => {
            if (data !== undefined) {
                this.setState({
                    recipientsEmail: data,
                })
            }
        });
    }

    getMessagePatterns() {
        return request({
            url: "/api/messagePatterns",
            method: "GET",
        });
    }

    getRecipientsEmail() {
        return request({
            url: "/api/getRecipientsEmail",
            method: "POST",
            body: JSON.stringify(this.state.selectedContacts),
        });
    }

    sendEmail() {
        let messageDto = {};

        messageDto.receivers = this.state.selectedContacts.slice();
        messageDto.messageSubject = this.state.messageTheme;
        messageDto.messageText = this.state.messageText;

        fetch("/api/sendEmail", {
            method: "POST",
            body: JSON.stringify(messageDto),
            headers: {
                "Content-Type": "application/json",
                'Authorization': 'Bearer ' + localStorage.getItem(ACCESS_TOKEN)
            },
        }).then(response => {
            if (response.status === 200) {

            }
        }).catch(err => {
            console.log(err.messageText);
        })
    }

    handleMessageTextChange = (e) => {
        this.setState({
            messageText: e.target.value,
        });
    };

    handleInputPatternsChange = (e) => {
        let value = e.target.value;
        this.setState(prevState => ({
            messageText: prevState.messagePatterns[value],
            messageTheme: value,
        }));
    };

    handleInputThemeChange = (e) => {
        this.setState({
            messageTheme: e.target.value,
        });
    };

    render() {
        return <div className="offset-lg-4 col-lg-4 superuserform_companylist animated fadeIn">
            <label>
                <FormattedMessage id={"detail.labels.recipients"}/>
            </label>
            <input type="text" className="form-control" value={this.state.recipientsEmail} disabled={true}/>

            <label>
                <FormattedMessage id={"detail.labels.patterns"}/>
            </label>
            <select className="custom-select" onChange={this.handleInputPatternsChange}>
                {
                    Object.keys(this.state.messagePatterns).map((option, index) => {
                        return (<option key={index} value={option}>{option}</option>)
                    })
                }
            </select>

            <label>
                <FormattedMessage id={"detail.labels.theme"}/>
            </label>
            <input type="text" className="form-control" onChange={this.handleInputThemeChange}
                   value={this.state.messageTheme}/>

            <label>
                <FormattedMessage id={"detail.labels.messageContent"}/>
            </label>
            <textarea cols={90} rows={10} onChange={this.handleMessageTextChange}
                      value={this.state.messageText}/>

            <div>
                <Link to={"/index"}>
                    <button id="send-email-button" type="button" className="btn btn-primary" onClick={() => {
                        this.sendEmail()
                    }}>
                        <FormattedMessage id={"detail.buttons.sendEmail"}/>
                    </button>
                </Link>

                <Link to={"/index"}>
                    <button id="back-to-index" type="button" className="btn btn-primary">
                        <FormattedMessage id={"detail.buttons.back"}/>
                    </button>
                </Link>

            </div>
        </div>

    }
}

export default Email;