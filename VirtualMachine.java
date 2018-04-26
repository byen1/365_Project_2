/*
*
* CS365 Project 2 
*
* Contributors: Alex Teepe, Brandon Yen, Austin Saporito, Austin Day, and Charles Rizzo
*
* Entry point for jar file that creates a virtual machine to interpret byte code generated from project 1. 
* Can be ran in Verbose mode with -v as second command line arg.
*/
import java.io.*;
import java.util.*;

public class VirtualMachine extends ByteCode {
	//Constructs a VirtualMachine object with 4096 bytes of memory    
    public VirtualMachine() {
        size = 1024;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }
    
	//Constructs a VirtualMachine object with user specified amount bytes of memory    
    public VirtualMachine(int sizeInBytes) {
        size = sizeInBytes/4;
        memory = new int[size];
        sp = size - 1;
        ip = 0;
    }

    //Reads throught bytecode file; Checks to make sure file starts with 0xfeedbeef; Inserts all commands into an ArrayList that is converted into an Array
    public void readFile(String filename) {
        DataInputStream r;
		int fromFile, fileHeaderCheck;
        ArrayList <Integer> instrs = new ArrayList <Integer>();
		
		try{
			r = new DataInputStream(new FileInputStream(filename));
		}catch(IOException ex){
			System.err.println("Unable to open file " + filename);
			return;
		}
		
		fileHeaderCheck = 1;

		do{
			try{
				fromFile = r.readInt();
			}catch(IOException ex){
				break;
			}

			if((fileHeaderCheck == 1) && (fromFile != 0xfeedbeef)){
				System.err.println("Bin file does not start with magic header.");
				return;
			}else if(fileHeaderCheck == 0){
				instrs.add(swap(fromFile));
			}
			
			fileHeaderCheck = 0;
		}while(true);

		try{ r.close(); } catch(IOException ex){}
		
		int []instructions = instrs.stream().mapToInt(Integer::intValue).toArray();
        this.loadInstructions(instructions);        
    }
    
    //Runs the virtual machine
    public int run() {
        
        jump(0);
        try {
            return execLoop();
        } catch (Exception e) {
            System.err.println("Erroroneous instruction, terminating program: " + e.toString());
            return -1;
        }
        
    }

    //Major loop body of the virtual machine that calls Interpret() in ByteCode.java to interpret and run each command.
    public int execLoop() {
        int ipp = this.ip;
        while(interpret() != -1) {
            if (ipp == this.ip) { 
                this.ip++;
            }
            ipp = this.ip;
        }
        return this.exitCode;
        
    }

	//Used to swap byte endianness because Java and C# don't like each other
	public int swap (int value){
		int b1 = (value >>  0) & 0xff;
		int b2 = (value >>  8) & 0xff;
		int b3 = (value >> 16) & 0xff;
		int b4 = (value >> 24) & 0xff;

		return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
	}

}
