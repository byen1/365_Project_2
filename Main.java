
public class Main {
    
    public static boolean verbose = false;
    
    public static void main(String[] args) {
        if(args.length < 1)
        {
            System.out.println("Usage: java -jar Project2.jar BYTECODE_FILE");
            return;
        }
        if (args.length == 2) {
            if (args[1].equals("-v")) verbose = true;
        }
        VirtualMachine VM = new VirtualMachine();
        
        VM.readFile(args[0]);
        
        int exit = VM.run();
        if (exit != -1) System.out.println("Exited with code " + exit);
        //can't return exit codes to operating system in java, so this should do
        
    }
    
    public static void debugPrint(String msg) {
        if (verbose) System.err.println(msg);
    }
    
}
