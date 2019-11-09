package duke.parser;

import duke.logic.commands.ExportLockerSelectCommand;

public class ExportLockerSelectCommandParser {

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     * @param arguments this is the user input string for the tags that the user wants
     */

    public ExportLockerSelectCommand parse(String arguments)  {
        return new ExportLockerSelectCommand();
    }
}
