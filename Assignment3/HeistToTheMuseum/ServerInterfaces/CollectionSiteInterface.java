package ServerInterfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type CollectionSite.
 *
 * It provides the functionality to access the Collection Site.
 */
public interface CollectionSiteInterface extends Remote {
    /**
     * Master thief waiting for a group arrival.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void takeARest() throws RemoteException;

    /**
     * Master thief collecting a canvas from a ordinary thief who arrived.
     * 
     * @return canvas collection results
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public int[] collectACanvas() throws RemoteException;
    
    /**
     * Ordinary thief signalling master to hand a canvas.
     * 
     * @param thiefId thief id
     * @param partyId party id
     * @param hasCanvas has canvas status
     * @param partyTargetRoomId party target room id
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void handACanvas(int thiefId, int partyId, boolean hasCanvas, int partyTargetRoomId) throws RemoteException;

    /**
     * Operation server shutdown.
     *
     * New operation.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void shutdown() throws RemoteException;
}
