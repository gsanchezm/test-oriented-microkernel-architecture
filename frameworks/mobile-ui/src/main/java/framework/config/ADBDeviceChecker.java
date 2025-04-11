package framework.config;

import config.TOMException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ADBDeviceChecker {

    public static List<String> listConnectedDevices() {
        List<String> devices = new ArrayList<>();
        try {
            Process process = new ProcessBuilder("adb", "devices").start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("device") && !line.startsWith("List")) {
                        String udid = line.split("\\s+")[0].trim();
                        devices.add(udid);
                    }
                }
            }
        } catch (Exception e) {
            throw new TOMException("❌ Failed to run 'adb devices'", e);
        }

        return devices;
    }

    public static void assertDeviceConnected(String expectedUdid) {
        List<String> devices = listConnectedDevices();

        if (devices.isEmpty()) {
            throw new TOMException("❌ No devices connected via ADB. Start your emulator or connect a device.");
        }

        if (!devices.contains(expectedUdid)) {
            throw new TOMException("❌ Device with UDID '" + expectedUdid + "' not found. Connected devices: " + devices);
        }

        System.out.println("✅ ADB device check passed: " + expectedUdid);
    }
}
