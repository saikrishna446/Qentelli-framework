package com.qentelli.services.util;

import java.util.HashMap;
import java.util.Map;

public class OnlineAPI {

    public OnlineAPI() {
        //Create required stuff to initialize
    }

    /**
     * Code has to be modified to build SOAP Template
     * @return
     * @throws Exception
     */
    public String getBodyForGetRepInfo() throws Exception {

        /*MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage soapMsg = factory.createMessage();
        SOAPPart part = soapMsg.getSOAPPart();
        SOAPEnvelope envelope = part.getEnvelope();
        envelope.addNamespaceDeclaration("sec","http://www.securefreedom.com/");
        SOAPBody body = envelope.getBody();
        SOAPBodyElement repInfoElement = body.addBodyElement(envelope.createName("sec:GetRepInfo", "", ""));
        SOAPElement credentialsElement = repInfoElement.addChildElement(envelope.createName("sec:Credentials","",""));
        credentialsElement.addChildElement(envelope.createName("sec:Username")).addTextNode("rbathula");
        credentialsElement.addChildElement(envelope.createName("sec:password")).addTextNode("Ac123456");
        credentialsElement.addChildElement(envelope.createName("sec:Token")).addTextNode("?");
        repInfoElement.addChildElement(envelope.createName("sec:RepNumberOrURL")).addTextNode("319001");
        soapMsg.writeTo(System.out);
        return soapMsg.toString();*/

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sec=\"http://www.securefreedom.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <sec:GetRepInfo>\n" +
                "         <!--Optional:-->\n" +
                "         <sec:Credentials>\n" +
                "            <!--Optional:-->\n" +
                "            <sec:Username>rbathula</sec:Username>\n" +
                "            <!--Optional:-->\n" +
                "            <sec:Password>Ac123456</sec:Password>\n" +
                "            <!--Optional:-->\n" +
                "            <sec:Token>?</sec:Token>\n" +
                "         </sec:Credentials>\n" +
                "         <!--Optional:-->\n" +
                "         <sec:RepNumberOrURL>319001</sec:RepNumberOrURL>\n" +
                "      </sec:GetRepInfo>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
    }

    /**
     *
     * @return
     * @throws Exception
     */

    public Map<String, String> getHeadersForGetRepInfo() throws Exception {
        Map<String, String> authHeaders = new HashMap<>();
        authHeaders.put("SOAPAction", "http://www.securefreedom.com/GetRepInfo");
        return  authHeaders;
    }
}
