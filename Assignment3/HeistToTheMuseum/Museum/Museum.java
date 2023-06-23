package Museum;

import CommInfra.ConfigsOffsets;
import ServerInterfaces.MuseumInterface;

/**
 * Museum class.
 */
public class Museum implements MuseumInterface {
    /**
     * Museum rooms array.
     */
    private Room[] rooms;
    /**
     * Party members number.
     */
    private int partyMembersNumber;
    /**
     * Number of party thieves ready to reverse direction.
     */
    private int partyThievesReverse[];
    /**
     * Party thieves leaving room flag.
     */
    private boolean partyThievesLeaving[];
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;
    /**
     * Simulation debug flag.
     */
    private boolean DEBUG = true;

    /**
     * Museum constructor.
     * 
     * @param roomsNumber number of museum rooms
     * @param partyMembersNumber party members number
     * @param partiesNumber parties number
     * @param maxRoomPaintings maximum paintings per room
     */
    public Museum(int roomsNumber, int partyMembersNumber, int partiesNumber, int maxRoomPaintings) {
        this.rooms = new Room[roomsNumber];
        /*for (int i = 0; i < roomsNumber; i++) {
            int paintings = (int) (Math.random() * (maxRoomPaintings - 8 + 1)) + 8;     // minimum number of paintings hardcoded to 8
            this.rooms[i] = new Room(i + 1, paintings);
        }*/
        this.partyMembersNumber = partyMembersNumber;
        this.partyThievesReverse = new int[partiesNumber];
        this.partyThievesLeaving = new boolean[partiesNumber];

        this.nEntities = 0;

        System.out.println("< Museum initialized");
    }

    /**
     * Ordinary thief roll a museum canvas function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @param targetRoomId target room id
     * @return true if a canvas was rolled
     */
    @Override
    public synchronized boolean rollACanvas(int thiefId, int thiefPartyId, int targetRoomId) {
        boolean hasCanvas = false;

        for (int i = 0; i < this.rooms.length; i++) {
            if (this.rooms[i].getRoomId() == targetRoomId) {
                if (DEBUG) System.out.printf("> Thief < %d > (p = %d) is ::ROLLING A CANVAS:: (total paintings = %d)\n", thiefId, thiefPartyId, this.rooms[i].getPaitingsNumber());
            
                if (this.rooms[i].getPaitingsNumber() > 0) {
                    this.rooms[i].decrementPaitingsNumber();
                    hasCanvas = true;
                }

                break;
            }
        }

        return hasCanvas;
    }

    /**
     * Ordinary thief get ready to reverse direction function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     */
    @Override
    public synchronized void reverseDirection(int thiefId, int thiefPartyId) {
        if (DEBUG) System.out.printf("> Thief < %d > (p = %d) is reversing direction\n", thiefId, thiefPartyId);

        this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber]++;

        if (this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber] == this.partyMembersNumber)
            this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber] = true;

        notifyAll();
        while (!this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber]--;

        // last thief to arrive at the room resets party pointer before starting crawling out
        if (this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber] == 0)
            this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber] = false;
    }

    /**
     * Get museum rooms array.
     * 
     * @return rooms array
     */
    public Room[] getRooms() {
        return rooms;
    }

    /**
     * Set museum rooms array.
     * 
     * @param rooms museum rooms array
     */
    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    /**
     * Fill museum rooms.
     * 
     * @param museumRooms museum rooms
     */
    @Override
    public synchronized void fillMuseum(int[][] museumRooms) {
        if (DEBUG) System.out.printf("> Filling museum rooms\n");

        for (int i = 0; i < this.rooms.length; i++)
            this.rooms[i] = new Room(i + 1, museumRooms[1][i]);
    }

    /**
     * Operation server shutdown.
     *
     * New operation.
     */
    @Override
    public synchronized void shutdown() {
        nEntities += 1;
        if (nEntities >= SimulationInput.inputArray[ConfigsOffsets.thievesNumber] - 1)
            Main.shutdown();
    }
}