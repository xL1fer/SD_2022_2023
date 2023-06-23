package AssaultParty;

import CommInfra.*;

/**
 * Interface to the assault party.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * assault party and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class AssaultPartyInterface {
    /**
     * Assault party object.
     */
    private final AssaultParty assaultParty;

    /**
     * Assault party interface constructor.
     * 
     * @param assaultParty assault party object
     */
    public AssaultPartyInterface(AssaultParty assaultParty) {
        this.assaultParty = assaultParty;
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
            case MessageType.CRAWLIN:
                if (inMessage.getThiefId() == -1 || inMessage.getMaximumDisplacement() == -1 || inMessage.getPartyTargetRoomId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.CRAWLOUT:
                if (inMessage.getThiefId() == -1 || inMessage.getMaximumDisplacement() == -1)
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
            case MessageType.CRAWLIN:
                returnBoolean = assaultParty.crawlIn(inMessage.getThiefId(), inMessage.getMaximumDisplacement(), inMessage.getPartyTargetRoomId());
                outMessage = new Message(MessageType.CRAWLINDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, returnBoolean, -1, null);
                break;

            case MessageType.CRAWLOUT:
                returnBoolean = assaultParty.crawlOut(inMessage.getThiefId(), inMessage.getMaximumDisplacement());
                outMessage = new Message(MessageType.CRAWLOUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, returnBoolean, -1, null);
                break;

            case MessageType.SHUT:
                assaultParty.shutdown();
                outMessage = new Message(MessageType.SHUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;
        }

        return (outMessage);
    }
}
