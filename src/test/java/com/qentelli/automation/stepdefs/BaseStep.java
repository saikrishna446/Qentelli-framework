package com.qentelli.automation.stepdefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseStep {

    Logger logger = LogManager.getLogger(BaseStep.class);

    /**
     * Handle Exceptions
     * @param e the exception which has been caught and passed in
     * @throws Exception
     */
    protected void handleExceptions(Exception e) throws Exception {
       logger.error("Caught " + e.getClass().getSimpleName());
       throw e;
    }
}
