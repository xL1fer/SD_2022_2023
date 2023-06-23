package Thieves;

import SharedRegions.*;

/**
 * Ordinary thief class.
 */
public class OrdinaryThief extends Thief {
    // monitor attributes
    /**
     * Information repository monitor.
     */
    private InformationRepository informationRepository;
    /**
     * Museum monitor.
     */
    private Museum museum;
    /**
     * Assault party monitor.
     */
    private AssaultParty assaultParty;
    /**
     * Concentration site monitor.
     */
    private ConcentrationSite concentrationSite;
    /**
     * Collection site monitor.
     */
    private CollectionSite collectionSite;

    // own attributes
    /**
     *  Thief identifier.
     */
    private int thiefId;
    /**
     * Thief canvas status.
     */
    private boolean hasCanvas;
    /**
     * Thief maximum displacement.
     */
    private int maximumDisplacement;
    /**
     * Thief party id.
     */
    private int partyId;
    /**
     * Thief party target room id.
     */
    private int partyTargetRoomId;

    /**
     * Ordinary thief constructor.
     * 
     * @param currentState thief's current state
     * @param informationRepository information repository monitor
     * @param museum museum monitor
     * @param assaultParty assault party monitor
     * @param concentrationSite concentration site monitor
     * @param collectionSite collection site monitor
     * @param thiefId thief's identification
     * @param maximumDisplacement thief's maximum displacement
     * @param partyId party id
     */
    public OrdinaryThief(int currentState, InformationRepository informationRepository, Museum museum, AssaultParty assaultParty, ConcentrationSite concentrationSite, CollectionSite collectionSite, int thiefId, int maximumDisplacement, int partyId) {
        super(currentState);

        this.informationRepository = informationRepository;
        this.museum = museum;
        this.assaultParty = assaultParty;
        this.concentrationSite = concentrationSite;
        this.collectionSite = collectionSite;

        this.thiefId = thiefId;
        this.hasCanvas = false;
        this.maximumDisplacement = maximumDisplacement;
        this.partyId = partyId;
        this.partyTargetRoomId = 0;

        System.out.printf("< Ordinary Thief %d initialized (md = %d)\n", thiefId, maximumDisplacement);
    }

    @Override
    public void run() {
        // ordinary thief lifecycle logic
        while (true) {
            this.partyTargetRoomId = this.concentrationSite.amINeeded(this.thiefId, this.partyId);

            this.concentrationSite.prepareExcursion(this.thiefId, this.partyId);
            // break in case master assigned room with id -2
            if (this.partyTargetRoomId == -2)
                break;

            // thief crawling in
            changeState(OrdinaryThiefStates.CRAWLING_INWARDS, true, 0);
            while (!this.assaultParty.crawlIn(this.thiefId, this.maximumDisplacement, this.partyTargetRoomId))
                this.informationRepository.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, true, 0);
            changeState(OrdinaryThiefStates.AT_A_ROOM, true, 0);

            // thief inside museum room
            this.hasCanvas = this.museum.rollACanvas(thiefId, this.partyId, this.partyTargetRoomId);
            changeState(OrdinaryThiefStates.CONCENTRATION_SITE, false, this.hasCanvas ? 1 : 0);
            this.museum.reverseDirection(thiefId, this.partyId);

            // thief crawling out
            changeState(OrdinaryThiefStates.CRAWLING_OUTWARDS, true, 0);
            while (!this.assaultParty.crawlOut(this.thiefId, this.maximumDisplacement))
                this.informationRepository.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, true, 0);
            changeState(OrdinaryThiefStates.COLLECTION_SITE, false, 0);

            // thief handing a canvas
            this.collectionSite.handACanvas(this.thiefId, this.partyId, this.hasCanvas, this.partyTargetRoomId);
            this.hasCanvas = false;

            changeState(OrdinaryThiefStates.CONCENTRATION_SITE, false, 0);
        }

        this.concentrationSite.thiefLeaving();
        //System.out.println("::::::::::::::: THIEF " + this.thiefId + " EXITING :::::::::::::::");
    }

    /**
     * Change thief state and write in log
     * 
     * @param state new state
     * @param isPartyActive party active status
     * @param removeCanvas remove canvas from museum
     */
    public void changeState(int state, boolean isPartyActive, int removeCanvas) {
        setCurrentState(state);
        this.informationRepository.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, isPartyActive, removeCanvas);
    }

    /**
     * Get thief's identification.
     * 
     * @return thief's identification
     */
    public int getThiefId() {
        return thiefId;
    }

    /**
     * Set thief's identification.
     * 
     * @param thiefId thief's identification
     */
    public void setThiefId(int thiefId) {
        this.thiefId = thiefId;
    }

    /**
     * Get thief's canvas status.
     * 
     * @return thief's canvas status.
     */
    public boolean hasCanvas() {
        return hasCanvas;
    }

    /**
     * Set thief's canvas status.
     * 
     * @param hasCanvas thief's canvas status.
     */
    public void setHasCanvas(boolean hasCanvas) {
        this.hasCanvas = hasCanvas;
    }

    /**
     * Get thief's maximum displacement.
     * 
     * @return thief's maximum displacement
     */
    public int getMaximumDisplacement() {
        return maximumDisplacement;
    }

    /**
     * Set thief's maximum displacement.
     * 
     * @param maximumDisplacement thief's maximum displacement
     */
    public void setMaximumDisplacement(int maximumDisplacement) {
        this.maximumDisplacement = maximumDisplacement;
    }

    /**
     * Get thief's information repository monitor.
     * 
     * @return information repository monitor
     */
    public InformationRepository getInformationRepository() {
        return informationRepository;
    }

    /**
     * Set thief's information repository monitor.
     * 
     * @param informationRepository information repository monitor
     */
    public void setInformationRepository(InformationRepository informationRepository) {
        this.informationRepository = informationRepository;
    }

    /**
     * Get thief's museum monitor.
     * 
     * @return thief's museum monitor
     */
    public Museum getMuseum() {
        return museum;
    }

    /**
     * Set thief's museum monitor.
     * 
     * @param museum thief's museum monitor
     */
    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    /**
     * Get thief's assault party monitor.
     * 
     * @return thief's assault party monitor
     */
    public AssaultParty getAssaultParty() {
        return assaultParty;
    }

    /**
     * Set thief's assault party monitor.
     * 
     * @param assaultParty thief's assault party monitor
     */
    public void setAssaultParty(AssaultParty assaultParty) {
        this.assaultParty = assaultParty;
    }

    /**
     * Set thief's concentration site monitor.
     * 
     * @return thief's concentration site monitor
     */
    public ConcentrationSite getConcentrationSite() {
        return concentrationSite;
    }

    /**
     * Get thief's concentration site monitor.
     * 
     * @param concentrationSite thief's concentration site monitor
     */
    public void setConcentrationSite(ConcentrationSite concentrationSite) {
        this.concentrationSite = concentrationSite;
    }

    /**
     * Get thief's collection site monitor.
     * 
     * @return thief's collection site monitor
     */
    public CollectionSite getCollectionSite() {
        return collectionSite;
    }

    /**
     * Set thief's collection site monitor.
     * 
     * @param collectionSite thief's collection site monitor
     */
    public void setCollectionSite(CollectionSite collectionSite) {
        this.collectionSite = collectionSite;
    }
}