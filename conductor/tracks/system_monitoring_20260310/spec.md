# Track Specification: System Resource Monitoring (The 'Tooie' Dashboard)

## Goal
Implement a robust and detailed system resource monitoring endpoint (`/v1/system/resources`) in the Tooie Local API. This endpoint will serve as the data source for the launcher's system dashboard, providing real-time metrics on CPU, Memory, Battery, Storage, Network, and Thermal states.

## Background
Power users require detailed insights into their device's performance. Many of these metrics (like per-core CPU usage or thermal zone data) are restricted in the standard Android sandbox. By leveraging Shizuku, the Tooie API can provide these metrics to shell scripts and launcher widgets.

## Scope
- **CPU Monitoring:** Expose per-core usage percentages and system load averages.
- **Memory Monitoring:** Provide detailed RAM usage (total, available, free, cached, active/inactive).
- **Battery Monitoring:** Detailed state (level, health, temperature, voltage, charging status).
- **Storage Monitoring:** Partition-level usage statistics.
- **Network Monitoring:** Per-interface rx/tx byte and packet counts.
- **Thermal Monitoring:** Temperature readings from all available system thermal zones.

## Acceptance Criteria
- [ ] `GET /v1/system/resources` returns a JSON object containing the specified metrics.
- [ ] CPU usage is calculated accurately over a sample window (e.g., 100ms).
- [ ] Memory metrics reflect the actual state from `/proc/meminfo`.
- [ ] Thermal data is correctly parsed from `/sys/class/thermal`.
- [ ] API handles cases where specific sensors or files are unavailable gracefully.
- [ ] Unit tests verify the parsing logic for `/proc` and `/sys` data.
