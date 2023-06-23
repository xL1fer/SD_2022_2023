package CollectionSite;

import CommInfra.ConfigsOffsets;
import ServerInterfaces.CollectionSiteInterface;

/**
 * Colletion site class.
 */
public class CollectionSite implements CollectionSiteInterface {
    /**
     * Collection site members.
     */
    private int[] collectionSite;
    /**
     * Number of party members.
     */
    private int partyMembersNumber;
    /**
     * Total thieves.
     */
    private int totalThieves;
    /**
     * Number of finished party members.
     */
    private int partyThievesFinished[];
    /**
     * Party targeted room.
     */
    private int partyTargetedRoom[];
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;
    /**
     * Simulation debug flag.
     */
    private boolean DEBUG = true;
    
    /**
     * Collection site constructor.
     * 
     * @param partyMembersNumber party members number
     * @param totalThieves total thieves
     */
    public CollectionSite(int partyMembersNumber, int totalThieves) {
        this.collectionSite = new int[totalThieves - 1];

        this.partyMembersNumber = partyMembersNumber;
        this.totalThieves = totalThieves;

        this.partyThievesFinished = new int[totalThieves / partyMembersNumber];
        this.partyTargetedRoom = new int[totalThieves / partyMembersNumber];

        this.nEntities = 0;

        System.out.println("< Collection Site initialized");
    }

    /**
     * Master thief waiting for a group arrival.
     */
    @Override
    public synchronized void takeARest() {
        if (DEBUG) System.out.printf("> Master Thief is taking a rest\n");

        while (getFirstCollectionPos() == -1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Master thief collecting a canvas from a ordinary thief who arrived.
     * 
     * @return canvas collection results
     */
    @Override
    public synchronized int[] collectACanvas() {
        int collectionPos = getFirstCollectionPos();
        int[] results = new int[(this.totalThieves / this.partyMembersNumber) + 1];

        results[0] = this.collectionSite[collectionPos];     // collect canvas

        if (DEBUG) System.out.printf("> Master Thief collected canvas from Thief < %d > (p = %d)\n", collectionPos + 1, results[0]);

        this.collectionSite[collectionPos] = 0;
        this.partyThievesFinished[collectionPos / this.partyMembersNumber]++;

        // check if any party finished
        for (int i = 0; i < this.partyThievesFinished.length; i++) {
            if (this.partyThievesFinished[i] == this.partyMembersNumber) {
                results[i + 1] = this.partyTargetedRoom[i];     // save party targeted room id
                this.partyThievesFinished[i] = 0;
                this.partyTargetedRoom[i] = 0;
            }
        }

        notifyAll();    // notify handACanvas()

        return results;
    }

    /**
     * Get first valid collection site position.
     * 
     * @return collection index
     */
    public int getFirstCollectionPos() {
        for (int i = 0; i < this.collectionSite.length; i++) {
            if (this.collectionSite[i] != 0)
                return i;
        }
        return -1;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Ordinary thief signalling master to hand a canvas.
     * 
     * @param thiefId thief id
     * @param partyId party id
     * @param hasCanvas has canvas status
     * @param partyTargetRoomId party target room id
     */
    @Override
    public synchronized void handACanvas(int thiefId, int partyId, boolean hasCanvas, int partyTargetRoomId) {
        // -1 -> has canvas ; else -> the assigned party room id
        this.collectionSite[thiefId - 1] = (hasCanvas) ? -1 : partyTargetRoomId;
        this.partyTargetedRoom[(thiefId - 1) / this.partyMembersNumber] = partyTargetRoomId;

        notifyAll();    // notify takeARest()

        if (DEBUG) System.out.printf("> Thief < %d > (p = %d) is waiting to hand a canvas\n", thiefId, partyId);

        while (this.collectionSite[thiefId - 1] != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Operation server shutdown.
     *
     * New operation.
     */
    @Override
    public synchronized void shutdown() {
        this.nEntities += 1;
        if (this.nEntities >= SimulationInput.inputArray[ConfigsOffsets.thievesNumber])
            Main.shutdown();
    }
}
