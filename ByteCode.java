/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zren
 */
public class ByteCode extends Memory {
    
    protected int exitCode = 0;
    
    /* Run the current instruction and increment instruction pointer as appropriate */
    public int interpret() {
        
        /* Get instruction from memory */
        int instr = this.getInstruction();
        this.ip++;
        
        int cmd = 0xff000000 & instr; // cmd = first 8 bits
        int arg = 0x00ffffff & instr; // arg = last 24 bits
        
        switch (cmd) {
            case 0:
                return funcExit(arg);
            default:
                return 0;
        }
    }
    
    public int funcExit(int arg) {
        
        int mask = 0xff;
        this.exitCode = arg & mask;
        
        return -1;
        
    }
    
    /* Implement functions as ints here */
    /* Returning -1 ceases execution, generally return 0 */
    
    /* Brandon's Functions: Sub through Or */
    public int funcSub()
    {
        int num1, num2;
        	
        num1 = pop();
        num2 = pop();
        push(num1 + num2);
        return 0;
    }
    	
    public int funcMul()
    {
        int num1, num2;
        		
        num1 = pop();
        num2 = pop();
        push(num1 * num2);
        return 0;	
    }
    
    public int funcDiv()
    {
        int num1, num2;
        		
        num1 = pop();
        num2 = pop();
        push(num1 / num2);
        return 0;	
    }
    	
    public int funcRem()
    {
        int num1, num2;
        		
		num1 = pop();
        num2 = pop();
        push(num1 % num2);
        return 0;	
    }
    	
    public int funcAnd()
    {
        int num1, num2;
        		
        num1 = pop();
        num2 = pop();
        push(num1 & num2);
        return 0;	
    }
    	
    public int funcOr()
    {
        int num1, num2;
        	
        num1 = pop();
        num2 = pop();
        push(num1 | num2);
        return 0;	
    }
}
