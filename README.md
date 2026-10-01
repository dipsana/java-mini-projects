# Java Mini Projects

A collection of mini Java projects — each on its own branch, fork-able independently.

---

## What is this?

This repository is a **collection of small, self-contained Java projects**.
Every project lives on its **own branch** as a **single folder**.
The `main` branch is the aggregate — it contains all projects side by side.

**Why this structure?**

- **Fork only what you need** — browse to a project branch, fork it, done.
- **Work independently** — each project has its own branch, its own lifecycle.
- **Clean merges** — project branches only add a folder, so merges never conflict.
- **Discoverable** — `main` shows everything at a glance.

Full rules are in [`STRUCTURE.md`](STRUCTURE.md).

---

## Projects

| Project | Branch | Description | Language |
| :-- | :-- | :-- | :-- |
| **PlayQuiz** | [`j-play-quiz`](https://github.com/dipsana/java-mini-projects/tree/j-play-quiz) | A 78-question console Java quiz with smart input handling | Java 21 |

More coming soon.

---

## How to Use

### Fork an entire collection

Fork the `main` branch — you get all projects.

### Fork a single project

1. Switch to the project's branch (e.g., `j-play-quiz`)
2. Click **Fork** on GitHub
3. You now have only that project — no extra baggage

### Clone locally

**Option A — Clone the whole repo:**

```bash
git clone https://github.com/dipsana/java-mini-projects.git
cd java-mini-projects
git checkout j-play-quiz       # switch to the project you want
```

**Option B — Clone only one project:**

```bash
git clone -b j-play-quiz https://github.com/dipsana/java-mini-projects.git j-play-quiz
cd j-play-quiz
cd PlayQuiz
```

### Build a project

Each project has its own `README.md` and documents with build instructions. For example:

```bash
cd PlayQuiz
# Follow the instructions in PlayQuiz/README.md
```

---

## Structure at a Glance

    java-mini-projects/
    │
    ├── .github  
    │   └── workflows/build-playquiz.yml  →  github action to build pre-built executables
    │
    ├── main branch           →  all projects as folders
    │   ├── LICENSE              (this file's sibling — root license)
    │   ├── README.md            (you are here)
    │   ├── STRUCTURE.md         (rules of the pattern)
    │   ├── PlayQuiz/
    │   └── ...
    │
    ├── empty branch (new)    →  locked template (empty)
    │
    ├── j-play-quiz branch    →  PlayQuiz/
    │
    └── j-<project> branches  →  one per project

---

## Licensing

- **Root [`LICENSE`](LICENSE)** — covers the repository structure, README, and project manager (when added).
- **Each project's `LICENSE`** — covers that project's code. They may differ.

See the `LICENSE` file inside each project for its specific terms.

---

## Contributing

This is primarily a personal learning collection, but feedback is welcome.

- Found a bug in a project? Open an issue on that project's branch.
- Have a suggestion? Start a discussion.
- Want to use the pattern for your own repo? Fork freely.

---

## Acknowledgment

The branch-per-project structure was designed as a modular alternative to traditional monorepos.
It trades a bit of familiarity for cleaner forks, independent project lifecycles, and conflict-free merges.

Enjoy exploring.

— Dipsana
