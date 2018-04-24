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
//Functions that Charlie and/or aday implemented    
    public int funcExit(int arg) {
        
        int mask = 0xff;
        this.exitCode = arg & mask;
        
        return -1;
        
    }

<<<<<<< HEAD
	public int funcSwap(){
		return 0;
	}

	public int funcInpt(){
		return 0;
	}

	public int funcNop(){
		return 0;
	}

	public int funcPop(){
		return 0;
	}

	public int funcAdd(){
		return 0;
	}


//End of functions Charlie and/or aday implemented


	public void XOR(){
	public int funcXOR(){

		push(pop()^pop());
		return 0;

	}
	public int funcNeg(){
		
		push(pop()*-1);
		return 0;
	}
	public int funcNot(){

		push (~pop());
		return 0;

	}
	public int funcGoto(){
		int label;
		
		label=this.ip;
		label=(1<<24)-1;
		jump(label);
		return 0;
	}
	public int funcIF1(){
		int left, right;
		left=pop();
		right=pop();

		if(left==right){

		}else if(left!=right){

		}else if(left<right){

		}else if(left>right){

		}else if(left<=right){

		}else if(left>=right){

		}
		return 0;
	}

	

    
    /* Implement functions as ints here */
    /* Returning -1 ceases execution, generally return 0 */
    
    /* Brandon's Functions: Sub through Or */
    public int funcSub()
    {
        push(pop() - pop());
        return 0;
    }
    	
    public int funcMul()
    {
        push(pop() * pop());
        return 0;	
    }
    
    public int funcDiv()
    {
        push(pop() / pop());
        return 0;	
    }
    	
    public int funcRem()
    {
        push(pop() % pop());
        return 0;	
    }
    	
    public int funcAnd()
    {
        push(pop() & pop());
        return 0;	
    }
    	
    public int funcOr()
    {
        push(pop() | pop());
        return 0;	
    }
	
}
