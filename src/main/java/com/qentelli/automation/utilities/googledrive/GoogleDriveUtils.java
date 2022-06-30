package com.qentelli.automation.utilities.googledrive;

import com.qentelli.automation.common.World;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.SecurityUtils;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoogleDriveUtils {
    private World world;
    private Drive drive;
    private int readTimeout = 60000;
    private int connectTimeout = 60000;
    private String serviceAccountId = "bb-automation@enterpriseqeautomation.iam.gserviceaccount.com";
    private String serviceAccountEmail = "bb-automation@enterpriseqeautomation.iam.gserviceaccount.com";
    private String serviceAccountPrivateKeyFile = "google_drv_key.p12";
    private String serviceAccountPrivateKeyFilePassword = "notasecret";
    private JacksonFactory jacksonFactory = new JacksonFactory();
    private NetHttpTransport httpTransport = new NetHttpTransport();
    private List<String> googleScopeList = Arrays.asList(/*"https://www.googleapis.com/auth/drive.readonly",
            "https://www.googleapis.com/auth/admin.directory.group.readonly",
            "https://www.googleapis.com/auth/admin.directory.user.alias.readonly",
            "https://www.googleapis.com/auth/admin.directory.group", "https://www.googleapis.com/auth/admin.directory.user",*/
            "https://www.googleapis.com/auth/drive");

    /**
     * Class that holds all the helper methods to use with test data files on Google drive
     * @param world passed to help with logging and other references of the framework
     */
    public GoogleDriveUtils(World world) {
        this.world = world;
        drive = (new Drive.Builder(httpTransport,
                jacksonFactory,
                getRequestInitializer(getGoogleCredentials())))
                .setApplicationName("BBAutomation").build();
    }

    /**Initializes a request after setting up connection using credentials
     * @param requestInitializer Google Credential object
     * @return HttpRequestInitializer object
     * **/
    private HttpRequestInitializer getRequestInitializer(final GoogleCredential requestInitializer) {
        return httpRequest -> {
            requestInitializer.initialize(httpRequest);
            httpRequest.setConnectTimeout(readTimeout);
            httpRequest.setReadTimeout(connectTimeout);
        };
    }
    /**
     * Creates an authorized Credential object.
     * @return An authorized Credential object.
     */
    private GoogleCredential getGoogleCredentials() {
        GoogleCredential credential;

        try {
            GoogleCredential.Builder b = new GoogleCredential.Builder().setTransport(httpTransport)
                    .setJsonFactory(jacksonFactory).setServiceAccountId(serviceAccountId)
                    .setServiceAccountPrivateKey(SecurityUtils.loadPrivateKeyFromKeyStore(SecurityUtils.getPkcs12KeyStore(),
                            new FileInputStream(new File(GoogleDriveUtils.class.getClassLoader().getResource(serviceAccountPrivateKeyFile).getFile())), serviceAccountPrivateKeyFilePassword,
                            "privatekey", serviceAccountPrivateKeyFilePassword))
                    .setServiceAccountScopes(googleScopeList);
            if (serviceAccountEmail != null) {
                b = b.setServiceAccountUser(serviceAccountEmail);
            }
            credential = b.build();
            credential.refreshToken();
        } catch (IOException | GeneralSecurityException e1) {
            throw new RuntimeException("Could not build client secrets", e1);
        }
        return credential;
    }

    public static String getFileIDFromUrl(String url){
        url = url.split("uc?id=")[1];
        url = url.replaceAll("&export=download","");
        return url;
    }

    //UTILITY METHODS

    /**
     * Gets the id of the file on Google Drive.
     * Getting the id of the file is important since all API's use the id of the file for
     * reference
     * @param name Name of the file
     * @return id of the file on Google Drive
     */
    public String getFileID(String name) {
        try {
            String pageToken = null;
            do {
                FileList result = drive.files().list()
                        .setQ("name='" + name + "'")
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name)")
                        .setPageToken(pageToken)
                        .execute();
                for (com.google.api.services.drive.model.File file : result.getFiles()) {
                    return file.getId();
                }
                pageToken = result.getNextPageToken();
            } while (pageToken != null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the file object of the file
     * @param name Name of the file
     * @return Google File object
     */
    public com.google.api.services.drive.model.File getGFile(String name) {
        String id = getFileID(name);
        try {
            return drive.files().get(id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Uploads a file to Google Drive
     * @param filePath path of the source file
     * @param mimeType mime type of the google file. See https://developers.google.com/drive/api/v3/mime-types for more details
     * @return id of the newly uploaded file on google drive
     * @throws IOException
     */
    public String uploadFile(String filePath, String mimeType) throws IOException {
        String filename = new File(filePath).getName();
        com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
        fileMetaData.setName(filename);
        java.io.File file = new java.io.File(filePath);
        FileContent mediaContent = new FileContent(mimeType, file);
        com.google.api.services.drive.model.File myFile = null;
        String fileID = getFileID(filename);
        try {
            if (fileID != null) {
                myFile = drive.files().update(fileID, fileMetaData, mediaContent).execute();
            } else {
                myFile = drive.files().create(fileMetaData, mediaContent).execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (myFile == null) {
            throw new IOException("Null Result when requesting file creation");
        }

        return myFile.getId();
    }

    /**
     * Downloads a google file as an Outpputstream
     * @param filename name of the file on Google Drive
     * @return OutputStream object of the file
     * @throws IOException
     */
    public OutputStream downloadFileAsStream(String filename) throws IOException {
        String fileId = getFileID(filename);
        OutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(fileId)
                .executeMediaAndDownloadTo(outputStream);
        return outputStream;
    }

    /**
     * Updates the Metadata of the file viz: name/description/mimetype
     *
     * @param fileName name of the file on Google Drive
     * @param fieldName name of the field (name/description/mimetype)
     * @param fieldValue value to update for the given field
     * @throws IOException
     */
    public void updateMetaData(String fileName, String fieldName, String fieldValue) throws IOException {
        String id = getFileID(fileName);
        com.google.api.services.drive.model.File file = new  com.google.api.services.drive.model.File();

        switch (fieldName.toLowerCase()){
            case "name":
                file.setName(fieldValue);
            case "description":
                file.setDescription(fieldValue);
            case "mimetype":
                file.setMimeType(fieldValue);
        }

        drive.files().update(id, file).execute();
        System.out.println("Updated "+ fieldName +" of the Google file successfully.");
    }

    /**
     * Updates the content of the file on Google Drive
     * @param name name of the file
     * @param content content to be overwritten to the file on Google Drive
     * @throws IOException
     */
    public void updateContent(String name, String content) throws IOException {
        FileOutputStream fos = null;
        com.google.api.services.drive.model.File newFile, oldFile;
        try {
            newFile = new com.google.api.services.drive.model.File();
            oldFile = getGFile(name);
            File tempFile = new java.io.File(System.getProperty("java.io.tmpdir") + "\\temp_bb");
            fos = new FileOutputStream(tempFile);
            fos.write(content.getBytes());
            fos.flush();
            FileContent mediaContent = new FileContent(oldFile.getMimeType(), tempFile);
            drive.files().update(oldFile.getId(), newFile,mediaContent).execute();
            System.out.println("Updated content of the Google file successfully.");
        } finally {
            if (fos != null) fos.close();
        }
    }
    public static void main(String args[]) throws Exception
    {
        String path="C:\\Users\\bhargava.danduga\\Downloads\\Selenium Java 101_Assignment Problem Scenario & Instructions.pdf";
        PDFUtil pdfUtil = new PDFUtil();
        pdfUtil.getPageCount(path); //returns the page count
        //returns the pdf content - all pages
        String a=pdfUtil.getText(path);

// returns the pdf content from page number 2
        a= pdfUtil.getText(path,2);

// returns the pdf content from page number 5 to 8
        pdfUtil.getText("c:/sample.pdf", 5, 8);
        //set the path where we need to store the images
        pdfUtil.setImageDestinationPath("c:/imgpath");
        pdfUtil.extractImages("c:/sample.pdf");

// extracts and saves the pdf content from page number 3
        pdfUtil.extractImages("c:/sample.pdf", 3);

// extracts and saves the pdf content from page 2
        pdfUtil.extractImages("c:/sample.pdf", 2, 2);
        //set the path where we need to store the images
        pdfUtil.setImageDestinationPath("c:/imgpath");
        pdfUtil.savePdfAsImage("c:/sample.pdf");

        String file1="c:/files/doc1.pdf";
        String file2="c:/files/doc2.pdf";

// compares the pdf documents and returns a boolean
// true if both files have same content. false otherwise.
        pdfUtil.compare(file1, file2);

// compare the 3rd page alone
        pdfUtil.compare(file1, file2, 3, 3);

// compare the pages from 1 to 5
        pdfUtil.compare(file1, file2, 1, 5);


//pass all the possible texts to be removed before comparing
        pdfUtil.excludeText("1998", "testautomation");

//pass regex patterns to be removed before comparing
// \\d+ removes all the numbers in the pdf before comparing
        pdfUtil.excludeText("\\d+");

// compares the pdf documents and returns a boolean
// true if both files have same content. false otherwise.
        pdfUtil.compare(file1, file2);

// compare the 3rd page alone
        pdfUtil.compare(file1, file2, 3, 3);

// compare the pages from 1 to 5
        pdfUtil.compare(file1, file2, 1, 5);


// compares the pdf documents and returns a boolean
// true if both files have same content. false otherwise.
// Default is CompareMode.TEXT_MODE
        pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
        pdfUtil.compare(file1, file2);

// compare the 3rd page alone
        pdfUtil.compare(file1, file2, 3, 3);

// compare the pages from 1 to 5
        pdfUtil.compare(file1, file2, 1, 5);

//if you need to store the result
        pdfUtil.highlightPdfDifference(true);
        pdfUtil.setImageDestinationPath("c:/imgpath");
        pdfUtil.compare(file1, file2);


    }

    public static void main2(String args[]) throws Exception
    {
        File file = new File("C:\\Users\\bhargava.danduga\\Downloads\\Selenium Java 101_Assignment Problem Scenario & Instructions.pdf");
        PDDocument doc = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(doc);
        System.out.println("Text in PDF\n---------------------------------");
        System.out.println(text);

        //Closing the document
        doc.close();
    }

}


