package AssaultParty;

import CommInfra.*;

/**
 * Service provider agent for access to the assault party.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class AssaultPartyClientProxy extends Thread {
    /**
     * Number of instantiated threads.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private ServerCom sconi = null;
    /**
     * Interface to the assault party.
     */
    private AssaultPartyInterface assaultPartyInterface = null;

    /**
     * Assault party client proxy constructor.
     * 
     * @param sconi server communication object
     * @param assaultPartyInterface assault party interface
     */
    public AssaultPartyClientProxy(ServerCom sconi, AssaultPartyInterface assaultPartyInterface) {
        this.sconi = sconi;
        this.assaultPartyInterface = assaultPartyInterface;
    }

    /**
     * Life cycle of the service provider agent.
     */
    @Override
    public void run() {
        Message inMessage = null,                                                       // service request
                outMessage = null;                                                      // service reply

        // service providing

        inMessage = (Message)sconi.readObject();                                        // get service request
        try {
            outMessage = assaultPartyInterface.processAndReply(inMessage);              // process it
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit (1);
        }
        sconi.writeObject(outMessage);                                                  // send service reply
        sconi.close();                                                                  // close the communication channel
    }

    /**
     * Get proxy number.
     * 
     * @return proxy number
     */
    public static int getnProxy() {
        return nProxy;
    }

    /**
     * Set proxy number.
     * 
     * @param nProxy proxy number
     */
    public static void setnProxy(int nProxy) {
        AssaultPartyClientProxy.nProxy = nProxy;
    }

    /**
     * Get server communication.
     * 
     * @return server communication object
     */
    public ServerCom getSconi() {
        return sconi;
    }

    /**
     * Set server communication.
     * 
     * @param sconi server communication object
     */
    public void setSconi(ServerCom sconi) {
        this.sconi = sconi;
    }

    /**
     * Get assault party interface.
     * 
     * @return assault party interface
     */
    public AssaultPartyInterface getAssaultPartyInterface() {
        return assaultPartyInterface;
    }

    /**
     * Set assault party interface.
     * 
     * @param assaultPartyInterface assault party interface
     */
    public void setAssaultPartyInterface(AssaultPartyInterface assaultPartyInterface) {
        this.assaultPartyInterface = assaultPartyInterface;
    }
}
