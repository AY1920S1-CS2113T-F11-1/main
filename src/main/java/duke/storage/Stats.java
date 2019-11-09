package duke.storage;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stats {

    private static final int zone = 0;
    private static final int address = 1;
    private static final int tag = 2;
    private static final int len = 3;

    /**
     * This function lists out the stats for each tag.
     * @throws DukeException when there are errors while handling the file.
     */

    public static void readStats(List<Locker> lockerList) throws DukeException {
        try {

            Map<String,Integer> mapZone = new HashMap<>();
            Map<String,Integer> mapAddress = new HashMap<>();
            Map<String,Integer> mapTag = new HashMap<>();

            String[] details = new String[len];

            for (Locker l : lockerList) {
                Integer temp = 1;
                details[zone] = l.getZone().getZone();
                details[address] = l.getAddress().getAddress();
                details[tag] = l.getTag().getTagName();
                details[zone] = details[zone].toUpperCase();
                details[address] = details[address].toLowerCase();

                if (!mapZone.containsKey(details[zone])) {
                    mapZone.put(details[zone],temp);
                } else {
                    mapZone.replace(details[zone],
                            mapZone.get(details[zone]) + 1);
                }

                if (!mapAddress.containsKey(details[address])) {
                    mapAddress.put(details[address],temp);
                } else {
                    mapAddress.replace(details[address],
                            mapAddress.get(details[address]) + 1);
                }

                if (!mapTag.containsKey(details[tag])) {
                    mapTag.put(details[tag],temp);
                } else {
                    mapTag.replace(details[tag],
                            mapTag.get(details[tag]) + 1);
                }

            }
            System.out.print("Zone: ");
            System.out.println(mapZone);
            System.out.print("Address: ");
            System.out.println(mapAddress);
            System.out.print("Tag: ");
            System.out.println(mapTag);

        } catch (NullPointerException e) {
            throw new DukeException(" Unable to get stats ");
        }
    }
}
