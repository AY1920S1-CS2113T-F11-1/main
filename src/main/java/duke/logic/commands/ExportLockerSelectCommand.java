package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

public class ExportLockerSelectCommand extends Command {
    private final String argument;
    public static final String INVALID_FORMAT = " Invalid command format for exporting selection of csv file. "
            + "You must key in 'exports' with tags such as 'Locker,Name,Zone' etc.";
    public static final String COMMAND_WORD = "exports";


    public ExportLockerSelectCommand(String arg) {
        requireNonNull(arg);
        this.argument = arg;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.exportSelect();
        storage.exportSelection(lockerList,argument);
    }
}
