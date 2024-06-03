# Lights Out
A JavaFX version of the *Lights Out* puzzle game, submitted as a final project for my Computer Programming & Problem Solving course, fall 2019.

Note: This app can be run from the command line without using build tools like Maven or Gradle.

### Requirements
+ **JavaFX SDK 12.0.2** or later
+ **JDK 12.0.2** or later

### Linux/Mac
Add an environment variable called ```PATH_TO_FX``` that points to the JavaFX runtime:
```
export PATH_TO_FX=path/to/javafx-sdk-12.0.2/lib
```
Compile with the JavaFX SDK:
```
javac --module-path $PATH_TO_FX -d out/lightsout $(find src/ -name "*.java")
```
Run with:
```
java --module-path $PATH_TO_FX:out -m lightsout/lightsout.Main
```
### Windows
Add an environment variable called ```PATH_TO_FX``` that points to the JavaFX runtime:
```
set PATH_TO_FX=path\to\javafx-sdk-12.0.2\lib
```
Compile with the JavaFX SDK:
```
dir /s /b src\*.java > sources.txt & javac --module-path "%PATH_TO_FX%" -d out/lightsout @sources.txt & del sources.txt
```
Run with:
```
java --module-path "%PATH_TO_FX%;out" -m lightsout/lightsout.Main
```

Reference: [JavaFX runtime images](https://openjfx.io/openjfx-docs/#modular)
