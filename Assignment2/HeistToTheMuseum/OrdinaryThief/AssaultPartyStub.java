package OrdinaryThief;

import CommInfra.*;

/**
 * Stub to the ordinary thief's assault party.
 *
 * It instantiates a remote reference to the assault party.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class AssaultPartyStub {
    /**
     * Name of the platform where is located the assault party server.
     */
    private String serverHostName;

    /**
     * Port number for listening to service requests.
     */
    private int serverPortNumb;

    /**
     * Instantiation of a stub to the assault party.
     *
     * @param serverHostName name of the platform where is located the assault party server
     * @param serverPortNumb port number for listening to service requests
     */
    public AssaultPartyStub (String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    /**
     * Ordinary thief crawling in stub function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @param targetRoomId target room id
     * @return true if thief reached the targeted room
     */
    public boolean crawlIn(int thiefId, int thiefMaximumDisplacement, int targetRoomId) {
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
        outMessage = new Message(MessageType.CRAWLIN, thiefId, -1, -1, -1, -1, null, null, -1, -1, targetRoomId, thiefMaximumDisplacement, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.CRAWLINDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnBoolean();
    }

    /**
     * Ordinary thief crawling out stub function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @return true if thief reached the targeted room
     */
    public boolean crawlOut(int thiefId, int thiefMaximumDisplacement) {
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
        outMessage = new Message(MessageType.CRAWLOUT, thiefId, -1, -1, -1, -1, null, null, -1, -1, -1, thiefMaximumDisplacement, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.CRAWLOUTDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnBoolean();
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
