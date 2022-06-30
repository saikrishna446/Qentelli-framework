package com.qentelli.automation.testdatafactory.dataUtils;

import com.qentelli.automation.utilities.CommonUtilities;

public class DataCreation {
    public static String createEmail(){
//        return createUserName() + "@yopmail.com";
        return CommonUtilities.randomEmail() ;
    }

//    public static String createEmail(String domain){
//        return createUserName() + domain;
//    }


    public static String getUserFromEmail(String email){
        return email.replaceAll("@.*","");
    }

    public static String createUserName(){
        return createUserName("TU");
    }

    public static String createUserName(String prefix){
        return CommonUtilities.randomString(6);

        /*LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = DateTimeFormatter.ofPattern("YYMMdd-HHmmss").format(localDateTime);
        String randomString = RandomStringUtil.getRandomAlphanumericString(1);
        return prefix + dateTime + randomString;*/
    }


}
