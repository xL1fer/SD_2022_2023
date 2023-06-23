package OrdinaryThief;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import CommInfra.*;
import ServerInterfaces.*;

/**
 * Client side of the Museum Heist (ordinary thief).
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args runtime arguments
     * args[0] - name of the platform where is located the RMI registering service
     * args[1] - port number where the registering service is listening to service requests
     */
    public static void main(String[] args) {
        String rmiRegHostName;      // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;    // port number where the registering service is listening to service requests

        // getting problem runtime parameters

        if (args.length != 2) {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }
        rmiRegHostName = args[0];
        try {
            rmiRegPortNumb = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("args[1] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[1] is not a valid port number!");
            System.exit(1);
        }

        // problem initialization

        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber] - 1;
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int maximumDisplacement = configs[ConfigsOffsets.maximumDisplacement];
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];
        int maxRoomPaintings = configs[ConfigsOffsets.maxRoomPaintings];
        int maxRoomDistance = configs[ConfigsOffsets.maxRoomDistance];

        String nameEntryInfRepos = "InformationRepository";             // public name of the information repository object
        InformationRepositoryInterface infReposStub = null;             // remote reference to the information repository object
        String nameEntryConSite = "ConcentrationSite";                  // public name of the concentration site object
        ConcentrationSiteInterface conSiteStub = null;                  // remote reference to the concentration site object
        String nameEntryColSite = "CollectionSite";                     // public name of the collection site object
        CollectionSiteInterface colSiteStub = null;                     // remote reference to the collection site object
        String nameEntryAssaultParty1 = "AssaultParty1";                // public name of the assault party 1 object
        AssaultPartyInterface assaultPartyStub1 = null;                 // remote reference to the assault party 1 object
        String nameEntryAssaultParty2 = "AssaultParty2";                // public name of the assault party 2 object
        AssaultPartyInterface assaultPartyStub2 = null;                 // remote reference to the assault party 2 object
        String nameEntryMuseum = "Museum";                              // public name of the museum object
        MuseumInterface museumStub = null;                              // remote reference to the museum object
        Registry registry = null;                                       // remote reference for registration in the RMI registry service
        OrdinaryThief[] thieves = new OrdinaryThief[thievesNumber];     // array of ordinary thieves

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            infReposStub = (InformationRepositoryInterface) registry.lookup(nameEntryInfRepos);
        } catch (RemoteException e) {
            System.out.println("InfRepos lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("InfRepos not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            conSiteStub = (ConcentrationSiteInterface) registry.lookup(nameEntryConSite);
        } catch (RemoteException e) {
            System.out.println("ConSite lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ConSite not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            colSiteStub = (CollectionSiteInterface) registry.lookup(nameEntryColSite);
        } catch (RemoteException e) {
            System.out.println("ColSite lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ColSite not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            assaultPartyStub1 = (AssaultPartyInterface) registry.lookup(nameEntryAssaultParty1);
        } catch (RemoteException e) {
            System.out.println("AssaultParty1 lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("AssaultParty1 not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            assaultPartyStub2 = (AssaultPartyInterface) registry.lookup(nameEntryAssaultParty2);
        } catch (RemoteException e) {
            System.out.println("AssaultParty2 lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("AssaultParty2 not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            museumStub = (MuseumInterface) registry.lookup(nameEntryMuseum);
        } catch (RemoteException e) {
            System.out.println("Museum lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Museum not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // initialize museum rooms
        int[][] museumRooms = new int[2][paintingRooms];
        for (int i = 0; i < paintingRooms; i++) {
            int paintings = (int) (Math.random() * (maxRoomPaintings - 8 + 1)) + 8;     // minimum number of paintings hardcoded to 8
            int distance = (int) (Math.random() * (maxRoomDistance - 15 + 1)) + 15;     // minimum room distance hardcoded to 8

            museumRooms[0][i] = distance;
            museumRooms[1][i] = paintings;
            System.out.println("< Room " + (i + 1) + " initialized (d = " + distance + " ; p = " + paintings + ")");
        }

        // fill museum rooms in the information repository and museum servers
        try {
            infReposStub.fillMuseum(museumRooms);
            museumStub.fillMuseum(museumRooms);
        } catch (RemoteException e) {
            System.out.println("Ordinary Thief remote exception on fillMuseum: " + e.getMessage());
            System.exit(1);
        }

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
            System.out.println("Ordinary Thief " + (i + 1) + " has terminated.");

            try {
                infReposStub.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Information Repository shutdown: " + e.getMessage());
                System.exit(1);
            }
            try {
                conSiteStub.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Concentration Site shutdown: " + e.getMessage());
                System.exit(1);
            }
            try {
                colSiteStub.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Collection Site shutdown: " + e.getMessage());
                System.exit(1);
            }
            try {
                assaultPartyStub1.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Assault Party 1 shutdown: " + e.getMessage());
                System.exit(1);
            }
            try {
                assaultPartyStub2.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Assault Party 2 shutdown: " + e.getMessage());
                System.exit(1);
            }
            try {
                museumStub.shutdown();
            } catch (RemoteException e) {
                System.out.println("Ordinary Thief remote exception on Museum shutdown: " + e.getMessage());
                System.exit(1);
            }
        }
        System.out.println();
    }
}
