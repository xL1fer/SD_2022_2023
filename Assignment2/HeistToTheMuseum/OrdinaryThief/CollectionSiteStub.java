package OrdinaryThief;

import CommInfra.*;

/**
 * Stub to the ordinary thief's collection site.
 *
 * It instantiates a remote reference to the collection site.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class CollectionSiteStub {
    /**
     * Name of the platform where is located the collection site server.
     */
    private String serverHostName;

    /**
     * Port number for listening to service requests.
     */
    private int serverPortNumb;

    /**
     * Instantiation of a stub to the collection site.
     *
     * @param serverHostName name of the platform where is located the collection site server
     * @param serverPortNumb port number for listening to service requests
     */
    public CollectionSiteStub (String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Ordinary thief hand a canvas stub function.
     * 
     * @param thiefId thief id
     * @param partyId party id
     * @param canvasStatus canvas status
     * @param partyTargetRoomId party target room id
     */
    public void handACanvas(int thiefId, int partyId, int canvasStatus, int partyTargetRoomId) {
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
        outMessage = new Message(MessageType.HANDACANVAS, thiefId, -1, -1, -1, -1, null, null, -1, partyId, partyTargetRoomId, -1, canvasStatus, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.HANDACANVASDONE) {
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
