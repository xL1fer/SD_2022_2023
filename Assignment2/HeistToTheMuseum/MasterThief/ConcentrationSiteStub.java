package MasterThief;

import java.util.ArrayList;

import CommInfra.*;

/**
 * Stub to the master thief's concentration site.
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
     * Master thief appraise site stub function.
     * 
     * @param totalAvailableRooms total available rooms
     * @param totalAssaultParties total assault parties
     * @return true if there are still rooms to check
     */
    public boolean appraiseSit(int totalAvailableRooms, int totalAssaultParties) {
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
        outMessage = new Message(MessageType.APSIT, -1, -1, -1, totalAvailableRooms, totalAssaultParties, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.APSITDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnBoolean();
    }

    /**
     * Master thief prepare assault stub function.
     * 
     * @param availableRooms available rooms reference
     * @param targetedRooms targeted rooms reference
     * @return assigned room id
     */
    public int prepareAssaultParty(ArrayList<Integer> availableRooms, ArrayList<Integer> targetedRooms) {
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
        outMessage = new Message(MessageType.PREPASSPARTY, -1, -1, -1, -1, -1, availableRooms, targetedRooms, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.PREPASSPARTYDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();

        return inMessage.getReturnInteger();
    }

    /**
     * Master thief send assault stub function.
     */
    public void sendAssaultParty() {
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
        outMessage = new Message(MessageType.SNDASSPARTY, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.SNDASSPARTYDONE) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Invalid message type!");
            System.out.println(inMessage.toString());
            System.exit(1);
        }
        com.close();
    }

    /**
     * Master thief sum up results stub function.
     * 
     * @param totalAssaultParties total assault parties
     */
    public void sumUpResults(int totalAssaultParties) {
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
        outMessage = new Message(MessageType.SUMUPRES, -1, -1, -1, -1, totalAssaultParties, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
        com.writeObject(outMessage);
        inMessage = (Message)com.readObject();
        if (inMessage.getMsgType() != MessageType.SUMUPRESDONE) {
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
