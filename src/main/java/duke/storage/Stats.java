package duke.storage;

import duke.exceptions.DukeException;
import duke.models.locker.InUseLocker;
import duke.models.locker.Zone;
import duke.models.locker.Address;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class Stats {

    private static final int status = 0;
    private static final int address = 1;
    private static final int zone = 2;
    /**
     * This function gets the stats for lockers.
     * @throws DukeException when there are errors while handling the file.
     */

    public static void exportLockers(List<InUseLocker> lockerList) throws DukeException {


    }
}
