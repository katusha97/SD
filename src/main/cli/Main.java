package cli;

import java.util.Scanner;

public class Main {

    public static void main(final String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Executor executor = new Executor();
        while (scanner.hasNext()) {
            String inputString = scanner.nextLine();
            String res = executor.execute(inputString, System.in);
            System.out.println(res);
        }
    }
}
