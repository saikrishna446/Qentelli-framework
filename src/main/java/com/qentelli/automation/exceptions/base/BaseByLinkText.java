package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.PageObject;

public class BaseByLinkText extends AbstractBaseBy {
    protected Logger logger = LogManager.getLogger(BaseByClassName.class);

    protected BaseByLinkText(PageObject objects) {
        super(objects);
        driver = objects.getDriver();
        element = objects.bylinktext(this.getClass().getSimpleName());
        locatorText = objects.xpath;
		// logger.info("<" + this.getClass().getSimpleName() + ">" + "|\t " +
		// objects.xpath + "@" + objects.getRoot());
        // logger.info(String.format("%-20s|%-40s @%s", "<" + this.getClass().getSimpleName() + ">",
        // objects.xpath,
        // objects.getRoot()));

    }

}
