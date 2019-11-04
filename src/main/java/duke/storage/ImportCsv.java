package duke.storage;

import com.opencsv.exceptions.CsvException;
import duke.exceptions.DukeException;

import com.opencsv.CSVReader;
import duke.models.LockerList;
import duke.models.locker.Locker;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

public class ImportCsv {
    private static final String CSV_INPUT_PATH = "data.csv";

    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int len = 3;

    /**
     * This function imports a CSV file.
     *
     * @throws DukeException when there are errors while handling the file.
     */

    public static void importLockers(List<Locker> lockerList) throws DukeException, IOException {
        try {

            Reader reader = Files.newBufferedReader(Paths.get(CSV_INPUT_PATH));
            CSVReader csvReader = new CSVReader(reader);

            for (Locker l : lockerList) {

                String[] importlist = new String[len];
                importlist[FIRST_COLUMN] = l.getSerialNumber().getSerialNumberForLocker();
                importlist[SECOND_COLUMN] = l.getZone().getZone();
                importlist[THIRD_COLUMN] = l.getTag().getTagName();
                importlist[len] = String.valueOf(csvReader.readAll());
            }

        } catch (IOException | CsvException e) {
            throw new DukeException(" Unable to import csv ");
        }
    }
}