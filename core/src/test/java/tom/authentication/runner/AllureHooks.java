package tom.authentication.runner;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;

import static io.qameta.allure.Allure.getLifecycle;

public class AllureHooks {
    private final AllureLifecycle lifecycle = getLifecycle();

    @Before
    public void beforeScenario(Scenario scenario) {
        // Optional setup if needed
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            StatusDetails details = new StatusDetails().setMessage("Step failed: " + scenario.getName());
            lifecycle.updateTestCase(tc -> {
                tc.setStatus(Status.FAILED);
                tc.setStatusDetails(details);
            });
        } else {
            lifecycle.updateTestCase(tc -> tc.setStatus(Status.PASSED));
        }
        lifecycle.stopTestCase(scenario.getId());
        lifecycle.writeTestCase(scenario.getId());
    }
}

