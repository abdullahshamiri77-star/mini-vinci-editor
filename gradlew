#!/usr/bin/env sh
set -e
DIR=$(dirname "$0")
cd "$DIR"
exec ./gradle/wrapper/gradle-wrapper.jar "$@"
