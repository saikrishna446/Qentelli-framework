package com.qentelli.automation.common;

public class Constants
{
    // todo get rid of static world object
    public static World world;

    // Enum for the types of Drivers
    public enum DRIVERTYPE
    {
        SAUCE,
        LOCAL,
        GRID
    }

    // Enum for types of Browsers
    public enum BROWSER
    {
        CHROME,
        FIREFOX,
        SAFARI,
        IEXPLORER,
        EDGE,
        NONE
    }

    // Get the browser
    public enum BrowserName
    {
        FIREFOX("firefox"),
        CHROME("chrome"),
        SAFARI("safari"),
        EDGE("edge");

        private String browser;

        BrowserName(String browser)
        {
            this.browser = browser;
        }

        public String getBrowserName()
        {
            return browser;
        }
    }

    public static final String[] DATARULE_TRIGGERS =
        {"data-rule-restrict-to-digits", "data-rule-phone-intl", "data-rule-phone"};

}
