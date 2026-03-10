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

## Versioning Strategy

- **Nightly Builds:** Development builds are automatically versioned with the commit hash and timestamp.
- **Releases:** Formal releases are tagged in git and use a semantic versioning scheme (e.g., `v1.2.0`).

## Deployment Summary

1.  **Developer Pushes Code:** Triggers GitHub Action.
2.  **GitHub Action Builds APK:** Generates non-Shizuku and Shizuku variants.
3.  **Download & Install:** Install APK on device.
4.  **(Optional) Shizuku Setup:** Activate Shizuku and grant permissions.
5.  **Environment Sync:** Sync shell configurations (`tooie-shell-config`) into the Termux `$HOME`.
