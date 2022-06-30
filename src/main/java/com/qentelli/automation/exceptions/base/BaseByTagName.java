package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.PageObject;

public class BaseByTagName extends AbstractBaseBy {
    protected Logger logger = LogManager.getLogger(BaseByClassName.class);

    protected BaseByTagName(PageObject objects) {
        super(objects);
        driver = objects.getDriver();
        element = objects.bytagname(this.getClass().getSimpleName());
        locatorText = objects.xpath;
    }

}
