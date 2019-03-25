import * as React from "react";
import {FormattedMessage} from "react-intl";
import {request} from "./Utils";

class RenderUsers extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            selectedUsers: [],
            offSet: 0,
            isLast: false,
            isFirst: false,
            selectedContactId: -1,
            selectedIndex: -1,
            showCheckbox: false,
            isSelected: false,
        };

        document.title = "";
    }

    setSelectedContact(index) {
        if (this.state.selectedIndex !== index) {
            this.setState({
                selectedIndex: index,
                selectedContactId: this.props.users[index].id,
            });
        } else {
            this.setState({
                selectedIndex: -1,
                selectedContactId: -1,
            })
        }
    }

    handleToggleButtonClick = (e, index) => {
        let state = e.target.checked;
        let id = this.props.users[index].id;

        request({
            url: "/api/admin/user/changeState/" + id,
            method: "POST",
            body: JSON.stringify(state),
        }).then(() => {
            this.props.updateParent();
        });


    };

    renderTable(user, index) {
        let state = user.active;
        return <div key={index} className={'row table_row order_row animated fadeInUp'}>
            <div className="col-md-2">{user.name}</div>
            <div className="col-md-2">{user.email}</div>
            <div className="col-md-2">{user.role}</div>
            <div className="col-md-3">{state ? "true" : "false"}</div>
            <div className="col-md-3">
                <label className="switch">
                    <input type="checkbox" defaultChecked={state}
                           onClick={(e) => this.handleToggleButtonClick(e, index)}/>
                    <span className="slider round"> </span>
                </label>
            </div>
        </div>
    }

    render() {
        return <div className="row">
            <div className="offset-lg-2 col-lg-8 superuserform_companylist animated fadeIn">
                <div className="row table_header">
                    <div className="col-md-2">
                        <FormattedMessage id={"detail.table.header.name"}/>
                    </div>
                    <div className="col-md-2">
                        <FormattedMessage id={"detail.table.header.email"}/>
                    </div>
                    <div className="col-md-2">
                        <FormattedMessage id={"detail.table.header.site"}/>
                    </div>
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.surname"}/>
                    </div>
                    <div className="col-md-3">
                        <FormattedMessage id={"detail.table.header.surname"}/>
                    </div>
                </div>
                {
                    this.props.users.map((element, index) => {
                        return this.renderTable(element, index)
                    })
                }
            </div>
        </div>
    }
}

export default RenderUsers;
