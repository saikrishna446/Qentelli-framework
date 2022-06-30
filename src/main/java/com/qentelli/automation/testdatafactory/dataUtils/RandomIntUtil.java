package com.qentelli.automation.testdatafactory.dataUtils;

import java.util.Random;

public class RandomIntUtil {

    public static int getRandomInteger(int max){
        return new Random().nextInt(max + 1);
    }
}
