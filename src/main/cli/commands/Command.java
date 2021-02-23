package cli.commands;

import cli.Arguments;
import cli.exceptions.WrongArgumentsException;

import java.io.InputStream;
import java.util.Map;

/**
 * Интерфейс Command должны реализовывать все команды, которые будут поддерживаться нашим CLI.
 */
public interface Command {

    /**
     * Каждая команда на основе Input-а и аргументов порождает новый Output
     *
     * @param input
     * @param dictVal - словарь, в котором по названию переменной хранится ее значение на текущий
     *                момент.
     * @return InputStream stdout команды
     * @throws Exception
     * @throws WrongArgumentsException
     */
    InputStream call(InputStream input, Map<String, String> dictVal)
            throws Exception, WrongArgumentsException;

    /**
     * @return имя команды
     */
    String getName();

    /**
     * @return аргументы команды
     */
    Arguments getArguments();
}
