package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BYDTestDataObject extends AbstractApplicationsTestdataResourceBundle {
    protected static Logger logger = LogManager.getLogger(BYDTestDataObject.class);
    private final static String NAME = "BYD";
    public String coachAssignmentSuccessStatus;
    public String coachCancelledSuccessMessage;
    public String coachDowngradeSuccessMessage;
    public String coachToCustomerDowngradeMsgOnCustomersPage;
    public String coachToPCDowngradeNoteHeaderOnRepsPage;
    public String customerEmailWithOrders;
    public String customerEmailWithOrdersInBYD;
    public String customerIDWithOrdersInBYD;
    public String phone;
    public String firstName;
    public String lastName;
    public String inlineCancelledNote;
    public String orderNumber;
    public String payoutMethodType;
    public String pcDowngradeNoteHeaderInCustomerDetailPage;
    public String pcDowngradeSuccessMessage;
    public String pcToCoachUpgradeSuccessMessage;
    public String reportsTableHeaders;
    public String retailCommissionHeaderValues;
    public String subscriptionCommissionHeadersValues;
    public String teamBonusHeaderValues;

    public BYDTestDataObject() {
        super(NAME);
        coachAssignmentSuccessStatus = getResString("CoachAssignmentSuccessStatus");
        coachCancelledSuccessMessage = getResString("CoachCancelledSuccessMessage");
        coachDowngradeSuccessMessage = getResString("CoachDowngradeSuccessMessage");
        coachToCustomerDowngradeMsgOnCustomersPage = getResString("CoachToCustomerDowngradeMsgOnCustomersPage");
        coachToPCDowngradeNoteHeaderOnRepsPage = getResString("CoachToPCDowngradeNoteHeaderInRepsPage");
        customerEmailWithOrders = getResString("CustomerEmailWithOrders");
        customerEmailWithOrdersInBYD = getResString("CustomerEmailWithOrdersInBYD");
        customerIDWithOrdersInBYD = getResString("CUSTOMERIDWITHORDERSINBYD");
        phone = getResString("Phone");
        firstName = getResString("FirstName");
        lastName = getResString("LastName");
        inlineCancelledNote = getResString("InlineCancelledNote");
        orderNumber = getResString("OrderNumber");
        payoutMethodType = getResString("PayoutMethodType");
        pcDowngradeNoteHeaderInCustomerDetailPage = getResString("PCDowngradeNoteHeaderInCustomerDetailsPage");
        pcDowngradeSuccessMessage = getResString("PCDowngradeSuccessMessage");
        pcToCoachUpgradeSuccessMessage = getResString("PCToCoachUpgradeSuccessMessage");
        reportsTableHeaders = getResString("reportsTableHeaders");
        retailCommissionHeaderValues = getResString("retailCommissionHeaderValues");
        subscriptionCommissionHeadersValues = getResString("subscriptionCommissionHeadersValues");
        teamBonusHeaderValues = getResString("teamBonusHeaderValues");
    }

    public String getValue(String key) {
        return getResString(key);
    }
}
