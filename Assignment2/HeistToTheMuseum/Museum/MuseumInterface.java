package Museum;

import CommInfra.*;

/**
 * Interface to the museum.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * museum and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class MuseumInterface {
    /**
     * Museum object.
     */
    private final Museum museum;

    /**
     * Museum interface constructor.
     * 
     * @param museum museum object
     */
    public MuseumInterface(Museum museum) {
        this.museum = museum;
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
            case MessageType.ROLLACANVAS:
                if (inMessage.getThiefId() == -1 || inMessage.getPartyId() == -1 || inMessage.getPartyTargetRoomId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.REVERSEDIR:
                if (inMessage.getThiefId() == -1 || inMessage.getPartyId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.FILLMUSEUM:
                if (inMessage.getMuseumRooms() == null)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.SHUT:
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        // processing

        boolean returnBoolean = false;

        switch (inMessage.getMsgType()) {
            case MessageType.ROLLACANVAS:
                returnBoolean = museum.rollACanvas(inMessage.getThiefId(), inMessage.getPartyId(), inMessage.getPartyTargetRoomId());
                outMessage = new Message(MessageType.ROLLACANVASDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, returnBoolean, -1, null);
                break;

            case MessageType.REVERSEDIR:
                museum.reverseDirection(inMessage.getThiefId(), inMessage.getPartyId());
                outMessage = new Message(MessageType.REVERSEDIRDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.FILLMUSEUM:
                museum.fillMuseum(inMessage.getMuseumRooms());
                outMessage = new Message(MessageType.FILLMUSEUMDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.SHUT:
                museum.shutdown();
                outMessage = new Message(MessageType.SHUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;
        }

        return (outMessage);
    }
}
