package ServerInterfaces;

import java.rmi.*;
import java.util.ArrayList;

/**
 * Operational interface of a remote object of type ConcentrationSite.
 *
 * It provides the functionality to access the Concentration Site.
 */
public interface ConcentrationSiteInterface extends Remote {
    /**
     * Master thief appraise site function.
     * 
     * @param totalAvailableRooms total available rooms
     * @param totalAssaultParties total assault parties
     * @return true if there are still rooms to check
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public boolean appraiseSit(int totalAvailableRooms, int totalAssaultParties) throws RemoteException;
    
    /**
     * Master thief prepare assault function.
     * 
     * @param availableRooms available rooms reference
     * @param targetedRooms targeted rooms reference
     * @return assigned room id
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public int prepareAssaultParty(ArrayList<Integer> availableRooms, ArrayList<Integer> targetedRooms) throws RemoteException;

    /**
     * Master thief send assault function.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void sendAssaultParty() throws RemoteException;

    /**
     * Master thief ends the heist and sum up the results.
     * 
     * @param totalAssaultParties total assault parties
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void sumUpResults(int totalAssaultParties) throws RemoteException;

    /**
     * Ordinary thief wait orders.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @return assigned room id
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public int amINeeded(int thiefId, int thiefPartyId) throws RemoteException;

    /**
     * Ordinary thief preparing excursion.
     * 
     * @param thiefId thief id
     * @param thiefPartyId party thief id
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void prepareExcursion(int thiefId, int thiefPartyId) throws RemoteException;

    /**
     * Ordinary thief exiting concentration site.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void thiefLeaving() throws RemoteException;

    /**
     * Operation server shutdown.
     *
     * New operation.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void shutdown() throws RemoteException;
}
