import React from "react";
import Link from "react-router-dom/es/Link";

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
        return fetch("/messagePatterns", {method: "GET"}).then(response => {
            return response.json();
        }).then(result => {
            return result;
        });
    }

    getRecipientsEmail() {
        return fetch("/getRecipientsEmail", {
            method: "POST",
            body: JSON.stringify(this.state.selectedContacts),
            //body: this.state.selectedContacts,
            headers: {
                "Content-Type": "application/json",
            },
        }).then(response => {
            return response.json();
        }).then(result => {
            return result;
        });
    }

    sendEmail() {
        let messageDto = {};

        //messageDto.emailOfReceivers = this.state.recipientsEmail.slice();
        messageDto.receivers = this.state.selectedContacts.slice();
        messageDto.messageSubject = this.state.messageTheme;
        messageDto.messageText = this.state.messageText;

        fetch("/sendEmail", {
            method: "POST",
            body: JSON.stringify(messageDto),
            headers: {
                "Content-Type": "application/json",
            },
        }).then(response => {
            if (response.status === 200) {

            }
        }).catch(err => {
            console.log(err.messageText);
        })
    }

    handleMessageTextChange(e) {
        this.setState({
            messageText: e.target.value,
        });
    }

    handleInputPatternsChange(e) {
        let value = e.target.value;
        this.setState(prevState => ({
            messageText: prevState.messagePatterns[value],
            messageTheme: value,
        }));
    }

    handleInputThemeChange(e) {
        this.setState({
            messageTheme: e.target.value,
        });
    }

    render() {
        return <div className="offset-lg-4 col-lg-4 superuserform_companylist animated fadeIn">
            <label>Recipients </label>
            <input type="text" className="form-control" value={this.state.recipientsEmail} disabled={true}/>

            <label>Patterns</label>
            <select className="custom-select" onChange={(e) => this.handleInputPatternsChange(e)}>
                {
                    Object.keys(this.state.messagePatterns).map((option, index) => {
                        return (<option key={index} value={option}>{option}</option>)
                    })
                }
            </select>

            <label>Theme</label>
            <input type="text" className="form-control" onChange={(e) => this.handleInputThemeChange(e)}
                   value={this.state.messageTheme}/>

            <label>Message Content</label>
            <textarea cols={90} rows={10} onChange={(e) => this.handleMessageTextChange(e)}
                      value={this.state.messageText}/>

            <div>
                <Link to={"/index"}>
                    <button id="send-email-button" type="button" className="btn btn-primary" onClick={() => {
                        this.sendEmail()
                    }}>Send email
                    </button>
                </Link>

                <Link to={"/index"}>
                    <button id="back-to-index" type="button" className="btn btn-primary">Back</button>
                </Link>

            </div>
        </div>

    }
}

export default Email;