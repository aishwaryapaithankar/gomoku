# Peer to Peer Two-Player Gomoku (Five in a row)

## Team Members

Aishwarya Paithankar <br/>
Varad Raut
<br/><br/>

## Language Versions

Kotlin Version: 1.6.10
Android: Android 9 (API level 28) Minimum

## Steps to run
First, clone this repository. If you have `make` on your machine, build the code by executing
```bash command
make build
``` 
Then, to play the game:
```bash command
make run
```

If you don't have `make`, then you need to download the charting lib dependency. Follow these steps:
1. Download the dependency [zip](https://knowm.org/downloads/xchart/xchart-3.8.2.zip).
2. Extract &nbsp;```xchart-3.8.2.jar```&nbsp; from &nbsp;```xchart-3.8.2.zip```&nbsp;.
3. Create a directory ```lib``` in the root of the project, and place the extracted jar in there.
4. Next, build the project:
   ```bash command
   javac --source-path src -d build -cp lib/xchart-3.8.2.jar src/RotLA.java
   ```

5. Run
   ```bash command
   java -cp build:lib/xchart-3.8.2.jar -ea RotLA
   ```

<br/>

## Updated class diagram
![class diagram](class-diagram.png)


## Notes & Assumptions

1. ### Identifying Patterns in code
   We have commented about the four patterns in code, all these comments start with `@Pattern`.
