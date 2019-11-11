package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.AddLockerCommand;

import duke.models.locker.Address;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;
import duke.models.locker.Zone;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;
import duke.parser.utilities.Token;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static duke.parser.utilities.Syntax.TOKEN_ADDRESS;
import static duke.parser.utilities.Syntax.TOKEN_SERIAL;
import static duke.parser.utilities.Syntax.TOKEN_ZONE;

/**
 * Parses input arguments and creates a new AddLockerCommand object.
 */
public class AddLockerCommandParser {
    private static final Logger logger = Log.getLogger();

    /**
     * Parses the user input for adding a new locker to the list of lockers.
     * It includes all checks for validating the user input.
     * @param userInput stores the user input
     * @return reference to the class AddLockerCommand
     * @throws DukeException when the command format is invalid
     */
    public AddLockerCommand parse(String userInput) throws DukeException {
        logger.log(Level.INFO, "Attempting to parse user input for AddLockerCommand");
        MapTokensToArguments mapTokensToArguments =
                ParserTokenizer.tokenize(userInput, TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE);
        if (!checkAllTokensPresent(mapTokensToArguments,
                TOKEN_SERIAL, TOKEN_ADDRESS, TOKEN_ZONE)
                || !mapTokensToArguments.getTextBeforeFirstToken().isEmpty()) {
            throw new DukeException(AddLockerCommand.INVALID_FORMAT);
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(
                mapTokensToArguments.getValue(TOKEN_SERIAL).get());
        Address address = ParserCheck.parseAddress(
                mapTokensToArguments.getValue(TOKEN_ADDRESS).get());
        Zone zone = ParserCheck.parseZone(mapTokensToArguments.getValue(TOKEN_ZONE).get());
        Tag tag = new Tag(Tag.NOT_IN_USE);
        Locker locker = new Locker(serialNumber, address, zone, tag,null);
        return new AddLockerCommand(locker);
    }

    /**
     * Returns true if none of the tokens contain empty values.
     */
    private static boolean checkAllTokensPresent(MapTokensToArguments mapTokensToArguments,
                                                 Token... tokens) {

        return Stream.of(tokens).allMatch(token -> mapTokensToArguments
                .getValue(token).isPresent());
    }
}
