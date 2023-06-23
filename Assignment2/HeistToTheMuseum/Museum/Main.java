package Museum;

import java.net.SocketTimeoutException;

import CommInfra.*;

/**
 * Server side of the museum.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class Main {
    /**
     * Flag signaling the service is active.
     */
    public static boolean waitConnection;

    /**
     * Main method.
     *
     * @param args runtime arguments
     * args[0] - port number for listening to service requests
     */
    public static void main(String[] args) {
        Museum museum;                                      // museum
        MuseumInterface museumInterface;                    // interface to the museum
        ServerCom scon, sconi;                              // communication channels
        int portNumb = -1;                                  // port number for listening to service requests

        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber];
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int partiesNumber = thievesNumber / partyMembersNumber;
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];
        int maxRoomPaintings = configs[ConfigsOffsets.maxRoomPaintings];
        //int maxRoomDistance = configs[Configs.maxRoomDistance];
        //int partySeparationLimit = configs[Configs.partySeparationLimit];
        //int maximumDisplacement = configs[Configs.maximumDisplacement];

        if (args.length != 1) {
            System.out.println("Wrong number of parameters!");
            System.exit (1);
        }
        try {
            portNumb = Integer.parseInt (args[0]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[0] is not a number!");
            System.exit (1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536)) {
            System.out.println("args[0] is not a valid port number!");
            System.exit (1);
        }

        // service is established

        museum = new Museum(paintingRooms, partyMembersNumber, partiesNumber, maxRoomPaintings);    // service is instantiated
        museumInterface = new MuseumInterface(museum);                                              // interface to the service is instantiated
        scon = new ServerCom(portNumb);                                                             // listening channel at the public port is established
        scon.start();
        System.out.println("Service is established! (port " + portNumb + ")");
        System.out.println("Server is listening for service requests.");

        // service request processing

        MuseumClientProxy cliProxy;          // service provider agent

        waitConnection = true;
        while (waitConnection) {
            try {
                sconi = scon.accept();                                          // enter listening procedure
                cliProxy = new MuseumClientProxy(sconi, museumInterface);       // start a service provider agent to address
                cliProxy.start();                                               // the request of service
            }
            catch (SocketTimeoutException e) {}
        }
        scon.end();                                    // operations termination
        System.out.printf("\nMuseum server was shutdown.\n");
        System.out.println();
    }
}