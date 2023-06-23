package ServerInterfaces;

import java.rmi.*;

/**
 * Operational interface of a remote object of type AssaultParty.
 *
 * It provides the functionality to access the Assault Party.
 */
public interface InformationRepositoryInterface extends Remote {
    /**
     * Log file header writer.
     * 
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void writeHeaderLog() throws RemoteException;

    /**
     * Log file entities states writer.
     * 
     * @param masterThiefState master thief state
     * @param ordinaryThiefId ordinary thief id
     * @param ordinaryThiefState ordinary thief state
     * @param canvasStatus ordinary thief canvas status
     * @param partyTargetRoomId ordinary thief party target room id
     * @param partyStatus ordinary thief party status
     * @param removeCanvas ordinary thief remove museum room canvas
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void writeStatesLog(int masterThiefState, int ordinaryThiefId, int ordinaryThiefState, int canvasStatus, int partyTargetRoomId, boolean partyStatus, int removeCanvas) throws RemoteException;

    /**
     * Log file earnings writer.
     * 
     * @param paintingEarnings heist paintings earnings
     * @throws java.rmi.RemoteException RMI remote exception
     */
    public void writeEarningsLog(int paintingEarnings) throws RemoteException;

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
