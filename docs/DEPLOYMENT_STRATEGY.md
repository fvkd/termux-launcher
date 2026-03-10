# Deployment Strategy: `termux-launcher-shizuku`

The project utilizes a continuous deployment strategy using GitHub Actions for APK generation and Shizuku for runtime privileged access.

## APK Generation

APKs are generated as build artifacts for every push to the main development branches.

### 1. Build Variants

| Variant | Branch | Purpose | Key Features |
| --- | --- | --- | --- |
| **Non-Shizuku** | `main` | Default terminal launcher experience. | Standard Termux features, launcher UX. |
| **Shizuku Integration** | `shizuku-integration` | Privileged, local-first ecosystem. | Tooie Local API, system monitoring, privileged execution. |

### 2. Automated Workflows
APKs are built using the `Build nightly` workflow (`.github/workflows/debug_build.yml`). 
- **Trigger:** Pushes to `main` and `shizuku-integration`.
- **Artifacts:** Debug APKs attached to the GitHub Action run.

## Deployment Process

### 1. Installation
1.  Download the desired APK artifact from GitHub Actions.
2.  Install the APK on the target Android device via `adb install` or manual installation.

### 2. Post-Installation (Shizuku Integration Only)
The `shizuku-integration` variant requires additional steps for full functionality:
1.  **Shizuku Manager:** Ensure the Shizuku Manager app is installed and running on the device.
2.  **Grant Permissions:** Upon first launch, the `termux-launcher-shizuku` app will request privileged access via the Shizuku manager. The user must explicitly grant this permission.
3.  **Local API Activation:** Once permission is granted, the Tooie Local API (localhost) will become active and available for other ecosystem components (like shell scripts).

## Shizuku Integration and Usage

### 1. Overview
Shizuku is used as the cross-process bridge between the `termux-launcher-shizuku` app and the Android system server. It allows the app to execute privileged commands and access system-level data (like media control, notifications, and hardware metrics) that would otherwise require root access or complex ADB workarounds.

### 2. Requirements for Successful Deployment
To ensure a successful deployment with Shizuku features:
- **Device Compatibility:** Works on unrooted Android devices (Android 6.0+).
- **Shizuku App:** Must be installed (available on F-Droid or Play Store).
- **Activation:** Shizuku service must be started on the device. This can be done via:
    - **Wireless Debugging (Android 11+):** The most common and convenient method.
    - **ADB (Android 6.0+):** Requires a computer and the command `adb shell sh /sdcard/Android/data/moe.shizuku.privileged.api/files/start.sh`.
    - **Root (Optional):** Shizuku can also run with root privileges.
- **App Permission:** The `termux-launcher-shizuku` app must be authorized within the Shizuku manager.

### 3. Usage within the Tooie API
The Tooie Local API exposes several Shizuku-powered endpoints. For example:
- **`/v1/privileged/request-permission`:** Triggers the Shizuku permission dialog.
- **`/v1/system/resources`:** Uses Shizuku to gather low-level hardware metrics (e.g., CPU load per core, battery thermal state).
- **`/v1/exec`:** Uses Shizuku to execute system commands that require higher privileges than a standard Android app.

## Summary

1.  **Developer Pushes Code:** Triggers GitHub Action.
2.  **GitHub Action Builds APK:** Generates non-Shizuku and Shizuku variants.
3.  **Download & Install:** Install APK on device.
4.  **(Optional) Shizuku Setup:** Activate Shizuku and grant permissions.
5.  **Environment Sync:** Sync shell configurations (`tooie-shell-config`) into the Termux `$HOME`.