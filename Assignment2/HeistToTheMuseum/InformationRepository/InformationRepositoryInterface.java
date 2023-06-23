package InformationRepository;

import CommInfra.*;

/**
 * Interface to the information repository.
 *
 * It is responsible to validate and process the incoming message, execute the corresponding method on the
 * information repository and generate the outgoing message.
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class InformationRepositoryInterface {
    /**
     * Information repository object.
     */
    private final InformationRepository informationRepository;

    /**
     * Information repository interface constructor.
     * 
     * @param informationRepository information repository object
     */
    public InformationRepositoryInterface(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
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
            case MessageType.WRTHEADLOG:
                break;

            case MessageType.WRTEARNLOG:
                if (inMessage.getPaintingEarnings() == -1)
                    throw new MessageException ("Invalid message attributes!", inMessage);
                break;

            case MessageType.WRTSTATLOG:
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

        switch (inMessage.getMsgType()) {
            case MessageType.WRTHEADLOG:
                informationRepository.writeHeaderLog();
                outMessage = new Message(MessageType.WRTHEADLOGDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.WRTEARNLOG:
                informationRepository.writeEarningsLog(inMessage.getPaintingEarnings());
                outMessage = new Message(MessageType.WRTEARNLOGDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.WRTSTATLOG:
                informationRepository.writeStatesLog(inMessage.getMasterThiefState(), inMessage.getThiefId(), inMessage.getOrdinaryThiefState(), inMessage.getCanvasStatus(), inMessage.getPartyTargetRoomId(), inMessage.getPartyStatus(), inMessage.getRemoveCanvas());
                outMessage = new Message(MessageType.WRTSTATLOGDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.FILLMUSEUM:
                informationRepository.fillMuseum(inMessage.getMuseumRooms());
                outMessage = new Message(MessageType.FILLMUSEUMDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;

            case MessageType.SHUT:
                informationRepository.shutdown();
                outMessage = new Message(MessageType.SHUTDONE, -1, -1, -1, -1, -1, null, null, -1, -1, -1, -1, -1, false, -1, null, false, -1, null);
                break;
        }

        return (outMessage);
    }
}
