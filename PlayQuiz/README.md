# PlayQuiz

A 78-question console Java quiz with smart input handling, a friendly personality, and cross-platform builds — built for fun, learning, and sharing.

[![Java](https://img.shields.io/badge/Java-21_LTS-blue?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/) [![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE) [![Platform](https://img.shields.io/badge/Platform-Windows_%7C_macOS_%7C_Linux-lightgrey)](https://github.com/dipsana/java-mini-projects/tree/j-play-quiz)

---

## What is this?

PlayQuiz is a terminal-based quiz app with 78 questions on Java fundamentals. It's not just a quiz — it's a small demonstration of **smart input handling** and **cross-platform Java packaging**.

Highlights:

- **Accepts almost any input format** — `c`, `3`, `option c`, `third`, `1st`, `BBBBBBBBBB`
- **Skip any question** by pressing Enter
- **Graceful Ctrl+C / EOF handling** — no crashes, no stack traces
- **Friendly messages** with emoticons (`@''@`, `@(> ^ <)@`, `@^^@`)
- **Scoring + percentage** with dynamic feedback messages
- **Replay loop** — play as many times as you want
- **Cross-platform builds** — `.exe`, `.dmg`, `.deb`, `.jar`

---

## Download & Run

### Option 1 — Pre-built executables (recommended)

Download the latest release for your OS:

| Platform | Download |
| --- | --- |
| **Windows** | [PlayQuiz-1.0.0.exe](https://github.com/dipsana/java-mini-projects/releases/download/v1.0.0/PlayQuiz-windows-latest.zip) |
| **macOS** | [PlayQuiz-1.0.0.dmg](https://github.com/dipsana/java-mini-projects/releases/download/v1.0.0/PlayQuiz-macos-latest.zip) |
| **Linux** | [PlayQuiz-1.0.0.deb](https://github.com/dipsana/java-mini-projects/releases/download/v1.0.0/PlayQuiz-ubuntu-latest.zip) |
| **Any OS (with Java 21+)** | [play-quiz-1.0.0.jar](https://github.com/dipsana/java-mini-projects/releases/download/v1.0.0/play-quiz-1.0.0.jar) |

> Check the [Releases page](https://github.com/dipsana/java-mini-projects/releases/latest) for the latest version.

### Option 2 — Build from source (the fun way)

This is the recommended path if you want to **understand how the app is built** or modify it.

#### Prerequisites

You need:

- **Java 21 LTS or higher** — [Download](https://adoptium.net/)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- **PowerShell 7+** — [Download](https://github.com/PowerShell/PowerShell)

> The bootstrap scripts will **install Java and Maven for you** if they're
> missing. So technically, the only hard requirement is PowerShell.

#### Build

> Windows user, must run elevated powershell (as admin) to make the build work.

1. Clone the repo:

    ```bash
    git clone -b j-play-quiz https://github.com/dipsana/java-mini-projects.git j-play-quiz
    cd j-play-quiz/PlayQuiz
    ```

2. Run the build dispatcher:

    ```bash
    ./build.ps1
    ```

3. On Windows, if PowerShell blocks the script:

    ```ps1
    Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
    ./build.ps1
    ```

That's it. The script will:

1. Detect your OS
2. Install Java 21 + Maven (if missing) via:
   - **Windows** → Chocolatey
   - **macOS / Linux** → SDKMAN
3. Build the JAR
4. Package a native app with `jpackage`
5. Drop the result in `installer/`

#### Verify the build

```bash
# Windows
./installer/PlayQuiz/PlayQuiz.exe

# macOS
open installer/PlayQuiz.app

# Linux
./installer/PlayQuiz/bin/PlayQuiz
```

---

## Running the Bootstrap and Build Independently

The `build.ps1` at the root is a **dispatcher** — it detects your OS and runs the right native script. But you can also run the bootstrap and build **separately** if you prefer.

### Bootstrap only (install dependencies)

```bash
# Windows — uses Chocolatey
./scripts/bootstrap-windows.ps1

# macOS / Linux — uses SDKMAN
bash scripts/bootstrap-unix.sh
```

The bootstrap scripts are **idempotent** — safe to run multiple times. They check what's already installed before installing anything.

### Build only (assumes dependencies are installed)

```bash
# Windows
./build.ps1

# macOS / Linux
bash scripts/bootstrap-unix.sh   # this includes the build
```

> **Why a dispatcher?**
>`build.ps1` is an **elevated PowerShell script** that solves the "which script do I run?" hassle on all platforms.
> One command, any OS.

### Elevated PowerShell — the dispatcher

On Windows, `build.ps1` will **self-elevate** if it's not running as Administrator. It opens a UAC prompt, then continues in an elevated window. This is required for Chocolatey to install Java and Maven system-wide.

On macOS and Linux, the script calls the appropriate bootstrap `.sh` script. These don't need elevation — SDKMAN installs everything into your home directory.

---

## What's Inside

### Folder structure

    PlayQuiz/
    ├── assets/
    │   ├── icon.ico          # Windows icon
    │   └── icon.png          # Linux icon
    ├── docs/
    │   ├── SRS.pdf           # Software Requirements Specification
    │   ├── SRS.docx
    │   └── test-log.txt      # Full test run (78 questions)
    ├── scripts/
    │   ├── bootstrap-windows.ps1
    │   └── bootstrap-unix.sh
    ├── src/
    │   ├── main/java/com/dipsana/quiz/
    │   │   ├── Main.java
    │   │   ├── model/Question.java
    │   │   ├── service/QuestionService.java
    │   │   └── ui/QuizApp.java
    │   └── test/java/        # (empty — for future tests)
    ├── .gitattributes
    ├── .gitignore
    ├── build.ps1             # ← run this
    ├── pom.xml
    ├── CHANGELOG.md
    ├── LICENSE
    └── README.md

### How the app works

The quiz uses a small set of **global helper methods** to keep the code clean and the UX smooth:

**`safeInput()`** — strips 1000 character (resistant to memory failure), handles newlines, EOF, and Ctrl+C gracefully
**`clearInput()`** — flushes the input buffer between reads (prevents animation cut-off)

    Terminal
        ↓
    System.in
        ↓
    BufferedReader
        ↓
    PlayQuiz

The **welcome screen** is an animated sequence of words, printed one at a time with a short delay.
It's intentionally playful — a "hello" from the app before the questions begin.

For each word: pause 500ms, clear any pending input, print the word.
This prevents users from accidentally skipping the intro by typing ahead.

---

## Input Handling — The Fun Part

PlayQuiz accepts **many formats** for each answer. Here are real examples from the test log:

| You type | It resolves to |
| --- | --- |
| `c` | Option C |
| `C` | Option C |
| `3` | Option C |
| `third` | Option C |
| `opt 3` | Option C |
| `option c` | Option C |
| `o p t i o n    c` | Option C (whitespace stripped) |
| `BBBBBBBBBB` | Option B (extra chars ignored) |
| `1st` | Option A |
| `one` | Option A |
| `int` | Matched against answer text |
| `char c = "a"` | Matched against answer text |
| *(empty)* | Skip question |

### How it works

Exact match against option text first, then a normalization step (lowercase, regex), then case-insensitive matching.
See `ui/QuizApp.java` for details.

### Why?

Because quizzes should be **forgiving**.
The goal is learning, not testing your ability to type exactly right.
As long as the intent is clear, the app accepts it.

---

## Scoring System

The system shall display a feedback message based on the final percentage:

### 90% and above

"Brilliant! You aced it!"

### 50% to below 90%

"Nice!"

### 25% to below 50%

"Well tried!"

### Below 25%

"You need to study harder!"

---

## Post-Build — Moving the App Anywhere

After a successful build, you can copy the `installer/PlayQuiz` folder anywhere and run the `.exe` / `.app` / binary.
It's self-contained — includes a bundled JVM, so no Java needed on the target machine.

Quick copy-to-Desktop (Windows):

```ps1
Copy-Item "installer\PlayQuiz" -Destination "$env:USERPROFILE\Desktop\Quiz-$(Get-Random)" -Recurse
```

This copies the whole built app to your Desktop with a random suffix — handy for testing or for clearing the Windows icon cache.

---

## Cross-Platform Notes

PlayQuiz builds natively on **Windows**, **macOS**, and **Linux** using `jpackage`.
GitHub Actions runs all three builds automatically on each tagged release.

| Platform | Build tool | Output |
| --- | --- | --- |
| Windows | `jpackage --type app-image` | `installer/PlayQuiz/PlayQuiz.exe` |
| macOS | `jpackage --type dmg` | `installer/PlayQuiz-1.0.0.dmg` |
| Linux | `jpackage --type deb` | `installer/playquiz_1.0.0_amd64.deb` |

### Windows SmartScreen Warning

When you first run `PlayQuiz.exe`, Windows may show:

> "Windows protected your PC"

This is normal for **unsigned** open-source software.
Click **More info** → **Run anyway**.
The app is safe — and you can verify by reading the source in this repo, or by building it yourself.

### macOS Gatekeeper

macOS may block the app the first time.
Right-click the app → **Open** → **Open anyway**.
This is the same signing caveat as Windows.

> **Note:** macOS and Linux builds are written but **untested by the author**
> (I only have Windows). If you hit an issue, please open an issue and
> we'll fix it together.

---

## Credits

This project is a collaboration of curiosity, code, and AI-assisted building.

| Contribution | By |
| --- | --- |
| Original Java code for PlayQuiz, repository structure, concept, testing, review, design decisions | **Dipsana** ([@dipsana](https://github.com/dipsana)) |
| Documentation assistance, bootstrap & build scripts (vibe coded), cross-platform design, `jpackage` pipeline | **DeepSeek** |
| SRS documentation assistance | **Claude** |
| App icon image generation | **ChatGPT** |
| Regex assistance and brainstorming Buffered Reader | **Gemini** |
| App icon resizing (`.ico` / `.png`) | **GIMP** |

**Made with curiosity, coffee, and a lot of terminal output.** ☕

---

## License

MIT License — see [`LICENSE`](LICENSE) for details.

You're free to use, modify, and distribute this project. Attribution appreciated.

---

## Links

- **Repository:** [java-mini-projects](https://github.com/dipsana/java-mini-projects)
- **Releases:** [Latest release](https://github.com/dipsana/java-mini-projects/releases/latest)
- **All releases:** [Releases page](https://github.com/dipsana/java-mini-projects/releases)
- **Branch:** [`j-play-quiz`](https://github.com/dipsana/java-mini-projects/tree/j-play-quiz)
- **Structure rules:** [STRUCTURE.md](https://github.com/dipsana/java-mini-projects/blob/main/STRUCTURE.md)
- **Root README:** [README.md](https://github.com/dipsana/java-mini-projects/blob/main/README.md)
- **Root LICENSE:** [LICENSE](https://github.com/dipsana/java-mini-projects/blob/main/LICENSE)

---

## Feedback

Found a bug? Have a question? Want to suggest a question for the quiz?

Open an [issue](https://github.com/dipsana/java-mini-projects/issues) or
start a [discussion](https://github.com/dipsana/java-mini-projects/discussions).

Enjoy the quiz.

— Dipsana
