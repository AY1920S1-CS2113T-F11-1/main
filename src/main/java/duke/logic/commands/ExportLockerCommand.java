package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.storage.ExportCsv;
import duke.ui.Ui;

public class ExportLockerCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        ui.exportMessage();
        ExportCsv.exportLockers(lockerList.getLockerList());

    }
}
