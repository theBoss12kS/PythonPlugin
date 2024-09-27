
public class MultiThreadedExample {
    public static void main(String[] args) {
        System.out.println("Java doing Java");
        Thread newThread = new Thread(() -> threadForPython());
        newThread.start();
        while (true) {
            System.out.println("Java is running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void threadForPython() {
        PythonHandler handler = new PythonHandler("py");
        System.out.println("Python being Python");
        handler.script("import time\n" +
                "print ('Hello World from Python')\n" +
                "a=89\n" +
                "time.sleep(5);\n" +
                "b=a+9000\n");

        //System.out.println(handler.getVariable("a"));
        System.out.println(handler.getVariable("b"));
        System.out.println("Getting value");
    }
}

