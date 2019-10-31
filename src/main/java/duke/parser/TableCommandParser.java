package duke.parser;

import duke.logic.commands.Command;
import duke.logic.commands.TableCommand;

public class TableCommandParser {

    public Command parse() {
        return new TableCommand();
    }
}
