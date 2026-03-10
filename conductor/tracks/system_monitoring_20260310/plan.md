# Track Plan: System Resource Monitoring (The 'Tooie' Dashboard)

## Phase 1: Core Resource Parsing Logic
- [ ] Task: Implement parsing logic for CPU metrics from `/proc/stat` and `/proc/loadavg`.
- [ ] Task: Implement parsing logic for Memory metrics from `/proc/meminfo`.
- [ ] Task: Implement thermal zone discovery and parsing from `/sys/class/thermal`.
- [ ] Task: Write tests to verify the parsing accuracy using mock data.
- [ ] Task: Conductor - User Manual Verification 'Phase 1: Core Resource Parsing Logic' (Protocol in workflow.md)

## Phase 2: Dashboard Data Aggregation
- [ ] Task: Implement Battery status retrieval via Android's BatteryManager and intent filters.
- [ ] Task: Implement Storage statistics retrieval using Android's StatFs API.
- [ ] Task: Implement Network interface statistics parsing from `/proc/net/dev`.
- [ ] Task: Create a unified model to aggregate all system resource data.
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Dashboard Data Aggregation' (Protocol in workflow.md)

## Phase 3: Endpoint Integration and Refinement
- [ ] Task: Implement the `GET /v1/system/resources` endpoint in `TooieApiServer.java`.
- [ ] Task: Ensure the endpoint uses Shizuku-powered fallbacks for restricted proc/sys files.
- [ ] Task: Verify the JSON response structure matches the specification.
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Endpoint Integration and Refinement' (Protocol in workflow.md)
