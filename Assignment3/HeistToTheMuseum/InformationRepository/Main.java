package InformationRepository;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import CommInfra.*;
import ServerInterfaces.InformationRepositoryInterface;
import ServerInterfaces.Register;

/**
 * Instantiation and registering of a information repository object.
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

        // instantiate a information repository object

        SimulationInput.getSimulationInput();
        int[] configs = SimulationInput.inputArray;

        // get config variables
        int thievesNumber = configs[ConfigsOffsets.thievesNumber];
        int partyMembersNumber = configs[ConfigsOffsets.partyMembersNumber];
        int partiesNumber = thievesNumber / partyMembersNumber;
        int paintingRooms = configs[ConfigsOffsets.paintingRooms];

        InformationRepository infRepos = new InformationRepository(partiesNumber, partyMembersNumber, paintingRooms);   // information repository object
        InformationRepositoryInterface infReposStub = null;                 // remote reference to the information repository object

        try {
            infReposStub = (InformationRepositoryInterface)UnicastRemoteObject.exportObject(infRepos, portNumb);
        } catch (RemoteException e) {
            System.out.println("Information Repository stub generation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Information Repository Stub was generated!");

        // register it with the general registry service

        String nameEntryBase = "RegisterHandler";               // public name of the object that enables the registration
                                                                // of other remote objects
        String nameEntryObject = "InformationRepository";       // public name of the information repository object
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
            reg.bind(nameEntryObject, infReposStub);
        } catch (RemoteException e) {
            System.out.println("Information Repository registration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("Information Repository already bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Information Repository object was registered!");

        // wait for the end of operations

        System.out.println("Information Repository is in operation!");
        try {
            while (!end) {
                synchronized(Class.forName("InformationRepository.Main")) {
                    try {
                        (Class.forName("InformationRepository.Main")).wait();
                    } catch (InterruptedException e) {
                        System.out.println("Information Repository main thread was interrupted!");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type InformationRepository.Main was not found (blocking)!");
            e.printStackTrace();
            System.exit(1);
        }

        // server shutdown

        boolean shutdownDone = false;                  // flag signalling the shutdown of the information repository service

        try {
            reg.unbind(nameEntryObject);
        } catch (RemoteException e) {
            System.out.println("Information Repository deregistration exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        catch (NotBoundException e) {
            System.out.println("Information Repository not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Information Repository was deregistered!");

        try {
            shutdownDone = UnicastRemoteObject.unexportObject(infRepos, true);
        } catch (NoSuchObjectException e) {
            System.out.println("Information Repository unexport exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println();
        if (shutdownDone)
            System.out.println("Information Repository was shutdown!");
    }

    /**
     * Close of operations.
     */
    public static void shutdown() {
        end = true;
        try {
            synchronized(Class.forName("InformationRepository.Main")) {
                (Class.forName("InformationRepository.Main")).notify();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("The data type InformationRepository.Main was not found (waking up)!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}