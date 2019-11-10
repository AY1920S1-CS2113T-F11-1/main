package duke.storage;

import com.opencsv.CSVWriter;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExportSelection {
    private static final String CSV_OUTPUT_PATH = "export.csv";

    /**
     * This function exports a CSV file based on the input tags keyed in.
     * @throws DukeException when there are errors while handling the file.
     */

    public static void exportSelect(List<Locker> lockerList, String item) throws DukeException {
        try {

            ArrayList<String> title = new ArrayList<String>();

            item = item.toLowerCase();

            if (item.contains("locker")) {
                title.add("Locker");
            }
            if (item.contains("address")) {
                title.add("Address");
            }
            if (item.contains("zone")) {
                title.add("Zone");
            }
            if (item.contains("status")) {
                title.add("Status");
            }
            if (item.contains("name")) {
                title.add("Name");
            }
            if (item.contains("matrix")) {
                title.add("Matrix");
            }
            if (item.contains("course")) {
                title.add("Course");
            }
            if (item.contains("email")) {
                title.add("Email");
            }

            String[] header = new String[title.size()];

            for (int j = 0; j < title.size(); j++) {
                header[j] = title.get(j);
            }

            Writer writer = Files.newBufferedWriter(Paths.get(CSV_OUTPUT_PATH));

            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            csvWriter.writeNext(header);

            for (Locker l : lockerList) {
                int count = 0;
                String zoneStats = "not-in-use";
                String[] details = new String[header.length];

                if (!title.contains("Locker")) {
                    throw new DukeException("Serial Number is Mandatory, please input 'Locker'!");
                }
                if (title.contains("Locker")) {
                    details[count] = l.getSerialNumber().getSerialNumberForLocker();
                    count += 1;
                }
                if (title.contains("Address")) {
                    details[count] = l.getAddress().getAddress();
                    count += 1;
                }
                if (title.contains("Zone")) {
                    details[count] = l.getZone().getZone();
                    count += 1;
                }
                if (title.contains("Status")) {
                    details[count] = l.getTag().getTagName();
                    zoneStats = details[count];
                    count += 1;
                }

                if (zoneStats.equals("in-use")) {
                    if (title.contains("Name")) {
                        details[count] = l.getUsage().get().getStudent().getName().getName();
                        count += 1;
                    }
                    if (title.contains("Matrix")) {
                        details[count] = l.getUsage().get().getStudent().getMatricNumber().getMatricId();
                        count += 1;
                    }
                    if (title.contains("Course")) {
                        details[count] = l.getUsage().get().getStudent().getMajor().getCourse();
                        count += 1;
                    }
                    if (title.contains("Email")) {
                        details[count] = l.getUsage().get().getStudent().getEmail().getEmail();
                        count += 1;
                    }
                }

                csvWriter.writeNext(details);
            }

            csvWriter.close();

        } catch (IOException e) {
            throw new DukeException(" Unable to export selected tags to csv file ");
        }
    }

}