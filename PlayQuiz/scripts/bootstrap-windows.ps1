# scripts/bootstrap-windows.ps1
#
# Windows setup + build: installs Java 21 (LTS) and Maven via Chocolatey,
# then compiles the JAR and packages a native Windows app with jpackage.
#
# Idempotent, safe to run multiple times. Checks before installing.

Write-Host "`n--- Windows Bootstrap ---`n" -ForegroundColor Cyan

# --- Helper: check if a command exists ---
function Test-CommandExists {
    param([string]$Command)
    return [bool](Get-Command $Command -ErrorAction SilentlyContinue)
}

# --- Helper: get installed Java major version (0 if not installed) ---
function Get-JavaMajorVersion {
    if (-not (Test-CommandExists "java")) { return 0 }
    try {
        $output = & java -version 2>&1 | Select-Object -First 1
        # Output looks like: java version "21.0.2" 2024-01-16 LTS
        if ($output -match 'version "(\d+)') {
            return [int]$Matches[1]
        }
    }
    catch { }
    return 0
}

# --- 1. Verify we're running as admin ---
$isAdmin = ([Security.Principal.WindowsPrincipal] `
        [Security.Principal.WindowsIdentity]::GetCurrent()
).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if (-not $isAdmin) {
    Write-Host "ERROR: This script must run as Administrator." -ForegroundColor Red
    Write-Host "The dispatcher (build.ps1) should have elevated automatically." -ForegroundColor Red
    Write-Host "If you ran this directly, please run it from an elevated terminal." -ForegroundColor Yellow
    Write-Host "And make sure Windows Security doesn't block you!" -ForegroundColor Red
    Write-Host "If you've enabled ransomware protection, go to protected folders, and remove it from the list till the build finishes." -ForegroundColor Yellow
    exit 1
}
Write-Host "Running as Administrator. OK" -ForegroundColor Green

# --- 2. Check / install Chocolatey ---
if (Test-CommandExists "choco") {
    $chocoVersion = (choco --version)
    Write-Host "Chocolatey already installed (v$chocoVersion). Skipping." -ForegroundColor Green
}
else {
    Write-Host "Chocolatey not found. Installing..." -ForegroundColor Yellow
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = `
        [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    try {
        Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
        # Refresh PATH so 'choco' is available in this session
        $env:Path = [System.Environment]::GetEnvironmentVariable("Path", "Machine") + ";" +
        [System.Environment]::GetEnvironmentVariable("Path", "User")
        if (Test-CommandExists "choco") {
            Write-Host "Chocolatey installed successfully." -ForegroundColor Green
        }
        else {
            Write-Host "ERROR: Chocolatey install completed but 'choco' still not found." -ForegroundColor Red
            Write-Host "Don't worry, restart your terminal then re-run this script." -ForegroundColor Yellow
            Write-Host "Hang tight! The terminal might take a couple of restarts." -ForegroundColor Yellow
            Write-Host "Not working, huh? Try installing Chocolatey manually J>'' <L" -ForegroundColor Gray
            exit 1
        }
    }
    catch {
        Write-Host "ERROR: Failed to install Chocolatey." -ForegroundColor Red
        Write-Host "Strange! Try re-running this script." -ForegroundColor Yellow
        Write-Host "Not working, huh? Try installing Chocolatey manually @(> ^ <)@" -ForegroundColor Gray
        Write-Host $_ -ForegroundColor Red
        exit 1
    }
}

# --- 3. Check / install Java 21 (LTS) ---
$requiredJavaVersion = 21
$currentJavaVersion = Get-JavaMajorVersion

if ($currentJavaVersion -ge $requiredJavaVersion) {
    Write-Host "Java $currentJavaVersion already installed (>= $requiredJavaVersion). Skipping." -ForegroundColor Green
}
else {
    if ($currentJavaVersion -gt 0) {
        Write-Host "Java $currentJavaVersion found, upgrading to Java $requiredJavaVersion (LTS)..." -ForegroundColor Yellow
    }
    else {
        Write-Host "Java not found. Installing OpenJDK $requiredJavaVersion..." -ForegroundColor Yellow
    }
    choco install -y temurin21
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERROR: Failed to install Java via Chocolatey." -ForegroundColor Red
        Write-Host "Don't worry, re-run this script again..." -ForegroundColor Yellow
        Write-Host "Not working, huh? Watch a YouTube guide to install java in your System @>''<@" -ForegroundColor Gray
        exit 1
    }
    Write-Host "Java installed." -ForegroundColor Green
}

# --- 4. Check / install Maven ---
if (Test-CommandExists "mvn") {
    $mvnVersion = (mvn --version 2>&1 | Select-Object -First 1)
    Write-Host "Maven already installed: $mvnVersion" -ForegroundColor Green
}
else {
    Write-Host "Maven not found. Installing..." -ForegroundColor Yellow
    choco install -y maven
    if ($LASTEXITCODE -ne 0) {
        Write-Host "ERROR: Failed to install Maven via Chocolatey." -ForegroundColor Red
        Write-Host "Don't worry, re-run this script again..." -ForegroundColor Yellow
        Write-Host "Not working, huh? Watch a YouTube guide to install Maven in your System @(> ^ <)@" -ForegroundColor Gray
        exit 1
    }
    Write-Host "Maven installed." -ForegroundColor Green
}

# --- 5. Refresh PATH so java/mvn are usable in this session ---
$env:Path = [System.Environment]::GetEnvironmentVariable("Path", "Machine") + ";" +
[System.Environment]::GetEnvironmentVariable("Path", "User")

# --- 6. Final report ---
Write-Host "`n--- Bootstrap Report ---" -ForegroundColor Cyan

$finalJavaVersion = Get-JavaMajorVersion
if ($finalJavaVersion -ge $requiredJavaVersion) {
    Write-Host "Java:  v$finalJavaVersion  OK" -ForegroundColor Green
}
else {
    Write-Host "Java:  NOT VERIFIED (restart terminal)" -ForegroundColor Yellow
}

if (Test-CommandExists "mvn") {
    Write-Host "Maven: installed  OK" -ForegroundColor Green
}
else {
    Write-Host "Maven: NOT VERIFIED (restart terminal)" -ForegroundColor Yellow
}

Write-Host "`nBootstrap finished.`n" -ForegroundColor Cyan
Write-Host "`n--- Windows Build ---`n" -ForegroundColor Cyan

# --- 7. Verify prerequisites ---
if (-not (Test-CommandExists "java")) {
    Write-Host "ERROR: Java not found on PATH." -ForegroundColor Red
    Write-Host "Don't worry, restart your terminal then run this script." -ForegroundColor Yellow
    Write-Host "Hang tight! The terminal might take a couple of restarts." -ForegroundColor Yellow
    Write-Host ":/ Not working, huh?" -ForegroundColor Gray
    Write-Host "Watch a YouTube guide on how to add java PATH in Windows environment variables." -ForegroundColor Gray
    exit 1
}

if (-not (Test-CommandExists "mvn")) {
    Write-Host "ERROR: Maven not found on PATH." -ForegroundColor Red
    Write-Host "Don't worry, restart your terminal then run this script." -ForegroundColor Yellow
    Write-Host "Hang tight! The terminal might take a couple of restarts." -ForegroundColor Yellow
    Write-Host ":/ Not working, huh?" -ForegroundColor Gray
    Write-Host "Watch a YouTube guide on how to add Maven PATH in Windows environment variables." -ForegroundColor Gray
    exit 1
}

if (-not (Test-CommandExists "jpackage")) {
    Write-Host "ERROR: jpackage not found. Is a JDK installed (not just a JRE)?" -ForegroundColor Red
    Write-Host "Don't worry, restart your terminal then run this script" -ForegroundColor Yellow
    Write-Host "Hang tight! The terminal might take a couple of restarts." -ForegroundColor Yellow
    Write-Host ":/ Not working, huh? Watch a YouTube guide on jpackage or reinstall java." -ForegroundColor Gray
    exit 1
}

Write-Host "Java:     $((java -version 2>&1 | Select-Object -First 1))" -ForegroundColor Green
Write-Host "Maven:    $((mvn --version 2>&1 | Select-Object -First 1))" -ForegroundColor Green
Write-Host ""

# --- 8. Confirm working directory ---
# We expect to run from the project root (where pom.xml lives).
if (-not (Test-Path "pom.xml")) {
    Write-Host "ERROR: There is file called pom.xml, if you accidentally deleted it..." -ForegroundColor Red
    Write-Host "you can always bring it back, just saying." -ForegroundColor Red
    Write-Host "Hey-y-y! I told you just to double click me." -ForegroundColor Yellow
    Write-Host "Don't move things around, just clone the repository as it is." -ForegroundColor Yellow
    Write-Host "Run this script from the project root: play-quiz-j/ (the base folder, okay?)" -ForegroundColor Yellow
    Write-Host "Current directory(folder): $(Get-Location)" -ForegroundColor Yellow
    exit 1
}

# --- 9. Prompt before deleting old installer/ folder ---
if (Test-Path "installer") {
    Write-Host "The 'installer' folder already exists." -ForegroundColor Yellow
    Write-Host "If you didn't create it, or if you're running this again, just hit Y." -ForegroundColor Green
    $response = Read-Host "Delete it and rebuild? (Y/N)"
    if ($response -match '^[Yy]') {
        Write-Host "Deleting installer/ ..." -ForegroundColor Yellow
        Remove-Item -Recurse -Force "installer"
        Write-Host "Deleted." -ForegroundColor Green
    }
    else {
        Write-Host "Aborted. installer/ left untouched." -ForegroundColor Yellow
        exit 0
    }
}

# --- 10. Build the JAR with Maven ---
Write-Host "`nBuilding JAR with Maven..." -ForegroundColor Cyan
mvn clean package
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Maven build failed." -ForegroundColor Red
    Write-Host "Don't give up yet, try running this script again..." -ForegroundColor Yellow
    Write-Host "Not working, huh? W<. _ .>W" -ForegroundColor Yellow
    Write-Host "You're just two steps away. Watch a guide or run this in your terminal: mvn clean package" -ForegroundColor Yellow
    exit 1
}
Write-Host "JAR built successfully." -ForegroundColor Green

# --- 11. Locate the built JAR ---
$jarFile = Get-ChildItem -Path "target" -Filter "*.jar" |
Where-Object { $_.Name -notmatch 'sources|javadoc' } |
Select-Object -First 1

if (-not $jarFile) {
    Write-Host "ERROR: No JAR found in target/ after build." -ForegroundColor Red
    Write-Host "Captain, you're on the second last step! I hope you didn't move anything." -ForegroundColor Yellow
    Write-Host "If you've put the target folder or it's files somewhere else don't worry!" -ForegroundColor Yellow
    Write-Host "Delete the target folder and re-run this script, I'll fix it but don't touch anything." -ForegroundColor Yellow
    Write-Host "Not working, huh? @(> ^ <)@ Watch a YouTube guide or ask an AI, okay?" -ForegroundColor Yellow
    exit 1
}
Write-Host "Found JAR: $($jarFile.Name)" -ForegroundColor Green

# --- 12. Package with jpackage ---
Write-Host "`nPackaging with jpackage..." -ForegroundColor Cyan

jpackage `
    --type app-image `
    --name "PlayQuiz" `
    --app-version "1.0.0" `
    --input target `
    --icon "assets\icon.ico" `
    --main-jar $jarFile.Name `
    --main-class com.dipsana.quiz.Main `
    --win-console `
    --dest installer

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: jpackage failed." -ForegroundColor Red
    Write-Host "Buddy, you're on the last step! I hope you didn't move anything." -ForegroundColor Yellow
    Write-Host "Just keep things in place, and re-run this script." -ForegroundColor Yellow
    Write-Host "Make sure jpackage is on PATH (a JDK is installed, not just a JRE)." -ForegroundColor Yellow
    Write-Host "Not working, huh? Go and ask Gemini, why jpackage is failing @(>'' <)@" -ForegroundColor Cyan
    exit 1
}

# --- 13. Report success ---
$exePath = Join-Path "installer" "PlayQuiz\PlayQuiz.exe"

Write-Host "`n--- Build Complete ---" -ForegroundColor Cyan
Write-Host "JAR:  target\$($jarFile.Name)" -ForegroundColor Green
if (Test-Path $exePath) {
    Write-Host "EXE:  $exePath" -ForegroundColor Green
    Write-Host "`nDouble-click '$exePath' to run the quiz." -ForegroundColor Cyan
}
else {
    Write-Host "WARNING: Expected EXE not found at $exePath" -ForegroundColor Yellow
    Write-Host "Check the installer/ folder for your built app." -ForegroundColor Yellow
    Write-Host "If you see PlayQuiz.exe, double-click on it and play @^^@" -ForegroundColor Yellow
}

Write-Host ""