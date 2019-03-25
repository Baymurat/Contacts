import * as React from "react";
import {request} from "../Utils";
import {FormattedMessage} from "react-intl";

class Signup extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            validName: false,
            validLogin: false,
            validPassword: false,
            validEmail: false,
        }
    }


    handleNameChange = (e) => {
        let valid = e.target.value.length >= 4 && e.target.value.length <= 40;

        this.setState({
            name: e.target.value,
            validName: valid,
        });
    };

    handleUsernameChange = (e) => {
        let valid = e.target.value.length >= 4 && e.target.value.length <= 20;

        this.setState({
            validLogin: valid,
            username: e.target.value,
        })
    };

    handlePasswordChange = (e) => {
        let valid = e.target.value.length >= 6;

        this.setState({
            password: e.target.value,
            validPassword: valid,
        })
    };

    handleEmailChange = (e) => {
        let valid = e.target.value.length >= 10;

        this.setState({
            email: e.target.value,
            validEmail: valid,
        })
    };

    handleButtonClick = () => {
        let req = {
            name: this.state.name,
            username: this.state.username,
            email: this.state.email,
            password: this.state.password,
        };

        request({
            url: "/api/auth/signup",
            method: "POST",
            body: JSON.stringify(req),
        }).then(response => {
            this.props.history.push("/login");
        })
    };

    render() {
        let validData = this.state.validName && this.state.validPassword && this.state.validEmail;

        return (
            <form className="form-signin center animated fadeInUp" id="login-form">
                <span style={{color: 'red'}} id="error-span"/>

                <label>
                    <FormattedMessage id={"detail.labels.name"}/>
                </label>
                <input type="text"
                       className="form-control"
                       placeholder="Имя"
                       autoFocus={true} onChange={this.handleNameChange}/>
                <label>
                    <FormattedMessage id={"detail.labels.password"}/>
                </label>
                <input type="password"
                       className="form-control"
                       placeholder="Пароль" onChange={this.handlePasswordChange}/>
                <label>
                    <FormattedMessage id={"detail.labels.email"}/>
                </label>
                <input type="text"
                       className="form-control"
                       placeholder="Email" onChange={this.handleEmailChange}/>
                <button className="btn btn-lg btn-secondary"
                        type="button"
                        disabled={!validData}
                        onClick={this.handleButtonClick}>
                    <FormattedMessage id={"detail.buttons.signup"}/>
                </button>
            </form>
        )
    }
}

export default Signup;