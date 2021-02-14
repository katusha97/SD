package main.java.commands;;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class Cat extends AbstractCommand{

    public Cat(List<String> s) {
        super(s, "cat");
    }

    @Override
    public InputStream call(InputStream input) throws Exception {
        if (args.size() == 0) {
            return input;
        }
        return new FileInputStream(args.get(0));
    }
}
