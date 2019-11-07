package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class Address {
    public static final String ERROR_MESSAGE = " Addresses can take any value,"
            + "but it should not be empty";

    public static final String CHECK_REGEX = "[^\\s].*";
    public String address;

    /**
     * This constructor initializes the object with the address value passed to it.
     * @param address stores the address
     * @throws DukeException when the address is in a invalid format
     */
    @JsonCreator
    public Address(@JsonProperty("address") String address) throws DukeException {
        requireNonNull(address);
        if (!checkIsValidAddress(address)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.address = address;
    }

    public static boolean checkIsValidAddress(String address) {
        return address.matches(CHECK_REGEX);
    }

    @JsonGetter("address")
    public String getAddress() {
        return address;
    }

    /*We need to override equals and hashCode in order to account
    for user defined checks for streams
     */
    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if the two objects are same
                || (other instanceof Address //handles all the cases for null
                && address.equalsIgnoreCase(((Address) other).address));// check for equality
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
