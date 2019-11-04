package duke.parser;

import duke.logic.commands.ImportLockerCommand;

public class ImportLockerCommandParser {

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     */

    public ImportLockerCommand parse()  {
        return new ImportLockerCommand();
    }
}
