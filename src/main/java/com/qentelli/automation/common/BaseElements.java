package com.qentelli.automation.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.qentelli.automation.exceptions.base.AutomationIssueException;
import com.qentelli.automation.pages.BasePage;
import com.qentelli.automation.utilities.PageObject;

public class BaseElements {
	protected Logger logger = LogManager.getLogger(BasePage.class);

	WebDriver driver = null;
	protected PageObject objects;

	public BaseElements(WebDriver d) {
		driver = d;
        objects = initElements();
        if (driver == null)
			throw new AutomationIssueException("base element driver is null");
      }

	public void navigate(String url) {
		driver.get(url);
	}

    private String getName() {
      return getClass().getSimpleName();

    }

    protected PageObject initElements() {
      return new PageObject(driver, getName());
    }

}
