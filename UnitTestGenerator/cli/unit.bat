@echo off
setlocal

set APP_NAME=unit
set VERSION=1.0.0
set INSTALL_DIR=%APPDATA%\%APP_NAME%
set JAR_NAME=%APP_NAME%-%VERSION%-jar-with-dependencies.jar
set LAUNCHER=%INSTALL_DIR%\%APP_NAME%.cmd

if "%~1"=="" (
    java -jar "%INSTALL_DIR%\%JAR_NAME%" %*
    exit /b
)

goto %~1

:install
echo Installing %APP_NAME% v%VERSION%...

mkdir "%INSTALL_DIR%" 2>nul
copy /y "%~dp0%JAR_NAME%" "%INSTALL_DIR%\" >nul

rem Crear el launcher
(
    echo @echo off
    echo setlocal
    echo set JAR_DIR=%%~dp0
    echo set JAR_FILE=%JAR_NAME%
    echo if "%%~1"=="--help" (
    echo   echo unit v%VERSION%
    echo   echo.
    echo   echo Usage:
    echo   echo   unit [command]
    echo   echo.
    echo   echo Commands:
    echo   echo   --help       Show this help
    echo   echo   --version    Show the version
    echo   echo   install      Install the application
    echo   echo   uninstall    Uninstall the application
    echo   exit /b 0
    echo ^)
    echo if "%%~1"=="--version" (
    echo   echo %VERSION%
    echo   exit /b 0
    echo ^)
    echo java -jar "%%JAR_DIR%%%%JAR_FILE%%" %%*
    echo endlocal
) > "%LAUNCHER%"

echo.
echo ✓ Installed to: %INSTALL_DIR%
echo.
echo ➤ To run "%APP_NAME%" from any location:
echo   ➤ Add the following to your user PATH:
echo     %INSTALL_DIR%
goto end

:uninstall
echo Removing %APP_NAME%...
del /q "%INSTALL_DIR%\%JAR_NAME%" 2>nul
del /q "%LAUNCHER%" 2>nul
rmdir /s /q "%INSTALL_DIR%" 2>nul
echo ✓ Uninstalled.
goto end

:end
endlocal
