package com.qentelli.automation.testdatafactory.data;

public enum CustomerType {
        FreeUser( "REGISTEREDUSER"),
        CoachUser( "REGISTEREDUSER,COACH"),
        ClubUser( "REGISTEREDUSER,CLUB"),
        ClubCoachUser("REGISTEREDUSER,CLUB,COACH"),
        PreferredCustomer("REGISTEREDUSER,PC"),
        ClubPreferredCustomer("REGISTEREDUSER,CLUB,PC");



        private final String custString;

        CustomerType(String custString){
            this.custString = custString;
        }

        public String getCustString(){
            return custString;
        }

}
