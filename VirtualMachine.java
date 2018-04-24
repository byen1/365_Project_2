import java.io.*;
import java.util.*;

public class VirtualMachine extends ByteCode {
    
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

	public int swap (int value){
		int b1 = (value >>  0) & 0xff;
		int b2 = (value >>  8) & 0xff;
		int b3 = (value >> 16) & 0xff;
		int b4 = (value >> 24) & 0xff;

		return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
	}

}
