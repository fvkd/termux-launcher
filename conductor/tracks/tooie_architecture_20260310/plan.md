# Track Plan: Project Architecture & Ecosystem Documentation

## Phase 1: Project Scope and Repository Definition
- [x] Task: Document the repository's core purpose and scope in a new `docs/REPO_SCOPE.md` file. [a37434f]
- [x] Task: Identify and list all key modules within the `termux-launcher-shizuku` repository. [a37328e]
- [x] Task: Define the boundaries between this repository and other "Tooie" project repositories. [bff2202]
- [~] Task: Conductor - User Manual Verification 'Phase 1: Project Scope and Repository Definition' (Protocol in workflow.md)

## Phase 2: Dependency and Ecosystem Mapping
- [ ] Task: Create a dependency map of internal modules (`app`, `native-entrypoint`, `terminal-emulator`, `terminal-view`, `termux-am-library`, `termux-shared`).
- [ ] Task: Map the inter-repo links for the "Tooie" project, identifying which repositories provide specific functionalities.
- [ ] Task: Document the versioning or linking strategy used between these repositories (e.g., git submodules, Gradle dependencies, local builds).
- [ ] Task: Conductor - User Manual Verification 'Phase 2: Dependency and Ecosystem Mapping' (Protocol in workflow.md)

## Phase 3: Build and Deployment Documentation
- [ ] Task: Define and document the correct build order for all "Tooie" repositories and their internal modules.
- [ ] Task: Document the deployment strategy, including APK generation for non-Shizuku and Shizuku-integration builds.
- [ ] Task: Specify how Shizuku is used and the requirements for a successful deployment (e.g., Shizuku installation, permission granting).
- [ ] Task: Conductor - User Manual Verification 'Phase 3: Build and Deployment Documentation' (Protocol in workflow.md)
