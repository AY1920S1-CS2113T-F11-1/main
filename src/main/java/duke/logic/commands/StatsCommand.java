package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.storage.Stats;
import duke.ui.Ui;

public class StatsCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        ui.readStats();
        Stats.readStats(lockerList.getLockerList());
    }
}
