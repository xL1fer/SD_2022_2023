package ServerInterfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type Museum.
 *
 * It provides the functionality to access the Museum.
 */
public interface MuseumInterface extends Remote {
    /**
     * Ordinary thief roll a museum canvas function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @param targetRoomId target room id
     * @return true if a canvas was rolled
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public boolean rollACanvas(int thiefId, int thiefPartyId, int targetRoomId) throws RemoteException;

    /**
     * Ordinary thief get ready to reverse direction function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void reverseDirection(int thiefId, int thiefPartyId) throws RemoteException;

    /**
     * Fill museum rooms.
     * 
     * @param museumRooms museum rooms
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void fillMuseum(int[][] museumRooms) throws RemoteException;

    /**
     * Operation server shutdown.
     *
     * New operation.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void shutdown() throws RemoteException;
}
