package SharedRegions;

import java.util.ArrayList;

import Main.MuseumHeist;

/**
 * Concentration site class.
 */
public class ConcentrationSite {
    /**
     * Number of members per party.
     */
    private int partyMembersNumber;
    /**
     * Concentration site queue.
     */
    private int[] concentrationQueue;
    /**
     * Number of thieves in each party.
     */
    private int[] thievesJoinedParty;
    /**
     * Each party current target room.
     */
    private int[] partiesTargetRoom;
    /**
     * Heist's active status.
     */
    private boolean heistActive;
    /**
     * Number of thieves that left the concentration site at the end of the simulation.
     */
    private int thievesExited;

    /**
     * Concentration site constructor.
     * 
     * @param partiesNumber number of assault parties
     * @param partyMembersNumber number of members per party
     */
    public ConcentrationSite(int partiesNumber, int partyMembersNumber) {
        this.partyMembersNumber = partyMembersNumber;
        this.concentrationQueue = new int[partiesNumber];
        this.thievesJoinedParty = new int[partiesNumber];

        this.partiesTargetRoom = new int[partiesNumber];
        for (int i = 0; i < this.partiesTargetRoom.length; i++)
            this.partiesTargetRoom[i] = -1;

        this.heistActive = true;
        this.thievesExited = 0;

        System.out.println("< Concentration Site initialized");
    }

    /**
     * Master thief appraise site function.
     * 
     * @param totalAvailableRooms total available rooms
     * @param totalAssaultParties total assault parties
     * @return true if there are still rooms to check
     */
    public synchronized boolean appraiseSit(int totalAvailableRooms, int totalAssaultParties) {
        if (MuseumHeist.DEBUG) System.out.printf("> Master Thief is deciding what to do\n");

        // check if there are enough thieves to start a new excursion or if there are enough thieves to present the report
        while ((getReadyAssaultPartyIndex() == -1 && totalAvailableRooms > 0) || (getTotalReadyAssaultParties() != totalAssaultParties && totalAvailableRooms < 1)) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // return true in case there are still rooms to check
        if (totalAvailableRooms > 0)
            return true;

        // return false in case all the rooms have already been checked
        return false;
    }

    /**
     * Master thief prepare assault function.
     * 
     * @param availableRooms available rooms reference
     * @param targetedRooms targeted rooms reference
     * @return assigned room id
     */
    public synchronized int prepareAssaultParty(ArrayList<Integer> availableRooms, ArrayList<Integer> targetedRooms) {
        int assaultPartyInd = getReadyAssaultPartyIndex();

        if (MuseumHeist.DEBUG) System.out.printf("> Master Thief is preparing an assault party (%d)\n", assaultPartyInd + 1);

        // assign a target room to the party
        int availableRoomId = -1;
        for (int i = 0; i < availableRooms.size(); i++) {
            if (!targetedRooms.contains(availableRooms.get(i))) {
                availableRoomId = availableRooms.get(i);
                break;
            }
        }
        // no more rooms available, set party target room to -2 to indicate them they can leave
        if (availableRoomId == -1)
            availableRoomId = -2;

        this.partiesTargetRoom[assaultPartyInd] = availableRoomId;

        notifyAll();    // notify amINeeded()

        // wait for the last thief to join the party
        while (this.thievesJoinedParty[assaultPartyInd] < partyMembersNumber /*&& availableRoomId != -2*/) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.thievesJoinedParty[assaultPartyInd] = 0;

        return availableRoomId;
    }

    /**
     * Master thief send assault function.
     */
    public synchronized void sendAssaultParty() {
        int assaultPartyInd = getReadyAssaultPartyIndex();

        this.concentrationQueue[assaultPartyInd] = 0;

        notifyAll();    // notify prepareExcursion()

        if (MuseumHeist.DEBUG) System.out.printf("> Master Thief sent assault party %d (room %d)\n", assaultPartyInd + 1, this.partiesTargetRoom[assaultPartyInd]);
        
        this.partiesTargetRoom[assaultPartyInd] = -1;
    }

    /**
     * Master thief ends the heist and sum up the results.
     * 
     * @param totalAssaultParties total assault parties
     */
    public synchronized void sumUpResults(int totalAssaultParties) {
        if (MuseumHeist.DEBUG) System.out.printf("> Master Thief is presenting the report\n");

        this.heistActive = false;

        notifyAll();    // notify amINeeded()

        while (this.thievesExited < (this.partyMembersNumber * totalAssaultParties)) {
            try {
                wait();     // wait thiefLeaving()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get first ready assault party id.
     * 
     * @return party index
     */
    public int getReadyAssaultPartyIndex() {
        for (int i = 0; i < this.concentrationQueue.length; i++) {
            if (this.concentrationQueue[i] == this.partyMembersNumber)
                return i;
        }
        return -1;
    }

    /**
     * Get number of total ready assault parties.
     * 
     * @return total ready assault parties
     */
    public int getTotalReadyAssaultParties() {
        int total = 0;
        for (int i = 0; i < this.concentrationQueue.length; i++) {
            if (this.concentrationQueue[i] == this.partyMembersNumber)
                total++;
        }
        return total;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
    /**
     * Ordinary thief wait orders.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @return assigned room id
     */
    public synchronized int amINeeded(int thiefId, int thiefPartyId) {
        if (MuseumHeist.DEBUG) System.out.printf("> Thief < %d > (p = %d) arrived outside\n", thiefId, thiefPartyId);

        this.concentrationQueue[thiefPartyId - 1]++;        // add thief to the outside queue
        notifyAll();                                        // notify appraiseSit()
        
        // wait until the party does not have a target room
        while (partiesTargetRoom[thiefPartyId - 1] == -1 && this.heistActive) {
            try {
                wait();     // wait prepareAssaultParty() or sumUpResults()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // heist has ended or the party is not needed anymore
        if (this.heistActive == false)
            return -2;

        return partiesTargetRoom[thiefPartyId - 1];
    }

    /**
     * Ordinary thief preparing excursion.
     * 
     * @param thiefId thief id
     * @param thiefPartyId party thief id
     */
    public synchronized void prepareExcursion(int thiefId, int thiefPartyId) {
        if (MuseumHeist.DEBUG) System.out.printf("> Thief < %d > (p = %d) is preparing excursion\n", thiefId, thiefPartyId);

        this.thievesJoinedParty[thiefPartyId - 1]++;

        notifyAll();    // notify prepareAssaultParty()

        while (this.concentrationQueue[thiefPartyId - 1] != 0  && this.heistActive) {
            try {
                wait();     // wait sendAssaultParty()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ordinary thief exiting concentration site.
     */
    public synchronized void thiefLeaving() {
        if (!this.heistActive) {
            this.thievesExited++;
            notifyAll();    // notify sumUpResults()
        }
    }
}
