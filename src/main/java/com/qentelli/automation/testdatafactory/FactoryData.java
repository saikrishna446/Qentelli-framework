package com.qentelli.automation.testdatafactory.data;


import com.qentelli.automation.testdatafactory.exceptions.UserListEmptyException;

import java.util.HashMap;


public class FactoryData {

    private String newUserEmail;

    private String productName;

    private String price;

    private String tax;

    private String shippingFee;

    private String orderTotal;

    private String orderNumber;

    private String caseID;

    private String env;

    private String runID;

    private String projects;

    private String currentProject;

    private String browser;

    private int currentTestStep = 1;

    private String testStepResult = "pass";

    private String testStepText;

    private HashMap<String,User> userList;

    private User user;

    private String geneology = "User A";

    public String getNewUserEmail() {
        return newUserEmail;
    }

    public void setNewUserEmail(String newUserEmail) {
        newUserEmail = newUserEmail;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String product) {
        productName = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        price = price;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        orderNumber = orderNumber;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        caseID = caseID;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getRunID() {
        return runID;
    }

    public void setRunID(String runID) {
        this.runID = runID;
    }

    public String getTestStepResult() {
        return testStepResult;
    }

    public void setTestStepResult(String testStepResult) {
        this.testStepResult = testStepResult;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(String currentProject) {
        this.currentProject = currentProject;
    }

    public int getCurrentTestStep() {
        return currentTestStep;
    }

    public void setCurrentTestStep(int currentTestStep) {
        this.currentTestStep = currentTestStep;
    }

    public String getTestStepText() {
        return testStepText;
    }

    public void setTestStepText(String testStepText) {
        this.testStepText = testStepText;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(String shipping) {
        this.shippingFee = shipping;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

  /*  public static ScenarioObject getScenarioObject() {
        return scenarioObject;
    }*/

    /*public static void setScenarioObject(ScenarioObject scenarioObject) {
        SessionData.scenarioObject = scenarioObject;
    }
*/
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public User getUserFromList(String genealogy) throws UserListEmptyException {
        if (userList.isEmpty()){
            throw new UserListEmptyException("User list is empty");
        }
        return userList.get(genealogy);
    }

    public void addToUserList(User user) {
        if (userList == null){
            userList = new HashMap<String, User>();
        }
        userList.put(user.getUsername(), user);
    }

    public void addToUserList(String genealogy, User user) {
        if (userList == null){
            userList = new HashMap<String, User>();
        }
        userList.put(genealogy, user);
    }

    public HashMap<String, User> getUserMap(){
        return userList;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGeneology() {
        return geneology;
    }

    public void setGeneology(String geneology) {
        this.geneology = geneology;
    }
}
