package zombicide.util.listchooser;

import java.util.Scanner;

/**
 * A utility class for entering strings or integers on standard input
 */

public class Input {

    private Scanner scanner;

    public Input() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows entry of a string on standard input
     *
     * @return the string entered
     */
    public static String readString() {
        return new Input().localReadString();
    }

    private String localReadString() {
        return this.scanner.next();
    }


    /**
     * Allows entry of an int on standard input
     *
     * @return the int entered
     */
    public static int readInt() throws java.io.IOException {
        return new Input().localReadInt();
    }

    private int localReadInt() throws java.io.IOException {
        try {
            return this.scanner.nextInt();
        } catch (Exception e) {
            this.scanner.skip(".*");
            throw new java.io.IOException();
        }
    }

}