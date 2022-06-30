package com.qentelli.automation.testdatafactory.api;

import com.qentelli.automation.common.World;
import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import com.qentelli.automation.testdatafactory.data.CustomerType;
import com.qentelli.automation.testdatafactory.data.User;
import com.qentelli.automation.testdatafactory.dataUtils.DataCreation;
import com.qentelli.automation.testdatafactory.exceptions.GNCIDNotFoundException;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;


public class BYDService {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static final String bydEmptyCreateUserRequest ="<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
            "\t<soap:Body>\n" +
            "\t\t<CSCreateCoach xmlns=\"http://www.securefreedom.com/\">\n" +
            "\t\t<TBBGUID>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:guid[1]}</TBBGUID>\n" +
            "\t\t<input>\n" +
            "\t\t\t<FirstName>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:firstName[1]}</FirstName>\n" +
            "\t\t\t<LastName>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:lastName[1]}</LastName>\n" +
            "\t\t\t<MiddleInitial xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Company>BBTechEntQA</Company>\n" +
            "\t\t\t<Email>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:email[1]}</Email>\n" +
            "\t\t\t<Phone1>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:telephoneNumber[1]}</Phone1>\n" +
            "\t\t\t<Phone2 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Phone3 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Phone4 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Phone5 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Phone6 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<BillingAddress>\n" +
            "\t\t\t\t<Street1>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billAddress1[1]}</Street1>\n" +
            "\t\t\t\t<Street2>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billAddress2[1]}</Street2>\n" +
            "\t\t\t\t<City>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billCity1[1]}</City>\n" +
            "\t\t\t\t<State>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billState1[1]}</State>\n" +
            "\t\t\t\t<PostalCode>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billPostalCode[1]}</PostalCode>\n" +
            "\t\t\t\t<County xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<Country>${searchOIMIdentity for Coach#ResponseAsXml#declare namespace ns1='https://identity.dev.gateway.test.com/searchIdentityCached'; //ns1:Response[1]/ns1:searchUser[1]/ns1:billCountry[1]}</Country>\n" +
            "\t\t\t</BillingAddress>\n" +
            "\t\t\t<ShippingAddress>\n" +
            "\t\t\t\t<Street1 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<Street2 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<City xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<State xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<PostalCode xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<County xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t\t<Country xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t</ShippingAddress>\n" +
            "\t\t\t<IsTaxExempt xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<TaxExemptID xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<Gender>Male</Gender>\n" +
            "\t\t\t<RepType xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<JoinDate>2019/03/24</JoinDate>\n" +
            "\t\t\t<BirthDate>1990/01/01</BirthDate>\n" +
            "\t\t\t<TokenizedTaxID>000710518</TokenizedTaxID>\n" +
            "\t\t\t<WaiveServiceFeeRequirement xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayoutVendorName xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayoutRoutingNumber xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayoutAccountNumber xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayoutAccountType xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayoutMethod xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<PayToName xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<CustomerType xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<SponsorDID xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\" />\n" +
            "\t\t\t<LeadWheelType>COACH</LeadWheelType>\n" +
            "\t\t\t<LeadWheelLanguage>en_US</LeadWheelLanguage>\n" +
            "\t\t</input>\n" +
            "\t</CSCreateCoach>\n" +
            "</soap:Body>\n" +
            "</soap:Envelope>\n";

    private static final String iCentrisExportRequest = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:sec=\"http://www.securefreedom.com\">\n" +
            "\t<soap:Header/>\n" +
            "\t\t<soap:Body>\n" +
            "\t\t\t<sec:iCentrisExport>\n" +
            "\t\t\t\t<!--Optional:-->\n" +
            "\t\t\t\t<sec:Credentials>\n" +
            "\t\t\t\t\t<!--Optional:-->\n" +
            "\t\t\t\t\t<!--Optional:-->\n" +
            "\t\t\t\t\t<sec:Username>dummyUsername</sec:Username>\n" +
            "\t\t\t\t\t<sec:Password>dummyPassword</sec:Password>\n" +
            "\t\t\t\t\t<!--Optional:-->\n" +
            "\t\t\t\t</sec:Credentials>\n" +
            "\t\t\t\t <!--Optional:-->\n" +
            "\t\t\t\t<sec:ExportChoice>dummyExportChoice</sec:ExportChoice>\n" +
            "\t\t\t\t<sec:StartDate>dummyStartData</sec:StartDate>\n" +
            "\t\t\t\t<sec:StopDate>dummyStopDate</sec:StopDate>\n" +
            "\t\t\t</sec:iCentrisExport>\n" +
            "\t\t</soap:Body>\n" +
            "</soap:Envelope>";

    private String dummyUsername = "dummyUsername";
    private String dummyPassword = "dummyPassword";
    private String dummyExportChoice = "dummyExportChoice";
    private String dummyStartData = "dummyStartData";
    private String dummyStopDate = "dummyStopDate";
    private String userNamePattern = "dummyUser";
    private String firstNamePattern = "dummyFirst";
    private String lastNamePattern = "dummyLast";
    private String emailPattern = "dummyEmail.com";
    private String cusTypePattern= "dummyType";
    private String sponsorPattern = "dummySponsor";
    private String localePattern = "dummyLocale";
    private String passwordPattern = "dummyPassword";
    private String companyPattern = "dummyCompany";
    private String datePattern = "dummyDate";
    private String inputSystemPattern = "dummyInputSystem";

    private static final String bydEmptyGetUserInfo = "";

    private String searchNamePattern = "dummySearchName";
    private String searchValuePattern = "dummySearchValue";



    private FactoryConfig factoryConfig;

    private SoapRequest soapRequest;
    private World world;

    public BYDService(World world){
        this.world = world;
        factoryConfig = world.factoryConfig;
        soapRequest = new SoapRequest(world);
    }



    //------------------------
    private void createIdentity(CustomerType customerType){
        createIdentity(DataCreation.createEmail(), customerType, factoryConfig.getDefaultSponsor());
    }

    private void createIdentityWithEmail(String email, CustomerType customerType){
        createIdentity(email,customerType, factoryConfig.getDefaultSponsor());
    }


    private void createIdentityWithSponsor(String sponsor, CustomerType customerType){
        createIdentity(DataCreation.createEmail(),customerType,sponsor);

    }



    private void createIdentity(String email, CustomerType customerType, String sponsorID){
        User user = new User();
        user.setEmail(email);
        user.setCustomerType(customerType);
        user.setSponsorID(sponsorID);
        String username = DataCreation.getUserFromEmail(email);
        user.setUsername(username);
        String createUserbody = generateCreateUserBody(username, email,customerType.getCustString(),sponsorID);
        soapRequest.doSoapRequest(factoryConfig.getOimEndpoint(), createUserbody);
        user.setValue("guid", soapRequest.getSoapResponseValue("Envelope.Body.createOIMIdentityResponse.create_identity_response.guid"));
        if (customerType.equals(CustomerType.CoachUser) || customerType.equals(CustomerType.ClubCoachUser) ) {
            LOGGER.debug("Getting Coach Info");
            String getUSerInfoBody = generateGetUserInfoBody("guid", user.getGuid());
            soapRequest.doSoapRequest(factoryConfig.getOimEndpoint(), getUSerInfoBody);
            String coachID = soapRequest.getSoapResponseValue("Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.gncCoachID");
            int count = 100;
            while ( coachID == null || coachID.isEmpty()) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                soapRequest.doSoapRequest(factoryConfig.getOimEndpoint(), getUSerInfoBody);
                coachID = soapRequest.getSoapResponseValue("Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.gncCoachID");
                try {
                    if (count == 0){
                        throw new GNCIDNotFoundException("Coach ID for " + email + " was not returned in time");
                    } else {
                        count--;
                        int tempCount = 100 - count;
                        LOGGER.debug("Failed to find coach " + tempCount + " times.");
                    }
                } catch (GNCIDNotFoundException e) {
                    e.printStackTrace();
                }

            }
            user.setGncID(coachID);
            LOGGER.debug("Coach Found:");
        } else {
            if(world.factoryData.getEnv().toLowerCase().equals("qa3")) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LOGGER.debug("Not a coach in qa3 Sleeping for 5 seconds");
            } else {
                LOGGER.debug("Not a coach no sleep.");
            }
        }
        world.factoryData.setUser(user);
        LOGGER.debug("USER Created: ");
        LOGGER.debug(user.toString());

    }

    private String generateCreateUserBody(String username, String email, String customerType, String sponsorID){
        LOGGER.debug("Create user body for: " + username + ", " + email + ", " +customerType + ", " + sponsorID) ;
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(localDateTime);
        String inputSystem = customerType.contains("COACH")?"SOA_EBS":"LIFERAY";
        String fname = (world.factoryData.getGeneology() == null)?username:world.factoryData.getGeneology();
        String str = bydEmptyCreateUserRequest;
        str = str.replaceAll(userNamePattern, username).replaceAll(emailPattern, email).replaceAll(cusTypePattern, customerType).replaceAll(lastNamePattern,username).replaceAll(companyPattern,username)
                .replaceAll(sponsorPattern, sponsorID).replaceAll(localePattern, factoryConfig.getLocaleString()).replaceAll(passwordPattern, factoryConfig.getPassword()).replaceAll(datePattern, date)
                .replaceAll(firstNamePattern, fname).replaceAll(inputSystemPattern,inputSystem);

        return str;
    }

    private String generateGetUserInfoBody(String searchFilterName, String searchFilterValue){
        LOGGER.debug("Get user info for " + searchFilterName +", " +searchFilterValue);
        String str = bydEmptyGetUserInfo;
        str = str.replaceAll(searchNamePattern, searchFilterName).replaceAll(searchValuePattern, searchFilterValue);
        //LOGGER.debug("Get user info response:\n" + str);
        return str;
    }

    private String generateICentrisExportBody(String username, String password, String exportChoice, String startDate, String stopDate) {
        return iCentrisExportRequest.replace(dummyUsername, username).replace(dummyPassword, password)
                .replace(dummyExportChoice, exportChoice).replace(dummyStartData, startDate)
                .replace(dummyStopDate, stopDate);
    }

    public Response hitICentrisExportEndpoint() {
        String bydusername = world.bydSessionUserDetails.get("username");
        String password = world.bydSessionUserDetails.get("password");
        return soapRequest.doSoapRequest(factoryConfig.getBydPersonalWebsrviceEndpoint(), generateICentrisExportBody(bydusername, password, "1", world.getCustomerDetails().get("StartLogTime"), world.getCustomerDetails().get("EndLogTime")));
    }
}
