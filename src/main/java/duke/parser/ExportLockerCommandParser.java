package duke.parser;

import duke.logic.commands.ExportLockerCommand;

import java.util.ArrayList;
import java.util.List;

public class ExportLockerCommandParser {
    private List<String> splitInput = new ArrayList<>();

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     */

    public ExportLockerCommand parse()  {
        return new ExportLockerCommand();
    }
}
