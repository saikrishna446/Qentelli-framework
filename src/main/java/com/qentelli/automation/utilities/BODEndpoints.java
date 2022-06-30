package com.qentelli.automation.utilities;

public class BODEndpoints extends AbstractApplicationsEndpointsResourceBundle {

    private static final String NAME = "BOD";
    private static final String BODHOMEPAGEURL = "BODHOMEPAGEURL";
    private static final String BODLETSGETUPWORKOUTSPAGE = "BODLETSGETUPWORKOUTSPAGE";
    private static final String TBBLETSGETUPPAGEURL ="TBBLETSGETUPPAGEURL";
    private static final String BODOFFERSPAGEURL = "BODOFFERSPAGEURL";
    public String bodHomeUrl;
    public String bodLetsGetUpWorkoutsPage;
    public String tbbLetsGetUpPageURL;
    public String bodOffersPageURL;

    BODEndpoints() {
        super(NAME);
        bodHomeUrl = bundle.getString(BODHOMEPAGEURL);
        bodLetsGetUpWorkoutsPage = bundle.getString(BODLETSGETUPWORKOUTSPAGE);
        tbbLetsGetUpPageURL = bundle.getString(TBBLETSGETUPPAGEURL);
        bodOffersPageURL = bundle.getString(BODOFFERSPAGEURL);
    }
}