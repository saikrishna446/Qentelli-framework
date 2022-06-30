package com.qentelli.automation.utilities;

public class E2EEndpoints extends AbstractApplicationsEndpointsResourceBundle {

  private static final String NAME = "E2E";
  private static final String IDENTITY = "IDENTITY";
  private static final String IDENTITYXAPI = "IDENTITYXAPI";
  private static final String ENROLLMENT = "ENROLLMENT";
  private static final String TSPSHAKE = "TSPSHAKE";

  public String identity;
  public String identityXapi;
  public String enrollment;
  public String tsp;


  E2EEndpoints() {
    super(NAME);
    identity = bundle.getString(IDENTITY);
    identityXapi = bundle.getString(IDENTITYXAPI);
    enrollment = bundle.getString(ENROLLMENT);
    tsp = bundle.getString(TSPSHAKE);

  }
}

