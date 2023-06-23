package ConcentrationSite;

import CommInfra.*;

/**
 * Interface to the concentration site.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * concentration site and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class ConcentrationSiteInterface {
    /**
     * Concentration site object.
     */
    private final ConcentrationSite concentrationSite;

    /**
     * Concentration site interface constructor.
     * 
     * @param concentrationSite concentration site object
     */
    public ConcentrationSiteInterface(ConcentrationSite concentrationSite) {
        this.concentrationSite = concentrationSite;
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
            case MessageType.APSIT:
                if (inMessage.getTotalAvailableRooms() == -1 ||  inMessage.getTotalAssaultParties() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.PREPASSPARTY:
                if (inMessage.getAvailableRooms() == null || inMessage.getTargetedRooms() == null)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.SNDASSPARTY:
                break;

            case MessageType.SUMUPRES:
                if (inMessage.getTotalAssaultParties() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.AMINEED:
                if (inMessage.getThiefId() == -1 || inMessage.getPartyId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.PREPEXC:
                if (inMessage.getThiefId() == -1 || inMessage.getPartyId() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.THIEFLEAV:
                break;

            case MessageType.SHUT:
                break;

            default:
                throw new MessageException("Invalid message type!", inMessage);
        }

        // processing

        boolean returnBoolean = false;
        int returnInteger = -1;

        switch (inMessage.getMsgType()) {
            case MessageType.APSIT:
                //((ConcentrationSiteClientProxy)Thread.currentThread()).setThiefState(inMessage.getThiefState());
                returnBoolean = concentrationSite.appraiseSit(inMessage.getTotalAvailableRooms(), inMessage.getTotalAssaultParties());
                outMessage = new Message(MessageType.APSITDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, returnBoolean, -1, null);
                break;

            case MessageType.PREPASSPARTY:
                returnInteger = concentrationSite.prepareAssaultParty(inMessage.getAvailableRooms(), inMessage.getTargetedRooms());
                outMessage = new Message(MessageType.PREPASSPARTYDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, returnInteger, null);
                break;

            case MessageType.SNDASSPARTY:
                concentrationSite.sendAssaultParty();
                outMessage = new Message(MessageType.SNDASSPARTYDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.SUMUPRES:
                concentrationSite.sumUpResults(inMessage.getTotalAssaultParties());
                outMessage = new Message(MessageType.SUMUPRESDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.AMINEED:
                returnInteger = concentrationSite.amINeeded(inMessage.getThiefId(), inMessage.getPartyId());
                outMessage = new Message(MessageType.AMINEEDDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, returnInteger, null);
                break;

            case MessageType.PREPEXC:
                concentrationSite.prepareExcursion(inMessage.getThiefId(), inMessage.getPartyId());
                outMessage = new Message(MessageType.PREPEXCDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.THIEFLEAV:
                concentrationSite.thiefLeaving();
                outMessage = new Message(MessageType.THIEFLEAVDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.SHUT:
                concentrationSite.shutdown();
                outMessage = new Message(MessageType.SHUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;
        }

        return (outMessage);
    }

}
