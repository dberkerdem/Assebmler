package project_514;
/**
 * @author Afize OZCAN & Daglar Berk ERDEM
 * @class cpu514exec corresponds to a pseudo assembly code simulator. 
 * Takes instructions from  Prog.bin in binary form and execute them. 
 */
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.*;
import java.util.*;
///ASSUMPTIONS: Memory is 64KB with a size of 65534 slots,each slot is 1Byte.


public class cpu514exec {
	//declaring and creating initialized flag registers, ProgramCounter and StackPointer
	static int ZF=0,CF=0,SF=0,PC=0,SP=65534;
     //declaring and creating initialized control statement
	static boolean HALT=false;
    //declaring and creating initialized registers
	static String A="",B="",C="",D="",E="";
	//declaring and creating memory 
	static String[] memory=new String[65535];
	static String[] stack=new String[65535];
	static Scanner s = new Scanner(System.in);
	//Client Code
	public static void main(String[] args) throws IOException{
		
		//Reading Prog.bin file and writing instructions to the memory
		String address ="src/case/add.bin";
		writeToMemory(address);
        //control loop of the program
		String[] Instruction=new String[3];
		//control loop
		int counter=0;
		System.out.println("Press 0 to Execute all.\nPress 1 to Execute line "
				+ "by line(Demo Mode)");
		boolean demoFlag= s.nextInt()==1? true:false;
		s.nextLine();
		do {
			//demo mode
			if(demoFlag) {
				System.out.println("-------------------------------------------------------");
				Instruction=fetchInstruction();
				execute(Instruction);
				System.out.println("\nIteration "+counter+" performed. Instruction was: "
					+Instruction[0]+" "+Instruction[1]+" "+Instruction[2]);
				counter++;
				System.out.println("registerA: "+A+"\nregisterB: "+B+"\nregisterC: "+C
						+"\nregisterD: "+D+"\nregisterE: "+E+"\nProgram Counter: "+ (memory.length-2-PC)
						+"\nStack Pointer: "+new BigInteger((String.valueOf(SP)),10).toString(16).toUpperCase()
						+"\nZF: "+ZF+" CF: "+CF+" SF: "+SF+"\n");
				System.out.println("Press Enter to continue...");
				s.nextLine();
			}//direct execution
			else {
				Instruction=fetchInstruction();
				execute(Instruction);
			}
		}while((!HALT));
		s.close();
		System.out.println("\n-------------------------------------------------------\n"
				+ "Program executed. Final image of CPU is: \n");
		System.out.println("registerA: "+A+"\nregisterB: "+B+"\nregisterC: "+C
				+"\nregisterD: "+D+"\nregisterE: "+E+"\nProgram Counter: "+ (memory.length-2-PC)
				+"\nStack Pointer: "+new BigInteger((String.valueOf(SP)),10).toString(16).toUpperCase()
				+"\nZF: "+ZF+" CF: "+CF+" SF: "+SF+"\n");
	}
	/**
	 * @method binToDec, converts binary string to decimal integer
	 * @param binary is a String, stores binary representation of a String/Data in String form.
	 * @return binToDec, returns translation of a 2 base inputs 10 base equivalent
	 */
	public static int binToDec(String binary) {
		return Integer.parseUnsignedInt(binary,2);
	}
	/**
	 * @method writeToMemory accesses to a String list directed by input parameter address, 
	 *  divides each line to 3 segments of each 8 bit
	 *  and writes each segment to a static String[] memory.
	 * @param address is a String that directs to the location of the .bin extended file.
	 * @return 
	 */
	public static void writeToMemory(String address) throws IOException {
		Path path = Paths.get(address);
        List<String> instructions = Files.readAllLines(path);
        int index=memory.length-2;//since memory starts from FFFE instead FFFF        
        for(String s:instructions) {//for each loop
        	memory[index]= s.substring(0,8);//8bit
        	memory[index-1]= s.substring(8,16);//8bit
        	memory[index-2]= s.substring(16,24);//8bit
        	index-=3;//updating index
        }
	}
	/**
     * @method fetchInstruction, divide next instruction to be executed
     * three part which are opcode, addressing modes and operands respectively, 
     * and updates PC(program counter)
     * @param 
     * @return fetchInstruction, returns a 1x3 String array called Instruction, which contains
     * opcode, addressing mode, operand with lengths of 6 2 16 respectively.
     */
	public static String[] fetchInstruction(){
		String[] Instruction=new String[3];
			//memory slot is empty
			if(memory[memory.length-2-PC]==null) {
				Instruction[0]=null;//opcode
				Instruction[1]=null;//addressing mode
				Instruction[2]=null;//operand
				HALT=true;//stop CPU
			}//memory slot not empty
			else{
				Instruction[0]=memory[memory.length-2-PC].substring(0,6);//opcode
				Instruction[1]=memory[memory.length-2-PC].substring(6,8);//addressing mode
				Instruction[2]=memory[memory.length-2-PC-1]+memory[memory.length-2-PC-2];//operand
				PC+=3;//updating program counter
			}
			return Instruction;
	}
    /** 
     * @method setFlag, takes an integer as input and determines which flag to be set by comparing this input within boundaries.
     * @param value is an integer that corresponds to the decimal value of the binary result after performing instructional operations
     * @return 
     */
	public static void setFlag(int value) {
		ZF= value==0? 1:ZF;//zero flag		
		SF= value<0?1:SF;//sign flag
		CF=value>65535||value<=-65535?1:CF;//carry flag
	}
    /** 
     * @method menu is takes an integer as input and prints out the corresponding parameters. 
     * Can be seen as an simple UI mechanism in console.
     * @param operation is an integer that corresponds to the user input to display content of CPU segments of interest
     * @return 
     */
	public static void menu(int operation) {
		if(operation==0) {
			System.out.println("registerA: "+A+"\nregisterB: "+B+"\nregisterC: "+C
					+"\nregisterD: "+D+"\nregisterE: "+E+"\nProgram Counter: "+ (memory.length-2-PC)
					+"\nStack Pointer: "+new BigInteger((String.valueOf(SP)),10).toString(16).toUpperCase()
					+"\n");
			
		}else if(operation==1) {
			System.out.println("ZF: "+ZF+" CF: "+CF+" SF: "+SF+"\n");
		}else if(operation==2) {
			System.out.println("registerA: "+A+"\nregisterB: "+B+"\nregisterC: "+C
					+"\nregisterD: "+D+"\nregisterE: "+E+"\nProgram Counter: "+ (memory.length-2-PC)
					+"\nStack Pointer: "+new BigInteger((String.valueOf(SP)),10).toString(16).toUpperCase()
					+"\nZF: "+ZF+" CF: "+CF+" SF: "+SF+"\n");
		}else if(operation==3) {
			System.out.println("Skipped."+"\n");
		}
		else if(operation==4) {
			HALT=true;
			System.out.println("LOOP TERMINATED.\n");
		}
		else {
			System.out.println("Invalid input. Fetching next Instructiın.\n");
		}
	}
	/**
     * @method execute, takes an instruction as input and executes the instruction,updates registers and flags accordingly
     * @param Instruction is an 1x3 String array that contains opcode, addressing mode, operand 
     * with lengths of 6 2 16 respectively.
     * @return 
     */
	public static void execute(String[] Instruction) {
		//nextInstruction has a size of 1x3 which contains opcode, addressing mode 
		//and operand respectively. 
		//nextInstruction[0]-> opcode, 
		//nextInstruction[1]-> addressing mode, 
		//nextInstruction[2]-> operand
		
		//HEX 1-HALT
		if(Instruction[0].equals("000001")) {
			HALT=true;
		}//HEX 2-LOAD
		else if(Instruction[0].equals("000010")) {
			A=execute(Instruction[1],Instruction[2]);
		}//HEX 3-STORE
		else if(Instruction[0].equals("000011")) {
			execute(Instruction,A);
		}//HEX 4-ADD
		else if(Instruction[0].equals("000100")) {
			int result= binToDec(A)+binToDec(execute(Instruction[1],Instruction[2]));
			setFlag(result);
			if(result<0) {
				D=Integer.toBinaryString(0x10000|result).substring(0,16);
				A=Integer.toBinaryString(0x10000|result).substring(16,32);
			}
			else {
				A=Integer.toBinaryString(0x10000|result).substring(1);
			}
		}//HEX 5-SUB
		else if(Instruction[0].equals("000101")) {
			int result= binToDec(A)-binToDec(execute(Instruction[1],Instruction[2]));
			setFlag(result);
			if(result<0) {
				D=Integer.toBinaryString(0x10000|result).substring(0,16);
				A=Integer.toBinaryString(0x10000|result).substring(16,32);
			}
			else {
				A=Integer.toBinaryString(0x10000|result).substring(1);
			}
		}//HEX 6- INC
		else if(Instruction[0].equals("000110")) {
			String temp=execute(Instruction[1],Instruction[2]);
			String increased=Integer.toBinaryString(0x10000 | binToDec(temp)+1).substring(1);
			setFlag(binToDec(increased));
			execute(Instruction, increased);	
		}//HEX 7- DEC
		else if(Instruction[0].equals("000111")) {
			String temp=execute(Instruction[1],Instruction[2]);
			String decreased=Integer.toBinaryString(0x10000 | binToDec(temp)-1).substring(1);
			setFlag(binToDec(decreased));
			execute(Instruction, decreased);
		}//HEX 8- XOR
		else if(Instruction[0].equals("001000")) {
			String temp=execute(Instruction[1],Instruction[2]);
			int result=binToDec(A)^binToDec(temp);
			setFlag(result);
			A=Integer.toBinaryString(0x10000|result).substring(1);
		}//HEX 9- AND
		else if(Instruction[0].equals("001001")) {
			String temp=execute(Instruction[1],Instruction[2]);
			int result=binToDec(A)&binToDec(temp);
			setFlag(result);
			A=Integer.toBinaryString(0x10000|result).substring(1);
		}//HEX A- OR
		else if(Instruction[0].equals("001010")) {
			String temp=execute(Instruction[1],Instruction[2]);
			int result=binToDec(A)|binToDec(temp);
			setFlag(result);
			A=Integer.toBinaryString(0x10000|result).substring(1);
		}//HEX B- NOT
		else if(Instruction[0].equals("001011")) {
			String notOpPerformed=execute(Instruction[1],Instruction[2]);
			int index=0;
			char[] temp=notOpPerformed.toCharArray(); 
			for(char c:temp) {
				if(c=='0') {
					temp[index]='1';
				}
				else {
					temp[index]='0';
				}
				index++;	
			}
			notOpPerformed=String.valueOf(temp);
			setFlag(binToDec(notOpPerformed));
			execute(Instruction, notOpPerformed);
		}//HEX C- SHL
		else if(Instruction[0].equals("001100")) {
			String temp=execute(Instruction[1],Instruction[2]);
			int shifted=binToDec(temp);
			shifted=shifted<<1;
			setFlag(shifted);
			execute(Instruction,Integer.toBinaryString(0x10000|shifted).substring(1));
		}//HEX D- SHR
		else if(Instruction[0].equals("001101")) {
			String temp=execute(Instruction[1],Instruction[2]);
			int shifted=binToDec(temp);
			shifted=shifted>>>1;
			setFlag(shifted);
			execute(Instruction,Integer.toBinaryString(0x10000|shifted).substring(1));
		}//HEX E- NOP
		else if(Instruction[0].equals("001110")) {
			//no operation here
		}//HEX F- PUSH
		else if(Instruction[0].equals("001111")) {
			String temp=execute(Instruction[1],Instruction[2]);
			stack[SP]=temp.substring(0,8);
			stack[SP-1]=temp.substring(8,temp.length());
			SP-=2;
		}//HEX 10- POP
		else if(Instruction[0].equals("010000")) {
			String temp=stack[SP+2]+stack[SP+1];
			execute(Instruction,temp);
			SP+=2;
		}//HEX 11- CMP
		else if(Instruction[0].equals("010001")) {
			int result= binToDec(A)-binToDec(execute(Instruction[1],Instruction[2]));
			setFlag(result);
		}//HEX 12- JMP
		else if(Instruction[0].equals("010010")) {
			PC=binToDec(Instruction[2]);
		}//HEX 13- JZ/JE
		else if(Instruction[0].equals("010011")) {
			PC=ZF==1?binToDec(Instruction[2]):PC;
		}//HEX 14- JNZ/JNE
		else if(Instruction[0].equals("010100")) {
			PC=ZF==0?binToDec(Instruction[2]):PC;
		}//HEX 15- JC
		else if(Instruction[0].equals("010101")) {
			PC=CF==1?binToDec(Instruction[2]):PC;
		}//HEX 16- JNC
		else if(Instruction[0].equals("010110")) {
			PC=CF==0?binToDec(Instruction[2]):PC;
		}//HEX 17- JA
		else if(Instruction[0].equals("010111")) {
			PC=ZF==0&&CF==0?binToDec(Instruction[2]):PC;
		}//HEX 18- JAE
		else if(Instruction[0].equals("011000")) {
			PC=CF==0?binToDec(Instruction[2]):PC;
		}//HEX 19- JB
		else if(Instruction[0].equals("011001")) {
			PC=CF==1?binToDec(Instruction[2]):PC;
		}//HEX 1A- JBE
		else if(Instruction[0].equals("011010")) {
			PC=ZF==1&&CF==1?binToDec(Instruction[2]):PC;
		}//HEX 1B-READ
		else if(Instruction[0].equals("011011")) {
			System.out.println("Read instruction called. Please enter char: ");
			String temp=Integer.toBinaryString(0x10000|(int)s.next().charAt(0)).substring(1);
			execute(Instruction,temp);
		}//HEX 1C- PRINT
		else if(Instruction[0].equals("011100")) {
			System.out.print("\nOutput is: "+(char)binToDec(execute(Instruction[1],Instruction[2])));
		}//Error-Invalid opcode,Opcode out of range
		else {
			System.out.println("Invalid opcode, program terminated");
			HALT=true;
		}
	}//returns binary string
	/**
     * @method execute method overloaded, takes addressing mode and operand as input 
     * and returns 16bit representation of corresponding operand as String      
     * @param addrMode is a String that represents 2bit addressing mode of the Instruction 
     * @param operand is a String that represents 16bit operand of the Instruction
     * with lengths of 6 2 16 respectively.
     * @return execute, returns String representation of 16bit operand that corresponds to the given addrMode and operand
     */
	private static String execute(String addrMode, String operand) {
		//operand is immediate data
		 if(addrMode.equals("00")) {
			return operand;
		}
		//operand is in given in the register
		else if(addrMode.equals("01")) {
			if(operand.equals("0000000000000001")) {
				return A;  
			}		
			else if(operand.equals("0000000000000010")) {
				return B;
			}
			else if(operand.equals("0000000000000011")) {
				return C;
			}
			else if(operand.equals("0000000000000100")) {
				return D;
			}
			else if(operand.equals("0000000000000101")) {
				return E;
			}
			else if(operand.equals("0000000000000110")) {
				return Integer.toBinaryString(0x10000|SP).substring(1);
			}			
		}
		//operand’s memory address is given in the register
		else if(addrMode.equals("10")) {
			//registerA
			if(operand.equals("0000000000000001")) {
				return memory[binToDec(A)];  
			}//registerB
			else if(operand.equals("0000000000000010")) {
				return memory[binToDec(B)];  
			}//registerC
			else if(operand.equals("0000000000000011")) {
				return memory[binToDec(C)]; 
			}//registerD
			else if(operand.equals("0000000000000100")) {
				return memory[binToDec(D)];  
			}//registerE
			else if(operand.equals("0000000000000101")) {
				return memory[binToDec(E)]; 
			}//registerSP
			else if(operand.equals("0000000000000110")) {
				return memory[SP];
			}
		}//operand is a memory address
		else if(addrMode.equals("11")) {
			return memory[binToDec(operand)];
		}//Error- Invalid addressing mode
		else {
			System.out.println("Invalid addressing mode: "+ addrMode +", Type: "+addrMode.getClass().getSimpleName());
			return null;
		}
		return null;
	}
	/**
     * @method execute method overloaded, takes 1x3 String array Instruction and String newBinary, 
     * updates memory or registers with newBinary by considering Instruction      
     * @param Instruction is an 1x3 String array that contains opcode, addressing mode, operand 
     * with lengths of 6 2 16 respectively.
     * @param newBinary is an String that represents 16bit operand evaluated by performing opcode operations
     * @return execute, returns String representation of 16bit operand that corresponds to the given addrMode and operand
     */
	private static void execute(String[] Instruction,String newBinary) {
		//Immediate addressing mode
		if(Instruction[1].equals("00")) {
			Instruction[2]=newBinary;
		}//Register addressing mode
		else if(Instruction[1].equals("01")) {
			//register A
			if(Instruction[2].equals("0000000000000001")) {
				A=newBinary;
			}//register B
			else if(Instruction[2].equals("0000000000000010")) {
				B=newBinary;
			}//register C
			else if(Instruction[2].equals("0000000000000011")) {
				C=newBinary;
			}//register D
			else if(Instruction[2].equals("0000000000000100")) {
				D=newBinary;
			}//register E
			else if(Instruction[2].equals("0000000000000101")) {
				E=newBinary;
			}//register S
			else if(Instruction[2].equals("0000000000000110")) {
				SP=binToDec(newBinary);
			}
		}//operands memory address is given in register
		else if(Instruction[1].equals("10")){
			//register A
			if(Instruction[2].equals("0000000000000001")) {
				memory[binToDec(A)]=newBinary;
			}//register B
			else if(Instruction[2].equals("0000000000000010")) {
				memory[binToDec(B)]=newBinary;
			}//register C
			else if(Instruction[2].equals("0000000000000011")) {
				memory[binToDec(C)]=newBinary;
			}//register D
			else if(Instruction[2].equals("0000000000000100")) {
				memory[binToDec(D)]=newBinary;
			}//register E
			else if(Instruction[2].equals("0000000000000101")) {
				memory[binToDec(E)]=newBinary;
			}//register S
			else if(Instruction[2].equals("0000000000000110")) {
				memory[SP]=newBinary;
			}
		}//operand is a memory address
		else if(Instruction[1].equals("11")) {
			memory[binToDec(Instruction[2])]=newBinary;
		}//error
		else {
			System.out.println("Invalid addressing mode.");
		}
	}
}

