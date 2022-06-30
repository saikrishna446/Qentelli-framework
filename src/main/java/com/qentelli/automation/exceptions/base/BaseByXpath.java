package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.utilities.PageObject;

public class BaseByXpath extends AbstractBaseBy {
	protected Logger logger = LogManager.getLogger(BaseByXpath.class);
	String xpath_path;

	protected BaseByXpath(PageObject objects) {
		super(objects);
		element = objects.byxpath(this.getClass().getSimpleName());
		locatorText = objects.xpath;
	}

	public String path() {
		return locatorText;
	}
}
