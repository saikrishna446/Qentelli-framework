package com.qentelli.automation.utilities;


public class MYXEndpoints extends AbstractApplicationsEndpointsResourceBundle {
  private static final String NAME = "MYX";
  private static final String MYX = "MYX";
  private static final String BBMYX = "BBMYX";
  private static final String MYXADMIN = "MYXADMIN";
  private static final String BBMYXADMIN = "BBMYXADMIN";

  public String home = null;
  public String bbmyx = null;
  public String homeadmin = null;
  public String bbmyxadmin = null;

  MYXEndpoints() {
    super(NAME);
    home = bundle.getString(MYX);
    homeadmin = bundle.getString(MYXADMIN);
    bbmyx = bundle.getString(BBMYX);
    bbmyxadmin = bundle.getString(BBMYXADMIN);
    System.out.println(TextUtils.ConsoleColors.BLACK_BOLD
        + "\t<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>"
        + TextUtils.ConsoleColors.RESET);
  }
}

