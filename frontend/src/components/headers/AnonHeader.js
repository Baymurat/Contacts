import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {FormattedMessage} from "react-intl";

export default class AnonHeader extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loginCase: true,
        }
    }

    toggle = () => {
        this.setState(prevState => ({
            loginCase: !prevState.loginCase
        }))
    };

    render() {
        let signUpCase = <Link to={`/login`} className="nav-link" onClick={this.toggle}>
            <FormattedMessage id={"detail.buttons.login"}/>
        </Link>;

        let loginCase = <div>
            <Link to={`/signup`} className="nav-link" onClick={this.toggle}>
                <FormattedMessage id={"detail.buttons.signup"}/>
            </Link>
        </div>;

        return (
            <nav className="navbar navbar-expand-lg navbar-dark" style={{backgroundColor: '#4e4e4e'}}>
                <button className="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"/>
                </button>
                <div className="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul className="navbar-nav mr-auto">
                        <li className="nav-item active">
                            {this.state.loginCase ? loginCase : signUpCase}
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }

}