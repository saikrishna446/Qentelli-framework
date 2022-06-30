package com.qentelli.automation.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qentelli.automation.singletons.RuntimeSingleton;

public class AbstractTestSpecificResourceBundle extends AbstractApplicationsResourceBundle {

    private static final long ID = Thread.currentThread().getId();
	protected static Logger logger = LogManager.getLogger(COOTestcaseResourceBundle.class);
    private final static String BASENAME = "testdata.";

    public AbstractTestSpecificResourceBundle() {
        super(BASENAME + getEnv() + "."
                + RuntimeSingleton.getInstance().scenarios.get(ID).testrail + "."
                + RuntimeSingleton.getInstance().scenarios.get(ID).testrail);
        int i = 0;
    }

    public AbstractTestSpecificResourceBundle(String tc) {
        super(BASENAME + getEnv() + "." + tc + "." + tc);
        int i = 0;
    }

    public String getValue(String key){
        return bundle.getString(key);
    }
}
