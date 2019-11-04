package duke.storage;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import java.util.List;

public class Stats {

    private static final int zone = 0;
    private static final int address = 1;
    private static final int tag = 2;
    private static final int len = 3;

    private static int countZoneA = 0;
    private static int countZoneB = 0;
    private static int address1 = 0;
    private static int address2 = 0;
    private static int inUse = 0;
    private static int NotinUse = 0;
    private static int Broken = 0;
    private static int UnAuthorized = 0;

    /**
     * This function lists out the stats for each tag
     * @throws DukeException when there are errors while handling the file.
     */

    public static void readStats(List<Locker> lockerList) {

        for (Locker l : lockerList) {

            String[] details = new String[len];
            details[zone] = l.getZone().getZone();
            details[address] = l.getAddress().getAddress();
            details[tag] = l.getTag().getTagName();


            if (details[zone].equals("A"))  {
                countZoneA += 1;
            }
            if (details[zone].equals("B"))  {
                countZoneB += 1;
            }

            if (details[address].equals("com1"))  {
                address1 += 1;
            }
            if (details[address].equals("com2"))  {
                address2 += 1;
            }

            if (details[tag].equals("in-use"))  {
                inUse += 1;
            }
            if (details[tag].equals("not-in-use"))  {
                NotinUse += 1;
            }
            if (details[tag].equals("broken"))  {
                Broken += 1;
            }
            if (details[tag].equals("unauthorized"))  {
                UnAuthorized += 1;
            }
        }

        System.out.print("Zone[A]:" + countZoneA + "    ");
        System.out.println("Zone[B]:" + countZoneB);

        System.out.print("COM-[1]:" + address1 + "    ");
        System.out.println("COM-[2]:" + address2);

        System.out.print("In-Use:" + inUse + "    ");
        System.out.print("Not-In-Use:" + NotinUse + "    ");
        System.out.print("Broken:" + Broken + "    ");
        System.out.println("Unauthorized:" + UnAuthorized);
    }
}
