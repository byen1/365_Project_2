/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zren
 */
public class Main {
    
    public static void main(String[] args) {
        
        VirtualMachine VM = new VirtualMachine();
        
        VM.readFile(args[0]);
        
        int exit = VM.run();
        if (exit != -1) System.out.println("Exited with code " + exit);
        //can't return exit codes to operating system in java, so this should do
        
    }
    
}
