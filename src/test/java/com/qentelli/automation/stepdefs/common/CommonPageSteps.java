package com.qentelli.automation.stepdefs.common;

import com.qentelli.automation.common.World;
import com.qentelli.automation.managers.StepExecutive;
import com.qentelli.automation.pages.common.CommonPage;
import cucumber.api.java.en.When;

public class CommonPageSteps{
    private World world;
    CommonPage commonPage;

    public CommonPageSteps(World world) {
        this.world = world;
        commonPage = new CommonPage(world);
    }

    @When("I wait for {int} minutes")
    public void iWaitForMinutes(int minutes) throws Exception{
        new StepExecutive(world) {
            @Override
            public void step() throws Exception {
                commonPage.pause(minutes* 60000);
            }
        }.run();
    }

    @When("I close active tab and switch to parent tab")
    public void closeActiveTabAndFocusOnFirstTab() throws Exception {
        new StepExecutive(world) {
            @Override
            public void step() throws Exception {
                commonPage.closeWindow();
                commonPage.focusOnFirstBrowserTab();
            }
        }.run();
    }

    @When("I switch to child window or tab")
    public void iSwitchToChildWindowOrTab() {
        commonPage.switchToChildWindow();
    }
}
