import * as React from "react";
import {Button, Modal, ModalHeader, ModalBody} from 'reactstrap';
import {FormattedMessage} from "react-intl";
import {Link} from "react-router-dom";

class Header extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            modal: false
        }
    }

    submitLogout = () => {
        localStorage.removeItem("accessToken");
        this.toggle();
        window.location.href = "/";
    };

    toggle = () => {
        this.setState({
            modal: !this.state.modal
        });
    };

    render() {
        return <nav className="navbar navbar-expand-lg navbar-dark" style={{backgroundColor: '#4e4e4e'}}>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <div>
                    <a className={this.props.className} style={{color: '#E5E8E8'}} onClick={this.toggle}><span
                        className={'btn'}><b><FormattedMessage id={"detail.buttons.logout"}/></b></span></a>
                    <Link className={'btn'} style={{color: '#E5E8E8'}} to={`/changePassword`}>
                        <FormattedMessage id={"detail.buttons.changePassword"}/>
                    </Link>
                    <Modal isOpen={this.state.modal}>
                        <form>
                            <ModalHeader><FormattedMessage id={"detail.buttons.logout"}/></ModalHeader>
                            <ModalBody className={"logoutForm"}>
                                <Button color="danger" onClick={this.submitLogout}>
                                    <FormattedMessage id={"detail.buttons.logout"}/>
                                </Button>
                                <Button style={{marginLeft: '2%', backgroundColor: '#4e4e4e'}}
                                        onClick={this.toggle}>
                                    <FormattedMessage id={"detail.buttons.cancel"}/>
                                </Button>
                            </ModalBody>
                        </form>
                    </Modal>
                </div>
            </div>
        </nav>
    }
}

export default Header;