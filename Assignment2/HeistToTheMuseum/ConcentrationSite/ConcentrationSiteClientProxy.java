package ConcentrationSite;

import CommInfra.*;

/**
 * Service provider agent for access to the concentration site.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ConcentrationSiteClientProxy extends Thread {
    /**
     * Number of instantiated threads.
     */
    private static int nProxy = 0;
    /**
     * Communication channel.
     */
    private ServerCom sconi = null;
    /**
     * Interface to the concentration site.
     */
    private ConcentrationSiteInterface concentrationSiteInterface = null;

    /**
     * Concentration site client proxy constructor.
     * 
     * @param sconi server communication object
     * @param concentrationSiteInterface concentration site interface
     */
    public ConcentrationSiteClientProxy(ServerCom sconi, ConcentrationSiteInterface concentrationSiteInterface) {
        this.sconi = sconi;
        this.concentrationSiteInterface = concentrationSiteInterface;
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
            outMessage = concentrationSiteInterface.processAndReply(inMessage);         // process it
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
        ConcentrationSiteClientProxy.nProxy = nProxy;
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
     * Get concentration site interface.
     * 
     * @return concentration site interface
     */
    public ConcentrationSiteInterface getConcentrationSiteInterface() {
        return concentrationSiteInterface;
    }

    /**
     * Set concentration site interface.
     * 
     * @param concentrationSiteInterface concentration site interface
     */
    public void setConcentrationSiteInterface(ConcentrationSiteInterface concentrationSiteInterface) {
        this.concentrationSiteInterface = concentrationSiteInterface;
    }
}
