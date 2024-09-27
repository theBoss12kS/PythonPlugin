/*
This file has been developed by Paramesh Sriram P S, of Amrita Vishwa Vidyapeetham.
For details, please email me at parameshsriram2020@gmail.com 
*/

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class PythonHandler {
    private volatile String finalScript;
    private String pythonCompilerPrompt;
    public PythonHandler(String argument) //Constructors with arguments.
    {
        pythonCompilerPrompt=argument;
    }
    public void script(String script) //This is used to receive the Python script from the main script.
    {
        finalScript = script;
    }
    public void readFile(String path)
    {
        try {
            finalScript = readFileAsString(path);
        }
        catch (Exception ae)
        {
            System.out.println(ae);
        }
    }
    public String getVariable(String variableName) {
        String finalRet = "";
        String[] lines = finalScript.split("\n");
        List<String> modifiedLines = new ArrayList<>();

        // Flag to track if the variable was found
        boolean variableFound = false;

        // Iterate through each line of the Python code
        for (String line : lines) {
            modifiedLines.add(line);

            // Check if the line contains the specified variable assignment and if it hasn't already been printed
            if (line.matches(".*\\b" + variableName + "\\b\\s*=.*") && !variableFound) {
                // Find the indentation of the current line
                String indentation = line.replaceAll("^(\\s*).*", "$1"); // Matches leading whitespace

                // Add the print statement with the correct indentation
                String printStatement = indentation + "print(\"" + "INTERNAL_VAR:" + variableName + " =\", " + variableName + ")";
                modifiedLines.add(printStatement);

                // Set the flag to true indicating the variable was found
                variableFound = true;
            }
        }

        // Join the modified lines back into a single string
        String scriptExec = (String.join("\n", modifiedLines));
        // System.out.println(scriptExec);
        String res = executeScript(scriptExec); //This will get the output of the Python code.
        String[] split = res.split("\n");
        for (String line : split) {
            if (line.contains("INTERNAL_VAR:" + variableName)) {
                String[] splitAgain = line.split("=");
                finalRet = splitAgain[1];
                break;
            } else {
                finalRet = "VARIABLE NOT FOUND";
            }
        }
        if(Objects.equals(finalRet, "VARIABLE NOT FOUND")) //This attempts to check if the function, where the variable is present, has not been called and if it has any initialised value.
        {
            String[] codeSplit=finalScript.split("\n");
            // System.out.println("Attempting to search here");
            for(String line:codeSplit)
            {
                // System.out.println(line);
                if(line.contains(variableName+"="))
                {
                    String[] splitAgain=line.split("=");
                    finalRet=splitAgain[1];
                    break;
                }
            }
        }
        return finalRet;
    }

    private String executeScript(String script) //This function will execute the script and return the output.
    {
        String finalOutput = "";
        try {
            // Create a temporary file to store the Python script
            File tempScript = File.createTempFile("temp_script", "."+pythonCompilerPrompt);
            tempScript.deleteOnExit(); // Delete the file on exit

            // Write the Python script to the temporary file
            FileWriter writer = new FileWriter(tempScript);
            writer.write(script);
            writer.close();

            // Execute the Python script
            ProcessBuilder processBuilder = new ProcessBuilder("py", tempScript.getAbsolutePath());
            processBuilder.redirectErrorStream(true); // Combine standard output and error

            Process process = processBuilder.start();

            // Read the output from the Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                finalOutput = finalOutput + line;
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalOutput;
    }
    public static String readFileAsString(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

}


