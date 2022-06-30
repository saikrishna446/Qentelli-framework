package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.PageObject;

public class BaseByPartialLinkText extends AbstractBaseBy {
    protected Logger logger = LogManager.getLogger(BaseByClassName.class);

    protected BaseByPartialLinkText(PageObject objects) {
        super(objects);
        driver = objects.getDriver();
        element = objects.bypartiallinktext(this.getClass().getSimpleName());
        locatorText = objects.xpath;
        // logger.info(String.format("%-20s|%-40s @%s", "<" + this.getClass().getSimpleName() + ">",
        // objects.xpath,
        // objects.getRoot()));

    }

}
