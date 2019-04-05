import React from "react";
import {Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {FormattedMessage} from "react-intl";
import {ACCESS_TOKEN} from "./constants";
import { saveAs } from 'file-saver';

class Attachment extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            isModalOpen: false,
            selectedIndex: null,
            isSelected: false,
            fileName: "",
            comments: "",
            loadDate: "",
            selectedFile: null,
            fileLoaded: 0,
        }
    }

    handleSelectedFile = (e) => {
        this.setState({
            selectedFile: e.target.files[0],
            loaded: 0,
        });
    };

    handleClickAdd = () => {
        this.setState({
            isModalOpen: true,
            isSelected: false,
        })
    };

    handleClickEdit = () => {
        let attachment = this.props.attachments[this.state.selectedIndex];

        this.setState(prevState => ({
            fileName: attachment.fileName,
            comments: attachment.comments,
            loadDate: attachment.loadDate,
            isModalOpen: true,
        }));
    };

    handleClickDelete = () => {
        let index = this.state.selectedIndex;
        let attachments = this.props.attachments.slice();
        let selectedAttach = attachments[index];

        if (selectedAttach.id !== undefined) {
            this.props.onAttachmentDelete(selectedAttach.id);
        }

        attachments.splice(index, 1);
        this.props.onAttachmentsChange(attachments);

        this.setState({
            isSelected: false,
        })
    };

    handleClickModalAccept = () => {
        let attachment = {
            fileName: this.state.fileName,
            loadDate: new Date(),
            comments: this.state.comments,
            selectedFile: this.state.selectedFile,
            isNew: true,
        };

        let attachments = this.props.attachments.slice();

        if (this.state.isSelected) {
            attachments[this.state.selectedIndex] = {...attachments[this.state.selectedIndex], ...attachment};
        } else {
            attachments.push(attachment);
        }

        this.props.onAttachmentsChange(attachments);

        this.handleClickModalCancel();
    };

    handleClickModalCancel = () => {
        this.setState({
            isModalOpen: false,
            isSelected: false,
            fileName: "",
            loadDate: "",
            comments: "",
            selectedFile: null,
        })
    };

    handleInputAttachName = (e) => {
        this.setState({
            fileName: e.target.value,
        })
    };

    handleInputComments = (e) => {
        this.setState({
            comments: e.target.value,
        })
    };

    setSelected(index) {
        this.setState(prevState => ({
            selectedIndex: index,
            isSelected: !prevState.isSelected,
        }));
    }

    download = (e) => {
        e.preventDefault();
        const headers = new Headers();

        if (localStorage.getItem(ACCESS_TOKEN)) {
            headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
        }

        let url = e.target.getAttribute('href');
        fetch(url, {headers: headers})
            .then(response => response.blob())
            .then(blob => {
                saveAs(blob);
            })
    };

    renderTable(attachment, index) {
        let href = window.location.href.split("/");
        let pId = href[href.length - 1];

        let attId = this.props.attachments[index].id;

        let div;

        if (this.props.attachments[index].isNew) {
            div = <div className="col-md-4">{attachment.fileName}</div>
        } else {
            div = <div className="col-md-4"><a
                href={`/api/attachment/${pId}/${attId}`} onClick={(e) => this.download(e)}>{attachment.fileName}</a></div>
        }

        let loadDate;

        if (attachment.loadDate !== null && attachment.loadDate instanceof String) {
            loadDate = attachment.loadDate;
        } else {
            loadDate = new Date().toLocaleDateString("ru-RU");
        }

        return <div key={index} className="row table_row order_row animated fadeInUp"
                    onClick={() => this.setSelected(index)}>
            {div}
            <div className="col-md-4">{attachment.comments}</div>
            <div className="col-md-4">{loadDate}</div>
        </div>
    }

    render() {
        return <div className="row">

            <Modal isOpen={this.state.isModalOpen}>
                <form>
                    <ModalHeader>
                        <FormattedMessage id={"detail.table.header.addAttach"}/>
                    </ModalHeader>

                    <ModalBody>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"fn"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.fileName"}/>
                            </label>
                            <input id={"fn"} type={"text"} className={"form-control"} value={this.state.fileName}
                                   onChange={this.handleInputAttachName}/>
                        </div>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"cm"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.comments"}/>
                            </label>
                            <textarea id={"cm"} rows={5} cols={70} className={"form-control"}
                                      value={this.state.comments}
                                      onChange={this.handleInputComments}/>
                        </div>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"ld"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.loadDate"}/>
                            </label>
                            <input id={"ld"} type={"text"} className={"form-control"} disabled={true}/>
                        </div>
                        <div style={{margin: "15px"}}>
                            <input id={"file-input"} type={"file"} className={"form-control"}
                                   onChange={(e) => this.handleSelectedFile(e)}/>
                        </div>

                    </ModalBody>

                    <ModalFooter>
                        <Button onClick={this.handleClickModalAccept}>
                            <FormattedMessage id={"detail.buttons.accept"}/>
                        </Button>
                        <Button onClick={this.handleClickModalCancel}>
                            <FormattedMessage id={"detail.buttons.cancel"}/>
                        </Button>
                    </ModalFooter>
                </form>
            </Modal>
            <div className="offset-lg-4 col-lg-4 superuserform_companylist animated fadeIn">
                <div className="row table_header">
                    <div className="col-md-4">
                        <FormattedMessage id={"detail.labels.fileName"}/>
                    </div>
                    <div className="col-md-4">
                        <FormattedMessage id={"detail.labels.loadDate"}/>
                    </div>
                    <div className="col-md-4">
                        <FormattedMessage id={"detail.labels.comments"}/>
                    </div>
                </div>


                {this.props.attachments.map((element, index) => {
                    return this.renderTable(element, index);
                })}

            </div>

            <div className="offset-lg-4 col-lg-4 superuserform_companylist animated"
                 style={{display: this.props.addMode ? 'block' : 'none'}}>
                <button onClick={this.handleClickAdd} className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.add"}/>
                </button>
                <button disabled={!this.state.isSelected} onClick={this.handleClickEdit} className="btn btn-primary">
                    <FormattedMessage id={"detail.buttons.edit"}/>
                </button>
                <button disabled={!this.state.isSelected} onClick={this.handleClickDelete}
                        className="btn btn-danger">
                    <FormattedMessage id={"detail.buttons.delete"}/>
                </button>
            </div>
        </div>
    }
}

export default Attachment;