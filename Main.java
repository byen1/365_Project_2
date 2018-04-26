/*
*
* CS365 Project 2 
*
* Contributors: Alex Teepe, Brandon Yen, Austin Saporito, Austin Day, and Charles Rizzo
*
* Entry point for jar file that creates a virtual machine to interpret byte code generated from project 1. 
* Can be ran in Verbose mode with -v as second command line arg.
*/
public class Main {
    
    public static boolean verbose = false;
    
	//Creates a VirtualMachine object and calls function to read bytes from the file. 
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
    
	//Prints debugging information if -v is specified as argument
    public static void debugPrint(String msg) {
        if (verbose) System.err.println(msg);
    }
    
}
