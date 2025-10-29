# Software-Development
coursework

// how to run the files
javac -d bin src/TestFileName.java //bin or out
java -cp bin TestFileName

javac -d bin src/Card.java src/CardTest.java // to run both files together

/// to run all files ALWAYS USE THIS ONE 
javac -d bin src/*.java
// COMPILE AND RUN WITH JUNIT FILES WITH THIS 
javac -d bin -cp ".:library/junit-4.13.2.jar:library/hamcrest-core-1.3.jar" src/*.java
java -cp ".:bin:library/junit-4.13.2.jar:library/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore testdeck (or whatever)


/// file locations 
> bin 
    .class files 
> doc
    pre
    considerations
    worklog 
>lib 
    jar file (after developing)

>src
    all source code files and the test files (for now)
    main file

>test
    to move the test files 


