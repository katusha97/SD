package cli;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {

    public static String getString(InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
