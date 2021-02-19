package main.java;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        Executor executor = new Executor();
        String res = executor.execute(inputString, System.in);
        System.out.println(res);
    }
}
