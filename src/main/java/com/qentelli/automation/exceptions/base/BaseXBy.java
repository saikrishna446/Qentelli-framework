package com.qentelli.automation.exceptions.base;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class BaseXBy extends AbstractBaseBy {
	public String path ; 
	public BaseXBy(WebDriver objects,String path) {
		super(objects);
		setElement(By.xpath(path)) ;
	}
 

}
