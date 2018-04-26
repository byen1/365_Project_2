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
        
        int cmd = 0xff000000 & instr>>24; // cmd = first 8 bits
        int arg = 0x00ffffff & instr; // arg = last 24 bits
        
        switch (cmd) {
            case 0:
                return funcExit(arg);
			case 1:
				return funcSwap();
			case 2:
				return funcInpt();
			case 3:
				return funcNop();
			case 16:
				return funcPop();
			case 32:
				return funcAdd();
			case 33:
				return funcSub();
			case 34:
				return funcMul();
			case 35:
				return funcDiv();
			case 36:
				return funcRem();
			case 37:
				return funcAnd();
			case 38:
				return funcOr();
			case 144:
				return funcIfEz(arg);
			case 145:
				return funcIfNz(arg);
			case 146:
				return funcIfMi(arg);
			case 147:
				return funcIfPl(arg);
			case 208:
				return funcPrint(arg);
			case 224:
				return funcDump(arg);
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

	public int funcSwap(){
		int val1 = 0, val2 = 0, successfulPops = 2;
		try{
			val1 = funcPop(); //Gotta make sure that 3 cases are covered and that doing a pop at the end doesnt explode...
		}catch(Exception ex){
			successfulPops--;
		}

		try{
			val2 = funcPop();
		}catch(Exception ex){
			successfulPops--;
		}

		if(successfulPops == 2){
			push(val1);
			push(val2);
		}else if(successfulPops == 1){
			push(0);
		}else{}
		
		return 0;
	}

	public int funcInpt(){
		int toPush = Integer.parseInt(System.console().readLine()); //Might be error prone...
		push(toPush);
		return 0;
	}

	public int funcNop(){
		return 0;
	}

	public int funcPop(){
		return pop();
	}

	public int funcAdd(){
		push(pop() + pop());
		return 0;
	}


//End of functions Charlie and/or aday implemented
//Start of Saporito's functions

	/*
		pops the left and right value off the stack and xor's them and push that value on the stack
	*/
	public int funcXOR(){

		push(pop()^pop());
		return 0;

	}
	/*
		
	*/
	public int funcNeg(){
		
		push(pop()*-1);
		return 0;
	}
	public int funcNot(){

		push (~pop());
		return 0;

	}
	public int funcGoto(int arg){
		
		jump(arg);
		return 0;

	}
	public int funcIF1(int arg,int cmd){
		int condition,left,right;

		left=pop();
		right=pop();
		condition=cmd;
		condition&=7;

		if(condition==0){
			if(left==right){
				funcGoto(arg);
			}else{
				return 0;
			}
			
		}else if(condition==1){
			if(left!=right){
				funcGoto(arg);
			}else{
				return 0;
			}

		}else if(condition==2){
			if(left<right){
				funcGoto(arg);
			}else{
				return 0;
			}

		}else if(condition==3){
			if(left>right){
				funcGoto(arg);
			}else{
				return 0;
			}

		}else if(condition==4){
			if(left<=right){
				funcGoto(arg);
			}else{
				return 0;
			}

		}else if(condition==5){
			if(left>=right){
				funcGoto(arg);
			}else{
				return 0;
			}
		}
		return 0;
	}

	
//End of Saporito's functions
    
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
    	
    /* aTeepe's functions */
    public int funcIfEz(int arg)
    {
        int value = peek(0);
       
        if (value == 0)
        {
            this.ip = arg;
        }
       
        return 0;
    }
    public int funcIfNz(int arg)
    {
        int value = peek(0);
 
        if (value != 0)
        {
            this.ip = arg;
        }
 
        return 0;
    }
    public int funcIfMi(int arg)
    {
        int value = peek(0);
        
        if (value < 0)
        {
            this.ip = arg;
        }
 
        return 0;
    }
    public int funcIfPl(int arg)
    {
        int value = peek(0);
 
        if (value >= 0)
        {
            this.ip = arg;
        }
 
        return 0;
    }
   
    public int funcDup(int arg)
    {
        if (arg % 4 != 0)
        {
            throw new IllegalArgumentException();
        }
       
        int value = peek(arg / 4);
       
        push(value);
       
        return 0;
    }
   
    public int funcPrint(int arg)
    {
        if (arg != 0)
        {
            throw new IllegalArgumentException();
        }
       
        System.out.println(peek(0));
       
        return 0;
    }
   
    public int funcDump(int arg)
    {
        if (arg != 0)
        {
            throw new IllegalArgumentException();
        }
       
        for (int i = this.size-1; i >= this.sp; i--)
        {
            System.out.println(memory[i]);
        }
       
        return 0;
    }
   
    public int funcPush(int arg)
    {
        this.push(arg);
       
        return 0;
    }
	
}
