package com.qentelli.automation.testdatafactory.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static java.lang.Thread.sleep;

public class GmailMessage {

    private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private List<String> messageList = new ArrayList<String>();
    private List<String> messageFromEmailList = new ArrayList<String>();
    private List<String> messageDateList = new ArrayList<String>();
    private List<String> messageSubjectList = new ArrayList<String>();

    private static final String PROTOCOL = "imaps";
    private static final String HOST_NAME = "imap.gmail.com";
    private static final String USERID = "usertest@gmail.com";
    private static final String PASSWORD = "test1234@";
    private String folderName = "INBOX";
    private javax.mail.Message[] messages;
    private Folder emailFolder;
    private String mailDate="";
    private String messageContent="";
    private Date date=null;
    private Date receivedDate=null;
    private String actualSubject="";
    private boolean subjectFound;
    private Message message;
    private SimpleDateFormat sdf;
    public  void initService(){
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", PROTOCOL);
            Session emailSession = Session.getDefaultInstance(properties);
            Store emailStore = emailSession.getStore(PROTOCOL);
            emailStore.connect(HOST_NAME, USERID, PASSWORD);

            emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            messages = emailFolder.search(new FlagTerm(new Flags(
                    Flags.Flag.SEEN), false));//emailFolder.getMessages();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }

    public void listMessagesMatchingQuery() {
        try{
            for (int i=0; i<messages.length;i++) {
                //for(int i=messages.length-1; i>=messages.length-5;i--){
                Message message = messages[i];
                Address[] fromAddresses = message.getFrom();
                String fromemail= fromAddresses[0].toString();
                String toemail = parseAddresses(message.getRecipients(Message.RecipientType.TO));
                String ccemails = parseAddresses(message.getRecipients(Message.RecipientType.CC));
                String subject = message.getSubject();
                String sentdate = message.getSentDate().toString();

                String contentType = message.getContentType();
                String messageContent = "";

                // store attachment file name, separated by comma
                String attachFiles = "";

                if (contentType.contains("multipart")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                    MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                    int numberOfParts = mimeMultipart.getCount();
                    messageContent = getTextFromMimeMultipart(mimeMultipart);


                    if (attachFiles.length() > 1) {
                        attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                    }
                } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }
                LOGGER.debug("...................");
                LOGGER.debug("\t Message #: "+(i+1));
                LOGGER.info("\t From: "+ fromemail);
                messageFromEmailList.add(fromemail);
                LOGGER.info("\t To: "+ toemail);
                LOGGER.info("\t CC: "+ ccemails);
                LOGGER.info("\t Subject: "+ subject);
                messageSubjectList.add(subject);
                LOGGER.info("\t Sent Date: "+sentdate);
                messageDateList.add(sentdate);
                LOGGER.info("\t Message: "+messageContent);
                messageList.add(messageContent);
                LOGGER.info("\t Attachments: "+attachFiles);

            }

        }catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("Exception"+e.getMessage());
        }
    }


    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private static String parseAddresses(Address[] address) {

        String listOfAddress = "";
        if ((address == null) || (address.length < 1))
            return null;
        if (!(address[0] instanceof InternetAddress))
            return null;

        for (int i = 0; i < address.length; i++) {
            InternetAddress internetAddress =
                    (InternetAddress) address[0];
            listOfAddress += internetAddress.getAddress()+",";
        }
        return listOfAddress;
    }
    public boolean loginMail(String USERID, String PASSWORD){
        boolean connected=false;
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", PROTOCOL);
            Session emailSession = Session.getDefaultInstance(properties);
            Store emailStore = emailSession.getStore(PROTOCOL);
            emailStore.connect(HOST_NAME, USERID, PASSWORD);
            connected=emailStore.isConnected();
            LOGGER.info("Email is Connected with given credentials ie., "+connected);
            emailFolder = emailStore.getFolder("INBOX");

            emailFolder.open(Folder.READ_WRITE);
           /* messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));//emailFolder.getMessages();//

            System.out.println("\nNew Message count: "+messages.length);*/
        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
        return connected;
    }
    public boolean getSubjectFromMailValidateOrder(String subject) {
        message=null;
        subjectFound=false;
        try{
            sdf = new SimpleDateFormat("MMM dd, yyyy h:mm:ss aa");//Jul 15, 2019, 4:39 PM based on gmail date format
            Date date = new Date();
            mailDate=sdf.format(date);
            date.setSeconds(date.getSeconds()- 10);
            LOGGER.debug("Response Sent date/time: "+mailDate);
            LOGGER.debug("Response Sent date/time minus 10 secs : "+date);

            //loginMail(USERID, PASSWORD);
            int wait=0;

            whileloop:
            while(wait<300) {
                emailFolder.getMessages();
                messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                LOGGER.debug(wait + " UnRead Messages count: "+messages.length);
                if(messages.length>0) {
                    /*for(int i=0;i<messages.length;i++) {
                        message = messages[i];*/
                        message=messages[messages.length - 1];
                        String actualSubject = message.getSubject();
                        System.out.println(wait + " Message Subject actual : " + actualSubject);
                        Date receivedDate = message.getReceivedDate();
                        System.out.println(wait + " Actual received date : " + receivedDate+" and mail sent date : "+date);
                        System.out.println(wait + " Actual Subject : '" + subject+"' comparing with expected subject : '"+actualSubject+"'");

                        if (subject.contains(actualSubject))// || subject.contains("Votre Confirmation Équipe test Ordre")
                        {
                            System.out.println(wait + " Actual Receive date: " + receivedDate+" will compare with sent date : "+date);
                            if (receivedDate.after(date) || String.valueOf(receivedDate).equals(String.valueOf(date))) {
                                System.out.println(wait + " Actual Receive date: " + receivedDate + " is after response date:" + date);
                                System.out.println(wait + " Message Content Type having subject : " + actualSubject);
                                messageContent = message.getContent().toString();
                                System.out.println(wait + " Message Content : " + messageContent);
                                subjectFound = true;
                                break whileloop;
                            } else {
                                LOGGER.debug(wait + " Actual Receive date: " + receivedDate + " is not after expected " + date);
                            }

                        } else {
                            LOGGER.debug(wait + " Actual Subject: '" + actualSubject + "'  is not equals to expected '" + subject + "'");
                        }
                    //}
                }
                else {
                    LOGGER.debug(wait + " No Unread mails found...");
                }
                sleep(2000);
                wait++;
            }
            if(subjectFound)
            {
                LOGGER.info("Subject Found: "+subject);
                emailFolder.getMessages();
                Message[] unreadMessages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                LOGGER.info("Total unread messages : "+unreadMessages.length);
                for (int q = 0; q < unreadMessages.length; q++) {
                    LOGGER.info(q+" Subject which is unread is : "+ unreadMessages[q].getSubject() +" will compare with expected subject : "+subject);
                    if(unreadMessages[q].getSubject().contains(subject) || subject.contains(unreadMessages[q].getSubject())) {
                        unreadMessages[q].setFlag(Flags.Flag.SEEN, true);
                        LOGGER.info(q+" Subject '"+ unreadMessages[q].getSubject() +"' which is unread is now read");
                    }
                }
            }
            else
            {
                LOGGER.error("Mail Subject: '"+subject+"' NOT Found for waiting 10 mins");
            }

        }catch (Exception e) {
            LOGGER.error("Exception: "+e.getLocalizedMessage());
        }
        return subjectFound;
    }
    public String getContentMessage()
    {
        String msgContent=messageContent;
        return msgContent;
    }

}

