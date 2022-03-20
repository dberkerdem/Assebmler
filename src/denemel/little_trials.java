package denemel;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class little_trials {
	public static void main(String[] args) throws IOException {
		String A="1000000000100101";
//		//increase operation below from binary string to binary string
//		System.out.println("A was: "+ A +" , "+(char)binToDec(A));
//		//A=decToBin(String.valueOf(binToDec(A)+1));
//		A=Integer.toBinaryString(0x10000 | binToDec(A)+1).substring(1);
//		System.out.println("A after increased: "+A+ ", "+(char)binToDec(A));
//		String C= A;
//		System.out.println(C);
//		int ZF=0;
//		ZF= binToDec("000000001")==0? 1:0;
//		System.out.println("Zero flag is: "+ZF);
		int SF= A.charAt(0)=='1'?1:0;
//		System.out.println("Sign flag is: "+SF);
//		System.out.println(Integer.toBinaryString(0x10000 | 5).substring(1));
//		System.out.println(Integer.toBinaryString(0x10000 | -2).substring(1));
		String D="11111011";
		long l = Long.parseLong(Integer.toBinaryString(-5), 2);
		System.out.println("l is: "+l);
		int i = (int) l;
		System.out.println("i is: "+i);
		SF=D.length()==32?1:0;
		System.out.println("Sign flag is: "+SF);
		System.out.println("-5 with toString method: "+Integer.toString(-5, 2));
		System.out.println("-5 with toBinaryString method: "+Integer.toBinaryString(0x10000|5).substring(1));
		//System.out.println("parseInt method: "+Integer.parseInt("1111111111111111111111111111011", 2));
		System.out.println("parseUnsignedInt method:"+Integer.parseUnsignedInt("11111111", 2));
		System.out.println("Big Integer method: "+new BigInteger(String.valueOf(5),10).setBit(16).toString(2).substring(1));
		System.out.println(Integer.toBinaryString(0x10000|5).substring(1).equals(new BigInteger(String.valueOf(5),10).setBit(16).toString(2).substring(1)));
		//bitwise trials
		int s=(Integer.parseUnsignedInt("0000000000001110",2)&Integer.parseUnsignedInt("0000000000001010",2));
		System.out.println("s is: "+s);
		System.out.println("Bitwise and operation is: "+Integer.toBinaryString(0x10000 |s).substring(1));
		String notOpPerformed="0000000000001010";
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
		System.out.println("My way is: "+notOpPerformed);
		int d=15>>>1;//result:7
		System.out.println("Bitwise SHR operation is: "+ (d));
		d=d<<1;//result: 15
		System.out.println("Bitwise SHR operation is: "+ (d));
		System.out.println("Bitwise XOR operation is: "+ Integer.toBinaryString(0x10000|d^d).substring(1).length());
		Scanner read=new Scanner(System.in);
		System.out.println("Read instruction called. Please enter char");
		String readtrial=Integer.toBinaryString(0x10000|(int)(read.next().charAt(0))).substring(1);
		System.out.println("Output of read instruction is: "+readtrial
				);
		read.close();
//		//HEX TO BIN CONVERRTER
//		String address="src/inc_hex.bin";
//		Path path = Paths.get(address);
//        List<String> instructions = Files.readAllLines(path);
//		for(String s:instructions) {
//			System.out.println(new BigInteger(s,16).setBit(24).toString(2).substring(1));
//		}
//		System.out.println(new BigInteger("0",10).setBit(24).toString(2).substring(1));
	}
	public static int binToDec(String binary) {
		return Integer.parseInt(binary,2);
	}
	private static String decToBin(String decimal) {
		return new BigInteger(decimal,10).toString(2);
	}
}
