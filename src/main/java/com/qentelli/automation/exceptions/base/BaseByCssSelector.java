package com.qentelli.automation.exceptions.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.qentelli.automation.utilities.PageObject;

public class BaseByCssSelector extends AbstractBaseBy {
  protected Logger logger = LogManager.getLogger(BaseByCssSelector.class);

  protected BaseByCssSelector(PageObject objects) {
        super(objects);
        driver = objects.getDriver();
        element = objects.bycss(this.getClass().getSimpleName());
        locatorText = objects.xpath;
        // logger.info("<" + this.getClass().getSimpleName() + "> CSS ResourceBundle: "
        // + objects.getRoot() + "|\t "
        // + objects.xpath);
    }

}
