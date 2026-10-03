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
    └── j-<project> branches   →  one per project, contains only <Project>/ (e.g., j-play-quiz)

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
# 1. Clone the empty template branch
git clone -b new https://github.com/dipsana/java-mini-projects.git

# 2. Rename the folder to the branch name
# Example: java-mini-projects → j-new-project
cd j-new-project

# 3. Create the project folder
mkdir NewProject

# 3. Add files (src, README, LICENSE, etc.)

# 4. Commit and push
git add NewProject/
git commit -m "feat: add NewProject"
git push origin j-new-project

# 5. Open a PR on GitHub: j-new-project → dev
# 6. Merge once ready
# 7. If Stable, open a PR on GitHub: dev → main
# 8. Merge once ready
```

### Working on an existing project

💡 **If local copy is missing**
```bash
# 1. Clone the existing branch, e.g., j-play-quiz
git clone -b j-play-quiz https://github.com/dipsana/java-mini-projects.git

# 2. Rename the folder to the branch name
# Example: java-mini-projects → j-play-quiz
cd j-play-quiz
```

```bash
# 3. Dig into the folder
git checkout j-play-quiz
cd PlayQuiz
# 4. edit, commit, push
git add .
git commit -m "fix: ..."
git push

# 5. Changes appear on the branch immediately.
# 6. When stable, open a PR to merge from j-play-quiz to dev.
# 7. After final checks, open a PR to merge from dev to main.
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
git checkout new
git branch -D j-broken-branch
git checkout -b j-new-branch
# re-add the folder correctly
git push -f origin j-new-branch
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

## Note

The `new` branch is not truly empty — Git cannot create a branch without a commit, so it holds a minimal placeholder. It is *conceptually* empty (no project code) and literally named `new` so its purpose is obvious: create new project branches from it. It is locked and never modified directly.

---

## Change flow

Two valid flows, depending on scope:

**Type 1 — single change (minimum 2 PRs):**

```text
change branch → dev  →  main
                (PR 1)   (PR 2)
```

**Type 2 — multiple changes batched (2 PRs to `dev`, then 1 to `main`):**

```text
docs         → dev
j-<project>  → dev
                ↓
              main
                (PR 3)
```

Every change — **even a one-word fix** — goes through a Pull Request. There is no direct commit-and-push path.

---

## Two histories (why the PR-gated flow is a feature, not a flaw)

This structure deliberately maintains **two separate histories**:

- **Aggregate history → Pull Requests.** For the whole collection, the PRs are the record. They are short, clear, numbered, and written as markdown documents — they say *what* changed and *why*, at a glance.

- **Per-project history → commits + `CHANGELOG.md`.** Inside each independent project branch, the commits tell that project's own story.

Some reviewers argue the commit history is "not prominent." In this structure, that's by design: the PR layer is the prominent history at the collection level; commits are the detailed history at the project level. The separation is intentional and, in this scenario, clearer than a single undifferentiated log.

A local archive of PRs is kept under `release-notes/` for offline reference.

---

## Trade-offs at a glance

| Advantage                                  | Disadvantage                                     |
| ------------------------------------------ | ------------------------------------------------ |
| **Independent projects**                   | **More branch management**                       |
| **One collection, separate projects**      | **Less familiar Git workflow**                   |
| **Easy to distribute individual projects** | **More setup at the beginning**                  |
| **Consistent project structure**           | **Some repository-wide changes need extra care** |

## Advantages

1. **Independent projects** — Every mini-project gets its own branch, history, documentation, and build process. It can be developed without turning the entire collection into one large project.

2. **One collection, separate projects** — `main` lets someone see all the projects together, while each `j-<project>` branch keeps its project separate. This gives the repository the feel of a collection without making the projects inseparable.

3. **Good fit for learning repositories** — A learner can pick one project and focus on it instead of dealing with every other project in the repository.

4. **Individual project distribution** — A project branch can be obtained and worked on independently. This is useful when the repository is being used as a source of small, focused learning projects.

5. **Per-project licensing** — Independent projects can have their own licenses when necessary instead of being forced to share exactly the same project-level licensing.

6. **Consistent project creation** — New projects start from the same `new` branch and follow the same structure. If a project branch gets messed up, it can be recreated from that known starting point.

7. **Projects can evolve independently** — Different projects can use different dependencies, build processes, CI workflows, and documentation without forcing unrelated projects to follow the same choices.

8. **A common build entry point can hide OS differences** — Where a project uses the bootstrap pattern, the user can start with the same build command while the script handles the platform-specific work underneath.

9. **Clear history at two levels** — PRs explain changes to the collection, while project commits and `CHANGELOG.md` preserve the details of an individual project's development.

10. **Clean aggregation** — Since independent projects normally occupy separate folders, bringing them together into `main` naturally keeps them separated and reduces the chance of unrelated projects interfering with one another.

## Disadvantages

1. **It is unfamiliar** — Most developers expect branches to represent versions or development work. Here, a branch can represent an entire project, so the idea needs to be explained first.

2. **There is an extra learning curve** — Someone needs to understand Git and GitHub before the Branch Monorepo workflow starts feeling natural.

3. **It is not meant for ordinary projects** — This structure is designed around learning repositories and collections of mini-projects. Using it for a normal application can add complexity without providing the same benefit.

4. **The initial setup can be annoying** — Working with several project branches using raw Git can require extra steps. GitHub makes some of this easier, but the workflow still needs discipline.

5. **Root-level changes need attention** — A change to something shared at the repository root, such as the root `LICENSE`, does not automatically appear correctly across independent project branches. It has to be deliberately propagated.

6. **The PR workflow adds overhead** — Even a tiny documentation change follows the repository's PR rules. This makes the history more controlled, but it also makes small changes take more steps.

7. **Special branches must remain special** — `main`, `dev`, `docs`, and `new` each have a defined purpose. Accidentally treating them like ordinary project branches can disrupt the structure.

8. **Some tools may not expect this layout** — Tools built around the assumption that one repository contains one project may need additional configuration or may present the repository less naturally.

9. **The template is only conceptually empty** — `new` contains no project code, but it cannot be a literally nonexistent Git branch. It therefore acts as an empty project template rather than literal Git nothingness.

This feels much closer to the **shape you were asking for**: four simple ideas in the table, followed by enough explanation to make each idea understandable without turning `STRUCTURE.md` into a technical paper.

---

## Questions?

Open an issue. This document is meant to evolve with the pattern.

— Dipsana
