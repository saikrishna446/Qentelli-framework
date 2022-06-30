package com.qentelli.automation.stepdefs.application;

import com.qentelli.automation.common.World;
import com.qentelli.automation.managers.StepExecutive;
import cucumber.api.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonSteps {
    private World world;
    Logger logger = LogManager.getLogger(CommonSteps.class);

    public CommonSteps(World world) {
        this.world = world;
    }

    @Then("Print Log Message {string}")
    public void printLogMessage(String text) {
        new StepExecutive(world) {
            @Override
            public void step() throws Exception {
                logger.info(text);
            }
        }.run();
    }
}
