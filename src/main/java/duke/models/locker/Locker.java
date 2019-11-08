package duke.models.locker;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.exceptions.DukeException;
import duke.models.tag.Tag;


import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class Locker {
    private SerialNumber serialNumber;
    private Address address;
    private Zone zone;
    private Tag tag;
    private Usage usage;

    /**
     * Locker stores all the information regarding the status of the locker.
     *
     * @param serialNumber stores the serial numbers associated with each locker
     * @param address      stores the location of the locker
     * @param zone         stores the zone to which the locker belongs
     * @param tag          instance of the class Tag that stores the status of the locker
     */
    @JsonCreator
    public Locker(@JsonProperty("LockerSerial") SerialNumber serialNumber,
                  @JsonProperty("LockerAddress") Address address,
                  @JsonProperty("LockerZone") Zone zone,
                  @JsonProperty("LockerTag") Tag tag,
                  @JsonProperty("Usage") Usage usage) {
        requireNonNull(serialNumber);
        requireNonNull(address);
        requireNonNull(zone);
        requireNonNull(tag);
        this.serialNumber = serialNumber;
        this.address = address;
        this.zone = zone;
        this.tag = tag;
        this.usage = usage;
    }


    public void setTagAs(String tagName) {
        tag.tagName = tagName;
    }

    public void setStatusAsBroken() {
        tag.tagName = Tag.BROKEN;
    }

    public void setStatusAsUnAuthorized() {
        tag.tagName = Tag.UNAUTHORIZED;
    }

    public void setStatusAsNotInUse() {
        tag.tagName = Tag.NOT_IN_USE;
    }

    public void setStatusAsInUse() {
        tag.tagName = Tag.IN_USE;
    }

    public boolean isOfTypeInUse() throws DukeException {
        return getUsage().isPresent() && getTag().equals(new Tag(Tag.IN_USE));
    }

    public boolean hasSameTagAs(Tag checkTag) {
        return getTag().equals(checkTag);
    }

    /**
     * checks if the locker is of invalid type as per the constraints.
     * @return true if the locker is of invalid type, false otherwise
     * @throws DukeException if the Tag is invalid
     */
    public boolean isOfInValidType() throws DukeException {
        Tag testInUse = new Tag(Tag.IN_USE);
        return (hasSameTagAs(testInUse) && getUsage().isEmpty())
                || (!hasSameTagAs(testInUse) && getUsage().isPresent());
    }

    /**
     * checks if the locker is already present in the lockerList.
     * @param other to check if the object is already present
     * @return true if the object is present, false otherwise
     */
    public boolean hasSameSerialNumber(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Locker)) {
            return false;
        }

        return this.getSerialNumber().equals(((Locker) other).getSerialNumber());
    }

    /**
     * This function is used to convert the locker info into displayable strings.
     *
     * @return a string in a format that can be used for printing out the current locker
     */
    public String toString() {
        return " Locker #" + serialNumber.getSerialNumberForLocker() + ": " + "Area: " + address.getAddress()
                + " Zone: " + zone.getZone()
                + " [" + getTag().tagName + "]";
    }

    /**
     * This function is used to convert the serial number of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the serial number of a locker
     */
    public String serialNumberToString() {
        return serialNumber.getSerialNumberForLocker();
    }

    /**
     * This function is used to convert the tag of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the tag of a locker
     */
    public String tagToString() {
        return getTag().tagName;
    }

    /**
     * This function is used to convert the located zone of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the located zone of a locker
     */
    public String zoneToString() {
        return zone.getZone();
    }

    /**
     * This function is used to convert the located area of a locker into displayable strings.
     * @return a string in a format that can be used for printing out the located area of a locker
     */
    public String areaToString() {
        return address.getAddress();
    }

    @JsonGetter("LockerTag")
    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @JsonGetter("LockerSerial")
    public SerialNumber getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(SerialNumber serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonGetter("LockerAddress")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonGetter("LockerZone")
    public Zone getZone() {
        return zone;
    }


    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @JsonGetter("Usage")
    public Optional<Usage> getUsage() {
        return Optional.ofNullable(usage);
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    /* We need to override function equals() and hashCode() in order to account
       for user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; //both objects are the same
        }

        if (!(other instanceof Locker)) {
            return false; //handles all the cases for null and irrelevant references
        }

        Locker otherLocker = (Locker) other;
        return otherLocker.getSerialNumber().equals(this.getSerialNumber())
                && otherLocker.getAddress().equals(this.getAddress())
                && otherLocker.getZone().equals(this.getZone())
                && otherLocker.getTag().equals(this.getTag())
                && otherLocker.getUsage().equals(this.getUsage());//handles checks for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, address, zone, tag);
    }

    /**
     * This function is used to compare the locker info with a locker that was searched.
     * This is used in conjunction with Java in-streams.
     * @param serialNumber is one of the attributes the locker that was searched for.
     * @param address is one of the attributes the locker that was searched for.
     * @param zone is one of the attributes the locker that was searched for.
     * @return refers to a boolean value to check if the comparison was true or false.
     */

    public boolean compare(SerialNumber serialNumber, Address address, Zone zone) {

        if (serialNumber.equals(this.getSerialNumber())) {
            return true;
        } else if (address.equals(this.getAddress())) {
            return true;
        } else if (zone.equals(this.getZone())) {
            return true;
        } else {
            return false;
        }
    }
}
