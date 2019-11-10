package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AssignLockerCommand;
import duke.models.locker.LockerDate;
import duke.models.locker.Usage;
import duke.models.locker.Zone;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.MatricNumber;
import duke.models.student.Name;
import duke.models.student.Student;
import duke.parser.utilities.MapTokensToArguments;
import duke.parser.utilities.ParserTokenizer;
import duke.parser.utilities.Token;

import java.util.List;
import java.util.stream.Stream;

import static duke.parser.utilities.Syntax.TOKEN_EMAIL;
import static duke.parser.utilities.Syntax.TOKEN_END_DATE;
import static duke.parser.utilities.Syntax.TOKEN_PREFERENCES;
import static duke.parser.utilities.Syntax.TOKEN_START_DATE;
import static duke.parser.utilities.Syntax.TOKEN_STUDENTID;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_COURSE;
import static duke.parser.utilities.Syntax.TOKEN_STUDENT_NAME;

/**
 * Parses user input and creates a new AssignLockerCommand object.
 */
public class AssignLockerCommandParser {

    /**
     * Checks if all the entries entered by the user are valid as per
     * the specifications of the assign command.
     * @param userInput stores the userInput
     * @return a reference to the AssignLockerCommand()
     * @throws DukeException when the command is in invalid format
     */
    public AssignLockerCommand parse(String userInput) throws DukeException {
        MapTokensToArguments mapTokensToArguments = ParserTokenizer
                .tokenize(userInput, TOKEN_STUDENT_NAME, TOKEN_STUDENTID, TOKEN_EMAIL,
                        TOKEN_STUDENT_COURSE, TOKEN_START_DATE, TOKEN_END_DATE, TOKEN_PREFERENCES);

        if (!checkAllTokensPresent(mapTokensToArguments, TOKEN_STUDENT_NAME, TOKEN_STUDENTID,
                TOKEN_EMAIL, TOKEN_STUDENT_COURSE, TOKEN_START_DATE,
                TOKEN_END_DATE, TOKEN_PREFERENCES)
                || !mapTokensToArguments.getTextBeforeFirstToken().isEmpty()) {
            throw new DukeException(AssignLockerCommand.INVALID_FORMAT);
        }

        Name name = ParserCheck.parseName(mapTokensToArguments.getValue(TOKEN_STUDENT_NAME).get());
        MatricNumber matricNumber = ParserCheck.parseMatricNumber(
                mapTokensToArguments.getValue(TOKEN_STUDENTID).get());
        Email email = ParserCheck.parseEmail(mapTokensToArguments.getValue(TOKEN_EMAIL).get());
        Major major = ParserCheck.parseMajor(mapTokensToArguments.getValue(
                TOKEN_STUDENT_COURSE).get());
        LockerDate startDate = ParserCheck.parseDate(mapTokensToArguments.getValue(
                TOKEN_START_DATE).get());
        LockerDate endDate = ParserCheck.parseDate(mapTokensToArguments.getValue(
                TOKEN_END_DATE).get());
        List<Zone> getPreferences = ParserCheck.parsePreferences(mapTokensToArguments.getValue(
                TOKEN_PREFERENCES).get());
        ParserCheck.parseDifferenceBetweenStartAndEndDate(startDate, endDate);
        Student student = new Student(name, matricNumber, email, major);
        Usage usage = new Usage(student, startDate, endDate);
        return new AssignLockerCommand(usage, getPreferences);
    }

    /**
     * Returns true if there are no tokens with empty values.
     */
    private static boolean checkAllTokensPresent(
            MapTokensToArguments mapTokensToArguments, Token... tokens) {
        return Stream.of(tokens).allMatch(token -> mapTokensToArguments
                .getValue(token).isPresent());
    }
}
