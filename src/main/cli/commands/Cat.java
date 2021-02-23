package cli.commands;

import cli.Arguments;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

class Cat extends AbstractCommand {

    public Cat(final Arguments args) {
        super(args, "cat");
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal)
            throws Exception {
        if (args.size() == 0) {
            return input;
        }
        return new FileInputStream(args.get(0, dictVal));
    }
}
