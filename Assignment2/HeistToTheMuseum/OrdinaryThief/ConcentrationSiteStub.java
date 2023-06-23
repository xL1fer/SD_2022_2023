package OrdinaryThief;

import CommInfra.*;

/**
 * Stub to the ordinary thief's concentration site.
 *
 * It instantiates a remote reference to the concentration site.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ConcentrationSiteStub {
    /**
     * Name of the platform where is located the concentration site server.
     */
    private String serverHostName;

    /**
     * Port number for listening to service requests.
     */
    private int serverPortNumb;

    /**
     * Instantiation of a stub to the concentration site.
     *
     * @param serverHostName name of the platform where is located the concentration site server
     * @param serverPortNumb port number for listening to service requests
     */
    public ConcentrationSiteStub (String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Ordinary thief am I needed stub function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @return assigned room id
     */
    public int amINeeded(int thiefId, int thiefPartyId) {
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
        outMessage = new Message(MessageType.AMINEED, thiefId, -1, -1, -1, -1, null, null, -1, thiefPartyId, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.AMINEEDDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnInteger();
    }

    /**
     * Ordinary thief prepare excursion stub function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId party thief id
     */
    public void prepareExcursion(int thiefId, int thiefPartyId) {
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
        outMessage = new Message(MessageType.PREPEXC, thiefId, -1, -1, -1, -1, null, null, -1, thiefPartyId, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.PREPEXCDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }

    /**
     * Ordinary thief exiting concentration site.
     */
    public void thiefLeaving() {
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
        outMessage = new Message(MessageType.THIEFLEAV, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.THIEFLEAVDONE) {
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
