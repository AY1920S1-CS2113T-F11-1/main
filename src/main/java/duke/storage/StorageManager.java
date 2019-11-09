package duke.storage;

import duke.exceptions.DukeException;
import duke.models.LockerList;

import static java.util.Objects.requireNonNull;

public class StorageManager implements Storage {

    private FileStorage fileStorage;
    private ExportCsv writeToCsv;
    private ExportSelection selectionCsv;

    /**
     * This function managers storage data from the file.
     */
    public StorageManager(String fileName) {
        fileStorage = new FileStorage(fileName);
        writeToCsv = new ExportCsv();
        selectionCsv = new ExportSelection();

    }

    @Override
    public void saveData(LockerList dataToStore) throws DukeException {
        requireNonNull(dataToStore);
        fileStorage.saveData(dataToStore);
    }

    @Override
    public LockerList retrieveData() throws DukeException {
        return fileStorage.retrieveData();
    }

    @Override
    public void exportAsCsv(LockerList lockerList) throws DukeException {
        requireNonNull(lockerList);
        writeToCsv.exportLockers(lockerList.getAllLockers());
    }

    public void exportSelection(LockerList lockerList, String input) throws DukeException {
        requireNonNull(lockerList);
        selectionCsv.exportSelect(lockerList.getAllLockers(),input);
    }
}
