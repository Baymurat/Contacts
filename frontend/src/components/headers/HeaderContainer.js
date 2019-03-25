import * as React from "react";
import AnonHeader from "./AnonHeader";
import Header from "./Header";

class HeaderContainer extends React.Component{
    render() {
        let anon = !localStorage.hasOwnProperty("accessToken");
        return anon ? <AnonHeader/> : <Header/>;
    }
}

export default HeaderContainer;