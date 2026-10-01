# Changelog

All notable changes to the **java-mini-projects** collection will be documented in this file.

Each project within this repository may also have its own `CHANGELOG.md` inside its folder — see the project's own README for details.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## \[1.0.0] — Initial Base Release

📅 *Release Date: 2026-10-01*

### Added

- **Repository structure** — branch-per-project pattern
  - `main` branch: aggregate of all projects
  - `new` branch: locked empty template for creating new projects
  - `j-<project>` branches: one per project, containing only that project's folder
- **Root documentation:**
  - `README.md` — collection overview
  - `STRUCTURE.md` — branch-per-project rules and recipes
  - `LICENSE` — MIT (covers repo structure, README, project manager)
  - `CHANGELOG.md` — this file
- **First project: PlayQuiz** on branch [`j-play-quiz`](https://github.com/dipsana/java-mini-projects/tree/j-play-quiz)
  - 78-question Java fundamentals quiz
  - Smart input handling — accepts many formats
  - Skip with Enter, graceful Ctrl+C / EOF handling
  - Scoring, percentage, replay loop
  - Friendly personality with emoticons
  - Cross-platform build pipeline (Windows / macOS / Linux)
  - Custom astronaut + coffee icon
  - Its own `LICENSE` (MIT) inside the project folder

### Notes

- macOS and Linux builds are written but untested by the author.
- Windows builds may show a SmartScreen warning (unsigned).
