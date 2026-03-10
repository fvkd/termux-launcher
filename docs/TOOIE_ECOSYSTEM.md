# Tooie Project Ecosystem: Repository Boundaries

The "Tooie" project is a multi-repo ecosystem designed for a modular and specialized developer environment on Android.

## Core Repositories

### 1. `termux-launcher-shizuku` (This Repository)
- **Role:** The primary Android Host Application.
- **Scope:** 
    - Full Android app source code (Java/Kotlin/C++).
    - Multi-module Gradle project (Launcher + Terminal core).
    - Tooie Local API (Shizuku-powered).
    - All launcher-specific visual assets (icons, screenshots).
- **Boundary:** This repo is strictly for the **binary components** and the **UI** of the Android app. It does NOT contain user-level shell configurations.

### 2. `tooie-shell-config` (External)
- **Role:** User-level Environment Configuration.
- **Scope:** 
    - Dotfiles for shells (`bash`, `fish`, `xonsh`).
    - Tool configurations (`nvim`, `tmux`, `zellij`).
    - Custom scripts for the Tooie environment.
- **Boundary:** These files are meant to be synced into the Termux home directory (`$HOME`) and are managed independently of the Android app lifecycle.

### 3. `tooie-automation` (External)
- **Role:** Cross-device and system automation.
- **Scope:** 
    - Tasker profiles and projects.
    - Automate (LlamaLab) flows.
    - Python-based automation for interacting with the Tooie Local API.
- **Boundary:** These are high-level automation artifacts that consume the APIs provided by the `termux-launcher-shizuku` app.

## Inter-Repo Links and Functionalities

| Repository | Primary Functionality | Provided Service/API | Consumed Service/API |
| --- | --- | --- | --- |
| `termux-launcher-shizuku` | Android UI & API Bridge | Tooie Local API (localhost) | Shizuku Manager API |
| `tooie-shell-config` | Shell & TUI Configuration | Shell Environment, Aliases | Tooie Local API (via `curl/python`) |
| `tooie-automation` | System-wide Automation | Automated Flows, Shortcuts | Tooie Local API, Android Intent API |

## Communication Channels

- **Local API:** The `termux-launcher-shizuku` app provides a REST-like API (Tooie Local API) at `localhost` that other scripts and apps (from `tooie-shell-config` or `tooie-automation`) can consume.
- **Shizuku:** Acts as the cross-process bridge between the `termux-launcher-shizuku` app and the Android system server, enabling privileged operations.
- **Environment Variables:** The app sets specific environment variables (e.g., `TOOIE_VERSION`, `TOOIE_API_ENDPOINT`) that are then utilized by the shell configurations.