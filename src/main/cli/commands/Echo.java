package cli.commands;

import cli.Arguments;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

class Echo extends AbstractCommand {

    public Echo(final Arguments s) {
        super(s, "echo");
    }

    @Override
    public InputStream call(final InputStream input, final Map<String, String> dictVal)
            throws Exception {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            string.append(args.get(i, dictVal));
            if (args.size() - i > 1) {
                string.append(" ");
            }
        }
        return new ByteArrayInputStream(string.toString().getBytes());
    }
}
