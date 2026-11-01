@rem Gradle startup script for Windows
@if "%DEBUG%"=="" @echo off
where gradle >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    gradle %*
) else (
    echo Abra este projeto diretamente no Android Studio para compilar o APK.
)
