/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zren
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
        sp++;
        return memory[sp-1];
    }
    
    public void push(int val) {
        memory[--sp] = val;
    }
    
    public int getInstruction() {
        return memory[ip];
    }
    
    
    public void jump(int label) {
        this.ip = label;
    }
    
    public void loadInstructions(int[] instr) {
        //load instructions from an array of instructions into memory starting at address 0
        System.arraycopy(instr, 0, memory, 0, instr.length);
    }
    
    
}
