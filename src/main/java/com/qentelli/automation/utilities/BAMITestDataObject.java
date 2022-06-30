package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BAMITestDataObject extends AbstractApplicationsTestdataResourceBundle {
    protected static Logger logger = LogManager.getLogger(BAMITestDataObject.class);
    private final static String NAME = "BAMI";
    public String oktaAdminEmail;
    public String oktaAdminPassword;
    public String bamiNewAddress;
    public String bamiEmailSearch;
    public String billingAddressStreet1;
    public String billingAddressStreet2;
    public String billingCity;
    public String billingCountry;
    public String billingState;
    public String billingZipCode;

    public BAMITestDataObject() {
        super(NAME);
        oktaAdminEmail = bundle.getString("OKTAADMINEMAIL");
        oktaAdminPassword = bundle.getString("OKTAADMINPASSWORD");
        bamiEmailSearch = bundle.getString("BAMIEMAILSEARCH");
        bamiNewAddress = bundle.getString("BAMINEWADDRESS");
        billingAddressStreet1 = bundle.getString("BILLINGADDRESSSTREET1");
        billingAddressStreet2 = bundle.getString("BILLINGADDRESSSTREET2");
        billingCity = bundle.getString("BILLINGCITY");
        billingCountry = bundle.getString("BILLINGCOUNTRY");
        billingState = bundle.getString("BILLINGSTATE");
        billingZipCode = bundle.getString("BILLINGZIPCODE");
    }
}
