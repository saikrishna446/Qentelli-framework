package com.qentelli.pojo;

public class SearchUserResponse {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public SearchUser getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(SearchUser searchUser) {
        this.searchUser = searchUser;
    }

    private String status;
    private String searchStatus;
    private SearchUser searchUser;

    public class SearchUser{
        public String guid;
        public String firstName;
        public String lastName;
        public String username;
        public String email;
        public String dob;
        public String gender;
        public String customerType;
        public String gncCustomerID;
        public String gncCoachID;
        public String sponsorREPID;
        public String preferredLanguage;
        public String status;
        public String telephoneNumber;
        public String leadWheelType;
        public String companyName;
        public String startDate;
        public String inputSystem;
        public String govID;
        public String billAddress1;
        public String billAddress2;
        public String billCity;
        public String billState;
        public String billPostalCode;
        public String billCountry;
        public String shipAddress1;
        public String shipAddress2;
        public String shipCity;
        public String shipState;
        public String shipPostalCode;
        public String shipCountry;
        public String businessAddress1;
        public String businessAddress2;
        public String businessCity;
        public String businessState;
        public String businessPostalCode;
        public String businessCountry;
        public String termAndCondition;
        public String leadWheelLanguage;
        public String preferredProduct;

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getCustomerType() {
            return customerType;
        }

        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }

        public String getGncCustomerID() {
            return gncCustomerID;
        }

        public void setGncCustomerID(String gncCustomerID) {
            this.gncCustomerID = gncCustomerID;
        }

        public String getGncCoachID() {
            return gncCoachID;
        }

        public void setGncCoachID(String gncCoachID) {
            this.gncCoachID = gncCoachID;
        }

        public String getSponsorREPID() {
            return sponsorREPID;
        }

        public void setSponsorREPID(String sponsorREPID) {
            this.sponsorREPID = sponsorREPID;
        }

        public String getPreferredLanguage() {
            return preferredLanguage;
        }

        public void setPreferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTelephoneNumber() {
            return telephoneNumber;
        }

        public void setTelephoneNumber(String telephoneNumber) {
            this.telephoneNumber = telephoneNumber;
        }

        public String getLeadWheelType() {
            return leadWheelType;
        }

        public void setLeadWheelType(String leadWheelType) {
            this.leadWheelType = leadWheelType;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getInputSystem() {
            return inputSystem;
        }

        public void setInputSystem(String inputSystem) {
            this.inputSystem = inputSystem;
        }

        public String getGovID() {
            return govID;
        }

        public void setGovID(String govID) {
            this.govID = govID;
        }

        public String getBillAddress1() {
            return billAddress1;
        }

        public void setBillAddress1(String billAddress1) {
            this.billAddress1 = billAddress1;
        }

        public String getBillAddress2() {
            return billAddress2;
        }

        public void setBillAddress2(String billAddress2) {
            this.billAddress2 = billAddress2;
        }

        public String getBillCity() {
            return billCity;
        }

        public void setBillCity(String billCity) {
            this.billCity = billCity;
        }

        public String getBillState() {
            return billState;
        }

        public void setBillState(String billState) {
            this.billState = billState;
        }

        public String getBillPostalCode() {
            return billPostalCode;
        }

        public void setBillPostalCode(String billPostalCode) {
            this.billPostalCode = billPostalCode;
        }

        public String getBillCountry() {
            return billCountry;
        }

        public void setBillCountry(String billCountry) {
            this.billCountry = billCountry;
        }

        public String getShipAddress1() {
            return shipAddress1;
        }

        public void setShipAddress1(String shipAddress1) {
            this.shipAddress1 = shipAddress1;
        }

        public String getShipAddress2() {
            return shipAddress2;
        }

        public void setShipAddress2(String shipAddress2) {
            this.shipAddress2 = shipAddress2;
        }

        public String getShipCity() {
            return shipCity;
        }

        public void setShipCity(String shipCity) {
            this.shipCity = shipCity;
        }

        public String getShipState() {
            return shipState;
        }

        public void setShipState(String shipState) {
            this.shipState = shipState;
        }

        public String getShipPostalCode() {
            return shipPostalCode;
        }

        public void setShipPostalCode(String shipPostalCode) {
            this.shipPostalCode = shipPostalCode;
        }

        public String getShipCountry() {
            return shipCountry;
        }

        public void setShipCountry(String shipCountry) {
            this.shipCountry = shipCountry;
        }

        public String getBusinessAddress1() {
            return businessAddress1;
        }

        public void setBusinessAddress1(String businessAddress1) {
            this.businessAddress1 = businessAddress1;
        }

        public String getBusinessAddress2() {
            return businessAddress2;
        }

        public void setBusinessAddress2(String businessAddress2) {
            this.businessAddress2 = businessAddress2;
        }

        public String getBusinessCity() {
            return businessCity;
        }

        public void setBusinessCity(String businessCity) {
            this.businessCity = businessCity;
        }

        public String getBusinessState() {
            return businessState;
        }

        public void setBusinessState(String businessState) {
            this.businessState = businessState;
        }

        public String getBusinessPostalCode() {
            return businessPostalCode;
        }

        public void setBusinessPostalCode(String businessPostalCode) {
            this.businessPostalCode = businessPostalCode;
        }

        public String getBusinessCountry() {
            return businessCountry;
        }

        public void setBusinessCountry(String businessCountry) {
            this.businessCountry = businessCountry;
        }

        public String getTermAndCondition() {
            return termAndCondition;
        }

        public void setTermAndCondition(String termAndCondition) {
            this.termAndCondition = termAndCondition;
        }

        public String getLeadWheelLanguage() {
            return leadWheelLanguage;
        }

        public void setLeadWheelLanguage(String leadWheelLanguage) {
            this.leadWheelLanguage = leadWheelLanguage;
        }

        public String getPreferredProduct() {
            return preferredProduct;
        }

        public void setPreferredProduct(String preferredProduct) {
            this.preferredProduct = preferredProduct;
        }
    }

}

