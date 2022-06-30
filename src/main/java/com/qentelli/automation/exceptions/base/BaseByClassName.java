package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.PageObject;

public class BaseByClassName extends AbstractBaseBy {
	protected Logger logger = LogManager.getLogger(BaseByClassName.class);

	protected BaseByClassName(PageObject objects) {
		super(objects);
		driver = objects.getDriver();
		element = objects.byclassname(this.getClass().getSimpleName());
		locatorText = objects.xpath;
        // logger.info("<" + this.getClass().getSimpleName() + "> ResourceBundle: " +
        // objects.getRoot() + "|\t "
        // + objects.xpath);
	}

}
