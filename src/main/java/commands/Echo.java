package main.java.commands;

import main.java.Arguments;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Echo extends AbstractCommand {

    public Echo(final Arguments s) {
        super(s, "echo");
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal) throws Exception {
        String string = "";
        for (int i = 0; i < args.size(); i++) {
            string += args.get(i, dictVal) ;
            if (args.size() - i > 1) {
                string += " ";
            }
        }
        return new ByteArrayInputStream(string.getBytes());
    }
}
