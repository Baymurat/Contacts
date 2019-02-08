import React from "react";
import {BrowserRouter as Router} from 'react-router-dom'
import {Route} from 'react-router';
import Add from "./Add";
import Edit from "./Edit";
import About from "./About";
import Email from "./Email";
import Search from "./Search";
import Index from "./Index";

class MainRouter extends React.Component {

    render() {
        return (
            <Router>
                <div>
                    {/*<Route path="/*" component={}/>*/}
                    <Route path="/index" component={Index}/>
                    <Route path="/add" component={Add}/>
                    <Route path="/edit/:id" component={Edit}/>
                    <Route path="/about/:id" component={About}/>
                    <Route path="/email" component={Email}/>
                    <Route path="/search" component={Search}/>
                </div>
            </Router>
        );
    }
}

export default MainRouter;