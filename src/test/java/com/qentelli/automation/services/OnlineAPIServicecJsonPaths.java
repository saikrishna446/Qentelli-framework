package com.qentelli.automation.services;

public class OnlineAPIServicecJsonPaths {
	 public interface OMIAccountDetailPaths {
         String emailJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.email";
         String billingAddressJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billAddress1";
         String billingCityJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billCity";
         String billPostalCodeJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.billPostalCode";
         String shippingAddressOneJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipAddress1";
         String shipCityJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipCity";
         String shipPostalCodeJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipPostalCode";
         String firstNameJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.firstName";
         String lasttNameJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.lastName";
         String shippingStateJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipState";
         String shippingCityJsonPath = "Envelope.Body.searchOIMIdentityResponse.search_identity_response.searchUser.shipCity";
	 }
	 public interface TbbOrderDetailsPath {
		 String coachIDJsonPath = "Envelope.Body.CSGetOrderInfoResponse.CSGetOrderInfoResult.CoachDID";
		 String customerIDJsonPath = "Envelope.Body.CSGetOrderInfoResponse.CSGetOrderInfoResult.CustomerDID";

	 }
	 public interface CoatchDetailsPath {
		 String billingCountryJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.BillCountry";
		 String billingStreetOneJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.BillStreet1";
		 String shipCityJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.ShipCity";
		 String shipStateJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.ShipState";
		 String shipPostalCodeJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.ShipPostalCode";
		 String phoneOneJsonPath = "Envelope.Body.GetRepInfoResponse.GetRepInfoResult.Phone1";
		 
	 }
	public interface CustomerDetailsPath {
		String billingCountryJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.BillCountry";
		String billingStreetOneJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.BillStreet1";
		String shipCityJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.BillCity";
		String shipStateJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.BillState";
		String shipPostalCodeJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.BillPostalCode";
		String phoneOneJsonPath = "Envelope.Body.GetCustomerInfoResponse.GetCustomerInfoResult.Phone1";

	}
}
