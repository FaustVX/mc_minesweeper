Minesweeper mod
===========

## To use this mod
* use command: `/setblock ~ ~ ~ minesweeper:mine[flagged=false,mine=false,revealed=true,mines=0]`
    * with: 
        * `mines`: mines around this block, from `0` to `8`
        * `mine`: if this block isa mine, `true` or `false`

## Setting up a Workspace/Compiling from Source
Note: Git MUST be installed and in the system path to use our scripts.
* clone this repo `git clone https://github.com/FaustVX/mc_minesweeper.git`
* Setup: Run [gradle]in the repository root: `gradlew[.bat] [eclipse] OR import build.gradle into idea`
* Build: Run [gradle]in the repository root: `gradlew[.bat] build`
* If obscure Gradle issues are found try running `gradlew clean` and `gradlew cleanCache`