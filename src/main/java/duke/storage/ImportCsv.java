package duke.storage;

import com.opencsv.exceptions.CsvException;
import duke.exceptions.DukeException;

import com.opencsv.CSVReader;
import duke.models.locker.Locker;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;

public class ImportCsv {
    private static final String CSV_INPUT_PATH = "./src/main/java/data/import.csv";

    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;

    /**
     * This function imports a CSV file.
     *
     * @throws DukeException when there are errors while handling the file.
     */

    public static void importLockers(List<Locker> lockerList) throws DukeException {
        try {

            Reader reader = Files.newBufferedReader(Paths.get(CSV_INPUT_PATH));
            CSVReader csvReader = new CSVReader(reader);

            String[] importList;
            int i = 0;
            while ((importList = csvReader.readNext()) != null) {
                if (i < lockerList.size()) {
                    lockerList.get(i).getSerialNumber().serialNumberForLocker = importList[FIRST_COLUMN];
                    lockerList.get(i).getZone().zone = importList[SECOND_COLUMN];
                    lockerList.get(i).getTag().tagName = importList[THIRD_COLUMN];
                    i++;
                } else {
                    lockerList.add(0,null);
                    lockerList.get(i).getSerialNumber().serialNumberForLocker = importList[FIRST_COLUMN];
                    lockerList.get(i).getZone().zone = importList[SECOND_COLUMN];
                    lockerList.get(i).getTag().tagName = importList[THIRD_COLUMN];
                    i++;
                }
            }
            csvReader.close();
        } catch (IOException | CsvException e) {
            throw new DukeException(" Unable to import csv ");
        }
    }
}