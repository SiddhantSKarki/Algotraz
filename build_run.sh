#!/bin/bash

ROOT_DIR="."
BUILD_DIR="build/"
SRC_DIR="src"
# need to update this as we go on implementing other classes
# once each package is complete we can simply add 'package/*.java'
PACKAGE_DIRS=("$SRC_DIR/engine/ASCII.java" "$SRC_DIR/main/Algotraz.java")

if [ -d "$BUILD_DIR" ]; then
    echo "Build directory exists.."
else
    echo "Build directory does not exist"
    echo "Creating Build directory"
    mkdir -p "$BUILD_DIR"
fi

echo "Building the project...."
if ! javac "${PACKAGE_DIRS[@]}" -d "$BUILD_DIR"; then
    echo "Error: Failed to build project"
    exit 1
else
    echo "Compiled Files successfully"
fi

echo "Running the project...."
MAIN_CLASS="main.Algotraz"
if ! java -cp "$BUILD_DIR" "$MAIN_CLASS"; then
    echo "Error: Failed to run the project"
    exit 1
fi