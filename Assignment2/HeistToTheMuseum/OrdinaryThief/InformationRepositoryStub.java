package OrdinaryThief;

import CommInfra.*;

/**
 * Stub to the ordinary thief's information repository.
 *
 * It instantiates a remote reference to the information repository.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class InformationRepositoryStub {
    /**
     * Name of the platform where is located the information repository server.
     */
    private String serverHostName;

    /**
     * Port number for listening to service requests.
     */
    private int serverPortNumb;

    /**
     * Instantiation of a stub to the information repository.
     *
     * @param serverHostName name of the platform where is located the information repository server
     * @param serverPortNumb port number for listening to service requests
     */
    public InformationRepositoryStub (String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Log file entities states writer stub function.
     * 
     * @param masterThiefState master thief state
     * @param ordinaryThiefId ordinary thief id
     * @param ordinaryThiefState ordinary thief state
     * @param canvasStatus ordinary thief canvas status
     * @param partyTargetRoomId ordinary thief party target room id
     * @param partyStatus ordinary thief party status
     * @param removeCanvas ordinary thief remove museum room canvas
     */
    public void writeStatesLog(int masterThiefState, int ordinaryThiefId, int ordinaryThiefState, int canvasStatus, int partyTargetRoomId, boolean partyStatus, int removeCanvas) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep((long)(1000));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.WRTSTATLOG, ordinaryThiefId, -1, ordinaryThiefState, -1, -1, null, null, -1, -1, partyTargetRoomId, -1, canvasStatus, partyStatus, removeCanvas, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.WRTSTATLOGDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }

    /**
     * Fill museum rooms stub function.
     * 
     * @param museumRooms museum rooms
     */
    public void fillMuseum(int[][] museumRooms) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep((long)(1000));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.FILLMUSEUM, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, museumRooms, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.FILLMUSEUMDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }

    /**
     * Operation server shutdown.
     *
     * New operation.
     */
    public void shutdown() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom(serverHostName, serverPortNumb);
        while (!com.open()) {
            try {
                Thread.sleep((long)(1000));
            }
            catch (InterruptedException e) {}
        }
        outMessage = new Message(MessageType.SHUT, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.SHUTDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }
}
