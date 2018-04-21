import java.io.*;


public class VirtualMachine extends ByteCode {

    protected ByteCode bc;
    
    public VirtualMachine() {
        size = 1024;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }
    
    public VirtualMachine(int sizeInBytes) {
        size = sizeInBytes/4;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }
    
    public void readFile(String filename) {
        
        int[] instructions = {0};
        /* Read in file to array of ints */
        
        this.loadInstructions(instructions);
        
    }
    
    public void jump(int label) {
        this.ip = label;
    }
    
    public int run() {
        
        jump(0);
        try {
            return execLoop();
        } catch (Exception e) {
            System.err.println("Erroroneous instruction, terminating program: " + e.getMessage());
            return -1;
        }
        
    }
    
    public int execLoop() {
        
        while(interpret() != -1);
        return this.exitCode;
        
    }
}
