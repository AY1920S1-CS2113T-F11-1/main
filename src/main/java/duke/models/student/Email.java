package duke.models.student;

import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class Email {

    public static final String ERROR_MESSAGE = " Email ID consists of two parts local-part@domain"
            + " and should satisfy the following constraints:"
            + "\n 1. The local-part and the domain name are separated by '@'"
            + "\n 2. The local-part should contain only aplhanumeric characters and special symbols"
            + " like (!#$%&'*+/=?`{|}~^.-)"
            + "\n 3. The domain name should contain at least two characters and start and end with"
            + "alphanumeric characters"
            + "\n 4. The domain name should consists of only alphanumeric characters, a '.' "
            + "or a '-' in the middle (optional)";
    public static final String VALID_REGEX = "^[\\W!#$%&'*+/=?`{|}~^.-]+" + "@" + "[^\\W_]"
            + "[a-zA-Z0-9.-]*" + "[^\\W_]$";

    public final String email;

    public Email(String email) throws DukeException {
        requireNonNull(email);
        if (!checkIsValidEmail(email)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.email = email;
    }

    public static boolean checkIsValidEmail(String email) {
        return email.matches(VALID_REGEX);
    }
}
