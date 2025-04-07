package common;

import java.util.Scanner;

public class InputHelper {
    private static final Scanner sc = new Scanner(System.in);

    public static Scanner getScanner() {
        return sc;
    }

    public static String nextLine() {
        return sc.nextLine();
    }

    public static int nextInt() {
        return sc.nextInt();
    }

    public static double nextDouble() {
        return sc.nextDouble();
    }

    public static char nextChar() {
        return sc.next().charAt(0);
    }

    public static void clearBuffer() {
        sc.nextLine();
    }
} 
