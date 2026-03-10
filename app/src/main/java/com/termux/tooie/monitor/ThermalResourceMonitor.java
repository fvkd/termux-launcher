package com.termux.tooie.monitor;

import android.util.Log;

import com.termux.privileged.PrivilegedBackendManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Utility for parsing Thermal metrics from /sys/class/thermal
 */
public class ThermalResourceMonitor {
    private static final String TAG = "ThermalResourceMonitor";
    private static final String THERMAL_ROOT = "/sys/class/thermal";

    /**
     * Get thermal readings from all zones
     * @return JSONArray containing thermal data
     */
    public JSONArray getThermalInfo() {
        JSONArray result = new JSONArray();
        List<File> zones = findThermalZones();
        
        if (zones.isEmpty()) {
            // Try to find zones via privileged backend if directory list is empty or restricted
            String zoneList = executePrivileged("ls " + THERMAL_ROOT);
            if (zoneList != null && !zoneList.isEmpty()) {
                String[] names = zoneList.split("\\s+");
                for (String name : names) {
                    if (name.startsWith("thermal_zone")) {
                        result.put(readZonePrivileged(name));
                    }
                }
            }
            return result;
        }

        for (File zone : zones) {
            JSONObject info = readZoneInfo(zone);
            if (info != null) {
                result.put(info);
            }
        }
        return result;
    }

    private List<File> findThermalZones() {
        File root = new File(THERMAL_ROOT);
        if (!root.exists() || !root.isDirectory()) return Collections.emptyList();

        File[] zones = root.listFiles((dir, name) -> name != null && name.startsWith("thermal_zone"));
        if (zones == null) return Collections.emptyList();

        List<File> list = Arrays.asList(zones);
        Collections.sort(list, (a, b) -> a.getName().compareTo(b.getName()));
        return list;
    }

    private JSONObject readZoneInfo(File zoneDir) {
        String type = readFileContent(new File(zoneDir, "type"));
        String tempRaw = readFileContent(new File(zoneDir, "temp"));

        if (tempRaw == null) {
            // Fallback to privileged for specific zone files if direct read fails
            return readZonePrivileged(zoneDir.getName());
        }

        return buildZoneJson(zoneDir.getName(), type, tempRaw);
    }

    private JSONObject readZonePrivileged(String zoneName) {
        String type = executePrivileged("cat " + THERMAL_ROOT + "/" + zoneName + "/type");
        String tempRaw = executePrivileged("cat " + THERMAL_ROOT + "/" + zoneName + "/temp");
        return buildZoneJson(zoneName, type, tempRaw);
    }

    private JSONObject buildZoneJson(String name, String type, String tempRaw) {
        if (tempRaw == null || tempRaw.isEmpty()) return null;
        try {
            JSONObject json = new JSONObject();
            json.put("zone", name);
            json.put("type", type != null ? type.trim() : "unknown");
            
            long temp = Long.parseLong(tempRaw.trim());
            // Many kernels expose temp in millidegrees Celsius.
            // If > 1000, it's likely millidegrees.
            double tempC = temp > 1000 ? (temp / 1000.0) : (double) temp;
            json.put("tempC", tempC);
            return json;
        } catch (Exception e) {
            return null;
        }
    }

    private String readFileContent(File file) {
        if (!file.exists() || !file.canRead()) return null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private String executePrivileged(String command) {
        try {
            return PrivilegedBackendManager.getInstance().executeCommand(command).get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            return null;
        }
    }
}
