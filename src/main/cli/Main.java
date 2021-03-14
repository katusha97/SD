package cli;

import java.util.Scanner;

import static cli.Utils.getString;


public class Main {

    public static void main(final String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Executor executor = new Executor();
        while (scanner.hasNext()) {
            String inputString = scanner.nextLine();
            try {
                System.out.println(getString(executor.execute(inputString, System.in)));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
