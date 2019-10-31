package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.InUseLocker;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

public class DeleteUsageCommand extends Command {

    private final SerialNumber serialNumberToDeleteUsage;

    public DeleteUsageCommand(SerialNumber serialNumber) {
        this.serialNumberToDeleteUsage = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        Locker lockerToDelete = CommandCheck.getLockerToEdit(lockerList,serialNumberToDeleteUsage);
        if (!(lockerToDelete instanceof InUseLocker)) {
            throw new DukeException(" usage of only an in-use locker can be deleted");
        }
        lockerList.addLockerInPosition(new Locker(lockerToDelete.getSerialNumber(),
                lockerToDelete.getAddress(),lockerToDelete.getZone(),new Tag(Tag.NOT_IN_USE)),
                lockerList.getIndexOfLocker(lockerToDelete));
        ui.showDeleteUsage();
        storage.saveData(lockerList);
    }
}
