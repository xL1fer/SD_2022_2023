package MasterThief;

import java.util.ArrayList;

/**
 * Master thief class.
 */
public class MasterThief extends Thief {
    /**
     * Information repository stub.
     */
    private InformationRepositoryStub infReposStub;
    /**
     * Concentration site stub.
     */
    private ConcentrationSiteStub conSiteStub;
    /**
     * Collection site stub.
     */
    private CollectionSiteStub colSiteStub;

    /**
     * Master thief's available rooms notes.
     */
    private ArrayList<Integer> availableRooms;
    /**
     * Master thief's targeted room notes.
     */
    private ArrayList<Integer> targetedRooms;
    /**
     * Painting earnings.
     */
    private int paintingEarnings;
    /**
     * Total number of assault parties.
     */
    private int totalAssaultParties;

    /**
     * Master thief constructor.
     * 
     * @param currentState master thief's current state
     * @param infReposStub information repository stub
     * @param conSiteStub concentration site stub
     * @param colSiteStub collection site stub
     * @param paintingRooms number os paintings per room
     * @param totalAssaultParties total number of assault parties
    */
    public MasterThief(int currentState, InformationRepositoryStub infReposStub, ConcentrationSiteStub conSiteStub, CollectionSiteStub colSiteStub,
            int paintingRooms, int totalAssaultParties) {
        super(currentState);
        
        this.infReposStub = infReposStub;
        this.conSiteStub = conSiteStub;
        this.colSiteStub = colSiteStub;

        this.availableRooms = new ArrayList<Integer>();
        for (int i = 1; i <= paintingRooms; i++)
            this.availableRooms.add(i);
        this.targetedRooms = new ArrayList<Integer>();

        this.paintingEarnings = 0;
        this.totalAssaultParties = totalAssaultParties;

        System.out.println("< Master Thief initialized");
    }
    
    @Override
    public void run() {
        // write information repository header log
        this.infReposStub.writeHeaderLog();

        // start master thief operations
        this.startOperations();

        // master thief lifecycle logic
        while (true) {
            changeState(MasterThiefStates.DECIDING_WHAT_TO_DO);

            // break and sum up results or form another party
            if (!this.conSiteStub.appraiseSit(this.getAvailableRooms().size(), this.totalAssaultParties))
                break;

            // prepare an assault party
            changeState(MasterThiefStates.ASSEMBLING_A_GROUP);
            int availableRoomId = this.conSiteStub.prepareAssaultParty(this.availableRooms, this.targetedRooms);

            if (availableRoomId > 0)
                targetRoom(availableRoomId);                // a room was successfully assigned
            else if (availableRoomId == -2)
                decrementTotalAssaultParties();             // no room assigned, decrement total assault parties
            
            // send the assault party
            this.conSiteStub.sendAssaultParty();

            // take a rest in case all assault parties are operational
            while (this.targetedRooms.size() == this.totalAssaultParties) {
                changeState(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);

                // wait for the arrival of a thief
                this.colSiteStub.takeARest();

                // results[0]           ->  -1 if thief returned a canvas ; targeted room id otherwise
                // results[i] (i > 0)   ->  target room id in case of last thief to return from excursion
                int results[] = this.colSiteStub.collectACanvas();

                if (results[0] == -1)
                    addPaintingEarnings();                                  // collect a thief canvas
                else
                    this.availableRooms.remove((Integer) results[0]);       // remove room from available rooms

                // check if any room needs to be detargeted
                for (int i = 1; i < results.length; i++) {
                    if (results[i] != 0)
                        detargetRoom(results[i]);
                }
            }
        }

        // ending up simulation
        changeState(MasterThiefStates.PRESENTING_THE_REPORT);
        this.conSiteStub.sumUpResults(this.totalAssaultParties);

        this.infReposStub.writeEarningsLog(this.paintingEarnings);
    }

    /**
     * Change thief state and write in log
     * 
     * @param state new state
     */
    public void changeState(int state) {
        setCurrentState(state);
        this.infReposStub.writeStatesLog(this.currentState, -1, -1, -1, -1, false, 0);
    }

    /**
     * Change current state in order to start the operations.
     */
    public void startOperations() {
        changeState(MasterThiefStates.DECIDING_WHAT_TO_DO);
    }

    /**
     * Get master thief's available rooms notes.
     * 
     * @return mater thief's available rooms notes
     */
    public ArrayList<Integer> getAvailableRooms() {
        return availableRooms;
    }

    /**
     * Set master thief's available rooms notes.
     * 
     * @param availableRooms master thief'savailable rooms notes
     */
    public void setAvailableRooms(ArrayList<Integer> availableRooms) {
        this.availableRooms = availableRooms;
    }

    /**
     * Add a paiting to the earnings.
     */
    public void addPaintingEarnings() {
        this.paintingEarnings += 1;
    }

    /**
     * Get painting earnings.
     *  
     * @return painting earnings
     */
    public int getPaintingEarnings() {
        return paintingEarnings;
    }

    /**
     * Get information repository stub.
     * 
     * @return information repository stub
     */
    public InformationRepositoryStub getInfReposStub() {
        return infReposStub;
    }

    /**
     * Set information repository stub.
     * 
     * @param infReposStub information repository stub
     */
    public void setInfReposStub(InformationRepositoryStub infReposStub) {
        this.infReposStub = infReposStub;
    }

    /**
     * Get concentration site stub.
     * 
     * @return concentration site stub
     */
    public ConcentrationSiteStub getConSiteStub() {
        return conSiteStub;
    }

    /**
     * Set concentration site stub.
     * 
     * @param conSiteStub concentration site stub
     */
    public void setConcentrationSite(ConcentrationSiteStub conSiteStub) {
        this.conSiteStub = conSiteStub;
    }

    /**
     * Get collection site stub.
     * 
     * @return collection site stub
     */
    public CollectionSiteStub getColSiteStub() {
        return colSiteStub;
    }

    /**
     * Set collection site stub
     * 
     * @param colSiteStub collection site stub
     */
    public void setCollectionSiteStub(CollectionSiteStub colSiteStub) {
        this.colSiteStub = colSiteStub;
    }

    /**
     * Add a room to the target rooms
     * 
     * @param roomId target room identifier
     */
    public void targetRoom(int roomId) {
        this.targetedRooms.add(roomId);
    }

    /**
     * Detarget a room of the target rooms
     * 
     * @param roomId target room identifier
     */
    public void detargetRoom(Integer roomId) {
        // NOTE(L1fer): this parameter needs to be an Integer object since we want to remove by object from the array list (and not by index)
        this.targetedRooms.remove(roomId);
    }

    /**
     * Check if a room is in the target rooms 
     * 
     * @param roomId target room identifier
     * @return if room is targeted
     */
    public boolean isTargeted(int roomId) {
        return this.targetedRooms.contains(roomId);
    }

    /**
     * Get total assault parties' number
     * 
     * @return total assault parties' number
     */
    public int getTotalAssaultParties() {
        return totalAssaultParties;
    }

    /**
     * Decrement current assault parties' number
     * 
     */
    public void decrementTotalAssaultParties() {
        this.totalAssaultParties -= 1;
    }
}