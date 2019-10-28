package duke.storage;

import com.opencsv.CSVWriter;

import duke.exceptions.DukeException;
import duke.models.Locker;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class OpenCsv {
    private static final String STRING_ARRAY_SAMPLE = "data.csv";

    /**
     * This function exports a CSV file.
     * @throws DukeException when there are errors while handling the file.
     */

    public static void exportLockers(List<Locker> lockerList) throws DukeException {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(STRING_ARRAY_SAMPLE));

            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = {"Locker", "Zone", "Status"};
            csvWriter.writeNext(header);

            for (Locker l : lockerList) {

                String[] details = new String[header.length];
                details[0] = l.getSerialNumber().getSerialNumberForLocker();
                details[1] = l.getZone().getZone();
                details[2] = l.getTag().getTagName();

                csvWriter.writeNext(details);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new DukeException(" Unable to export csv ");
        }
    }
}