/*
*
* CS365 Project 2 
*
* Contributors: Alex Teepe, Brandon Yen, Austin Saporito, Austin Day, and Charles Rizzo
*
* Entry point for jar file that creates a virtual machine to interpret byte code generated from project 1. 
* Can be ran in Verbose mode with -v as second command line arg.
*/
public class Memory {
    
    protected int memory[];
    protected int sp;
    protected int ip;
    protected int size;
    
    public Memory() {
        size = 1024;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }
    
    public Memory(int sizeInBytes) {
        size = sizeInBytes/4;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }
    
    public int pop() {
        if (sp + 1 >= size) throw new IndexOutOfBoundsException();
        return memory[sp++];
    }
    
    public void push(int val) {
        memory[--sp] = val;
    }
    
    public int getInstruction() {
        return memory[ip];
    }
    
    
    public void jump(int offset) {
        this.ip += (offset / 4);
    }
    
    public void loadInstructions(int[] instr) {
        //load instructions from an array of instructions into memory starting at address 0
        System.arraycopy(instr, 0, memory, 0, instr.length);
    }

    public int peek(int offset)
    {
        if (sp + offset >= size) throw new IndexOutOfBoundsException();
			return memory[sp + offset];
    }
    
    
}
