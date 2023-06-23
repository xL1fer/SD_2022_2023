#!/bin/bash
#sed -i -e 's/\r$//' compile.sh

find -name "*.java" > sources.txt
javac @sources.txt