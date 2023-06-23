package InformationRepository;

import CommInfra.ConfigsOffsets;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Information repository class.
 */
public class InformationRepository {
    /**
     * Log file writer object.
     */
    private FileWriter logFile;
    /**
     * Master thief state.
     */
    private int masterThiefState;
    /**
     * Ordinary thieves states.
     */
    private int[][] ordinaryThievesStates;
    /**
     * Museum rooms info.
     */
    private int[][] museumRooms;
    /**
     * Parties target room.
     */
    private int[] partiesTargetRoom;
    /**
     * Total assault parties.
     */
    private int assaultPartiesNumber;
    /**
     * Members per assault party.
     */
    private int partyMembersNumber;
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;
    /**
     * Simulation debug flag.
     */
    private boolean DEBUG = true;

    /**
     * Information repository constructor.
     * 
     * @param assaultPartiesNumber total assault parties
     * @param partyMembersNumber members per assault party
     * @param museumRoomsNumber number of museum rooms
     */
    public InformationRepository(int assaultPartiesNumber, int partyMembersNumber, int museumRoomsNumber) {
        try {
            this.logFile = new FileWriter("simulationlog.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.masterThiefState = 1;
        this.ordinaryThievesStates = new int[4][assaultPartiesNumber * partyMembersNumber];

        this.partiesTargetRoom = new int [assaultPartiesNumber];
        for (int i = 0; i < assaultPartiesNumber; i++)
            this.partiesTargetRoom[i] = -1;

        this.assaultPartiesNumber = assaultPartiesNumber;
        this.partyMembersNumber = partyMembersNumber;
        this.museumRooms = new int[2][museumRoomsNumber];

        Arrays.fill(ordinaryThievesStates[0], 1);   // state
        for (int i = 0; i < ordinaryThievesStates[1].length; i++)   // partyPos
            ordinaryThievesStates[1][i] = i % this.partyMembersNumber;
        Arrays.fill(ordinaryThievesStates[2], 0);   // hasCanvas

        this.nEntities = 0;

        System.out.println("< Information Repository initialized");
    }

    /**
     * Log file header writer.
     */
    public synchronized void writeHeaderLog() {
        try {
            if (DEBUG) 
                System.out.printf("> Writing header log\n");

            this.logFile.write("MstT\t");
            for (int i = 0; i < this.ordinaryThievesStates[0].length; i++)
                this.logFile.write(String.format("Thief %d\t\t", i + 1));

            this.logFile.write("\n");

            // assault parties
            for (int i = 0; i < this.assaultPartiesNumber; i++) {
                for (int j = 0; j < this.partyMembersNumber + 2; j++)
                    this.logFile.write("\t");
                this.logFile.write(String.format("Assault Party %d\t\t", i + 1));
            }

            // museum
            this.logFile.write("\t");
            for (int i = 0; i < this.museumRooms[0].length; i++) {
                this.logFile.write("\t");
            }
            this.logFile.write(String.format("Museum"));
            
            this.logFile.write("\n");

            // assault parties elements
            for (int i = 0; i < this.assaultPartiesNumber; i++) {
                this.logFile.write("\t");
                for (int j = 0; j < partyMembersNumber; j++) {
                    this.logFile.write(String.format("\t\tElem %d", j + 1));
                }
            }

            // museum rooms
            this.logFile.write("\t");
            for (int i = 0; i < this.museumRooms[0].length; i++) {
                this.logFile.write(String.format("\tRoom %d", i + 1));
            }

            this.logFile.write("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Log file entities states writer.
     * 
     * @param masterThiefState master thief state
     * @param ordinaryThiefId ordinary thief id
     * @param ordinaryThiefState ordinary thief state
     * @param canvasStatus ordinary thief canvas status
     * @param partyTargetRoomId ordinary thief party target room id
     * @param partyStatus ordinary thief party status
     * @param removeCanvas ordinary thief remove museum room canvas
     */
    public synchronized void writeStatesLog(int masterThiefState, int ordinaryThiefId, int ordinaryThiefState, int canvasStatus, int partyTargetRoomId, boolean partyStatus, int removeCanvas) {
        // sleep for a certain ammount of time
        if (DEBUG) {
            /*
            try {
                Thread.sleep((long) Math.floor(Math.random() * (200 - 100 + 1) + 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            if (ordinaryThiefId != -1)
                System.out.printf("> Writing ordinary thief state log\n");
            else
                System.out.printf("> Writing master thief state log\n");
        }

        if (ordinaryThiefId != -1) {
            this.ordinaryThievesStates[0][ordinaryThiefId - 1] = ordinaryThiefState;
            this.ordinaryThievesStates[2][ordinaryThiefId - 1] = canvasStatus;
            this.partiesTargetRoom[(ordinaryThiefId - 1) / this.partyMembersNumber] = partyTargetRoomId;
        }
        else
            this.masterThiefState = masterThiefState;

        if (removeCanvas == 1)
            this.museumRooms[1][partyTargetRoomId - 1]--;

        try {
            // thieves states
            this.logFile.write(String.format("%d\t", this.masterThiefState));
            for (int i = 0; i < this.ordinaryThievesStates[0].length; i++) {
                char situation = 'W';
                if (partyStatus)
                    situation = 'P';

                this.logFile.write(String.format("\t%d %c %d\t", this.ordinaryThievesStates[0][i], situation, this.ordinaryThievesStates[3][i]));
            }

            this.logFile.write("\n\t");

            // party thief's (id, pos and canvas) staus
            for (int i = 0; i < this.ordinaryThievesStates[0].length; i++) {
                if (i % this.partyMembersNumber == 0)
                    this.logFile.write(String.format("%d\t", partiesTargetRoom[i / this.partyMembersNumber]));

                this.logFile.write(String.format("\t%d %d %d\t", i + 1, ordinaryThievesStates[1][i], ordinaryThievesStates[2][i]));
            }

            this.logFile.write("\t");

            // museum room's status
            for (int i = 0; i < this.museumRooms[0].length; i++) {
                this.logFile.write(String.format("%d\t%d\t", this.museumRooms[1][i], this.museumRooms[0][i]));
            }

            this.logFile.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Log file earnings writer.
     * 
     * @param paintingEarnings heist paintings earnings
     */
    public synchronized void writeEarningsLog(int paintingEarnings) {
        try {
            if (DEBUG) 
                System.out.printf("> My friends, tonight's effort produced %d priceless paintings!\n", paintingEarnings);
                
            this.logFile.write(String.format("My friends, tonight's effort produced %d priceless paintings!\n", paintingEarnings));
            this.logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill museum rooms.
     * 
     * @param museumRooms museum rooms
     */
    public synchronized void fillMuseum(int[][] museumRooms) {
        if (DEBUG) System.out.printf("> Filling museum rooms\n");
        
        this.museumRooms = museumRooms;
    }

    /**
     * Operation server shutdown.
     *
     * New operation.
     */
    public synchronized void shutdown() {
        this.nEntities += 1;
        if (this.nEntities >= SimulationInput.inputArray[ConfigsOffsets.thievesNumber])
            Main.waitConnection = false;
    }
}