public class Trial {
    public static void main(String[] args)
    {
        PythonHandler handler=new PythonHandler("py"); // Create the constructor and use it to specify the Python compiler arguement. 
        handler.script("print ('Hello World')\n" + //Paste your python script here. Alternatively, use the readFile method to point to the path of the Python file. 
                "a=89\n" +
                "b=a+90");
        System.out.println(handler.getVariable("b")); //Get the variable value after execution of Python code. 
    }

}

