import React from "react";
import {BrowserRouter as Router} from 'react-router-dom'
import {Route} from 'react-router';
import Add from "./Add";
import Edit from "./Edit";
import About from "./About";
import Email from "./Email";
import Search from "./Search";
import Index from "./Index";
import Login from "./auth/Login";
import Signup from "./auth/Signup";
import HeaderContainer from "./headers/HeaderContainer";
import ChangePassword from "./auth/ChangePassword";
import AdminPage from "./AdminPage";

class MainRouter extends React.Component {

    render() {
        return (
            <Router>
                <div>
                        <Route path={"/"} component={HeaderContainer}/>
                        <Route exact path={"/signup"} component={Signup}/>
                        <Route exact path={"/login"} component={Login}/>
                        <Route exact path={"/changePassword"} component={ChangePassword}/>
                        <Route exact path="/index" component={Index}/>
                        <Route exact path="/add" component={Add}/>
                        <Route exact path="/edit/:id" component={Edit}/>
                        <Route exact path="/about/:id" component={About}/>
                        <Route exact path="/email" component={Email}/>
                        <Route exact path="/search" component={Search}/>
                        <Route exact path="/admin" component={AdminPage}/>
                </div>
            </Router>
        );
    }
}

export default MainRouter;