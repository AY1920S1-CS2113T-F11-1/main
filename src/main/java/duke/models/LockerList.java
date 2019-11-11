package duke.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * Stores the list of lockers that are currently being managed by SpongeBob.
 */
public class LockerList {

    private static final String NO_LOCKER_FOUND = " There are no lockers associated to "
            + "the serial number entered.";
    public static final String DUPLICATE_LOCKERS_FOUND = " Duplicate entries not allowed. "
            + "Serial number for every locker should be unique.";
    private static final int EMPTY_LIST = 0;

    private List<Locker> lockerList;

    public LockerList(List<Locker> lockerList) {
        requireNonNull(lockerList);
        this.lockerList = lockerList;
    }

    public LockerList(LockerList copyLockerList) {
        this.lockerList = new ArrayList<>(copyLockerList.getLockerList());
    }

    public LockerList() {
        lockerList = new ArrayList<>();
    }

    /**
     * This function is used to check if the locker is already present in the list.
     * @param newLocker list of lockers to be checked.
     * @return true if at least one of the locker is present.
     */
    public boolean isPresentLocker(Locker newLocker) {
        requireNonNull(newLocker);
        return lockerList.stream()
                .anyMatch(locker -> locker.hasSameSerialNumber(newLocker));
    }

    /**
     * This function is used to check if the lockers are already present in the list.
     * @param newLockers list of lockers to be checked.
     * @return true if atleast one of the locker is present.
     */
    public boolean areLockersPresent(List<Locker> newLockers) {
        requireNonNull(newLockers);
        for (Locker newLocker: newLockers) {
            if (isPresentLocker(newLocker)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function returns all the lockers that match a given property.
     * @param isMatching stores the predicate for matching.
     * @return list of lockers that match the given predicate.
     */
    public List<Locker> getMatchingLockers(Predicate<Locker> isMatching) {
        requireNonNull(isMatching);
        return lockerList.stream()
                .filter(isMatching)
                .collect(Collectors.toList());
    }

    /**
     * This function returns a list of lockers that are tagged with not-in-use Tag.
     * @param availableTag tag used to check if the locker is currently not-in-use.
     * @return list of available lockers.
     */
    public List<Locker> getAnyAvailableLocker(Tag availableTag) {
        requireNonNull(availableTag);
        return lockerList.stream()
                .filter(locker -> locker.getTag().equals(availableTag))
                .collect(Collectors.toList());
    }

    /**
     * This function returns a locker that is associated with the serialNumber.
     * @param serialNumberToFind stores the serial number.
     * @return locker with the given serial number.
     * @throws DukeException if there are no lockers associated with the serial number.
     */
    public Locker getLockerToEdit(SerialNumber serialNumberToFind) throws DukeException {
        requireNonNull(serialNumberToFind);
        List<Locker> checkAllLockers = lockerList.stream()
                .filter(locker -> locker.getSerialNumber().equals(serialNumberToFind))
                .collect(Collectors.toList());
        if (checkAllLockers.size() == EMPTY_LIST) {
            throw new DukeException(NO_LOCKER_FOUND);
        }
        return checkAllLockers.get(0);
    }

    /**
     * This function adds a locker to the list of lockers.
     * @param lockerToAdd stores the locker to be added.
     */
    public void addLocker(Locker lockerToAdd) {
        requireNonNull(lockerToAdd);
        lockerList.add(lockerToAdd);
    }

    /**
     * This function Adds a locker to the specified position.
     * @param lockerToAdd stores the locker to be added.
     * @param index stores the zero-based position at which the locker is to be added.
     */
    public void addLockerAtPosition(Locker lockerToAdd, int index) {
        requireNonNull(lockerToAdd);
        lockerList.add(index, lockerToAdd);
    }

    /**
     * This function sets a locker at the specified position. The locker already present at that position
     * is replaced by the new locker.
     * @param newLocker stores the locker to be added.
     * @param index stores the zero-based index at which the locker is to be added.
     */
    public void setLockerInPosition(Locker newLocker, int index) {
        requireNonNull(newLocker);
        lockerList.set(index, newLocker);
    }

    /**
     * This function adds multiple lockers to the list of lockers.
     * @param lockersToAdd stores the list of lockers that are to be added.
     */
    public void addAllLockersInList(List<Locker> lockersToAdd) {
        requireNonNull(lockersToAdd);
        lockerList.addAll(lockersToAdd);
    }

    /**
     * This function deletes the {@lockerToDelete} from the list of lockers.
     */
    public void deleteLocker(Locker lockerToDelete) {
        requireNonNull(lockerToDelete);
        lockerList.remove(lockerToDelete);
    }

    /**
     * This function mmpties the locker list. Used for deleting all the lockers from SpongeBob.
     */
    public void removeAllLockers() {
        lockerList.clear();
    }

    /**
     * This function updates the lockerlist to the newest version after undo/redo.
     * @param updatedLockerList stores the updated version of lockerlist after undo/redo.
     */
    public void updateLockerList(LockerList updatedLockerList) {
        lockerList.clear();
        lockerList.addAll(updatedLockerList.getAllLockers());
    }

    public Locker getLocker(int index) {
        return lockerList.get(index);
    }

    public List<Locker> getAllLockers() {
        return lockerList;
    }

    public int getIndexOfLocker(Locker locker) {
        requireNonNull(locker);
        return lockerList.indexOf(locker);
    }

    public int numLockers() {
        return lockerList.size();
    }

    @JsonGetter("lockers")
    public List<Locker> getLockerList() {
        return lockerList;
    }

    @JsonSetter("lockers")
    public void setLLockerList(List<Locker> lockerList) {
        this.lockerList = lockerList;
    }

}
