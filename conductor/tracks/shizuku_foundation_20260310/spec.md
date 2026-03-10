# Track Specification: Shizuku Integration & API Foundation

## Goal
Establish the foundational Shizuku integration and the baseline `/v1/status` endpoint for the Tooie Local API. This ensures the launcher can reliably communicate with the Shizuku service and provide status information to the ecosystem.

## Background
The "Tooie" project relies on Shizuku for privileged Android system access. Before implementing advanced features like resource monitoring or app management, the app must have a robust mechanism to check Shizuku's availability, request permissions, and expose this status via a local API.

## Scope
- **Shizuku Handshake:** Implement logic to detect Shizuku service availability and version.
- **Permission Request:** Create a mechanism to request Shizuku permissions from the user.
- **Tooie API Core:** Set up the basic infrastructure for the local HTTP server (Tooie API).
- **Status Endpoint (`/v1/status`):** Implement an endpoint that returns the current status of the app, Shizuku integration, and system baseline.

## Acceptance Criteria
- [ ] App correctly detects if the Shizuku service is running.
- [ ] App can trigger the Shizuku permission request dialog.
- [ ] A local HTTP server is running and accessible at a designated port (e.g., 8080).
- [ ] `GET /v1/status` returns a JSON object containing:
    - `app_version`
    - `shizuku_available` (boolean)
    - `shizuku_permission_granted` (boolean)
    - `shizuku_version` (integer)
- [ ] Unit tests verify the status logic and API response format.
