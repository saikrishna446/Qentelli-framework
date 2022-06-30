package com.qentelli.automation.testdatafactory.config;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class FactoryConfig {

	private ResourceBundle factorySettings;

	public FactoryConfig(String environment, Locale locale) {
		factorySettings = ResourceBundle.getBundle(
				"com.qentelli.automation.testdatafactory.config." + environment + ".factorySettings", locale);
	}

	public String getOrderImportUrl() {

		return factorySettings.getString("order.import.status.url");
	}

	public String getSabrixUrl() {

		return factorySettings.getString("sabrix.url");
	}

	public String getStrGUID() {
		return factorySettings.getString("order.import.guid");
	}

	public String getCustomerNumber() {
		return factorySettings.getString("order.import.customernumber");
	}

	public String getGncCustomerID() {
		return factorySettings.getString("order.import.gnccustomerid");
	}

	public String getGncSponsorID() {
		return factorySettings.getString("order.import.gncsponsorid");
	}

	public String getCustomerEmailID() {
		return factorySettings.getString("order.import.customeremailid");
	}

	public String getEmailToSOA() {
		return factorySettings.getString("email.group.soa");
	}

	public String getEmailToATG() {
		return factorySettings.getString("email.group.atg");
	}

	public String getEmailToOSB() {
		return factorySettings.getString("email.group.osb");
	}

	public String getEmailToMerlin() {
		return factorySettings.getString("email.group.merlin");
	}

	public String getEmailToEBS() {
		return factorySettings.getString("email.group.ebs");
	}

	public String getEmailToCommon() {
		return factorySettings.getString("email.group.common");
	}

	public String getIngestStatusUrl() {
		return factorySettings.getString("ingest.status.url");
	}

	public String getIngestHeaderXAPIKey() {
		return factorySettings.getString("ingest.header.xapikey");
	}

	public String getClientId() {
		return factorySettings.getString("test.orderconfirm.mail.api.clientid");
	}

	public String getGmailUsername() {
		return factorySettings.getString("test.gmail.username");
	}

	public String getGmailPassword() {
		return factorySettings.getString("test.gmail.password");
	}

	public String getOrderConfirmEndpoint() {
		return factorySettings.getString("test.orderconfirm.mail.api.endpoint");
	}

	public String getSoapUsername() {
		return factorySettings.getString("test.orderconfirm.mail.api.username");
	}

	public String getSoapPassword() {
		return factorySettings.getString("test.orderconfirm.mail.api.password");
	}

	public String getServerUrl() {
		return factorySettings.getString("test.default.url");
	}

	public String getDefaultVisa() {
		return factorySettings.getString("test.default.visa");
	}

	public String getTestDBHost() {
		return factorySettings.getString("testDB.host");
	}

	public String getTestDBPort() {
		return factorySettings.getString("testDB.port");
	}

	public String getTestDBDatabaseName() {
		return factorySettings.getString("testDB.database");
	}

	public String getOimEndpoint() {
		return factorySettings.getString("test.OIM.endpoint");
	}

	public String getOimAPIKey() {
		return factorySettings.getString("test.OIM.apikey");
	}

	public String getDefaultSponsor() {
		String sponsor = Optional.ofNullable(System.getProperty("override.sponsor")).orElse("").toLowerCase();
		if (sponsor == null || sponsor.isEmpty()) {
			return factorySettings.getString("test.default.sponsor");
		} else {
			return sponsor;
		}
	}

//        public String getDefaultHost() {
//                return factorySettings.getString("qentelli.default.host");
//        }

	public String getAuthHost() {
		return factorySettings.getString("test.auth.host");
	}

	public String getAuthURL() {
		return factorySettings.getString("test.auth.url");
	}

	public String getLocale() {
		return factorySettings.getString("test.locale");
	}

	public String getLocaleString() {
		return factorySettings.getString("test.locale.string");
	}

	public String getHeaderLanguage() {
		return factorySettings.getString("test.header.language");
	}

	public String getPushSite() {
		return factorySettings.getString("test.locale.pushSite");
	}

	public String getPassword() {
		return factorySettings.getString("test.default.password");
	}

	public String getDefaultEmailDomain() {
		return factorySettings.getString("default.email.domain");
	}

	public String getBydesignEndpoint() {
		return factorySettings.getString("bydesign.api.endpoint");
	}

	public String getBydesignUsername() {
		return factorySettings.getString("bydesign.api.username");
	}

	public String getBydesignPassword() {
		return factorySettings.getString("bydesign.api.password");
	}

	public String getHealthCheckATGURLs() {
		return factorySettings.getString("test.atg.healthcheck.urls");
	}

	public String getHealthCheckATGResponse() {
		return factorySettings.getString("test.atg.healthcheck.response");
	}

	public String getHealthCheckMerlinURLs() {
		return factorySettings.getString("test.merlin.healthcheck.urls");
	}

	public String getHealthCheckMerlinResponse() {
		return factorySettings.getString("test.merlin.healthcheck.response");
	}

	public String getHealthCheckSyncIdUrl() {
		return factorySettings.getString("test.syncid.url");
	}

	public String getHealthCheckSOAServiceUrl() {
		return factorySettings.getString("test.soa.service.url");
	}

	public String getHealthCheckManageServersUrl() {
		return factorySettings.getString("test.mgservers.url");
	}

	public String getHealthCheckEBSCCJobsUrl() {
		return factorySettings.getString("test.ebsccjobs.url");
	}

	public String getAtgDBQA3Host() {
		return factorySettings.getString("atgDB.QA3.host");
	}

	public String getAtgDBQA3Port() {
		return factorySettings.getString("atgDB.QA3.port");
	}

	public String getAtgDBQA3Database() {
		return factorySettings.getString("atgDB.QA3.database");
	}

	public String getAtgDBQA3Username() {
		return factorySettings.getString("atgDB.QA3.username");
	}

	public String getAtgDBQA3Password() {
		return factorySettings.getString("atgDB.QA3.password");
	}

	public String getAtgDBUATHost() {
		return factorySettings.getString("atgDB.UAT.host");
	}

	public String getAtgDBUATPort() {
		return factorySettings.getString("atgDB.UAT.port");
	}

	public String getAtgDBUATDatabase() {
		return factorySettings.getString("atgDB.UAT.database");
	}

	public String getAtgDBUATUsername() {
		return factorySettings.getString("atgDB.UAT.username");
	}

	public String getAtgDBUATPassword() {
		return factorySettings.getString("atgDB.UAT.password");
	}

	public String getCustomerCreateUpdateIsgAuthorization() {
		return factorySettings.getString("customer.create.update.isg.authorization");
	}

	public String getCustomerCreateUpdateIsgUrl() {
		return factorySettings.getString("customer.create.update.isg.url");
	}

	public String getOrderImportIsgAuthorization() {
		return factorySettings.getString("customer.create.update.isg.authorization");
	}

	public String getOrderImportIsgUrl() {
		return factorySettings.getString("order.import.isg.url");
	}

	public String getCoachIdWithMySite() {
		return factorySettings.getString("CoachIdWithMySite");
	}

	public String getBydPersonalWebsrviceEndpoint(){
		return factorySettings.getString("PersonalWebservice.Endpoint");
	}
}
