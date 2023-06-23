package MasterThief;

import CommInfra.*;

/**
 * Stub to the master thief's collection site.
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
     * Master thief waiting for a group arrival stub function.
     */
    public void takeARest() {
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
        outMessage = new Message(MessageType.TAKEAREST, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.TAKEARESTDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }

    /**
     * Master thief collect a canvas stub function.
     * 
     * @return canvas collection results
     */
    public int[] collectACanvas() {
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
        outMessage = new Message(MessageType.COLLACANVAS, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.COLLACANVASDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnIntegerArray();
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
