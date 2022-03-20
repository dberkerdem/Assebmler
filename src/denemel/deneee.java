package denemel;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class deneee {
	static int SP=65533,PC=0;
	
	public static void main(String[] args) throws IOException {
		String address ="src/Prog.bin";
		String[] memory=new String[65534];
		String[] nextInstruction=new String[3];
		writeToMemory(address,memory);
		boolean HALT=false;
		int counter=0;
		while(!HALT) {
			nextInstruction=convertToInstruction(memory);
			System.out.println("Instruction "+counter+" is: "+nextInstruction[0]+" "+nextInstruction[1]+" "+nextInstruction[2]);
			counter++;
			if(nextInstruction[0]==null) {
				HALT=true;
			}
			
		}
		System.out.println(PC);
		System.out.println(Integer.parseInt("1111111111111111",2));
		System.out.println(new BigInteger(String.valueOf(SP),10).toString(2));
		String A=new BigInteger(String.valueOf(SP),10).toString(2);
		System.out.println(memory[binToDec(A)]+memory[binToDec(A)-1]);
		System.out.println((char)binToDec("101101"));
		//System.out.println(nextInstruction[2].equals("0000000000000011"));
		}
	public static String binToHex(String binary) {
		return new BigInteger(binary,16).toString(10);
	}
	public static void writeToMemory(String address,String[] memory) throws IOException {
		Path path = Paths.get(address);
        List<String> instructions = Files.readAllLines(path);
        //memory[memory.length-1]=null;
        int index=memory.length-2;        
        for(String s:instructions) {
        	memory[index]= s.substring(0,8);
        	memory[index-1]= s.substring(8,16);
        	memory[index-2]= s.substring(16,24);
        	index-=3;
        }
	}
	public static String[] convertToInstruction(String[] memory){
		String[] nextInstruction=new String[3];
			if(memory[memory.length-2-PC]==null) {
				nextInstruction[0]=null;
				nextInstruction[1]=null;
				nextInstruction[2]=null;
				PC+=3;
			}
			else{
				nextInstruction[0]=memory[memory.length-2-PC].substring(0,6);
				nextInstruction[1]=memory[memory.length-2-PC].substring(6,8);
				nextInstruction[2]=memory[memory.length-2-PC-1]+memory[memory.length-2-PC-2];
				PC+=3;
			}
			return nextInstruction;
	}
	public static int binToDec(String binary) {
		return Integer.parseInt(binary,2);
	}
}
