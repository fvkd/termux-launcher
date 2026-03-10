# Product Guidelines: Termux Launcher

## Visual Identity & Aesthetic
- **Terminal-First Design:** The UI should embrace a "terminal emulator" aesthetic, utilizing monospaced fonts, dark backgrounds, and high-contrast accent colors.
- **TUI/GUI Hybridization:** Aim for a seamless blend where Terminal User Interface (TUI) widgets and traditional Android GUI elements coexist harmoniously.
- **Minimalist Overlays:** System information (CPU, RAM, Battery) should be displayed using clean, ASCII-style progress bars or compact text-based layouts.

## User Experience (UX) Principles
- **Software Keyboard Optimization:** Focus on fast, touch-friendly interactions that account for software keyboard layouts. Prioritize auto-completion, gesture-based shortcuts, and clear, reachable input fields.
- **Command-Centric Navigation:** Enable users to perform common launcher tasks (opening apps, toggling system settings) via a global command bar or terminal-style aliases.
- **Low-Latency Interactions:** Ensure the UI remains responsive even when multiple terminal sessions or privileged API calls are active in the background.
- **Contextual Transparency:** Provide terminal-style feedback for system events (e.g., app installations, Shizuku permission grants) to maintain the "everything is a process" philosophy.

## Design Constraints
- **Battery Efficiency:** Avoid heavy animations or translucent blurs that significantly impact battery life on mobile devices.
- **Accessibility:** Ensure that while the aesthetic is terminal-centric, font sizes and touch targets remain accessible and adjustable for all users.
