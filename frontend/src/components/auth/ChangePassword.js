import * as React from "react";
import {request} from "../Utils";
import {ACCESS_TOKEN} from "../constants";
import {FormattedMessage} from "react-intl";

class ChangePassword extends React.Component {

    handleUsernameChange = (e) => {
        this.setState({
            usernameOrEmail: e.target.value,
        })
    };

    handlePasswordChange = (e) => {
        this.setState({
            password: e.target.value,
        })
    };

    handleNewPasswordChange = (e) => {
        this.setState({
            newPassword: e.target.value,
        })
    };

    handleButtonClick = () => {
        let req = {
            usernameOrEmail: this.state.usernameOrEmail,
            oldPassword: this.state.password,
            newPassword: this.state.newPassword,
        };

        request({
            url: "/api/auth/changePassword",
            method: "POST",
            body: JSON.stringify(req),
        }).then(() => {
            this.props.history.push("/index");
        })
    };

    render() {
        return (
            <form className="form-signin center animated fadeInUp" id="login-form">
                <span style={{color: 'red'}} id="error-span"/>

                <label>
                    <FormattedMessage id={"detail.labels.email"}/>
                </label>
                <input type="text" id="inputUsername"
                       className="form-control"
                       placeholder="Почта" required={true}
                       onChange={this.handleUsernameChange}
                       autoFocus=""/>
                <label>
                    <FormattedMessage id={"detail.labels.oldPassword"}/>
                </label>
                <input type="password" id="oldPassword"
                       className="form-control"
                       placeholder="Пароль" required={true}
                       onChange={this.handlePasswordChange}/>
                <label>
                    <FormattedMessage id={"detail.labels.newPassword"}/>
                </label>
                <input type="password" id="newPassword"
                       className="form-control"
                       placeholder="Новый Пароль" required={true}
                       onChange={this.handleNewPasswordChange}/>
                <button className="btn btn-lg btn-secondary"
                        type="button"
                        onClick={this.handleButtonClick}>
                    <FormattedMessage id={"detail.buttons.accept"}/>
                </button>
            </form>
        )
    }
}

export default ChangePassword;