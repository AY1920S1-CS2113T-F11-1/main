package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.InUseLocker;
import duke.models.locker.Locker;
import duke.models.locker.LockerDate;
import duke.models.locker.Zone;
import duke.models.student.Student;
import duke.models.tag.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class AssignLockerCommand extends Command {

    private final Student student;
    private final LockerDate startDate;
    private final LockerDate endDate;
    private final List<Zone> preferences;
    public static final int FIRST_FREE_LOCKER = 0;

    public AssignLockerCommand(Student student, LockerDate startDate,
                               LockerDate endDate, List<Zone> preferences) {
        requireNonNull(student);
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(preferences);
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
        this.preferences = preferences;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        requireNonNull(lockerList);
        requireNonNull(ui);
        requireNonNull(storage);
        int storeIndex = assignLockerToStudent(lockerList,ui);
        ui.printSuccessfulAllocation(lockerList.getLocker(storeIndex).toString());
        storage.saveData(lockerList);
    }

    public int assignLockerToStudent(LockerList lockerList, Ui ui) throws DukeException {

        Locker freeLocker = getFreeLocker(lockerList.getAllLockers(),ui);
        int storeIndex = lockerList.getIndexOfLocker(freeLocker);
        lockerList.deleteLocker(freeLocker);
        freeLocker.setStatusAsInUse();
        Locker lockerAssignedToStudent = new InUseLocker(freeLocker.getSerialNumber(),
                freeLocker.getAddress(),freeLocker.getZone(),freeLocker.getTag(),student,
                startDate,endDate);
        lockerList.addLockerInPosition(lockerAssignedToStudent,storeIndex);
        return storeIndex;
    }

    public static Predicate<Locker> findLockerBasedOnPreferences(Zone zone) throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag)
                && p.getZone().equals(zone);
    }

    public static Predicate<Locker> findLockersInAnyZone() throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag);
    }

    public List<Locker> getListOfFreeLockersInAnyZone(List<Locker> lockers) throws DukeException {
        return lockers.stream()
                .filter(findLockersInAnyZone())
                .collect(Collectors.toList());
    }
    public List<Locker> getListOfFreeLockersInZone(List<Locker> lockers, Zone zone) throws DukeException {
        return lockers.stream()
                .filter(findLockerBasedOnPreferences(zone))
                .collect(Collectors.<Locker>toList());
    }

    public Locker getFreeLocker(List<Locker> lockerList, Ui ui) throws DukeException {
        for (Zone zone: preferences) {
            List<Locker> freeLockersInZone =
                    getListOfFreeLockersInZone(lockerList,zone);
            if (freeLockersInZone.size() > 0) {
                return freeLockersInZone.get(FIRST_FREE_LOCKER);
            }
        }
        //If the control reaches here, that means SpongeBob was unable to allocate
        //any Lockers in the given preferences and hence we will arbitrarily
        //assign any locker that is free
        List<Locker> freeLockersInAnyZone = getListOfFreeLockersInAnyZone(lockerList);
        if (freeLockersInAnyZone.size() == 0) {
            throw new DukeException(" There are no available lockers at the moment");
        }
        //We need to inform the user that a locker has been assigned not in the preferred
        //location
        ui.showNoLockersFoundInPreferences();
        return freeLockersInAnyZone.get(FIRST_FREE_LOCKER);
    }
}
