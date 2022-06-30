package com.qentelli.automation.utilities;

public class ResourceProps extends RuntimeProperties {
  public ResourceProps() {
    super();
  }

  public String getLocale() {
    return readProp("LOCALE");
  }

  public void writeLocale(String s) {
    writeProp("LOCALE", s);
  }
}

