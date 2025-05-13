@echo off
setlocal enabledelayedexpansion

set APP_NAME=unit
set VERSION=1.0.0
set INSTALL_DIR=%APPDATA%\%APP_NAME%
set JAR_NAME= unit-%VERSION%-jar-with-dependencies.jar

if "%1" == "" (
    java -jar "%INSTALL_DIR%\%JAR_NAME%"
    exit /b
)

:check_admin
net session >nul 2>&1
if %errorLevel% neq 0 (
    echo Requirement execute like Admin
    exit /b 1
)

goto %1

:install
echo Installing %APP_NAME%...
mkdir "%INSTALL_DIR%" 2>nul
copy "%~dp0%JAR_NAME%" "%INSTALL_DIR%\" >nul

setx PATH "%PATH%;%INSTALL_DIR%" /M >nul
echo Installed in %%PATH%%
goto end

:uninstall
echo Removing %APP_NAME%...
rmdir /s /q "%INSTALL_DIR%"
reg delete "HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment" /v Path /f >nul
echo Uninstallation complete...
goto end

:--version
:--v
echo %VERSION%
goto end

:--help
:--h
echo %APP_NAME% v%VERSION%
echo Uso:
echo   %APP_NAME% [comando]
echo Comandos:
echo   install    Install the application
echo   uninstall  Remove the application
echo   --version  Show version
echo   --help     Show this help
goto end

:end
endlocal