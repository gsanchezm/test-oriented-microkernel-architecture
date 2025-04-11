package framework.core;

import config.TOMException;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.net.URL;

public class AppiumServer {

    private final AppiumDriverLocalService service;

    public AppiumServer(String ipAddress, String appiumPath, String nodePath, String logs) {
        try {
            AppiumServiceBuilder serviceBuilder = new AppiumServiceBuilder()
                    .withArgument(() -> "--use-plugins", "element-wait,gestures")
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
}
