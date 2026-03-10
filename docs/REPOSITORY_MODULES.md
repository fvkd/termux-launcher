# Repository Modules: `termux-launcher-shizuku`

The project is structured as a multi-module Gradle project to facilitate separation of concerns and reuse of core components.

## Core Modules

### 1. `app`
- **Purpose:** The main Android application entry point and launcher UI.
- **Responsibilities:**
    - Launcher home screen, app drawer, and search interface.
    - Shizuku-powered API services (Tooie API).
    - Managing terminal session lifecycles.
    - User settings and configuration.
- **Languages:** Kotlin, Java.

### 2. `terminal-emulator`
- **Purpose:** The core terminal emulation engine.
- **Responsibilities:**
    - Parsing ANSI/VT-100 escape sequences.
    - Managing pseudo-terminal (PTY) I/O.
    - Handling terminal sessions and process state.
- **Languages:** Java, C++.

### 3. `terminal-view`
- **Purpose:** The Android-specific View component for terminal rendering.
- **Responsibilities:**
    - Efficiently drawing terminal buffers.
    - Handling touch input and gestures.
    - Keyboard interaction and IME (Input Method Editor) integration.
- **Languages:** Java.

### 4. `native-entrypoint`
- **Purpose:** Low-level native execution support.
- **Responsibilities:**
    - Providing a bridge for native execution within the Android sandbox.
    - Managing privileged execution logic where necessary.
- **Languages:** Java, C/C++.

## Shared Libraries

### 5. `termux-shared`
- **Purpose:** Common utilities and shared logic across the Termux ecosystem.
- **Responsibilities:**
    - Configuration management and property constants.
    - File system utilities for the Termux environment.
    - Shared models and abstractions.
- **Languages:** Java.

### 6. `termux-am-library`
- **Purpose:** Activity Manager abstractions for process management.
- **Responsibilities:**
    - Providing a Java-friendly API for Android's `am` command features.
    - Facilitating inter-process communication and task management.
- **Languages:** Java.
