# Linking and Versioning Strategy: `tooie` Project

The "Tooie" project employs a hybrid linking strategy to balance local development efficiency with modular repository management.

## Internal Linking (Within `termux-launcher-shizuku`)

All modules in this repository (`app`, `terminal-emulator`, `terminal-view`, etc.) are linked using **Gradle Project References**.

- **Mechanism:** Defined in `settings.gradle` using the `include` command.
- **Consumption:** Modules depend on each other in their respective `build.gradle` files via `implementation project(':module-name')`.
- **Versioning:** These modules share the same versioning lifecycle as the `termux-launcher-shizuku` app. Changes to any module are committed within this single repository.

## External Linking (Between Repositories)

The connection between `termux-launcher-shizuku`, `tooie-shell-config`, and `tooie-automation` is primarily **Loose Coupling via API and Git**.

### 1. Independent Git Repositories
- **Strategy:** Each repository is managed independently. They are NOT linked via git submodules or subtrees.
- **Rationale:** Allows for independent versioning, branching, and release cycles for the app, the shell configuration, and the automation scripts.

### 2. Runtime API Integration
- **Mechanism:** The primary link between repositories is the **Tooie Local API**.
- **Consumption:** `tooie-shell-config` (via shell scripts/Python) and `tooie-automation` (via Tasker/Automate) consume the API exposed by the `termux-launcher-shizuku` app at `localhost`.
- **Versioning Strategy:** The Local API follows a semantic versioning approach (e.g., `/v1/`). This ensures that shell configurations and automations remain compatible with older versions of the app as the project evolves.

### 3. Build-Time Integration (CI/CD)
- **Mechanism:** GitHub Actions workflows are used to trigger builds and generate artifacts independently.
- **Cross-Repo Triggers:** (Optional/Future) Using repository dispatch events to trigger builds in dependent repositories when a core component changes.

## Summary

| Link Type | Strategy | Repositories/Modules | Versioning |
| --- | --- | --- | --- |
| Internal | Gradle `project()` | `app`, `termux-shared`, etc. | Monolithic (within repo) |
| External | API-based (Localhost) | `app` <-> `shell-config` | Semantic API versioning |
| External | Workflow-based | `app` <-> `automation` | Independent |
