# Computer Systems Project
## **Authors:** Afize Ozcan & Daglar Berk Erdem 
## Introduction
The aim of this project is to create an assembler simulator which reads instructions, converts them
into binary, and executes accordingly. The program is separated into two parts, that are converting
instructions into binary and executing them.
## Part 1: Converting Instructions (cpu514assemble.java)
The aim of the first part is to convert given instructions into binary. Main method gathers instructions
from the instruction file, converts them into binary and writes them to a file in binary form. In order to
perform these operations, Java's built-in read and write methods are utilized. Moreover, 5 methods
are created to convert instructions into binary form. Further details are given below while describing
the structure of the algorithm.
First of all, to read instructions and convert them into convenient data form, the readAllLines method
is utilized, which reads the lines and stores them to a list. More specifically, the readAllLines method
of the Files class reads instructions from the prog.asm file and converts them to a String array by
using the toArray method.
Secondly, a new array is created to separate instructions and operand fields. The prepareArray
method is written for this goal. At the beginning, new array is created with 2 columns to keep both
instruction and operands. Next, a for loop is used to divide each line one by one. The lines are divided
into two parts based on the space character, it is assumed that there is no space character at the
beginning of the line.The first part is kept at arrOfStr arrays’ 0. Index, and the second part is kept at
the 1. index of the arrOfStr array. Then, their values are set to the new array’s first which is instruction
field and the second column, which is the operand field. At the end, method returns a new array with 2
columns which contains both instructions and operands at the different columns.
Thirdly, the opcode method is created to find the binary representation of the instruction. The
method’s input is an array which includes instruction codes. Instructions and their binary codes are
defined in the instruction array. For each index of the input array are matched with the instruction
array by using for loop and if condition. When the value of the input array is found at the instruction
array, its value is set to the opcode array which keeps the instructions of the input array and their
opcodes. When there is not any value at the instruction array against the instruction, “null” is set to the
opcode array. This method returns instructions and binary codes.
Moreover, findAdressingMode method is created to find addressing binary codes of the instructions.
The input of the method is an array which contains whole instructions. The second part of the array
includes operands which help to find addressing modes. When the operand contains ;
* “[“ character, it’s addressing mode becomes “10”(operand’s memory address is given in the register).
* "PC”,“A”,”B”,”C”,”D”,”E”,”S”, it’s addressing mode becomes “01”(operand is in given in the register),
* “PC” or “A” or ”B” or” C” or ”D” or ”E” or ”S” and “[“ character, it’s value becomes “11”(operand is a memory address) 

And set to the array which returns opcode’s binary codes.
The other method provides to find values of the labels. The method input is an array. When the
element of the array contains “:” character, the element is kept. All of the elements are controlled by
using for loop. At the end of the for loop, it is known that how many labels are in the input array. Then,
new array is created with size number of the labels. After that, the values of the labels are calculated.
The calculation should be 3*(index number of the array-number of the labels until this label). At the
end of the calculation, values are set to the result array which contains labels and their values.
In the last method, findoperand method is developed to find binary codes of the operands. The
method input is two arrays; first one is instruction array and the second one is label array. An array
and a variable are defined at the beginning of the method. The array is created for keeping results
which are operands and their binary codes that are generated at the end of the for loop in the method.
The variable is used to save operands value with string type. Operands can be numbers, character,
label, register or show the memory address. Thus, If loops are used to determine operands values.
PC, A, B,C, D, E and S are registers, their opcode values are given in the assignment. When the
operand is a character, it’s value converted to the decimal number with string type. Also, when the
opcode shows a label, it’s value is found from label array. At the end of the for loop, hex values with
string type converts to the binary numbers. Then, binary code's size is fixed to 16 characters. The left
side of the binary number is filled with 0 until it reaches to the 16 character. Finally, the result is set to
the result array which also returns data of the method.
Finally, all instructions are converted to the binary codes and they are written to the file. The return
values of opcode, findAdressingMode and findoperand method are assembled and written to the
Prog.bin file.
## Exercuting Intructions (cpu514exec.java)
The aim of the second part is to take instructions from a .bin extended file in binary form and execute
them. To start with, registers and the flags of the CPU are declared as global variables. Moreover,
memory and the stack are represented as global String arrays with a size of 1x65535, that represents
a 64K memory and each element of the arrays is assumed to be 1byte. In order to execute given
binary instructions, 6 different methods are created and one of them is overloaded 3 times. Further
details are given below while describing the structure of the algorithm.
The main method calls the writeToMemory method to write binary instructions from the file of interest
to the memory. After storing instructions into the memory, the program asks the user for mode of
operation, which can be direct or line by line execution and stores the choice into a Boolean called
demoFlag . Then, the procedure enters a do-while-loop, which is controlled by a global Boolean HALT.
This loop contains an if-else loop which controls mode of operations with demoFlag as the input
parameter. The logic behind the mode of is that both fetches the next instruction in the memory by
using fetchInstruction() method as long as the do-while loop proceeds. Then executes the instruction
by using execute(String s) method. The only difference between mode of operation within the do-while
loop is that, demo mode displays the latest image of the flags and registers and waits till user presses
enter to proceed to the next iteration. After the do-while loop is exited, the program closes the scanner
and prints out the final image of the CPU, i.e. registers and flags.
The binToDec method converts binary string to a decimal integer by using built-in
Integer.parseUnsignedInt() method and returns the translation of a 2base input String’s 10 base
equivalent integer representation.
The writeToMemory method accesses a String list directed by input parameter address and divides
each line to 3 segments of each 8 bit. Then writes each segment to the static String array memory.
Note that this method also utilizes Java’s built-in method readAllLines and remains the first slot of the
memory empty, i.e. the address FFFF.
The fetchInstruction method reads the instruction pointed by the program counter from the memory 3
times, since each instruction is 24 bit. Then divides it into a 1x3 String, which contains opcode,
addressing mode, and operand with lengths of 6 2 16 respectively and increases the global integer
PC by 3.
The setFlag method takes the result of an operation performed that was dictated by the current
instruction, and compares it logically with boundaries by using ternary operator. If the value is equal to
0, then the zero flag is set. Similarly if the value is smaller than the sign flag is set. In order to set the
carry flag, the resulting binary must be smaller or greater than 10000h which is equal to 65535 in
decimal. Otherwise, all flags are set to remain the same.
The execute method is overloaded 3 times within the cpu514exec class. The first execute method
contains an if-elseif-else loop. This method takes an instruction as input which contains the returned
parameter from fetchInstruction method, i.e. 1x3 String array. Then compares the opcode of the
instruction with 6bit binary numbers ranging between 000001 to 011100 to identify it. Note that each
binary number corresponds to a different operation of the CPU. When the opcode matches with a
binary number, the program enters the corresponding if-elseif node and fetches the operand by using
the second execute() method mentioned below. To be clear, it returns a16bit operand with a given
addressing mode and operand. This operand is then converted to a decimal value by using binToDec
method and inserted into the setFlag method to determine whether flags have to be set or not.
The second execute method takes the addressing mode and operand of the instruction as input and
returns a 16bit binary number pointed by input addressing mode and operand. Addressing mode is
compared with 2bit binary numbers ranging between 00 to 11. When the addressing mode matches
with a binary number, the program enters the corresponding if-elseif loop and compares the operand
in an internal if-elseif loop in order to determine what to return.
The third execute method takes a String array Instruction and String newBinary as input and updates
an address. Furthermore, the method determines which address to be updated by considering
addressing mode and the operand of input instruction. After matching addressing mode with an
if-elseif loop, the program enters the internal if-elseif loop by comparing the operand of the instruction.
When the operand is matched, the program sets the resulting address to the input newBinary, which
represents a 16bit binary number, which was obtained by performing arithmetic operations in the first
execute method.