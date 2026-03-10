package com.termux.tooie.monitor;

import android.util.Log;

import com.termux.privileged.PrivilegedBackendManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility for parsing Memory metrics from /proc/meminfo
 */
public class MemoryResourceMonitor {
    private static final String TAG = "MemoryResourceMonitor";
    private static final String PROC_MEMINFO = "/proc/meminfo";

    /**
     * Get detailed memory usage
     * @return JSONObject containing memory data
     */
    public JSONObject getMemoryUsage() {
        JSONObject result = new JSONObject();
        try {
            Map<String, Long> memInfo = readMemInfo();
            if (memInfo.isEmpty()) {
                result.put("ok", false);
                result.put("error", "Failed to read /proc/meminfo");
                return result;
            }

            long memTotal = memInfo.getOrDefault("MemTotal", 0L);
            long memAvailable = memInfo.getOrDefault("MemAvailable", 0L);
            long memFree = memInfo.getOrDefault("MemFree", 0L);
            long buffers = memInfo.getOrDefault("Buffers", 0L);
            long cached = memInfo.getOrDefault("Cached", 0L);
            long active = memInfo.getOrDefault("Active", 0L);
            long inactive = memInfo.getOrDefault("Inactive", 0L);
            long swapTotal = memInfo.getOrDefault("SwapTotal", 0L);
            long swapFree = memInfo.getOrDefault("SwapFree", 0L);

            result.put("ok", true);
            result.put("totalBytes", memTotal * 1024L);
            result.put("availableBytes", memAvailable * 1024L);
            result.put("freeBytes", memFree * 1024L);
            result.put("usedBytes", (memTotal - memAvailable) * 1024L);
            result.put("buffersBytes", buffers * 1024L);
            result.put("cachedBytes", cached * 1024L);
            result.put("activeBytes", active * 1024L);
            result.put("inactiveBytes", inactive * 1024L);
            result.put("swapTotalBytes", swapTotal * 1024L);
            result.put("swapFreeBytes", swapFree * 1024L);
            result.put("timestamp", System.currentTimeMillis());

        } catch (JSONException e) {
            Log.e(TAG, "Failed to build Memory usage JSON", e);
        }
        return result;
    }

    private Map<String, Long> readMemInfo() {
        Map<String, Long> memInfo = new HashMap<>();
        String content = readFileContent(PROC_MEMINFO);
        if (content == null || content.isEmpty()) {
            content = executePrivileged("cat " + PROC_MEMINFO);
        }

        if (content != null) {
            String[] lines = content.split("\n");
            for (String line : lines) {
                int colon = line.indexOf(':');
                if (colon > 0) {
                    String key = line.substring(0, colon).trim();
                    String valuePart = line.substring(colon + 1).trim();
                    String[] parts = valuePart.split("\\s+");
                    if (parts.length > 0) {
                        try {
                            memInfo.put(key, Long.parseLong(parts[0]));
                        } catch (NumberFormatException e) {
                            Log.w(TAG, "Failed to parse meminfo line: " + line);
                        }
                    }
                }
            }
        }
        return memInfo;
    }

    private String readFileContent(String path) {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) return null;

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
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
