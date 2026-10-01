# build.ps1
# Entry point: detects OS, runs the right bootstrap script.
# After running, the user might need to restart the terminal.
#
# Windows: scripts/bootstrap-windows.ps1
# macOs:   scripts/bootstrap-unix.sh
# Linux:   scripts/bootstrap-unix.sh


Write-Host "`n=== PlayQuiz Bootstrap ===`n" -ForegroundColor Cyan

# --- Detect OS ---
$os = if ($PSVersionTable.PSVersion.Major -ge 6) {
    if ($IsWindows) { 'Windows' }
    elseif ($IsMacOS) { 'macOS' }
    elseif ($IsLinux) { 'Linux' }
    else { 'Unknown' }
}
else {
    # Fallback for Windows PowerShell 5.1
    if ($env:OS -eq 'Windows_NT') { 'Windows' } else { 'Unknown' }
}

Write-Host "Detected OS: $os" -ForegroundColor Green

# --- Self-elevate on Windows ---
if ($os -eq 'Windows') {
    $isAdmin = ([Security.Principal.WindowsPrincipal] `
            [Security.Principal.WindowsIdentity]::GetCurrent()
    ).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

    if (-not $isAdmin) {
        Write-Host "Administrator privileges required. Elevating..." -ForegroundColor Yellow
        Start-Process powershell -Verb RunAs -ArgumentList "-NoProfile -ExecutionPolicy Bypass -File '$PSCommandPath'"
        exit 1
    }
    Write-Host "Running as Administrator. [OK]" -ForegroundColor Green
}

# --- Locate and run the appropriate script ---
$scriptDir = Join-Path $PSScriptRoot "scripts"

switch ($os) {
    'Windows' {
        $script = Join-Path $scriptDir "bootstrap-windows.ps1"
        if (-not (Test-Path $script)) {
            Write-Error "Missing: $script"
            exit 1
        }
        Write-Host "Running Windows bootstrap..." -ForegroundColor Yellow
        & $script
    }
    'macOS' {
        $script = Join-Path $scriptDir "bootstrap-unix.sh"
        if (-not (Test-Path $script)) {
            Write-Error "Missing: $script"
            exit 1
        }
        Write-Host "Running macOS bootstrap..." -ForegroundColor Yellow
        bash $script
    }
    'Linux' {
        $script = Join-Path $scriptDir "bootstrap-unix.sh"
        if (-not (Test-Path $script)) {
            Write-Error "Missing: $script"
            exit 1
        }
        Write-Host "Running Linux bootstrap..." -ForegroundColor Yellow
        bash $script
    }
    default {
        Write-Error "Unsupported OS: $os"
        exit 1
    }
}

Write-Host "`nBuild complete.`n" -ForegroundColor Green