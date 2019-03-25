import * as React from "react";
import {request} from "./Utils";
import RenderUsers from "./RenderUsers";

class AdminPage extends React.Component{

    constructor(props) {
        super(props);

        /*this.nextPage = this.nextPage.bind(this);
        this.previousPage = this.previousPage.bind(this);*/

        this.update = this.update.bind(this);

        this.state = {
            users: [],
            selectedUsers: [],
            offSet: 0,
            isLast: false,
            isFirst: false,
            selectedContactId: -1,
            selectedIndex: -1,
            showCheckbox: false,
            isSelected: false,
            pageSize: 5,
            totalPages: 0,
            selectedPage: 0,
        };

        document.title = "";
    }

    componentDidMount() {
        this.getUsers(0);
    }

    // TODO
    update() {
        this.getUsers(0);
    };

    getUsers(page) {
        let refThis = this;

        return request({
            url: "/api/admin/users?page=" + page + "&size=5",
            method: "GET",
        }).then(function (data) {
            console.log(data);
            refThis.setState({
                users: data.content,
                offSet: data.number,
                isFirst: data.first,
                isLast: data.last,
                selectedIndex: -1,
                showCheckbox: false,
                selectedUsers: [],
                totalPages: data.totalPages,
            })
        });
    }

    render() {
        return <div>
            <RenderUsers users={this.state.users} updateParent={this.update}/>
        </div>
    }
}

export default AdminPage;