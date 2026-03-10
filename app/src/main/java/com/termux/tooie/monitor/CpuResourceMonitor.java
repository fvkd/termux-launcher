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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Utility for parsing CPU metrics from /proc/stat and /proc/loadavg
 */
public class CpuResourceMonitor {
    private static final String TAG = "CpuResourceMonitor";
    private static final String PROC_STAT = "/proc/stat";
    private static final String PROC_LOADAVG = "/proc/loadavg";

    private static class CpuSnapshot {
        long total;
        long idle;

        CpuSnapshot(long total, long idle) {
            this.total = total;
            this.idle = idle;
        }
    }

    private final Map<String, CpuSnapshot> lastSnapshots = new HashMap<>();
    private long lastSnapshotTime = 0;

    /**
     * Get CPU usage for all cores and aggregate
     * @return JSONObject containing CPU usage data
     */
    public synchronized JSONObject getCpuUsage() {
        JSONObject result = new JSONObject();
        try {
            Map<String, CpuSnapshot> currentSnapshots = readProcStat();
            if (currentSnapshots.isEmpty()) {
                result.put("ok", false);
                result.put("error", "Failed to read /proc/stat");
                return result;
            }

            long currentTime = System.currentTimeMillis();
            JSONArray cores = new JSONArray();
            
            for (Map.Entry<String, CpuSnapshot> entry : currentSnapshots.entrySet()) {
                String coreName = entry.getKey();
                CpuSnapshot current = entry.getValue();
                CpuSnapshot last = lastSnapshots.get(coreName);

                double usage = -1;
                if (last != null) {
                    long totalDelta = current.total - last.total;
                    long idleDelta = current.idle - last.idle;
                    if (totalDelta > 0) {
                        usage = 100.0 * (1.0 - ((double) idleDelta / (double) totalDelta));
                        usage = Math.max(0, Math.min(100, usage));
                    }
                }

                if (coreName.equals("cpu")) {
                    result.put("aggregateUsage", usage);
                } else {
                    JSONObject coreInfo = new JSONObject();
                    coreName = coreName.substring(3); // Remove "cpu" prefix
                    coreInfo.put("id", Integer.parseInt(coreName));
                    coreInfo.put("usage", usage);
                    cores.put(coreInfo);
                }
                
                lastSnapshots.put(coreName, current);
            }

            result.put("ok", true);
            result.put("cores", cores);
            result.put("timestamp", currentTime);
            lastSnapshotTime = currentTime;

            // Add load average
            double[] loadAvg = readLoadAvg();
            if (loadAvg != null) {
                result.put("loadAvg1m", loadAvg[0]);
                result.put("loadAvg5m", loadAvg[1]);
                result.put("loadAvg15m", loadAvg[2]);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Failed to build CPU usage JSON", e);
        }
        return result;
    }

    private Map<String, CpuSnapshot> readProcStat() {
        Map<String, CpuSnapshot> snapshots = new HashMap<>();
        String content = readFileContent(PROC_STAT);
        if (content == null || content.isEmpty()) {
            // Fallback to privileged backend
            content = executePrivileged("cat " + PROC_STAT);
        }

        if (content != null) {
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.startsWith("cpu")) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 5) {
                        try {
                            String name = parts[0];
                            long user = Long.parseLong(parts[1]);
                            long nice = Long.parseLong(parts[2]);
                            long system = Long.parseLong(parts[3]);
                            long idle = Long.parseLong(parts[4]);
                            long iowait = parts.length > 5 ? Long.parseLong(parts[5]) : 0;
                            long irq = parts.length > 6 ? Long.parseLong(parts[6]) : 0;
                            long softirq = parts.length > 7 ? Long.parseLong(parts[7]) : 0;
                            long steal = parts.length > 8 ? Long.parseLong(parts[8]) : 0;

                            long total = user + nice + system + idle + iowait + irq + softirq + steal;
                            snapshots.put(name, new CpuSnapshot(total, idle + iowait));
                        } catch (NumberFormatException e) {
                            Log.w(TAG, "Failed to parse cpu line: " + line);
                        }
                    }
                }
            }
        }
        return snapshots;
    }

    private double[] readLoadAvg() {
        String content = readFileContent(PROC_LOADAVG);
        if (content == null || content.isEmpty()) {
            content = executePrivileged("cat " + PROC_LOADAVG);
        }

        if (content != null) {
            String[] parts = content.trim().split("\\s+");
            if (parts.length >= 3) {
                try {
                    return new double[]{
                        Double.parseDouble(parts[0]),
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2])
                    };
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Failed to parse loadavg: " + content);
                }
            }
        }
        return null;
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
