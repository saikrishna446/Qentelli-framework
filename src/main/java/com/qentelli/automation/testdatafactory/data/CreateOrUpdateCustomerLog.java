package com.qentelli.automation.testdatafactory.data;

public class CreateOrUpdateCustomerLog {

    private String cust_id;
    private String current_id_pc_date;
    private String current_id_coach_date;
    private String cust_class;
    private String email_add;
    private String guid;
    private String last_pc_join_date;
    private String reason;
    private String upgrade_downgrade_type;

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCurrent_id_pc_date() {
        return current_id_pc_date;
    }

    public void setCurrent_id_pc_date(String current_id_pc_date) {
        this.current_id_pc_date = current_id_pc_date;
    }

    public String getCurrent_id_coach_date() {
        return current_id_coach_date;
    }

    public void setCurrent_id_coach_date(String current_id_coach_date) {
        this.current_id_coach_date = current_id_coach_date;
    }

    public String getLast_pc_join_date() {
        return last_pc_join_date;
    }

    public void setLast_pc_join_date(String last_pc_join_date) {
        this.last_pc_join_date = last_pc_join_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCust_class() {
        return cust_class;
    }

    public void setCust_class(String cust_class) {
        this.cust_class = cust_class;
    }

    public String getEmail_add() {
        return email_add;
    }

    public void setEmail_add(String email_add) {
        this.email_add = email_add;
    }


    public String getUpgrade_downgrade_type() {
        return upgrade_downgrade_type;
    }

    public void setUpgrade_downgrade_type(String upgrade_downgrade_type) {
        this.upgrade_downgrade_type = upgrade_downgrade_type;
    }

}