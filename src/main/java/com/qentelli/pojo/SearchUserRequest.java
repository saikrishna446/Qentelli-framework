package com.qentelli.pojo;

public class SearchUserRequest {
    private String searchFilterName;
    private String searchFilterValue;

    public String getSearchFilterValue() {
        return searchFilterValue;
    }

    public void setSearchFilterValue(String searchFilterValue) {
        this.searchFilterValue = searchFilterValue;
    }

    public String getSearchFilterName() {
        return searchFilterName;
    }

    public void setSearchFilterName(String searchFilterName) {
        this.searchFilterName = searchFilterName;
    }
}
