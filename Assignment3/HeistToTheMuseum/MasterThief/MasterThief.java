package MasterThief;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ServerInterfaces.*;

/**
 * Master thief class.
 */
public class MasterThief extends Thief {
    /**
     * Information repository stub.
     */
    private InformationRepositoryInterface infReposStub;
    /**
     * Concentration site stub.
     */
    private ConcentrationSiteInterface conSiteStub;
    /**
     * Collection site stub.
     */
    private CollectionSiteInterface colSiteStub;

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
    public MasterThief(int currentState, InformationRepositoryInterface infReposStub, ConcentrationSiteInterface conSiteStub, CollectionSiteInterface colSiteStub,
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
        try {
            this.infReposStub.writeHeaderLog();
        } catch (RemoteException e) {
            System.out.println("Master Thief remote exception on writeHeaderLog: " + e.getMessage());
            System.exit(1);
        }

        // start master thief operations
        this.startOperations();

        // master thief lifecycle logic
        while (true) {
            changeState(MasterThiefStates.DECIDING_WHAT_TO_DO);

            // break and sum up results or form another party
            try {
                if (!this.conSiteStub.appraiseSit(this.getAvailableRooms().size(), this.totalAssaultParties))
                    break;
            } catch (RemoteException e) {
                System.out.println("Master Thief remote exception on appraiseSit: " + e.getMessage());
                System.exit(1);
            }

            // prepare an assault party
            changeState(MasterThiefStates.ASSEMBLING_A_GROUP);
            int availableRoomId = -1;
            try {
                availableRoomId = this.conSiteStub.prepareAssaultParty(this.availableRooms, this.targetedRooms);
            } catch (RemoteException e) {
                System.out.println("Master Thief remote exception on prepareAssaultParty: " + e.getMessage());
                System.exit(1);
            }

            if (availableRoomId > 0)
                targetRoom(availableRoomId);                // a room was successfully assigned
            else if (availableRoomId == -2)
                decrementTotalAssaultParties();             // no room assigned, decrement total assault parties
            
            // send the assault party
            try {
                this.conSiteStub.sendAssaultParty();
            } catch (RemoteException e) {
                System.out.println("Master Thief remote exception on sendAssaultParty: " + e.getMessage());
                System.exit(1);
            }

            // take a rest in case all assault parties are operational
            while (this.targetedRooms.size() == this.totalAssaultParties) {
                changeState(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);

                // wait for the arrival of a thief
                try {
                    this.colSiteStub.takeARest();
                } catch (RemoteException e) {
                    System.out.println("Master Thief remote exception on takeARest: " + e.getMessage());
                    System.exit(1);
                }

                // results[0]           ->  -1 if thief returned a canvas ; targeted room id otherwise
                // results[i] (i > 0)   ->  target room id in case of last thief to return from excursion
                int results[] = null;
                try {
                    results = this.colSiteStub.collectACanvas();
                } catch (RemoteException e) {
                    System.out.println("Master Thief remote exception on collectACanvas: " + e.getMessage());
                    System.exit(1);
                }

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
        try {
            this.conSiteStub.sumUpResults(this.totalAssaultParties);
        } catch (RemoteException e) {
            System.out.println("Master Thief remote exception on sumUpResults: " + e.getMessage());
            System.exit(1);
        }

        try {
            this.infReposStub.writeEarningsLog(this.paintingEarnings);
        } catch (RemoteException e) {
            System.out.println("Master Thief remote exception on writeEarningsLog: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Change thief state and write in log
     * 
     * @param state new state
     */
    public void changeState(int state) {
        setCurrentState(state);
        try {
            this.infReposStub.writeStatesLog(this.currentState, -1, -1, -1, -1, false, 0);
        } catch (RemoteException e) {
            System.out.println("Master Thief remote exception on writeStatesLog: " + e.getMessage());
            System.exit(1);
        }
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
    public InformationRepositoryInterface getInfReposStub() {
        return infReposStub;
    }

    /**
     * Set information repository stub.
     * 
     * @param infReposStub information repository stub
     */
    public void setInfReposStub(InformationRepositoryInterface infReposStub) {
        this.infReposStub = infReposStub;
    }

    /**
     * Get concentration site stub.
     * 
     * @return concentration site stub
     */
    public ConcentrationSiteInterface getConSiteStub() {
        return conSiteStub;
    }

    /**
     * Set concentration site stub.
     * 
     * @param conSiteStub concentration site stub
     */
    public void setConcentrationSite(ConcentrationSiteInterface conSiteStub) {
        this.conSiteStub = conSiteStub;
    }

    /**
     * Get collection site stub.
     * 
     * @return collection site stub
     */
    public CollectionSiteInterface getColSiteStub() {
        return colSiteStub;
    }

    /**
     * Set collection site stub
     * 
     * @param colSiteStub collection site stub
     */
    public void setCollectionSiteStub(CollectionSiteInterface colSiteStub) {
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
     */
    public void decrementTotalAssaultParties() {
        this.totalAssaultParties -= 1;
    }
}