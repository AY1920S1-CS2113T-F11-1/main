package duke;

import duke.logic.commands.Command;
import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.util.SampleData;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.storage.StorageManager;
import duke.ui.Ui;

/**
 * Runs the application.
 */
public class Duke {
    private Ui ui;
    private Storage storage;
    private LockerList lockers;
    private Parser parser;

    private static final String FILE_NAME_FOR_STORAGE = "data.json";

    /**
     * This function instantiates the SpongeBob class by loading data from a file.
     * @param filename stores the file name from which the data is being loaded.
     */
    public Duke(String filename) throws DukeException {
        try {
            ui = new Ui();
            parser = new Parser();
            storage = new StorageManager(filename);
            lockers = new LockerList(storage.retrieveData().getLockerList());
        } catch (DukeException e) {
            ui.showLoadingError(e.getMessage());
            lockers = SampleData.getSampleLockerList();
        }
        storage.initializeStateList(lockers);
    }

    /**
     *  This function executes various tasks/commands related to SpongeBob.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printDash();
                storage.updateHistoryList(fullCommand);
                Command c = parser.parse(fullCommand);
                c.execute(lockers, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Responsible for instantiating SpongeBob with the file name "data.json".
     * data.json is the file from which the data is loaded for the list of lockers.
     * @param args contains the supplied command-line arguments as an array of String objects.
     */
    public static void main(String[] args) throws DukeException {
        new Duke(FILE_NAME_FOR_STORAGE).run();
    }
}