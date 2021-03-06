import React from "react";
import {Button, Modal, ModalHeader, ModalBody, ModalFooter} from 'reactstrap';
import {FormattedMessage} from "react-intl";

class Phone extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            selectedIndex: null,
            isSelected: false,
            countryCode: 0,
            operatorCode: 0,
            phoneNumber: 0,
            type: 'mobile',
            comments: "",
            isModalOpen: false,
        }
    }

    renderTable(number, index) {
        return <div key={index} className="row table_row order_row animated fadeInUp"
                    onClick={() => this.setSelected(index)}>
            <div className="col-md-4">
                <div className="col-md-4">{"" + number.codeOfCountry + number.codeOfOperator + number.phoneNumber}</div>
            </div>
            <div className="col-md-4">{number.type}</div>
            <div className="col-md-4">{number.comments}</div>
        </div>
    }

    setSelected(index) {
        this.setState(prevState => ({
            selectedIndex: index,
            isSelected: !prevState.isSelected,
        }));
    }

    handleClickAdd = () => {
        this.setState({
            isModalOpen: true,
            isSelected: false,
        })
    };

    handleClickEdit = () => {
        let number = this.props.numbers[this.state.selectedIndex];

        this.setState(prevState => ({
            countryCode: number.codeOfCountry,
            operatorCode: number.codeOfOperator,
            phoneNumber: number.phoneNumber,
            type: number.type,
            comments: number.comments,
            isModalOpen: true,
        }));
    };

    handleClickDelete = () => {
        let index = this.state.selectedIndex;
        let numbers = this.props.numbers.slice();
        let selectedNumber = numbers[index];

        if (selectedNumber.id !== undefined) {
            this.props.onNumberDelete(selectedNumber.id);
        }

        numbers.splice(index, 1);
        this.props.onNumbersChange(numbers);

        this.setState({
            isSelected: false,
        });

    };

    handleClickModalAccept = () => {
        let number = {
            codeOfCountry: this.state.countryCode,
            codeOfOperator: this.state.operatorCode,
            phoneNumber: this.state.phoneNumber,
            type: this.state.type,
            comments: this.state.comments,
        };

        let numbers = this.props.numbers.slice();

        if (this.state.isSelected) {
            numbers[this.state.selectedIndex] = {...numbers[this.state.selectedIndex], ...number};
        } else {
            numbers.push(number);
        }


        this.props.onNumbersChange(numbers);

        this.handleClickModalCancel();
    };

    handleClickModalCancel = () => {
        this.setState({
            isModalOpen: false,
            isSelected: false,
            countryCode: 0,
            operatorCode: 0,
            phoneNumber: 0,
            comments: "",
        })
    };

    handleInputCoCode = (e) => {
        this.setState({
            countryCode: e.target.value,
        })
    };

    handleInputOpCode = (e) => {
        this.setState({
            operatorCode: e.target.value,
        })
    };

    handleInputPhNumber = (e) => {
        this.setState({
            phoneNumber: e.target.value,
        })
    };

    handleInputComments = (e) => {
        this.setState({
            comments: e.target.value,
        })
    };

    handleInputType = (e) => {
        this.setState({
            type: e.target.value,
        })
    };

    render() {
        return <div className="row">

            <Modal isOpen={this.state.isModalOpen}>
                <form>
                    <ModalHeader>
                        <FormattedMessage id={"detail.table.header.addPhNum"}/>
                    </ModalHeader>

                    <ModalBody>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"cc"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.countryCode"}/>
                            </label>
                            <input id={"cc"} type={"number"} min={0} className={"form-control"}
                                   value={this.state.countryCode}
                                   onChange={this.handleInputCoCode}/>
                        </div>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"oc"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.operatorCode"}/>
                            </label>
                            <input id={"oc"} type={"number"} min={0} className={"form-control"}
                                   value={this.state.operatorCode}
                                   onChange={this.handleInputOpCode}/>
                        </div>
                        <div style={{margin: "15px"}}>
                            <label htmlFor={"pn"} className={"mr-sm-2"}>
                                <FormattedMessage id={"detail.labels.phoneNumber"}/>
                            </label>
                            <input id={"pn"} type={"number"} min={0} className={"form-control"}
                                   value={this.state.phoneNumber}
                                   onChange={this.handleInputPhNumber}/>
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
                            <select onChange={this.handleInputType} className={"form-control"} value={this.state.type}>
                                <option value="mobile">
                                    <FormattedMessage id={"detail.labels.typeMobile"}/>
                                </option>
                                <option value="home">
                                    <FormattedMessage id={"detail.labels.typeHome"}/>
                                </option>
                            </select>
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
                        <FormattedMessage id={"detail.labels.phoneNumber"}/>
                    </div>
                    <div className="col-md-4">
                        <FormattedMessage id={"detail.labels.type"}/>
                    </div>
                    <div className="col-md-4">
                        <FormattedMessage id={"detail.labels.comments"}/>
                    </div>
                </div>

                {this.props.numbers.map((element, index) => {
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
                <button disabled={!this.state.isSelected} onClick={this.handleClickDelete} className="btn btn-danger">
                    <FormattedMessage id={"detail.buttons.delete"}/>
                </button>
            </div>
        </div>
    }
}

export default Phone;