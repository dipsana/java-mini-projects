#!/bin/bash
# scripts/bootstrap-unix.sh
#
# macOS/Linux setup + build: installs Java 21 (LTS) and Maven via SDKMAN,
# then compiles the JAR and packages a native app with jpackage.
#
# Works on: macOS, Linux, WSL.
# Idempotent — safe to run multiple times. Checks before installing.
# No sudo required — everything goes to ~/.sdkman/

# --- Colors ---
CYAN='\033[0;36m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
RED='\033[0;31m'
NC='\033[0m'  # No Color

echo -e "${CYAN}"
echo "=========================================="
echo "   PlayQuiz Setup + Build (macOS/Linux)"
echo "=========================================="
echo -e "${NC}"

# --- Helper: check if a command exists ---
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# --- Helper: get installed Java major version (0 if not installed) ---
get_java_major_version() {
    if ! command_exists java; then
        echo 0
        return
    fi
    local v
    v=$(java -version 2>&1 | head -n 1 | sed -E 's/.*version "?([0-9]+).*/\1/')
    if [[ "$v" =~ ^[0-9]+$ ]]; then
        echo "$v"
    else
        echo 0
    fi
}

# --- 1. Verify curl is available ---
if ! command_exists curl; then
    echo -e "${RED}ERROR: curl is not installed.${NC}"
    echo -e "${YELLOW}Install it and re-run:${NC}"
    echo "  macOS:  curl is pre-installed"
    echo "  Linux:  sudo apt install curl   (or your distro's equivalent)"
    exit 1
fi

# --- 2. Check / install SDKMAN ---
SDKMAN_DIR="$HOME/.sdkman"
if [ -d "$SDKMAN_DIR" ] && [ -s "$SDKMAN_DIR/bin/sdkman-init.sh" ]; then
    echo -e "${GREEN}SDKMAN already installed. Skipping.${NC}"
else
    echo -e "${YELLOW}SDKMAN not found. Installing...${NC}"
    if curl -s "https://get.sdkman.io" | bash; then
        echo -e "${GREEN}SDKMAN installed successfully.${NC}"
    else
        echo -e "${RED}ERROR: Failed to install SDKMAN.${NC}"
        echo "Check your internet connection and try again."
        exit 1
    fi
fi

# --- 3. Source SDKMAN so 'sdk' works in this session ---
export SDKMAN_DIR="$HOME/.sdkman"
# shellcheck disable=SC1091
source "$SDKMAN_DIR/bin/sdkman-init.sh"

if ! command_exists sdk; then
    echo -e "${RED}ERROR: 'sdk' command not available after sourcing SDKMAN.${NC}"
    echo "Open a new terminal and re-run this script."
    exit 1
fi

# --- 4. Check / install Java 21 (LTS) ---
REQUIRED_JAVA_VERSION=21
CURRENT_JAVA_VERSION=$(get_java_major_version)

if [ "$CURRENT_JAVA_VERSION" -ge "$REQUIRED_JAVA_VERSION" ]; then
    echo -e "${GREEN}Java $CURRENT_JAVA_VERSION already installed (>= $REQUIRED_JAVA_VERSION). Skipping.${NC}"
else
    if [ "$CURRENT_JAVA_VERSION" -gt 0 ]; then
        echo -e "${YELLOW}Java $CURRENT_JAVA_VERSION found — installing Java $REQUIRED_JAVA_VERSION (LTS)...${NC}"
    else
        echo -e "${YELLOW}Java not found. Installing Java $REQUIRED_JAVA_VERSION (Temurin)...${NC}"
    fi
    if sdk install java 21-tem; then
        echo -e "${GREEN}Java installed.${NC}"
    else
        echo -e "${RED}ERROR: Failed to install Java via SDKMAN.${NC}"
        echo "Try: sdk list java   (to see available versions)"
        exit 1
    fi
fi

# --- 5. Check / install Maven ---
if command_exists mvn; then
    MVN_VER=$(mvn --version 2>&1 | head -n 1)
    echo -e "${GREEN}Maven already installed ($MVN_VER). Skipping.${NC}"
else
    echo -e "${YELLOW}Maven not found. Installing...${NC}"
    if sdk install maven; then
        echo -e "${GREEN}Maven installed.${NC}"
    else
        echo -e "${RED}ERROR: Failed to install Maven via SDKMAN.${NC}"
        exit 1
    fi
fi

# --- 6. Setup report ---
echo -e "${CYAN}\n--- Setup Report ---${NC}"

FINAL_JAVA_VERSION=$(get_java_major_version)
if [ "$FINAL_JAVA_VERSION" -ge "$REQUIRED_JAVA_VERSION" ]; then
    echo -e "${GREEN}Java:  v$FINAL_JAVA_VERSION  OK${NC}"
else
    echo -e "${YELLOW}Java:  NOT VERIFIED (restart terminal)${NC}"
fi

if command_exists mvn; then
    echo -e "${GREEN}Maven: installed  OK${NC}"
else
    echo -e "${YELLOW}Maven: NOT VERIFIED (restart terminal)${NC}"
fi

# --- 7. Build phase ---
echo -e "${CYAN}\n--- Build ---${NC}"

# jpackage output type:
#   app-image  — works everywhere, no extra tools (folder with launcher)
#   dmg / pkg  — macOS only
#   deb / rpm  — Linux only (needs dpkg/fakeroot or rpm-build)
OS_TYPE=$(uname -s)
case "$OS_TYPE" in
    Darwin) JPACKAGE_TYPE="app-image" ;;
    Linux)  JPACKAGE_TYPE="app-image" ;;
    *)
        echo -e "${RED}ERROR: Unsupported Unix OS: $OS_TYPE${NC}"
        exit 1
        ;;
esac

# --- 8. Verify build prerequisites ---
if ! command_exists java; then
    echo -e "${RED}ERROR: Java not found on PATH.${NC}"
    echo -e "${YELLOW}Restart your terminal and re-run this script.${NC}"
    exit 1
fi

if ! command_exists mvn; then
    echo -e "${RED}ERROR: Maven not found on PATH.${NC}"
    echo -e "${YELLOW}Restart your terminal and re-run this script.${NC}"
    exit 1
fi

if ! command_exists jpackage; then
    echo -e "${RED}ERROR: jpackage not found. Is a JDK installed (not just a JRE)?${NC}"
    exit 1
fi

echo -e "${GREEN}Java:     $(java -version 2>&1 | head -n 1)${NC}"
echo -e "${GREEN}Maven:    $(mvn --version 2>&1 | head -n 1)${NC}"
echo ""

# --- 9. Confirm working directory ---
if [ ! -f "pom.xml" ]; then
    echo -e "${RED}ERROR: There is no file called pom.xml.${NC}"
    echo "If you accidentally deleted it, bring it back from the repository."
    echo -e "${YELLOW}Don't move things around — just clone the repository as it is.${NC}"
    echo "Run this script from the project root: play-quiz-j/ (the base folder, okay?)"
    echo "Current directory: $(pwd)"
    exit 1
fi

# --- 10. Prompt before deleting old installer/ folder ---
if [ -d "installer" ]; then
    echo -e "${YELLOW}The 'installer' folder already exists.${NC}"
    echo -e "${YELLOW}If you didn't create it, or if you're running this again, just hit Y.${NC}"
    read -p "Delete it and rebuild? (Y/N): " response
    if [[ "$response" =~ ^[Yy] ]]; then
        echo -e "${YELLOW}Deleting installer/ ...${NC}"
        rm -rf installer
        echo -e "${GREEN}Deleted.${NC}"
    else
        echo -e "${YELLOW}Aborted. installer/ left untouched.${NC}"
        exit 0
    fi
fi

# --- 11. Build the JAR with Maven ---
echo -e "${CYAN}\nBuilding JAR with Maven...${NC}"
if ! mvn clean package; then
    echo -e "${RED}ERROR: Maven build failed.${NC}"
    echo -e "${YELLOW}Don't give up hope. Try running this script again...${NC}"
    echo -e "${YELLOW}You can also run manually: mvn clean package${NC}"
    exit 1
fi
echo -e "${GREEN}JAR built successfully.${NC}"

# --- 12. Locate the built JAR ---
JAR_PATH=$(find target -maxdepth 1 -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" | head -n 1)
if [ -z "$JAR_PATH" ]; then
    echo -e "${RED}ERROR: No JAR found in target/ after build.${NC}"
    echo -e "${YELLOW}Captain, you're on the second last step! Don't touch anything.${NC}"
    echo "Delete the target/ folder and re-run this script."
    exit 1
fi
JAR_NAME=$(basename "$JAR_PATH")
echo -e "${GREEN}Found JAR: $JAR_NAME${NC}"

# --- 13. Package with jpackage ---
echo -e "${CYAN}\nPackaging with jpackage (--type $JPACKAGE_TYPE)...${NC}"

jpackage \
    --type "$JPACKAGE_TYPE" \
    --name "PlayQuiz" \
    --app-version "1.0.0" \
    --input target \
    --icon "assets/icon.png" \
    --main-jar "$JAR_NAME" \
    --main-class com.dipsana.quiz.Main \
    --dest installer

if [ $? -ne 0 ]; then
    echo -e "${RED}ERROR: jpackage failed.${NC}"
    echo -e "${YELLOW}Buddy, you're on the last step! Keep things in place and re-run.${NC}"
    echo "Make sure jpackage is on PATH (a JDK is installed, not just a JRE)."
    exit 1
fi

# --- 14. Report success ---
echo -e "${CYAN}\n--- Build Complete ---${NC}"
echo -e "${GREEN}JAR:     $JAR_PATH${NC}"
echo -e "${GREEN}Package: installer/${NC}"
echo ""
echo -e "${CYAN}Check the installer/ folder for your built app.${NC}"
echo ""