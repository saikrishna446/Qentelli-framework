package com.qentelli.automation.drivers;

import com.qentelli.automation.common.World;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Waits {
	private WebDriver driver;
    private long defaultImplicitSecs;
    private long currentImplicitSecs;
    private WebDriverWait expWait;
    private Wait<WebDriver> fluWait;
    public static final int MIN_WAIT = 5;
    public static final int MEDIUM_WAIT = 15;
    public static final int HALF_MINUTE = 30;
    public static final int LONG_WAIT = 60;
    public static final int POLLING = 2;
    public static final int ELEMENT_PRESENCE_WAIT = 100;
    public static final int ELEMENT_VISIBILITY_WAIT = 100;
    public static final int ELEMENT_CLICKABILITY_WAIT = 100;
    public static final int PAGE_LOAD_TIMEOUT = 180;

    public Waits(World world, WebDriver driver){
        this.driver = driver;
        defaultImplicitSecs = MIN_WAIT;
        setImplicit(defaultImplicitSecs);
    }

//Implicit Waits
    public void setImplicit(long seconds){
    	driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        currentImplicitSecs = seconds;
    }

    public void resetImplicit(){
        setImplicit(defaultImplicitSecs);
    }

    public void disableImplicit(){
        setImplicit(0);
    }

    public long getImplicit(){
        return currentImplicitSecs;
    }

    //Explicit Waits
    public WebDriverWait explicitWait(long seconds) {
        this.expWait = new WebDriverWait(driver, seconds);
        return expWait;
    }

    //Fluent Waits
    public Wait<WebDriver> fluentWait(long seconds, long polling) {
        this.fluWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofSeconds(polling))
                .ignoring(org.openqa.selenium.WebDriverException.class)
                .ignoring(org.openqa.selenium.NoSuchElementException.class);
        return fluWait;
    }
}
