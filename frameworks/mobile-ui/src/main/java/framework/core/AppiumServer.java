package framework.core;

import config.TOMException;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import utils.BaseLogger;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.lang.Thread.sleep;

public class AppiumServer extends BaseLogger {

    private final AppiumDriverLocalService service;

    public AppiumServer(String ipAddress, String appiumPath, String nodePath, String logs) {
        try {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
                    .withIPAddress(ipAddress)
                    .usingAnyFreePort()
                    .withAppiumJS(new File(appiumPath))
                    .usingDriverExecutable(new File(nodePath))
                    .withLogFile(new File(logs));

            this.service = AppiumDriverLocalService.buildService(serviceBuilder);

            if (!this.service.isRunning()) {
                this.service.start();
            }

        } catch (Exception e) {
            throw new TOMException("Failed to start Appium service: " + e.getMessage(), e);
        }
    }

    public URL getUrl() {
        return service.getUrl();  // this returns the actual endpoint, like http://127.0.0.1:4723
    }

    /**
     * Stops the Appium service if running.
     */
    public void stop() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

    /**
     * Returns the running AppiumDriverLocalService.
     */
    public AppiumDriverLocalService get() {
        return this.service;
    }

    public void waitUntilReady() {
        final URI statusUri = URI.create(getUrl().toString().replaceAll("/+$", "") + "/status");
        final HttpClient client = HttpClient.newHttpClient();

        final int maxRetries = 5;
        int attempt = 0;
        long waitTime = 1000; // initial delay in ms

        logger.info("🔄 Waiting for Appium server to be ready at: {}", statusUri);

        while (attempt < maxRetries) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(statusUri)
                        .timeout(Duration.ofSeconds(5))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 && response.body().contains("\"ready\":true")) {
                    logger.info("✅ Appium server is ready (attempt {})", attempt + 1);
                    return;
                }

                logger.warn("⏳ Appium not ready yet (status: {}, attempt: {})", response.statusCode(), attempt + 1);
            } catch (Exception e) {
                logger.warn("❌ Connection failed on attempt {}: {}", attempt + 1, e.getMessage());
            }

            attempt++;
            try {
                sleep(waitTime);
            } catch (InterruptedException e) {
                throw new TOMException(e.getMessage());
            }
            waitTime *= 2;
        }

        throw new TOMException("❌ Appium server didn't become ready in time at: " + statusUri);
    }

}
