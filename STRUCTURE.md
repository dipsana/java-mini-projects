# Repository Structure

This repository uses a **branch-per-project** pattern. Each project lives on its own branch as a self-contained folder.
The `main` branch is the aggregate. Works on union of disjoint sets concept.

If it's something new, I name this structure: `Branch Monorepo`.
This document explains the rules, the reasoning, and the recipes for working with the repository.

---

## The Pattern

    Repository: java-mini-projects
    │
    ├── dev branch             →  all project folders merged + root files (development)
    ├── main branch            →  all project folders merged + root files (stable)
    ├── empty branch           →  locked template (always empty)
    ├── docs branch            →  all documents related to aggregate branch
    ├── manager branch         →  future planned app manager
    └── j-<project> branches   →  one per project, contains only <Project>/

### Visual

    ┌───────────────────────────────────────────────┐
    │  main                                         │
    │  ├── LICENSE         (root)                   │
    │  ├── README.md       (root)                   │
    │  ├── STRUCTURE.md    (this file)              │
    │  ├── PlayQuiz/       ← project folder         │
    │  └── DemoProject/    ← project folder         │
    └───────────────────────────────────────────────┘

    ┌───────────────────────────────────────────────┐
    │  j-play-quiz                                  │
    │  └── PlayQuiz/                                │
    │      ├── LICENSE     (project's own)          │
    │      ├── README.md                            │
    │      ├── pom.xml                              │
    │      └── src/                                 │
    └───────────────────────────────────────────────┘

    ┌───────────────────────────────────────────────┐
    │  new    ← LOCKED — always empty               │
    └───────────────────────────────────────────────┘

---

## Rules

1. **Each project branch contains ONLY the project folder.**
   No root files, no shared README, no shared LICENSE. Just `<Project>/`.

2. **Shared files live on `main` only.**
   Root `LICENSE`, `README.md`, and `STRUCTURE.md` exist on `main`.
   They do not appear on project branches.

3. **Never edit `main` directly.**
   All changes come via pull requests from project branches.

4. **Never edit `new`.**
   It is the template for creating new branches. It stays empty, always.

5. **If a project branch gets polluted (wrong files, wrong structure),
   delete it and recreate from `new`.** Don't try to fix it manually.

6. **Licenses:** Each project carries its own `LICENSE` file inside its
   folder. The root `LICENSE` covers only the repo structure, not the code.

---

## Why This Pattern?

| Goal | How this pattern achieves it |
| :-- | :-- |
| **Fork only what you need** | Fork a project's branch → get only that project |
| **Independent lifecycles** | Each project lives on its own branch, merges into `main` when ready |
| **Clean merges** | Project branches only *add* a folder — no conflicting files |
| **Discoverable** | `main` shows every project as a folder |
| **Different licenses per project** | Each project's LICENSE is self-contained |
| **Small local clones** | Clone a specific branch to get only one project |

---

## Recipes

### Creating a new project

```bash
# 1. Start from the empty template
git checkout empty
git checkout -b j-new-project

# 2. Create the project folder
mkdir NewProject
cd NewProject

# 3. Add files (src, README, LICENSE, etc.)

# 4. Commit and push
cd ..
git add NewProject/
git commit -m "feat: add NewProject"
git push origin j-new-project

# 5. Open a PR on GitHub: j-new-project → main
# 6. Merge once ready
```

### Working on an existing project

```bash
git checkout j-play-quiz
cd PlayQuiz
# edit, commit, push
git add .
git commit -m "fix: ..."
git push

# Changes appear on the branch immediately.
# When stable, open a PR to merge into main.
```

### Forking a single project (GitHub UI)

1. Navigate to the project's branch (e.g., j-play-quiz)

2. Click the "Fork" button

3. You now own a copy of just that project

### Forking the whole repository

1. Stay on the main branch

2. Click "Fork"

3. You get everything — all branches, all projects

### Cloning locally (one project at a time)

```bash
git clone -b j-play-quiz https://github.com/dipsana/java-mini-projects.git j-play-quiz
cd j-play-quiz/PlayQuiz

# Later, for another project:
git clone -b j-demo-project https://github.com/dipsana/java-mini-projects.git j-demo-project
cd j-demo-project/DemoProject
```

### Recovering a polluted branch

```bash  
git checkout empty
git branch -D j-broken-branch
git checkout -b j-broken-branch
# re-add the folder correctly
git push -f origin j-broken-branch
```

---

## Naming Convention

    Language prefix:   j-    (Java)
    Branch name:       j-<project-kebab-case>     e.g., j-play-quiz
    Folder inside:     <Project-PascalCase>       e.g., PlayQuiz

Examples:

| Project name | Branch name    | Folder name  |
|--------------|----------------|--------------|
| PlayQuiz     | j-play-quiz    | PlayQuiz     |
| DemoProject  | j-demo-project | DemoProject  |

---

## Local Directory Layout

Each project is cloned into its own folder, checked out to its own branch:

    java-mini-projects\
    ├── j-play-quiz/         (git repo, branch: j-play-quiz)
    │   └── PlayQuiz/
    └── j-demo-project/      (git repo, branch: j-demo-project)
        └── DemoProject/

Each folder is an independent working copy. No branch switching required.

---

## Safety Rails

- **`new` is locked on GitHub.** Do not unlock it unless you're
  intentionally rebuilding the template.
- **Never force-push to `main`.**
- **Always PR before merging into `main`.**

---

## Questions?

Open an issue. This document is meant to evolve with the pattern.

— Dipsana
