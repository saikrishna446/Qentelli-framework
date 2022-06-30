package com.qentelli.automation.testdatafactory.dataUtils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomStringUtil {
    public static String getRandomAlphanumericString(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
