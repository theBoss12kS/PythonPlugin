# PythonPlugin
This is a Java class that can be used to embed and use Python codes within a Java file itself.
Just download the .java file and import it into your programs and you are ready to go. 
NOTE: I have made this file using JDK17, but it does not use any features specifically available in JDK17. So, it should work for all of you, including those of you on JDK1.x.

METHODS AVAILABLE
  1) public void script(String script) - This is used to receive the Python script from the main script.
  2) public void readFile(String path) - Reads the given file as a String and points it to the script method.
  3) public String getVariable(String variableName) - Used to get the desired variable's name. For example, getVariable("a") will search the executed Python script and get the updated value.

The Python code is executed only when the getVariable() method is called. 
I did think of making the code execute directly as soon as its declared, but this results in the entire program slowing down. Typically, I use Python to interface with hardware and perform CV tasks. So, I just need the final value of the variable, after execution, only at some points of the program. 

I would recommend that you use this class in a seperate thread if you are executing a large Python program. This way, you can avoid disrupting the functionality of the Java program, while getting the variable access once the program is executed. I have given an example code as MultiThreadedExecution.java

NOTE: Try to avoid printing the variable values so as to avoid bugs.
