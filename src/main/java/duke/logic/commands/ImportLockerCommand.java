package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.storage.ImportCsv;
import duke.ui.Ui;

public class ImportLockerCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        ui.importMessage();
        ImportCsv.importLockers(lockerList.getLockerList());

    }
}
