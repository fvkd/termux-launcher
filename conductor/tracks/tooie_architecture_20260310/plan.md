# Track Plan: Project Architecture & Ecosystem Documentation

## Phase 1: Project Scope and Repository Definition [checkpoint: 689329a]
- [x] Task: Document the repository's core purpose and scope in a new `docs/REPO_SCOPE.md` file. [a37434f]
- [x] Task: Identify and list all key modules within the `termux-launcher-shizuku` repository. [a37328e]
- [x] Task: Define the boundaries between this repository and other "Tooie" project repositories. [bff2202]
- [x] Task: Conductor - User Manual Verification 'Phase 1: Project Scope and Repository Definition' (Protocol in workflow.md)

## Phase 2: Dependency and Ecosystem Mapping [checkpoint: 47a2cdf]
- [x] Task: Create a dependency map of internal modules (`app`, `native-entrypoint`, `terminal-emulator`, `terminal-view`, `termux-am-library`, `termux-shared`). [9f460e6]
- [x] Task: Map the inter-repo links for the "Tooie" project, identifying which repositories provide specific functionalities. [9a04e69]
- [x] Task: Document the versioning or linking strategy used between these repositories (e.g., git submodules, Gradle dependencies, local builds). [da2acd1]
- [x] Task: Conductor - User Manual Verification 'Phase 2: Dependency and Ecosystem Mapping' (Protocol in workflow.md)

## Phase 3: Build and Deployment Documentation
- [x] Task: Define and document the correct build order for all "Tooie" repositories and their internal modules. [b0989b7]
- [x] Task: Document the deployment strategy, including APK generation for non-Shizuku and Shizuku-integration builds. [6f1cc48]
- [x] Task: Specify how Shizuku is used and the requirements for a successful deployment (e.g., Shizuku installation, permission granting). [96d0e11]
- [~] Task: Conductor - User Manual Verification 'Phase 3: Build and Deployment Documentation' (Protocol in workflow.md)
