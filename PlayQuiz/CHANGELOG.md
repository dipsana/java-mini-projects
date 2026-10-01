# Changelog

All notable changes to PlayQuiz will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## \[1.0.0] — Initial Base Release

📅 *Release Date: 2026-10-01*

### Added

- Initial release of PlayQuiz
- 78-question quiz covering Java fundamentals
- Smart input handling — accepts many formats (`c`, `3`, `option c`, `third`, `1st`, etc.)
- Skip any question by pressing Enter
- Graceful Ctrl+C / EOF handling with the message *"Calm down! Shutting down gracefully..."*
- Scoring with percentage and dynamic feedback messages
- Replay loop — play as many times as you want
- Welcome animation with word-by-word reveal and buffered input handling
- Cross-platform build scripts:
  - `build.ps1` — OS-detecting dispatcher
  - `scripts/bootstrap-windows.ps1` — Windows bootstrap via Chocolatey
  - `scripts/bootstrap-unix.sh` — macOS/Linux bootstrap via SDKMAN
- Custom app icon (astronaut + coffee, hand-drawn style)
- MIT License
- Idempotent bootstrap scripts (safe to run multiple times)

### Notes

- macOS and Linux builds are written but **untested by the author**.
  Feedback welcome.
- Windows builds may show a SmartScreen warning (unsigned). See README.
