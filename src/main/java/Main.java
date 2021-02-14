package main.java;

import main.java.commands.Command;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        Factory factory = new Factory();
        Parser parser = new Parser(factory);
        List<Command> commandList = parser.parse(inputString);
        InputStream input = System.in;
        for (Command command : commandList) {
            input = command.call(input);
        }
        String res = getString(input);
        System.out.println(res);
    }
}
