package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.storage.Storage;
import duke.ui.Ui;

public class ExportLockerSelectCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.exportSelect();
        storage.exportSelection(lockerList,ui.readCommand());
    }
}
