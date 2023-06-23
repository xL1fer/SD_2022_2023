package OrdinaryThief;

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
     * args[6] - name of the platform where is located the assault party 1 server
     * args[7] - port nunber for listening to assault party service requests
     * args[8] - name of the platform where is located the assault party 2 server
     * args[9] - port nunber for listening to assault party service requests
     * args[10] - name of the platform where is located the museum server
     * args[11] - port nunber for listening to museum service requests
     */
    public static void main(String[] args) {
        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber] - 1;
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int maximumDisplacement = configs[ConfigsOffsets.maximumDisplacement];
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];
        int maxRoomPaintings = configs[ConfigsOffsets.maxRoomPaintings];
        int maxRoomDistance = configs[ConfigsOffsets.maxRoomDistance];

        String infReposServerHostName;                              // name of the platform where is located the information repository server
        int infReposServerPortNumb = -1;                            // port number for listening to service requests
        String conSiteServerHostName;                               // name of the platform where is located the concentration site server
        int conSiteServerPortNumb = -1;                             // port number for listening to service requests
        String colSiteServerHostName;                               // name of the platform where is located the collection site server
        int colSiteServerPortNumb = -1;                             // port number for listening to service requests
        String assaultPartyServerHostName1;                         // name of the platform where is located the assault party 1 site server
        int assaultPartyServerPortNumb1 = -1;                       // port number for listening to service requests
        String assaultPartyServerHostName2;                         // name of the platform where is located the assault party 2 site server
        int assaultPartyServerPortNumb2 = -1;                       // port number for listening to service requests
        String museumServerHostName;                                // name of the platform where is located the museum site server
        int museumServerPortNumb = -1;                              // port number for listening to service requests
        OrdinaryThief[] thieves = new OrdinaryThief[thievesNumber]; // array of customer threads
        InformationRepositoryStub infReposStub;                     // remote reference to the information repository
        ConcentrationSiteStub conSiteStub;                          // remote reference to the concentration site
        CollectionSiteStub colSiteStub;                             // remote reference to the collection site
        AssaultPartyStub assaultPartyStub1;                         // remote reference to the assault party 1
        AssaultPartyStub assaultPartyStub2;                         // remote reference to the assault party 2
        MuseumStub museumStub;                                      // remote reference to the museum

        // getting simulation runtime parameters

        if (args.length != 12)
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
        assaultPartyServerHostName1 = args[6];
        try {
            assaultPartyServerPortNumb1 = Integer.parseInt (args[7]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[7] is not a number!");
            System.exit(1);
        }
        if ((assaultPartyServerPortNumb1 < 4000) || (assaultPartyServerPortNumb1 >= 65536)) {
            System.out.println("args[7] is not a valid port number!");
            System.exit(1);
        }
        assaultPartyServerHostName2 = args[8];
        try {
            assaultPartyServerPortNumb2 = Integer.parseInt (args[9]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[9] is not a number!");
            System.exit(1);
        }
        if ((assaultPartyServerPortNumb2 < 4000) || (assaultPartyServerPortNumb2 >= 65536)) {
            System.out.println("args[9] is not a valid port number!");
            System.exit(1);
        }
        museumServerHostName = args[10];
        try {
            museumServerPortNumb = Integer.parseInt (args[11]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[11] is not a number!");
            System.exit(1);
        }
        if ((museumServerPortNumb < 4000) || (museumServerPortNumb >= 65536)) {
            System.out.println("args[11] is not a valid port number!");
            System.exit(1);
        }

        // simulation initialization

        // initialize museum rooms
        int[][] museumRooms = new int[2][paintingRooms];
        for (int i = 0; i < paintingRooms; i++) {
            int paintings = (int) (Math.random() * (maxRoomPaintings - 8 + 1)) + 8;     // minimum number of paintings hardcoded to 8
            int distance = (int) (Math.random() * (maxRoomDistance - 15 + 1)) + 15;     // minimum room distance hardcoded to 8

            museumRooms[0][i] = distance;
            museumRooms[1][i] = paintings;
            System.out.println("< Room " + (i + 1) + " initialized (d = " + distance + " ; p = " + paintings + ")");
        }

        infReposStub = new InformationRepositoryStub(infReposServerHostName, infReposServerPortNumb);
        conSiteStub = new ConcentrationSiteStub(conSiteServerHostName, conSiteServerPortNumb);
        colSiteStub = new CollectionSiteStub(colSiteServerHostName, colSiteServerPortNumb);
        assaultPartyStub1 = new AssaultPartyStub(assaultPartyServerHostName1, assaultPartyServerPortNumb1);
        assaultPartyStub2 = new AssaultPartyStub(assaultPartyServerHostName2, assaultPartyServerPortNumb2);
        museumStub = new MuseumStub(museumServerHostName, museumServerPortNumb);

        // fill museum rooms in the information repository and museum servers
        infReposStub.fillMuseum(museumRooms);
        museumStub.fillMuseum(museumRooms);

        // initialize ordinary thieves threads
        for (int i = 0; i < thievesNumber; i++) {
            int curPartyInd = (i / partyMembersNumber) + 1;
            int curMaxDisplacement = (int) (Math.random() * (maximumDisplacement - 2 + 1)) + 2;   // minimum displacement hardcoded to 2 for now
            // assault party stub 1
            if (i < thievesNumber / 2) {
                thieves[i] = new OrdinaryThief(OrdinaryThiefStates.CONCENTRATION_SITE, infReposStub, conSiteStub, colSiteStub, assaultPartyStub1, museumStub, i + 1, curMaxDisplacement, curPartyInd, museumRooms);
            }
            // assault party stub 2
            else {
                thieves[i] = new OrdinaryThief(OrdinaryThiefStates.CONCENTRATION_SITE, infReposStub, conSiteStub, colSiteStub, assaultPartyStub2, museumStub, i + 1, curMaxDisplacement, curPartyInd, museumRooms);
            }
        }

        // simulation start

        for (int i = 0; i < thievesNumber; i++)
            thieves[i].start();

        // waiting for the end of the simulation

        System.out.println();
        for (int i = 0; i < thievesNumber; i++) {
            try {
                thieves[i].join();
            }
            catch (InterruptedException e) {}
            System.out.println("Ordinary thief " + (i + 1) + " has terminated.");

            infReposStub.shutdown();
            conSiteStub.shutdown();
            colSiteStub.shutdown();
            assaultPartyStub1.shutdown();
            assaultPartyStub2.shutdown();
            museumStub.shutdown();
        }
        System.out.println();
    }
}
