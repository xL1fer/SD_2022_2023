package CollectionSite;

import CommInfra.*;

/**
 * Service provider agent for access to the collection site.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class CollectionSiteClientProxy extends Thread {
    /**
     * Number of instantiated threads.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private ServerCom sconi = null;
    /**
     * Interface to the collection site.
     */
    private CollectionSiteInterface collectionSiteInterface = null;

    /**
     * Collection site client proxy constructor.
     * 
     * @param sconi server communication object
     * @param collectionSiteInterface collection site interface
     */
    public CollectionSiteClientProxy(ServerCom sconi, CollectionSiteInterface collectionSiteInterface) {
        this.sconi = sconi;
        this.collectionSiteInterface = collectionSiteInterface;
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
            outMessage = collectionSiteInterface.processAndReply(inMessage);            // process it
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
        CollectionSiteClientProxy.nProxy = nProxy;
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
     * Get collection site interface.
     * 
     * @return collection site interface
     */
    public CollectionSiteInterface getCollectionSiteInterface() {
        return collectionSiteInterface;
    }

    /**
     * Set collection site interface.
     * 
     * @param collectionSiteInterface collection site interface
     */
    public void setCollectionSiteInterface(CollectionSiteInterface collectionSiteInterface) {
        this.collectionSiteInterface = collectionSiteInterface;
    }
}
