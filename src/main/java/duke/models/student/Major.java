package duke.models.student;

import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class Major {

    public static final String ERROR_MESSAGE = " Major should contain only aphanumeric characters"
            + " and spaces and it should not be empty";

    public static final String CHECK_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String course;

    public Major(String course) throws DukeException {
        requireNonNull(course);
        if (!checkIsValidCourse(course)) {
            throw new DukeException(ERROR_MESSAGE);
        }
        this.course = course;
    }

    public static boolean checkIsValidCourse(String course) {
        return course.matches(CHECK_REGEX);
    }

    /* We need to override functions equals and hashCode in order to account for
       user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        return this == other //short circuit if both the objects are the same
                || (other instanceof Major //handles all null instances and other irrelevant references
                && course.equalsIgnoreCase(((Major) other).course)); //checks for equality
    }

    @Override
    public int hashCode() {
        return course.hashCode();
    }
}
