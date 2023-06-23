package ConcentrationSite;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import CommInfra.*;
import ServerInterfaces.ConcentrationSiteInterface;
import ServerInterfaces.Register;

/**
 * Instantiation and registering of a concentration site object.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on Java RMI.
 */
public class Main {
    /**
     * Flag signaling the end of operations.
     */
    public static boolean end = false;

    /**
     * Main method.
     *
     * @param args runtime arguments
     * args[0] - port number for listening to service requests
     * args[1] - name of the platform where is located the RMI registering service
     * args[2] - port number where the registering service is listening to service requests
     */
    public static void main(String[] args) {
        int portNumb = -1;                              // port number for listening to service requests
        String rmiRegHostName;                          // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;                        // port number where the registering service is listening to service requests

        if (args.length != 3) {
            System.out.println("Wrong number of parameters!");
            System.exit(1);
        }
        try {
            portNumb = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("args[0] is not a number!");
            System.exit(1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536)) {
            System.out.println("args[0] is not a valid port number!");
            System.exit(1);
        }
        rmiRegHostName = args[1];
        try {
            rmiRegPortNumb = Integer.parseInt(args[2]);
        }
        catch (NumberFormatException e) {
            System.out.println("args[2] is not a number!");
            System.exit(1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536)) {
            System.out.println("args[2] is not a valid port number!");
            System.exit(1);
        }

        // create and install the security manager

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        System.out.println("Security manager was installed!");

        // remote reference for registration in the RMI registry service

        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e) {
            System.out.println("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("RMI registry was created!");

        // instantiate a concentration site object

        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber];
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int partiesNumber = thievesNumber / partyMembersNumber;

        ConcentrationSite concentrationSite = new ConcentrationSite(partiesNumber, partyMembersNumber);   // concentration site object
        ConcentrationSiteInterface concentrationSiteStub = null;                 // remote reference to the concentration site object

        try {
            concentrationSiteStub = (ConcentrationSiteInterface)UnicastRemoteObject.exportObject(concentrationSite, portNumb);
        } catch (RemoteException e) {
            System.out.println("Concentration Site stub generation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Concentration Site Stub was generated!");

        // register it with the general registry service

        String nameEntryBase = "RegisterHandler";               // public name of the object that enables the registration
                                                                // of other remote objects
        String nameEntryObject = "ConcentrationSite";              // public name of the concentration site object
        Register reg = null;                                    // remote reference to the object that enables the registration
                                                                // of other remote objects

        try {
            reg = (Register)registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            reg.bind(nameEntryObject, concentrationSiteStub);
        } catch (RemoteException e) {
            System.out.println("Concentration Site registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Concentration Site already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Concentration Site object was registered!");

        // wait for the end of operations

        System.out.println("Concentration Site is in operation!");
        try {
            while (!end) {
                synchronized(Class.forName("ConcentrationSite.Main")) {
                    try {
                        (Class.forName("ConcentrationSite.Main")).wait();
                    } catch (InterruptedException e) {
                        System.out.println("Concentration Site main thread was interrupted!");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type ConcentrationSite.Main was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }

        // server shutdown

        boolean shutdownDone = false;                  // flag signalling the shutdown of the concentration site service

        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Concentration Site deregistration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch (NotBoundException e) {
            System.out.println("Concentration Site not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Concentration Site was deregistered!");

        try {
            shutdownDone = UnicastRemoteObject.unexportObject(concentrationSite, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Concentration Site unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println();
        if (shutdownDone)
            System.out.println("Concentration Site was shutdown!");
    }

    /**
     * Close of operations.
     */
    public static void shutdown() {
        end = true;
        try {
            synchronized(Class.forName("ConcentrationSite.Main")) {
                (Class.forName("ConcentrationSite.Main")).notify();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type ConcentrationSite.Main was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}