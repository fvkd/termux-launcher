# Technology Stack: Termux Launcher

## Core Platform
- **Android SDK:** Target API level compatible with modern Android devices (typically API 30+).
- **Gradle:** Multimodule build system for managing shared components and individual app features.

## Programming Languages
- **Kotlin:** Primary language for new feature development, launcher UI, and system integration.
- **Java:** Maintained for legacy Termux components, terminal emulation core, and shared libraries.

## System Integrations
- **Shizuku:** Provides a privileged execution environment for system APIs (notifications, media control, app management) without requiring root.
- **Tooie API:** A custom local-first API layer built to expose Shizuku-powered features to the launcher and terminal.

## Core Libraries
- **Termux Terminal Emulator:** C++ and Java-based terminal core for robust shell interaction.
- **Termux Terminal View:** Android-specific view components for rendering terminal sessions.
- **Termux Shared / AM Library:** Common utilities and Activity Manager abstractions for process handling.
- **Coroutines & Jetpack:** Modern Android architecture components for asynchronous tasks and UI lifecycle management.

## Development Tools
- **Android Studio / IntelliJ:** Recommended for IDE-based development and debugging.
- **Adb (Android Debug Bridge):** Primary tool for deployment, logcat analysis, and Shizuku troubleshooting.
