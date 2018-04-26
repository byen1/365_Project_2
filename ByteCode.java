/*
*
* CS365 Project 2 
*
* Contributors: Alex Teepe, Brandon Yen, Austin Saporito, Austin Day, and Charles Rizzo
*
* Entry point for jar file that creates a virtual machine to interpret byte code generated from project 1. 
* Can be ran in Verbose mode with -v as second command line arg.
*/
public class ByteCode extends Memory {
    
    protected int exitCode = 0;
    
    /* Run the current instruction and increment instruction pointer as appropriate */
    public int interpret() {
        
        /* Get instruction from memory */
        int instr = this.getInstruction();
        
        int cmd = (0xff000000 & instr) >>> 24; // cmd = first 8 bits
        int arg28 = (instr << 4) >> 4; // arg28 = last 28 bits, left then right shifted to preserve sign
        int arg = ((0x00ffffff & instr) << 8) >> 8; // arg = last 24 bits, signed
        
        Main.debugPrint("Reading instruction " + this.ip + ": 0x" + Integer.toHexString(cmd) + "(" + cmd + ") " + arg);
        
        switch (cmd) {
            case 0:
                Main.debugPrint("Exiting");
                return funcExit(arg);
            case 1:
                Main.debugPrint("Swapping");
                return funcSwap();
            case 2:
                Main.debugPrint("Inputting");
                return funcInpt();
            case 3:
                Main.debugPrint("Nop");
                return funcNop();
            case 16:
                Main.debugPrint("Popping");
                return funcPop();
            case 32:
                Main.debugPrint("Adding");
                return funcAdd();
            case 33:
                Main.debugPrint("Subtracting");
                return funcSub();
            case 34:
                Main.debugPrint("Multiplying");
                return funcMul();
            case 35:
                Main.debugPrint("Dividing");
                return funcDiv();
            case 36:
                Main.debugPrint("Remainder");
                return funcRem();
            case 37:
                Main.debugPrint("Anding");
                return funcAnd();
            case 38:
                Main.debugPrint("Oring");
                return funcOr();
            case 39:
                Main.debugPrint("XORing");
                return funcXOR();
            case 48:
                Main.debugPrint("Negating");
                return funcNeg();
            case 49:
                Main.debugPrint("Not-ing");
                return funcNot();
            case 128:
                Main.debugPrint("If1");
                return funcIF1(arg,cmd);
            case 129:
                return funcIF1(arg,cmd);
            case 130:
                return funcIF1(arg,cmd);
            case 131:
                return funcIF1(arg,cmd);
            case 132:
                return funcIF1(arg,cmd);
            case 133:
                return funcIF1(arg,cmd);
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
                cmd >>= 4;
                if(cmd == 7) {
                    Main.debugPrint("Goto");
                    return funcGoto(arg28);
                } else if(cmd == 12) {
                    Main.debugPrint("Dup");
                    return funcDup(arg28 >> 2);
                }else if(cmd == 15) {
                    Main.debugPrint("Pushing");
                    return funcPush(arg28);
                } else 
                    return 0;
        }
    }

    //Called to exit the program. A mask is used to extract the error code and set it.
	public int funcExit(int arg) {
        
        int mask = 0xff;
        this.exitCode = arg & mask;
        
        return -1;
        
    }
	//Swaps the topmost stack value with the penultimate value
	public int funcSwap(){
		if(this.sp ==1023){
			return 0;
		}else{
			int temp = this.memory[this.sp];
			this.memory[this.sp] = this.memory[this.sp + 1];
			this.memory[this.sp + 1] = temp;
		}
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
		left=peek(0);
		right=peek(1);
		condition=cmd;
		condition&=15;
		
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
            jump(arg);
        }
       
        return 0;
    }
    public int funcIfNz(int arg)
    {
        int value = peek(0);
 
        if (value != 0)
        {
            jump(arg);
        }
 
        return 0;
    }
    public int funcIfMi(int arg)
    {
        int value = peek(0);
        
        if (value < 0)
        {
            jump(arg);
        }
 
        return 0;
    }
    public int funcIfPl(int arg)
    {
        int value = peek(0);
 
        if (value >= 0)
        {
            jump(arg);
        }
 
        return 0;
    }
   
    public int funcDup(int arg)
    {
        int value = peek(arg);
       
        push(value);
       
        return 0;
    }
   
    public int funcPrint(int arg)
    {
        if (arg != 0)
        {
            throw new IllegalArgumentException();
        }
       
        System.out.println("Vmach print: " + peek(0));
       
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
