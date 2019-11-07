package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.storage.FileHandling;
import duke.ui.Ui;


public class DeleteLockerCommand extends Command {

    private final SerialNumber serialNumberToDelete;

    public DeleteLockerCommand(SerialNumber serialNumber) {
        this.serialNumberToDelete = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        Locker lockerToDelete = lockerList.getLockerToEdit(serialNumberToDelete);
        lockerList.deleteLocker(lockerToDelete);
        ui.deleteMessage(lockerList.numLockers(), lockerToDelete.toString());
        storage.saveData(lockerList);
    }
}
