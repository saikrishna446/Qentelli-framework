package com.qentelli.automation.utilities;

public class EBSProps extends RuntimeProperties {
  public EBSProps() {
    super();
  }

  public String getCustomerRole() {
    System.out.println("getCustomerRole   " + readProp("CUSTOMER_ROLE"));
    return readProp("CUSTOMER_ROLE");
  }


  public void writeCustomerRole(String role) {
    writeProp("CUSTOMER_ROLE", role);
  }

  public String getSourceSystem() {
    System.out.println("getSourceSystem   " + readProp("SOURCE_SYSTEM"));

    return readProp("SOURCE_SYSTEM");
  }

  public void writeSourceSystem(String source) {
    writeProp("SOURCE_SYSTEM", source);
  }
}
