package com.qentelli.automation.testdatafactory.api;

import com.qentelli.automation.testdatafactory.config.FactoryConfig;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderShipMail {
    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private SoapRequest soapRequest;
    private GmailMessage gmailMessage;
    private FactoryConfig factoryConfig;
    private javax.mail.Message[] messages;
    //Folder emailFolder;


    private String orderNumber = "";
    private Response response;
    private String[] trackingNumbers;
    private String USERID ;//"usertest@gmail.com";
    private String PASSWORD;//"test1234@";


    private String username;//"SFMC_BB_API_USER_DEV";
    private String password;//"beachb0dy!";
    private String clientID ;//"6416302";
    private String endPoint;//"https://webservice.s6.exacttarget.com/Service.asmx";

    private String[] srcFile = {"src/test/resources/body/ship_confirm_body_en_US.txt", "src/test/resources/body/ship_confirm_body_es_US.txt", "src/test/resources/body/ship_confirm_body_en_CA.txt", "src/test/resources/body/ship_confirm_body_fr_CA.txt", "src/test/resources/body/ship_confirm_body_en_GB.txt"
            ,"src/test/resources/body/order_confirm_body_en_CA.txt","src/test/resources/body/order_confirm_body_en_GB.txt","src/test/resources/body/order_confirm_body_en_US.txt","src/test/resources/body/order_confirm_body_es_US.txt","src/test/resources/body/order_confirm_body_fr_CA.txt","src/test/resources/body/order_confirm_body_promo_code.txt"
            ,"src/test/resources/body/coach_cancel_body_en_US.txt","src/test/resources/body/coach_cancel_body_es_US.txt","src/test/resources/body/coach_cancel_body_en_CA.txt","src/test/resources/body/coach_cancel_body_fr_CA.txt","src/test/resources/body/coach_cancel_body_en_GB.txt"
            ,"src/test/resources/body/coach_abandonment_body_en_US.txt","src/test/resources/body/coach_abandonment_body_es_US.txt"
            ,"src/test/resources/body/tbb_cart_abandonment_body_en_US.txt","src/test/resources/body/tbb_cart_abandonment_body_es_US.txt"};
    private String filepath = "";
    private String body = "";
    private String mailDate="";
    private String[] trackingNumber;
    private String tmpBody;
    private String[] promoCodes;

    public boolean getResponseSoap(String langPref)
    {

      username = factoryConfig.getSoapUsername();
        password = factoryConfig.getSoapPassword();
        clientID = factoryConfig.getClientId();
        endPoint = factoryConfig.getOrderConfirmEndpoint();
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("File to be used is: " + filepath+"\n");
        response = doSoapPostRequest(endPoint, filepath, clientID, username, password);
        boolean mailTrigger = response.asString().contains("Created TriggeredSend");
        LOGGER.info("Response Contains 'Created TriggeredSend' ie., "+mailTrigger);

        boolean status = response.getStatusLine().contains("200");
        LOGGER.info("Response Contains Status code as: "+response.getStatusLine());
        //assertTrue("Status Code is not 200 ie., Actual " + response.getStatusCode(), status);
        return mailTrigger;
    }
    public boolean login()
    {
        USERID = factoryConfig.getGmailUsername();
        PASSWORD = factoryConfig.getGmailPassword();
        boolean mailConnected = gmailMessage.loginMail(USERID, PASSWORD);
        LOGGER.info("Mail Connected: " + mailConnected, mailConnected);
        return mailConnected;
    }
    public String getOrderNumber(String path)
    {
        try {
            Path content = Paths.get(path);
            tmpBody = new String(Files.readAllBytes(content));
            String temp = tmpBody.substring(tmpBody.indexOf("<Name>ORDER_NUMBER</Name>"));
            orderNumber = temp.substring(0, temp.indexOf("</Value>")).replace("<Name>ORDER_NUMBER</Name>", "").replace("<Value>", "").trim();
            LOGGER.info("Order Number: " + orderNumber);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return orderNumber;
    }
    public boolean validateEmailContent(String header,String langPref)
    {
        boolean  searchText=false;
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        //System.out.print("Order and Tracking to be used is: " + filepath);
        String content = gmailMessage.getContentMessage();

        if(content.contains(header) || content.contains("We've Received Your Order."))
        {
            LOGGER.info("Mail content contains - '"+header+"' or We've Received Your Order.");
            searchText=true;
        }
        else
        {
            LOGGER.error("Mail content doesn't contains - '"+header+"'");
        }
        System.out.print("===================Come out the step scenario=======================\n");
        return searchText;
    }
    public boolean validatePromoCodeInContent(String header,String langPref)
    {
        boolean  promoCodeFound=false;
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("Order and Tracking to be used is: " + filepath);

        String content = gmailMessage.getContentMessage();
        if(content.contains(header))
        {
            LOGGER.info("Mail content contains - '"+header+"'");

            String[] promoCode=getPromoCodes(filepath);
            if(promoCode[0].isEmpty())
            {
                System.out.print("===================No Promo Codes=======================\n");
            }
            else
            {
                System.out.print("===================Promo Codes==========================\n");
                for(int i=0;i<promoCode.length;i++)
                {
                    if(content.contains(promoCode[i].trim()))
                    {
                        LOGGER.info(i+" - Mail content contains - '"+header+"' and is Promo Code : "+promoCode[i]);
                        promoCodeFound=true;
                    }
                }
            }
        }
        else
        {
            LOGGER.error("Mail content doesn't contains - '"+header+"'");
        }


        System.out.print("===================Come out the step scenario=======================\n");
        return promoCodeFound;
    }
    public boolean getSubjectValidationFromMail(String subject,String langPref)
    {
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("Order and Tracking File to be used is: " + filepath);

        boolean subjectFound = gmailMessage.getSubjectFromMailValidateOrder(subject);
        if(!subjectFound )
            LOGGER.error("No unread mails found.....subject - '"+subject+"'");

        if(subjectFound && langPref.contains("ship_confirm"))
        {
            if(!gmailMessage.getContentMessage().isEmpty())
            {
                validateOrderTrackingNumbers(gmailMessage.getContentMessage(),filepath);
            }
            else
            {
                System.out.print("Mail Content is EMPTY");

            }
        }
        System.out.print("===================Come out the step scenario=======================\n");
        return subjectFound;
    }
    public String[] getTrackingNumber(String path)
    {
        try {
            Path content = Paths.get(path);
            tmpBody = new String(Files.readAllBytes(content));
            String temp = temp = tmpBody.substring(tmpBody.indexOf("<Name>TRACKING_NUMBER</Name>"));
            temp = temp.substring(0, temp.indexOf("</Value>")).replace("<Name>TRACKING_NUMBER</Name>", "").replace("<Value>", "").trim();
            if (temp.contains(",")) {
                trackingNumber = new String[temp.split(",").length];
                trackingNumber = temp.split(",");
                System.out.print("\nOrder Number: " + orderNumber + " having " + trackingNumber.length + " tracking number i.e., " + Arrays.toString(trackingNumber));
            } else {
                trackingNumber = new String[0];
                trackingNumber[0] = temp;
                System.out.print("Order Number: " + orderNumber + " having " + trackingNumber.length + " tracking number i.e., " + Arrays.toString(trackingNumber));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return trackingNumber;
    }
    public String[] getPromoCodes(String path)
    {
        try {
            Path content = Paths.get(path);
            tmpBody = new String(Files.readAllBytes(content));

            if(tmpBody.contains("<Name>PROMO_CODES</Name>\n" +"<Value></Value>"))
            {
                System.out.print("=======Order " + orderNumber + " doesn't have any Promo_Codes===========\n");
                promoCodes = new String[0];
                promoCodes[0] = "";
            }
            else {
                System.out.print("=======Order " + orderNumber +  " have Promo_Codes===========\n");
                String temp = temp = tmpBody.substring(tmpBody.indexOf("<Name>PROMO_CODES</Name>"));
                temp = temp.substring(0, temp.indexOf("</Value>")).replace("<Name>PROMO_CODES</Name>", "").replace("<Value>", "").trim();
                if (temp.contains(",")) {
                    promoCodes = new String[temp.split(",").length];
                    promoCodes = temp.split(",");
                    LOGGER.info("\nOrder Number: " + orderNumber + " having " + promoCodes.length + " promo codes i.e., " + Arrays.toString(promoCodes));
                } else {
                    promoCodes = new String[0];
                    promoCodes[0] = temp;
                    LOGGER.info("Order Number: " + orderNumber + " having " + promoCodes.length + " promo codes i.e., " + Arrays.toString(promoCodes));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return promoCodes;
    }
    public String getTrackingLink(String path,String trackingNo)
    {
        String []trackingLink;
        String trackLink="";
        try {
            Path content = Paths.get(path);
            tmpBody = new String(Files.readAllBytes(content));
            String temp = temp = tmpBody.substring(tmpBody.indexOf("<Name>TRACKING_LINK</Name>"));
            temp = temp.substring(0, temp.indexOf("</Value>")).replace("<Name>TRACKING_LINK</Name>", "").replace("<Value>", "").trim();
            if (temp.contains(",")) {

                trackingLink = new String[temp.split(",").length];
                trackingLink = temp.split(",");
                //System.out.print("\nOrder Number: " + orderNumber + " having " + trackingLink.length + " tracking link i.e., " + Arrays.toString(trackingLink));
            } else {
                trackingLink = new String[0];
                trackingLink[0] = temp;
                //System.out.print("Order Number: " + orderNumber + " having " + trackingNumber.length + " tracking link i.e., " + Arrays.toString(trackingLink));
            }
            for(int i=0;i<trackingLink.length;i++)
            {
                if(trackingLink[i].trim().contains(trackingNo.trim()))
                {
                    trackLink=trackingLink[i].trim();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return trackLink;
    }
    public String readUpdateBodyFile(String path, String Client, String username, String password) {
        Path filePath = Paths.get(path);
        try {
            body = new String(Files.readAllBytes(filePath));
            body = body.replace("{{soapUsername}}", username).replace("{{soapPassword}}", password).replace("{{clientId}}", Client).replaceFirst("<EmailAddress>(.+)</EmailAddress>","<EmailAddress>"+ factoryConfig.getGmailUsername()+"</EmailAddress>").replaceFirst("<SubscriberKey>(.+)</SubscriberKey>","<SubscriberKey>"+ factoryConfig.getGmailUsername()+"</SubscriberKey>");
            System.out.println("String File Body Text: " + body);
            getOrderNumber(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
    public Response doSoapPostRequest(String endpoint, String filePath, String clientID, String username, String password) {
        body= readUpdateBodyFile(filePath,clientID,username,password);
        response = soapRequest.doSoapRequest(endpoint,body);
        return response;
    }
    public boolean getLinkForTrackingNumber(String messageContent,String trackingNumber)
    {
        boolean trackingLinkFound=false;
        Pattern regex = Pattern.compile("<a href=\\\"http://click\\.email\\.test\\.com/\\?[a-z]+=[a-zA-Z0-9]*\\\">"+trackingNumber.trim()+"</a>");
        Matcher regexMatcher = regex.matcher(messageContent);
        if(regexMatcher.find())
        {
            String link=regexMatcher.group(0).substring(regexMatcher.group(0).indexOf("<a href=\""),regexMatcher.group(0).indexOf("\">")).replace("<a href=\"","");
            System.out.print("In mail content Tracking link for "+trackingNumber+" is : "+link+"\n");
            trackingLinkFound=true;
        }
        return trackingLinkFound;
    }
    public void validateOrderTrackingNumbers(String messageContent,String filepath) {
        LOGGER.debug(" Mail Content: " + messageContent);
        orderNumber = getOrderNumber(filepath);
        trackingNumbers = getTrackingNumber(filepath);
        if (messageContent.contains(orderNumber)) {
            LOGGER.info("Order Number " + orderNumber + " is Present");
            for (int i = 0; i < trackingNumbers.length; i++) {
                boolean trackingFound =messageContent.contains(trackingNumbers[i].trim());
                if(!trackingFound)
                    LOGGER.error("\nTracking number " + trackingNumbers[i].trim() + " Not found: " + trackingFound);
                //assertTrue("\nTracking Number " + trackingNumbers[i] + " is Not Found in content: " + trackingFound, trackingFound);
                String trackingLink = getTrackingLink(filepath, trackingNumbers[i]);
                if(trackingLink.trim().isEmpty())
                    LOGGER.error("\nTracking number " + trackingNumbers[i].trim() + " with link not found in soap body: " + trackingLink);
                else
                    LOGGER.info("\nTracking number " + trackingNumbers[i].trim() + " with Link  Found: " + trackingLink);
                boolean mailContentTrackingLink=getLinkForTrackingNumber(messageContent,trackingNumbers[i].trim());
                if(!mailContentTrackingLink)
                    LOGGER.error("\nTracking number " + trackingNumbers[i].trim() + " with link Not found: " + mailContentTrackingLink);
            }
        } else {
            LOGGER.error("Order Number " + orderNumber + " is NOT Present");
        }
    }
    public boolean getCoachCancelResponseSoap(String langPref)
    {
        username = factoryConfig.getSoapUsername();
        password = factoryConfig.getSoapPassword();
        clientID = factoryConfig.getClientId();
        endPoint = factoryConfig.getOrderConfirmEndpoint();
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("File to be used is: " + filepath+"\n");
        response = doSoapPostRequestForCoachCancel(endPoint, filepath, clientID, username, password);
        boolean mailTrigger = response.asString().contains("Created TriggeredSend");
        LOGGER.info("Response Contains 'Created TriggeredSend' ie., "+mailTrigger);

        boolean status = response.getStatusLine().contains("200");
        LOGGER.info("Response Contains Status code as: "+response.getStatusLine());
        //assertTrue("Status Code is not 200 ie., Actual " + response.getStatusCode(), status);
        return mailTrigger;
    }
    public boolean getTBBCartAbandonmentResponseSoap(String langPref)
    {
        username = factoryConfig.getSoapUsername();
        password = factoryConfig.getSoapPassword();
        clientID = factoryConfig.getClientId();
        endPoint = factoryConfig.getOrderConfirmEndpoint();
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("File to be used is: " + filepath+"\n");
        response = doSoapPostRequestForCoachCancel(endPoint, filepath, clientID, username, password);
        boolean mailTrigger = response.asString().contains("Created TriggeredSend");
        LOGGER.info("Response Contains 'Created TriggeredSend' ie., "+mailTrigger);

        boolean status = response.getStatusLine().contains("200");
        LOGGER.info("Response Contains Status code as: "+response.getStatusLine());
        //assertTrue("Status Code is not 200 ie., Actual " + response.getStatusCode(), status);
        return mailTrigger;
    }
    public Response doSoapPostRequestForCoachCancel(String endpoint, String filePath, String clientID, String username, String password) {
        body= updateBodyFile(filePath,clientID,username,password);
        response = soapRequest.doSoapRequestWithParser(endpoint,body);
        return response;
    }
    public String updateBodyFile(String path, String Client, String username, String password) {
        Path filePath = Paths.get(path);
        try {
            body = new String(Files.readAllBytes(filePath));
            body = body.replace("{{soapUsername}}", username).replace("{{soapPassword}}", password).replace("{{clientId}}", Client).replaceFirst("<EmailAddress>(.+)</EmailAddress>","<EmailAddress>"+ factoryConfig.getGmailUsername()+"</EmailAddress>").replaceFirst("<SubscriberKey>(.+)</SubscriberKey>","<SubscriberKey>"+ factoryConfig.getGmailUsername()+"</SubscriberKey>");
            System.out.println("String File Body Text: " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }
    public boolean validateButtonTextFromResponse(String buttonText,String langPref)
    {
        boolean  buttonFound=false;
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("Order and Tracking to be used is: " + filepath);

        String content = gmailMessage.getContentMessage();
        if(content.contains("BODY_MAIN_CTA_EN_US"))
        {

            LOGGER.info("Mail content contains - '"+buttonText+"'");
            LOGGER.info("==================='YES,COMPLETE MY ENROLLMENT>' button exists==========================\n");
            content=content.substring(content.indexOf("We believe that you’ll find there’s nothing more gratifying"),content.indexOf("alt=\"BODY_MAIN_CTA_EN_US\""));
            LOGGER.info("Content index for - '"+content+"'");
            content=content.substring(content.indexOf("<a href=\"http://click.email.test.com/"),content.indexOf("title")).trim();
            LOGGER.info("Redirect URL link - '"+content+"'");
            String buttonLink=content.replace("<a href=\"","").replace("\"","").trim();
            LOGGER.info("Final Redirect URL link - '"+buttonLink+"'");
            response = soapRequest.doSoapRequestWithoutBody(buttonLink);
            if(!response.prettyPrint().isEmpty()) {
                response.getStatusLine().contains("200");
                LOGGER.info("Response Contains Status code as: " + response.getStatusLine());
                buttonFound = true;
            }
            else
            {
                response.getStatusLine().contains("200");
                LOGGER.error("Response does not contains Status code as 200 OK : "+ response.getStatusLine());
                buttonFound = false;
            }
        }
        else if(content.contains("BODY_MAIN_CTA_ES_US"))
        {

            LOGGER.info("Mail content contains - '"+buttonText+"'");
            LOGGER.info("==================='YES,COMPLETE MY ENROLLMENT>' button exists==========================\n");
            content=content.substring(content.indexOf("Una de las mejores decisiones de tu vida podría estar"),content.indexOf("alt=\"BODY_MAIN_CTA_ES_US\""));
            LOGGER.info("Content index for - '"+content+"'");
            content=content.substring(content.indexOf("<a href=\"http://click.email.test.com/"),content.indexOf("title")).trim();
            LOGGER.info("Redirect URL link - '"+content+"'");
            String buttonLink=content.replace("<a href=\"","").replace("\"","").trim();
            LOGGER.info("Final Redirect URL link - '"+buttonLink+"'");
            response = soapRequest.doSoapRequestWithoutBody(buttonLink);
            if(!response.prettyPrint().isEmpty()) {
                response.getStatusLine().contains("200");
                LOGGER.info("Response Contains Status code as: " + response.getStatusLine());
                buttonFound = true;
            }
            else
            {
                response.getStatusLine().contains("200");
                LOGGER.error("Response does not contains Status code as 200 OK : "+ response.getStatusLine());
                buttonFound = false;
            }
        }
        else
        {
            LOGGER.error("Mail content doesn't contains button with text - '"+buttonText+"'");
        }
        System.out.print("===================Come out the step scenario=======================\n");
        return buttonFound;
    }
    public boolean validateButtonTextTTBCartAbandonmentResponse(String buttonText,String langPref)
    {
        boolean  buttonFound=false;
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("Order and Tracking to be used is: " + filepath);

        String content = gmailMessage.getContentMessage();
        System.out.print("Content of the mail is: " + content);
        if(content.contains(buttonText) && langPref.contains("en_US") )
        {

            LOGGER.info("Mail content contains - '"+buttonText+"'");
            LOGGER.info("==================='"+buttonText+"' button exists==========================\n");
            content=content.substring(content.indexOf("We're confident you're going to love our programs and get incredible results"),content.indexOf(buttonText));
            LOGGER.info("Content index for - '"+content+"'");
            content=content.substring(content.indexOf("<a href='http://www.teamtest.com/shop/us/cart"),content.indexOf("style='color: ")).trim();
            LOGGER.info("Redirect URL link - '"+content+"'");
            String buttonLink=(content.replace("<a href='","")).replace("'","").trim();
            LOGGER.info("Final Redirect URL link - '"+buttonLink+"'");
            /*response = soapRequest.doSoapRequestWithHeader(buttonLink);
            if(!response.prettyPrint().isEmpty()) {
                response.getStatusLine().contains("200");
                LOGGER.info("Response Contains Status code as: " + response.getStatusLine());
                buttonFound = true;
            }
            else
            {
                response.getStatusLine().contains("200");
                LOGGER.error("Response does not contains Status code as 200 OK : "+ response.getStatusLine());
                buttonFound = false;
            }*/
            buttonFound = true;
        }
        else  if(content.contains(buttonText) && langPref.contains("es_US") )
        {

            LOGGER.info("Mail content contains - '"+buttonText+"'");
            LOGGER.info("==================='"+buttonText+"' button exists==========================\n");
            content=content.substring(content.indexOf("incluyen una garantía de devolución de dinero"),content.indexOf(buttonText));
            LOGGER.info("Content index for - '"+content+"'");
            content=content.substring(content.indexOf("<a href='http://www.teamtest.com/shop/us/cart"),content.indexOf("style='color: ")).trim();
            LOGGER.info("Redirect URL link - '"+content+"'");
            String buttonLink=(content.replace("<a href='","")).replace("'","").trim();
            LOGGER.info("Final Redirect URL link - '"+buttonLink+"'");
            /*response = soapRequest.doSoapRequestWithHeader(buttonLink);
            if(!response.prettyPrint().isEmpty()) {
                response.getStatusLine().contains("200");
                LOGGER.info("Response Contains Status code as: " + response.getStatusLine());
                buttonFound = true;
            }
            else
            {
                response.getStatusLine().contains("200");
                LOGGER.error("Response does not contains Status code as 200 OK : "+ response.getStatusLine());
                buttonFound = false;
            }*/
            buttonFound = true;
        }
        else
        {
            LOGGER.error("Mail content doesn't contains button with text - '"+buttonText+"'");
        }
        System.out.print("===================Come out the step scenario=======================\n");
        return buttonFound;
    }
    public boolean validateEmailSubjectText(String subject,String langPref)
    {
        for (int i = 0; i < srcFile.length; i++) {
            if (srcFile[i].contains(langPref)) {
                filepath = srcFile[i];
            }
        }
        System.out.print("Order and Tracking File to be used is: " + filepath);

        boolean subjectFound = gmailMessage.getSubjectFromMailValidateOrder(subject);
        if(!subjectFound )
            LOGGER.error("No unread mails found.....subject - '"+subject+"'");
        if(subjectFound && langPref.contains("coach_cancel"))
        {
            LOGGER.info("Mail found.....subject - '"+subject+"'");
        }
        if(subjectFound && langPref.contains("ship_confirm"))
        {
            if(!gmailMessage.getContentMessage().isEmpty())
            {
                validateOrderTrackingNumbers(gmailMessage.getContentMessage(),filepath);
            }
            else
            {
                System.out.print("Mail Content is EMPTY");
            }
        }
        System.out.print("===================Come out the step scenario=======================\n");
        return subjectFound;
    }
}
