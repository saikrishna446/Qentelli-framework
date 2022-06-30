package com.qentelli.automation.util;

import java.util.ArrayList;

public class DBResult {
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
    public class RootModel{
        public String testRail;
        public String logLink;
        public long lid;
        public boolean mobile;
        public long start;
        public String project;
        public int failed;
        public String env;
        public String locale;
        public String platform;
        public int skipped;
        public String bucket;
        public int duration;
        public int total;
        public String suite;
        public String application;
        public ArrayList<Scenario> scenario;
        public String browser;
        public ArrayList<Step> step;
        public long end;
        public long time;
        public String runId;
        public int passed;
        public String user;
    }

    public class Scenario{
        public String testRail;
        public String featureName;
        public long lid;
        public String errorType;
        public String testRailLink;
        public long start;
        public int totalSteps;
        public int failed;
        public String locale;
        public int skipped;
        public String sauceLink;
        public int duration;
        public String result;
        public String serverInfo;
        public String sauceVideo;
        public long end;
        public String comment;
        public long time;
        public String runId;
        public int passed;
        public String scenarioName;
        public String sauceHtml;
    }

    public class Step{
        public String testRail;
        public int duration;
        public String result;
        public String featureName;
        public long lid;
        public int line;
        public long start;
        public String step;
        public long end;
        public long time;
        public String runId;
        public String scenarioName;
    }


}
