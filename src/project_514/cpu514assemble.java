package project_514;

/**
 * @author Afize OZCAN & Daglar Berk ERDEM
 * @class cpu514assemble takes instructions from Prog.asm file, convert them to 
 * binary codes and writes them to Prog.bin file,
 * next executes the binary codes
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.FileWriter;

public class cpu514assemble {
    public static void main(String[] args) throws IOException {
        String file = "src/case/add.asm";
        Path path = Paths.get(file);
        List<String> lines = Files.readAllLines(path);
        String[] assemble = new String[lines.size()];
        assemble = lines.toArray(assemble);
        String[][] seperatedAssemble= prepareArray(assemble);       //keeps instructions and operands
        String[][] opcodePart = opcode(seperatedAssemble);          //keeps opcode's
        String[][] adressing= findAdressingMode(seperatedAssemble);     //keeps adressing types
        String[][] label= findLabel(seperatedAssemble);                 //keeps labels and their values
        String operand[][] = findOperand(seperatedAssemble, label);     //keeps operands and their values
        FileWriter writer = new FileWriter("src/add.bin");
        for (int index = 0; index < seperatedAssemble.length; index++) {
            if (!opcodePart[index][1].equals("null"))
                writer.write(opcodePart[index][1] + adressing[index][1] + operand[index][1] + System.lineSeparator());
            else
                continue;
        }
        writer.close();
    }

    /**
     * @method prepareArray seperate insturction lines to two part which are insturictions and operands
     * @param initialArray contains one line for each instructionline
     * @return finalArray which is splitted format of the initialArray
     */
    public static String[][] prepareArray(String initialArray[]) {
        String[][] finalArray = new String[initialArray.length][2];
        for (int index = 0; index < initialArray.length; index++) {
            String[] arrOfStr = initialArray[index].split(" ", 2);
            finalArray[index][0] = arrOfStr[0];     //first part of the insturction line
            if (arrOfStr.length > 1) {
                finalArray[index][1] = arrOfStr[1];     //second part of the instruction line
            } else if (arrOfStr[0].equals("HALT")) {
                finalArray[index][1] = "null";
            } else
                finalArray[index][1] = String.valueOf(Integer.toHexString(index));
        }
        return finalArray;
    }

    /**
     * @method opcode finds binary codes of the insturictions
     * @param finalArray is an array which keeps insturctions
     * @return opcode returns converted insturctions to binary codes
     */
    public static String[][] opcode(String[][] finalArray) {
        String[][] opcode = new String[finalArray.length][2];
        //instruction array keeps instructions and their binary codes
        String[][] instruction = {{"HALT", "000001"}, {"LOAD", "000010"}, {"STORE", "000011"}, {"ADD", "000100"}, {"SUB", "000101"},
                {"INC", "000110"}, {"DEC", "000111"}, {"XOR", "001000"}, {"AND", "001001"}, {"OR", "001010"}, {"NOT", "001011"}, {"SHL", "001100"},
                {"SHR", "001101"}, {"NOP", "001110"}, {"PUSH", "001111"}, {"POP", "010000"}, {"CMP", "010001"}, {"JMP", "010010"},
                {"JZ", "010011"}, {"JE", "010011"}, {"JNZ", "010100"}, {"JNE", "010100"}, {"JC", "010101"}, {"JNC", "010110"},
                {"JA", "010111"}, {"JAE", "011000"}, {"JB", "011001"}, {"JBE", "011010"}, {"READ", "011011"}, {"PRINT", "011100"}};
        //for loop is used to find binary codes of the each instruction which are in prog.bin
        for (int index = 0; index < finalArray.length; index++) {
            for(int instructionIndex=0;instructionIndex<instruction.length;instructionIndex++) {
                   if(finalArray[index][0].equals(instruction[instructionIndex][0]) ) {
                       opcode[index][0] = finalArray[index][0];
                       opcode[index][1] = instruction[instructionIndex][1];
                       break;
                   }
                   else {
                       opcode[index][0] = finalArray[index][0];
                       opcode[index][1] = "null";
                   }
            }
        }
        return opcode;
    }

    /**
     * @method findAdressingMode helps to find adressing types of the insturctions
     * @param seperatedAssemble is an array which keeps whole insturctions which are in Prog.bin
     * @return result returns adressing modes of the each insturction
     */
    public static String[][] findAdressingMode(String[][] seperatedAssemble) {
        String[][] result = new String[seperatedAssemble.length][2];
        for (int index = 0; index < seperatedAssemble.length; index++) {
            if (seperatedAssemble[index][1].equals("A") || seperatedAssemble[index][1].equals("B") || seperatedAssemble[index][1].equals("C") || seperatedAssemble[index][1].equals("D") || seperatedAssemble[index][1].equals("E") || seperatedAssemble[index][1].equals("S")) {
                result[index][0] = seperatedAssemble[index][0];
                result[index][1] = "01";
            } else if ((seperatedAssemble[index][1].contains("A") || seperatedAssemble[index][1].contains("B") || seperatedAssemble[index][1].contains("C") || seperatedAssemble[index][1].contains("D") || seperatedAssemble[index][1].contains("E") || seperatedAssemble[index][1].contains("S")) && seperatedAssemble[index][1].contains("[")) {
                result[index][0] = seperatedAssemble[index][0];
                result[index][1] = "10";
            } else if ((seperatedAssemble[index][1].contains("[")) || seperatedAssemble[index][1].contains("]")) {
                result[index][0] = seperatedAssemble[index][0];
                result[index][1] = "11";
            } else {
                result[index][0] = seperatedAssemble[index][0];
                result[index][1] = "00";
            }
        }
        return result;
    }

    /**
     * @method findOperand provides to find binary codes of the operands
     * @param seperatedAssemble is an array which keeps whole insturctions which are in Prog.bin
     * @param label keeps labels and their values
     * @return result returns operands' binary codes
     */
    public static String[][] findOperand(String[][] seperatedAssemble, String[][] label) {
        String[][] result = new String[seperatedAssemble.length][2];
        String character = null;
        for (int index = 0; index < seperatedAssemble.length; index++) {
            result[index][0]=seperatedAssemble[index][1];
            if (seperatedAssemble[index][1].contains("PC")|| seperatedAssemble[index][1].contains("[PC]") )
                character = "0000";
            else if (seperatedAssemble[index][1].equals("A") || seperatedAssemble[index][1].contains("[A]"))
                character = "0001";
            else if (seperatedAssemble[index][1].equals("B") || seperatedAssemble[index][1].contains("[B]"))
                character = "0002";
            else if (seperatedAssemble[index][1].equals("C") || seperatedAssemble[index][1].contains("[C]"))
                character = "0003";
            else if (seperatedAssemble[index][1].equals("D") || seperatedAssemble[index][1].contains("[D]"))
                character = "0004";
            else if (seperatedAssemble[index][1].equals("E") || seperatedAssemble[index][1].contains("[E]"))
                character = "0005";
            else if (seperatedAssemble[index][1].equals("S") || seperatedAssemble[index][1].contains("[S]"))
                character = "0006";
            else if (seperatedAssemble[index][1].contains("'")) {
                character = seperatedAssemble[index][1].replace("'", "");
                character = String.format("%H", character);
            } else if (seperatedAssemble[index][1].contains("[") || seperatedAssemble[index][1].contains("]")) {
                character = seperatedAssemble[index][1].replace("[", "");
                character=character.replace("]", "");
            }
            else if (seperatedAssemble[index][1].equals("null")) {
                character = "0000";
            } else if(label.length>0){
                for (int labelIndex = 0; labelIndex < label.length; labelIndex++) {
                    if (seperatedAssemble[index][1].equals(label[labelIndex][0])) {
                        character = label[labelIndex][1];
                        break;
                    }
                    else
                        character=seperatedAssemble[index][1];
                        continue;
                }
            }
            else
                character=seperatedAssemble[index][1];
                int operand = (Integer.parseInt(character, 16)); //converts string to hex
                result[index][1] = Integer.toBinaryString(operand);     ////converts hex number to binary
            //opcode size should be 16, so it is formatted. The beginning of the string value filled with 0
            if(result[index][1].length()<16)
                result[index][1] = String.format("%0" + (16 - result[index][1].length()) + "d%s", 0, result[index][1]);
            }

        return result;
    }

    /**
     * @method findLabel helps to find labels and their values
     * @param seperatedAssemble is an array which keeps whole insturctions which are in Prog.bin
     * @return labelList returns labels which are in the prog.ams file and values of the labels
     */
        public static String[][] findLabel(String[][] seperatedAssemble){
            int counter = 0;
            String[][] labelList;
            for (int seperatedAssembleIndex = 0; seperatedAssembleIndex < seperatedAssemble.length; seperatedAssembleIndex++) {
                if (seperatedAssemble[seperatedAssembleIndex][0].contains(":")) {
                    counter++;
                } else
                    continue;
            }
            labelList = new String[counter][2];
            counter = 0;
            for (int index = 0; index < seperatedAssemble.length; index++) {
                if (seperatedAssemble[index][0].contains(":")) {
                    labelList[counter][0] = seperatedAssemble[index][0].replace(":", "");
                    labelList[counter][1] = String.valueOf(Integer.toHexString(3 * (index - counter)));
                    counter++;
                } else
                    continue;
            }
            return labelList;
        }
    }

