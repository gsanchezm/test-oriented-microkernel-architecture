package tom.utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CoreHooks {
    @Before
    public void beforeScenario(Scenario scenario) {
        String feature = scenario.getUri().toString().split("/")[
                scenario.getUri().toString().split("/").length - 1
                ].replace(".feature", "");
        //ChainTestReporter.startScenario(feature, scenario.getName());
    }

    @After
    public void afterScenario() {
        //ChainTestReporter.finishScenario();
    }
}
