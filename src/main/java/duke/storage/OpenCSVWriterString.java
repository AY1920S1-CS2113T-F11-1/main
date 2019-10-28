package duke.storage;

import com.opencsv.CSVWriter;

import duke.exceptions.DukeException;
import duke.models.Locker;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class OpenCSVWriterString {
    private static final String STRING_ARRAY_SAMPLE = "data.csv";

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

                String[] Details = new String[header.length];
                Details[0] = l.getSerialNumber().getSerialNumberForLocker();
                Details[1] = l.getZone().getZone();
                Details[2] = l.getTag().getTagName();

                csvWriter.writeNext(Details);
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new DukeException(" Unable to export csv ");
        }
    }
}