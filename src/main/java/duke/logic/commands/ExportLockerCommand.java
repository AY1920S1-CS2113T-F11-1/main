package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.storage.OpenCSVWriterString;
import duke.ui.Ui;


public class ExportLockerCommand extends Command{

    public ExportLockerCommand(){}
    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {

        ui.exportMessage();
        OpenCSVWriterString.exportLockers(lockerList.getLockerList());

    }
}
