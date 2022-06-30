package com.qentelli.automation.testdatafactory.api;


import com.qentelli.automation.common.World;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;


public class BYDPlacement {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());


    private static final String updateRepPrefPlacementString = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sec=\"http://www.securefreedom.com/\">" +
               "<soapenv:Header/>\n" +
               "<soapenv:Body>\n" +
               "   <sec:UpdateRepPrefPlacement>\n" +
               "      <!--Optional:-->\n" +
               "      <sec:Credentials>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:Username>dummyUsername</sec:Username>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:Password>dummyPassword</sec:Password>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:Token>?</sec:Token>\n" +
               "      </sec:Credentials>\n" +
               "      <!--Optional:-->\n" +
               "      <sec:RepPrefPlacement>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:PlacementRepDID>dummySponsor</sec:PlacementRepDID>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:PlacementBCDID>1</sec:PlacementBCDID>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:BCDID>1</sec:BCDID>\n" +
               "         <!--Optional:-->\n" +
               "         <sec:PlacementPosition>dummyDirection</sec:PlacementPosition>\n" +
               "      </sec:RepPrefPlacement><sec:RepDID>dummySponsor</sec:RepDID>\n" +
               "      <!--Optional:-->\n" +
               "   </sec:UpdateRepPrefPlacement>\n" +
               "</soapenv:Body>\n" +
               "</soapenv:Envelope>";
    private World world;


    private String usernamePattern = "dummyUsername";
    private String passwordPattern = "dummyPassword";
    private String sponsorPattern = "dummySponsor";
    private String placementPattern = "dummyDirection";

    private FactoryConfig factoryConfig;

    private SoapRequest soapRequest;

    public BYDPlacement(World world){
        this.world = world;
        factoryConfig = world.factoryConfig;
        soapRequest = new SoapRequest(world);
    }
    public void setSponsorDirection(String sponsorID, String direction) {
        String createBYDPlacementBody = generateBYDPlacementBody(sponsorID,direction);
        soapRequest.doSoapRequest(factoryConfig.getBydesignEndpoint(), createBYDPlacementBody);
        LOGGER.debug("Sponsor direction is set to " + direction);
    }

    private String generateBYDPlacementBody(String sponsorID, String direction) {
        LOGGER.debug("Generate BYD Placement Body");
        String str = updateRepPrefPlacementString;
        str = str.replaceAll(usernamePattern, factoryConfig.getBydesignUsername()).replaceAll(passwordPattern, factoryConfig.getBydesignPassword())
                .replaceAll(sponsorPattern, sponsorID).replaceAll(placementPattern, direction);
        LOGGER.debug("Generate BYD Placebody body request:\n" + str);
        return str;
    }
}
