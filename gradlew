#!/bin/sh
# Gradle Start Up Script for UN*X
if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
else
    echo "Abrindo via Android Studio Gradle ou instale o Gradle 8.7+."
    exit 1
fi
