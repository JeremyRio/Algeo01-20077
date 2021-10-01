@echo off

javac -cp src src\*.java -d bin
java -cp bin MenuUI

pause