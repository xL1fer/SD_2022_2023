package ServerInterfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type AssaultParty.
 *
 * It provides the functionality to access the Assault Party.
 */
public interface AssaultPartyInterface extends Remote {
    /**
     * Ordinary thief crawling in function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @param targetRoomId target room id
     * @return true if thief reached the targeted room
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public boolean crawlIn(int thiefId, int thiefMaximumDisplacement, int targetRoomId) throws RemoteException;
    
    /**
     * Ordinary thief crawling out function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @return true if thief reached the targeted room
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public boolean crawlOut(int thiefId, int thiefMaximumDisplacement) throws RemoteException;


    /**
     * Operation server shutdown.New operation.
     *
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void shutdown() throws RemoteException;

}
