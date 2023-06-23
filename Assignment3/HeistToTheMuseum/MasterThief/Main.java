package MasterThief;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import CommInfra.*;
import ServerInterfaces.*;

/**
 * Client side of the Museum Heist (master thief).
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
        int thievesNumber = configs[ConfigsOffsets.thievesNumber];
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int partiesNumber = thievesNumber / partyMembersNumber;
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];

        String nameEntryInfRepos = "InformationRepository";     // public name of the information repository object
        InformationRepositoryInterface infReposStub = null;     // remote reference to the information repository object
        String nameEntryConSite = "ConcentrationSite";          // public name of the concentration site object
        ConcentrationSiteInterface conSiteStub = null;          // remote reference to the concentration site object
        String nameEntryColSite = "CollectionSite";             // public name of the collection site object
        CollectionSiteInterface colSiteStub = null;             // remote reference to the collection site object
        Registry registry = null;                               // remote reference for registration in the RMI registry service
        MasterThief masterThief;                                // master thief thread

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

        masterThief = new MasterThief(MasterThiefStates.PLANNING_THE_HEIST, infReposStub, conSiteStub, colSiteStub, paintingRooms, partiesNumber);


        // simulation start

        masterThief.start();

        // waiting for the end of the simulation

        System.out.println();
        try {
            masterThief.join();
        }
        catch (InterruptedException e) {}
        System.out.println("Master thief has terminated.");
        System.out.println();

        try {
            infReposStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Master thief remote exception on Information Repository shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            conSiteStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Master thief remote exception on Concentration Site shutdown: " + e.getMessage());
            System.exit(1);
        }
        try {
            colSiteStub.shutdown();
        } catch (RemoteException e) {
            System.out.println("Master thief remote exception on Collection Site shutdown: " + e.getMessage());
            System.exit(1);
        }
    }
}
