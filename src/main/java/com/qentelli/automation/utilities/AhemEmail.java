//package com.qentelli.automation.utilities;
//
//import org.json.JSONException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//
//import java.io.IOException;
//
//public class AhemEmail {
//    private String endpoint;
//    public String emailAddress;
//    private String mailboxName;
//    private String token;
//    private RestAPI ahemAPI;
//
//    public AhemEmail(String emailAddress, String interfaceType) throws IOException {
//        if(interfaceType.equalsIgnoreCase("REST")){
//            this.emailAddress = emailAddress;
//            this.mailboxName = emailAddress.split("@")[0];
//            endpoint = "https://www.ahem.email/api";
//            ahemAPI = new RestAPI(endpoint);
//            JSONObject json = (JSONObject)ahemAPI.APIRequest("POST","auth/token",null);
//            token = json.get("token").toString();
//        }
//        else if(interfaceType.equalsIgnoreCase("WEB")){
//
//        }
//    }
//
//    public JSONObject getServerProperties(){
//        JSONObject json = (JSONObject)ahemAPI.APIRequest("GET","properties",token);
//        return json;
//    }
//
//    public JSONObject getServerEmailCount(){
//        JSONObject json = (JSONObject)ahemAPI.APIRequest("GET","emailCount",token);
//        return json;
//    }
//
//    public JSONArray getAllEmails(){
//        Object json = ahemAPI.APIRequest("GET","mailbox/" + mailboxName + "/email",token);
//        if(json.toString().contains("MAILBOX IS EMPTY")) {
//            return new JSONArray();
//        }
//        else{
//            return (JSONArray)json;
//        }
//    }
//
//    public JSONArray getEmails(String key, String value, String sortOrder){
//        JSONArray allEmails = getAllEmails();
//        if(allEmails.size() <= 0){
//            return allEmails;
//        }
//
//        JSONArray filtered = new JSONArray();
//        try {
//
//            for (int i = 0; i < allEmails.size(); ++i) {
//                JSONObject obj = (JSONObject)allEmails.get(i);
//                if(key.startsWith("sender")){
//                    String senderkey = key.replace("sender","");
//                    JSONObject senderObj = (JSONObject)obj.get("sender");
//                    if(senderObj.get(senderkey).toString().equalsIgnoreCase(value)){
//                        filtered.add(obj);
//                    }
//                }
//                else{
//                    if(obj.get(key).toString().equalsIgnoreCase(value)){
//                        filtered.add(obj);
//                    }
//                }
//
//            }
//        } catch (JSONException e) {
//            // handle exception
//        }
//       return filtered;
//    }
//
//    public JSONObject readEmail(JSONObject email){
//        JSONObject json = (JSONObject)ahemAPI.APIRequest("GET","mailbox/" + mailboxName + "/email/" + email.get("emailId").toString(),token);
//        return json;
//    }
//
//    public JSONObject deleteEmail(JSONObject email){
//        JSONObject json = (JSONObject)ahemAPI.APIRequest("DELETE","mailbox/" + mailboxName + "/email/" + email.get("emailId").toString(),token);
//        return json;
//    }
//
//    //main method is included onlly to debug and show example usages
//    public static void main(String[] args) throws IOException {
//        //Sample usage of the classes and methods
//        AhemEmail ahemEmail = new AhemEmail("forgotme@ahem-email.com","REST");
//        //Get the count of all emails on the server
//        ahemEmail.getServerEmailCount();
//        //Get array of all the emails in the inbox //e.g. test@ahem.email
//        JSONArray allEmails = ahemEmail.getAllEmails();
//        //Filter emails by sender address //can also get by sender name
//        JSONArray emails = ahemEmail.getEmails("senderaddress","jayvardhan.potabatti@qentelli.com","asc");
//        if(emails.size() > 0){
//            //get content of a specific email
//            JSONObject content = ahemEmail.readEmail((JSONObject)emails.get(0));
//            String to = content.get("to").toString();
//            String from = content.get("from").toString();
//            String subject = content.get("subject").toString();
//            String text = content.get("text").toString();
//            String textAsHtml = content.get("textAsHtml").toString();
//            String date = content.get("date").toString();
//            String timestamp = content.get("timestamp").toString();
//            JSONArray attachments = (JSONArray)content.get("attachments");
//
//            System.out.println(to);
//            System.out.println(from);
//            System.out.println(subject);
//            System.out.println(text);
//            System.out.println(textAsHtml);
//            System.out.println(date);
//            System.out.println(timestamp);
//            System.out.println(attachments);
//        }
//        else{
//            System.out.println("MAILBOX IS EMPTY!");
//        }
//
//    }
//}
