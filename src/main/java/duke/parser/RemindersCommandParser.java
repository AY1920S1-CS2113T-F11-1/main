package duke.parser;

import duke.logic.commands.Command;
import duke.logic.commands.RemindersCommand;

public class RemindersCommandParser {

    /**
     * This function is used to parse the user input for editing the status of a locker.
     * @return reference to the class RemindersCommand.
     */

    public Command parse() {

        return new RemindersCommand();

    }

}
