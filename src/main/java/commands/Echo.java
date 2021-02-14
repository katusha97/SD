package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class Echo extends AbstractCommand {

    public Echo(List<String> s) {
        super(s, "echo");
    }

    @Override
    public InputStream call(InputStream input) throws Exception {
        String string = String.join(" ", args);
        return new ByteArrayInputStream(string.getBytes());
    }
}
