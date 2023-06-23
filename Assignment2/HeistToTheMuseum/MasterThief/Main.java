package MasterThief;

import CommInfra.*;

/**
 * Client side of the master thief.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args runtime arguments
     * args[0] - name of the platform where is located the information repository server
     * args[1] - port nunber for listening to information repository service requests
     * args[2] - name of the platform where is located the concentration site server
     * args[3] - port nunber for listening to concentration site service requests
     * args[4] - name of the platform where is located the collection site server
     * args[5] - port nunber for listening to collection site service requests
     */
    public static void main(String[] args) {
        String infReposServerHostName;                      // name of the platform where is located the information repository server
        int infReposServerPortNumb = -1;                    // port number for listening to service requests
        String conSiteServerHostName;                       // name of the platform where is located the concentration site server
        int conSiteServerPortNumb = -1;                     // port number for listening to service requests
        String colSiteServerHostName;                       // name of the platform where is located the concentration site server
        int colSiteServerPortNumb = -1;                     // port number for listening to service requests
        MasterThief masterThief;                            // master thief thread
        InformationRepositoryStub infReposStub;             // remote reference to the information repository
        ConcentrationSiteStub conSiteStub;                  // remote reference to the concentration site
        CollectionSiteStub colSiteStub;                     // remote reference to the collection site

        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber];
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int partiesNumber = thievesNumber / partyMembersNumber;
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];
        //int maxRoomPaintings = configs[Configs.maxRoomPaintings];
        //int maxRoomDistance = configs[Configs.maxRoomDistance];
        //int partySeparationLimit = configs[Configs.partySeparationLimit];
        //int maximumDisplacement = configs[Configs.maximumDisplacement];

        // getting simulation runtime parameters

        if (args.length != 6)
        {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }
        infReposServerHostName = args[0];
        try {
            infReposServerPortNumb = Integer.parseInt (args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit(1);
        }
        if ((infReposServerPortNumb < 4000) || (infReposServerPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit(1);
        }
        conSiteServerHostName = args[2];
        try {
            conSiteServerPortNumb = Integer.parseInt(args[3]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[3] is not a number!");
            System.exit (1);
        }
        if ((conSiteServerPortNumb < 4000) || (conSiteServerPortNumb >= 65536)) {
            System.out.println("args[3] is not a valid port number!");
            System.exit(1);
        }
        colSiteServerHostName = args[4];
        try {
            colSiteServerPortNumb = Integer.parseInt (args[5]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[5] is not a number!");
            System.exit(1);
        }
        if ((colSiteServerPortNumb < 4000) || (colSiteServerPortNumb >= 65536)) {
            System.out.println("args[5] is not a valid port number!");
            System.exit(1);
        }

        // simulation initialization

        infReposStub = new InformationRepositoryStub(infReposServerHostName, infReposServerPortNumb);
        conSiteStub = new ConcentrationSiteStub(conSiteServerHostName, conSiteServerPortNumb);
        colSiteStub = new CollectionSiteStub(colSiteServerHostName, colSiteServerPortNumb);
        masterThief = new MasterThief(MasterThiefStates.PLANNING_THE_HEIST, infReposStub, conSiteStub, colSiteStub, paintingRooms, partiesNumber);

        // simulation start

        masterThief.start();

        // waiting for the end of the simulation

        System.out.println();
        //while (masterThief.isAlive())
        //{
            //conSiteStub.endOperation();
            //Thread.yield();     // causes the currently executing thread object to temporarily pause and allow other threads to execute
        //}
        try {
            masterThief.join();
        }
        catch (InterruptedException e) {}
        System.out.println("Master thief has terminated.");
        System.out.println();

        infReposStub.shutdown();
        conSiteStub.shutdown();
        colSiteStub.shutdown();
    }
}
