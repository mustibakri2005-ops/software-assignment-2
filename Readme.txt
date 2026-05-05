Masroofy - CS251 Final Submission Implementation

Project: Masroofy
Course: CS251 - Introduction to Software Engineering
Section: S31
TA: Mahetab
Team IDs: 20240783, 20243043
GitHub repository: https://github.com/mustibakri2005-ops/software-assignment-2.git

1. What is included
- src/: Java source code for the Masroofy prototype.
- generated-documentation/: JavaDoc HTML pages generated for classes and methods.

2. Tools used
- Java JDK 21 for compiling and running the prototype.
- JavaDoc for generated documentation.
- Git and GitHub for version control and commit evidence.
- Visual Studio Code / IntelliJ IDEA can be used as the code editor.
- Graphviz was used to create the design diagrams in the SDS.

3. How to run from source
Open a terminal inside the implementation folder and run:

javac -d bin $(find src -name "*.java")
java -cp bin com.masroofy.ui.MasroofyApp

On Windows PowerShell, run:

Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName } > sources.txt
javac -d bin @sources.txt
java -cp bin com.masroofy.ui.MasroofyApp

4. How to regenerate JavaDoc
javadoc -d generated-documentation $(find src -name "*.java")

5. Notes
This implementation is a console prototype that demonstrates the first seven Masroofy user stories: cycle setup, rapid expense logging, dashboard daily limit, visual spending insight data, daily rollover logic, threshold notification, and transaction history. The SDS explains how this same design maps to an offline mobile architecture with SQLite/local persistence.
