package CollectionSite;

import CommInfra.*;

/**
 * Interface to the collection site.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * collection site and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class CollectionSiteInterface {
    /**
     * Collection site object.
     */
    private final CollectionSite collectionSite;

    /**
     * Collection site interface constructor.
     * 
     * @param collectionSite collection site object
     */
    public CollectionSiteInterface(CollectionSite collectionSite) {
        this.collectionSite = collectionSite;
    }

    /**
     * Processing of the incoming messages.
     *
     * Validation, execution of the corresponding method and generation of the outgoing message.
     *
     * @param inMessage service request
     * @return service reply
     * @throws MessageException if the incoming message is not valid
     */
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;                                     // outgoing message

        // validation of the incoming message

        switch (inMessage.getMsgType()) {
            case MessageType.TAKEAREST:
                break;

            case MessageType.COLLACANVAS:
                break;

            case MessageType.HANDACANVAS:
                if (inMessage.getThiefId() == -1 || inMessage.getPartyId() == -1 || inMessage.getCanvasStatus() == -1 || inMessage.getPartyTargetRoomId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.SHUT:
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        // processing

        int[] returnIntegerArray = null;

        switch (inMessage.getMsgType()) {
            case MessageType.TAKEAREST:
                collectionSite.takeARest();
                outMessage = new Message(MessageType.TAKEARESTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.COLLACANVAS:
                returnIntegerArray = collectionSite.collectACanvas();
                outMessage = new Message(MessageType.COLLACANVASDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, returnIntegerArray);
                break;

            case MessageType.HANDACANVAS:
                collectionSite.handACanvas(inMessage.getThiefId(), inMessage.getPartyId(), inMessage.getCanvasStatus() == 1 ? true : false, inMessage.getPartyTargetRoomId());
                outMessage = new Message(MessageType.HANDACANVASDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.SHUT:
                collectionSite.shutdown();
                outMessage = new Message(MessageType.SHUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;
        }

        return (outMessage);
    }
}
