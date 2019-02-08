import React from "react";

class UserData extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            userData: {},
        };
    }

    inputName(e) {
        let newUserData = this.props.userData;
        newUserData.name = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputSurname(e) {
        let newUserData = this.props.userData;
        newUserData.surName = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputMiddlename(e) {
        let newUserData = this.props.userData;
        newUserData.middleName = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputDateBirth(e) {
        let newUserData = this.props.userData;
        newUserData.dateBirth = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputGender(e) {
        let newUserData = this.props.userData;
        newUserData.gender = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputCitizenship(e) {
        let newUserData = this.props.userData;
        newUserData.citizenship = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputFamilyStatus(e) {
        let newUserData = this.props.userData;
        newUserData.familyStatus = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputWebSite(e) {
        let newUserData = this.props.userData;
        newUserData.webSite = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputEmail(e) {
        let newUserData = this.props.userData;
        newUserData.email = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputCurrentJob(e) {
        let newUserData = this.props.userData;
        newUserData.currentJob = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputCountry(e) {
        let newUserData = this.props.userData;
        newUserData.country = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputCity(e) {
        let newUserData = this.props.userData;
        newUserData.city = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputIndex(e) {
        let newUserData = this.props.userData;
        newUserData.index = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    inputAddress(e) {
        let newUserData = this.props.userData;
        newUserData.streetHouseApart = e.target.value;
        this.props.onUserDataChange(newUserData);
    }

    handlePhotoInputChange(e) {
        if (e.target.files.length > 0) {
            if (e.target.files[0].size / 1024 / 1024 < 200) {
                let extension = e.target.files[0].name.split('.')[1].toLowerCase();
                if (extension === 'jpg' || extension === 'jpeg' || extension === 'png' || extension === 'bmp') {
                    this.renderImage(e.target.files[0]);
                } else {
                    alert("Upload only image files");
                }
            }
        }
    }

    renderImage(file) {
        let reader = new FileReader();

        reader.onload = (e) => {
            let newUserData = this.props.userData;
            newUserData.photo = e.target.result;
            newUserData.photoFile = file;
            this.props.onUserDataChange(newUserData);
        };

        reader.readAsDataURL(file);
    }

    static handlePhotoDivClick() {
        document.getElementById("photo-input").click();
    }

    render() {
        return <div className="row">
            <div className="offset-lg-4 col-lg-4 superuserform_companylist animated fadeIn">
                <div className="" onClick={() => UserData.handlePhotoDivClick()}>
                    <form>
                        <input type="file" id={"photo-input"} accept="image/*" hidden
                               onChange={(e) => this.handlePhotoInputChange(e)} disabled={!this.props.addMode}/>
                    </form>
                    <img id="image" srcSet={this.props.userData.photo} height="300" width="300" alt={"icon"}
                         src={"/favicon.ico"}/>
                </div>
                <div className="col-md-auto">
                    <label htmlFor="name">*Name</label>
                    <input type="text" className="form-control" placeholder="Username"
                           defaultValue={this.props.userData.name}
                           onChange={(e) => this.inputName(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="surname">*Surname</label>
                    <input type="text" className="form-control" placeholder="Surname"
                           defaultValue={this.props.userData.surName}
                           onChange={(e) => this.inputSurname(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="middlename">*Middlename</label>
                    <input type="text" className="form-control" placeholder="Middlename"
                           defaultValue={this.props.userData.middleName}
                           onChange={(e) => this.inputMiddlename(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="datebirth">Datebirth</label>
                    <input type="text" className="form-control" placeholder="__/__/____"
                           defaultValue={this.props.userData.dateBirth}
                           onChange={(e) => this.inputDateBirth(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="gender">Gender</label>
                    <select className="custom-select form-control"
                            defaultValue={this.props.userData.gender}
                            disabled={!this.props.addMode} onChange={(e) => this.inputGender(e)}>
                        <option defaultValue={true} value="male">male</option>
                        <option value="female">female</option>
                    </select>

                    <label htmlFor="citizenship">Citizenship</label>
                    <input type="text" className="form-control" placeholder="Citizenship"
                           defaultValue={this.props.userData.citizenship}
                           onChange={(e) => this.inputCitizenship(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="family-status">Family status</label>
                    <select className="custom-select form-control"
                            defaultValue={this.props.userData.familyStatus}
                            disabled={!this.props.addMode} onChange={(e) => this.inputFamilyStatus(e)}>
                        <option defaultValue={true} value="single">single</option>
                        <option value="married">married</option>
                    </select>
                </div>

                <div className="col-md-auto">
                    <label htmlFor="web-site">Website</label>
                    <input type="text" className="form-control" placeholder="Web Site"
                           defaultValue={this.props.userData.webSite}
                           onChange={(e) => this.inputWebSite(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="email">Email</label>
                    <input type="email" className="form-control" placeholder="Email"
                           defaultValue={this.props.userData.email}
                           onChange={(e) => this.inputEmail(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="currentjob">Currentjob</label>
                    <input type="text" className="form-control" placeholder="Current Job"
                           defaultValue={this.props.userData.currentJob}
                           onChange={(e) => this.inputCurrentJob(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="country">Country</label>
                    <input type="text" className="form-control" placeholder="Country"
                           defaultValue={this.props.userData.country}
                           onChange={(e) => this.inputCountry(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="city">City</label>
                    <input type="text" className="form-control" placeholder="City"
                           defaultValue={this.props.userData.city}
                           onChange={(e) => this.inputCity(e)}
                           disabled={!this.props.addMode}/>

                    <label htmlFor="street-house-apart">Street/House/Apart</label>
                    <input type="text" className="form-control" placeholder="Street/House/Apart"
                           defaultValue={this.props.userData.streetHouseApart}
                           onChange={(e) => this.inputAddress(e)}
                           disabled={!this.props.addMode}/>

                    <label>Index</label>
                    <input type="text" className="form-control" placeholder="Index"
                           defaultValue={this.props.userData.index}
                           onChange={(e) => this.inputIndex(e)}
                           disabled={!this.props.addMode}/>
                </div>
            </div>
        </div>
    }
}

export default UserData;