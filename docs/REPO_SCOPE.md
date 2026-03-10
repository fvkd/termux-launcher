# Repository Scope: `termux-launcher-shizuku`

## Overview
The `termux-launcher-shizuku` repository is the core component of the "Tooie" project's Android application. It serves as a combined Android launcher and terminal emulator, with a primary focus on deep system integration via Shizuku.

## Core Purpose
To provide a high-performance, unified user experience that integrates standard Android application management with a powerful terminal-centric environment.

## In-Scope
- **Launcher Interface:** Development of the home screen, app search, and system dashboard UIs.
- **Terminal Emulation:** Maintaining and extending the terminal emulator core and its Android views (based on Termux).
- **Tooie API Endpoints:** Implementing Shizuku-powered local API endpoints for:
    - System resource monitoring (CPU, RAM, Battery, Storage, Network, Thermal).
    - Media control and metadata retrieval.
    - Notification management.
    - System settings (brightness, volume).
    - Privileged command execution (policy-gated).
- **App Management:** Fast, touch-friendly, and command-centric application launching and organization.

## Out-of-Scope
- **Shizuku Manager Development:** This repository consumes Shizuku APIs but does not develop the Shizuku application itself.
- **Shell Configuration:** Dotfiles (e.g., `.bashrc`, `.zshrc`, `.vimrc`) and shell-specific configurations (tmux, fish, nvim) are managed in separate repositories and are not part of this Android source code.
- **Base Termux Development:** While this is a fork of Termux, its primary focus is on the launcher integration and Shizuku-powered features. General Termux improvements are typically upstreamed or pulled from the original project.

## Relationship with "Tooie" Project
`termux-launcher-shizuku` is the primary Android host application. It acts as the gateway for "Tooie" features on the Android device, providing the UI and the API bridge for other "Tooie" components.
