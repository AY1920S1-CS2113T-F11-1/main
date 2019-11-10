package duke.parser;

import duke.logic.commands.ExportLockerSelectCommand;

import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class ExportLockerSelectCommandParser {

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     * @param args this is the user input string for the tags that the user wants
     */

    public ExportLockerSelectCommand parse(String args) throws DukeException {
        requireNonNull(args);
        if (args.trim().length() == 0) {
            throw new DukeException(ExportLockerSelectCommand.INVALID_FORMAT);
        }
        if (!args.contains("locker")) {
            throw new DukeException(ExportLockerSelectCommand.MISSING_FORMAT);
        }
        return new ExportLockerSelectCommand(args);
    }
}
