package utils.listeners;

import io.cucumber.testng.PickleWrapper;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.Map;
import java.util.Optional;

public class Retry implements IRetryAnalyzer {
    private byte retryCount = 0;
    private static final byte MAX_RETRY = 3;

    private static final Map<Integer, String> TEST_STATUS_MAP = Map.of(
            ITestResult.SUCCESS, "SUCCESS",
            ITestResult.FAILURE, "FAILURE",
            ITestResult.SKIP, "SKIP"
    );

    @Override
    public boolean retry(ITestResult result) {
        // ✅ Check if scenario has @retryable tag
        Object[] params = result.getParameters();
        if (params.length > 0 && params[0] instanceof PickleWrapper pickleWrapper) {
            boolean hasRetryTag = pickleWrapper.getPickle()
                    .getTags()
                    .stream()
                    .anyMatch(tag -> tag.equalsIgnoreCase("@retryable"));

            if (hasRetryTag && retryCount < MAX_RETRY) {
                System.out.printf("🔁 Retrying %s (Attempt %d)%n", result.getName(), retryCount + 1);
                retryCount++;
                return true;
            }
        }

        return false;
    }

    private String getResultsStatusName(final int status) {
        return TEST_STATUS_MAP.getOrDefault(status, "UNKNOWN");
    }
}