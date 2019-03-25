import * as React from "react";
import {request} from "../Utils";
import {ACCESS_TOKEN} from "../constants";
import {FormattedMessage} from "react-intl";

class Login extends React.Component {

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

    handleButtonClick = () => {
        let req = {
            usernameOrEmail: this.state.usernameOrEmail,
            password: this.state.password,
        };

        let formData = new FormData();

        formData.append("usernameOrEmail", req.usernameOrEmail);
        formData.append("password", req.password);

        fetch("/api/auth/signin", {
            method: "POST",
            body: formData,
        }).then(response => {
            return response.json();
        }).then(jsonResponse => {
            localStorage.setItem(ACCESS_TOKEN, jsonResponse.accessToken);
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
                    <FormattedMessage id={"detail.labels.password"}/>
                </label>
                <input type="password" id="inputPassword"
                       className="form-control"
                       placeholder="Пароль" required={true}
                       onChange={this.handlePasswordChange}/>
                <button className="btn btn-lg btn-secondary"
                        type="button"
                        onClick={this.handleButtonClick}>
                    <FormattedMessage id={"detail.buttons.login"}/>
                </button>
            </form>
        )
    }
}

export default Login;