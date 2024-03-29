#!/bin/bash
set -e # exit if return != 0

# Need to install jdk-14-loom for lab6 ...
if [ "$TARGET" = "lab6" ]; then
    if [ "$TRAVIS_OS_NAME" = "linux" ]; then
        wget https://github.com/forax/java-next/releases/download/untagged-c59655314c1759142c15/jdk-14-loom-linux.tar.gz
        tar xvf jdk-14-loom-linux.tar.gz
    elif [ "$TRAVIS_OS_NAME" = "osx" ]; then
        wget https://github.com/forax/java-next/releases/download/untagged-c59655314c1759142c15/jdk-14-loom-osx.tar.gz
        tar xvf jdk-14-loom-osx.tar.gz
    fi

    export JAVA_HOME=$(pwd)/jdk-14-loom/
fi

cd $TARGET
mvn package