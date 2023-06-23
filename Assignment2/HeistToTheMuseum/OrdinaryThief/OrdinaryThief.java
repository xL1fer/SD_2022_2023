package OrdinaryThief;

/**
 * Ordinary thief class.
 */
public class OrdinaryThief extends Thief {
    /**
     * Information repository stub.
     */
    private InformationRepositoryStub infReposStub;
    /**
     * Concentration site stub.
     */
    private ConcentrationSiteStub conSiteStub;
    /**
     * Collection site stub.
     */
    private CollectionSiteStub colSiteStub;
    /**
     * Assault party stub.
     */
    private AssaultPartyStub assaultPartyStub;
    /**
     * Museum stub.
     */
    private MuseumStub museumStub;

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
     * Museum rooms.
     */
    private int[][] museumRooms;

    /**
     * Ordinary thief constructor.
     * 
     * @param currentState thief's current state
     * @param infReposStub information repository stub
     * @param conSiteStub concentration site stub
     * @param colSiteStub collection site stub
     * @param assaultPartyStub assault party stub
     * @param museumStub museum stub
     * @param thiefId thief's identification
     * @param maximumDisplacement thief's maximum displacement
     * @param partyId party id
     * @param museumRooms museum rooms
     */
    public OrdinaryThief(int currentState, InformationRepositoryStub infReposStub, ConcentrationSiteStub conSiteStub,
            CollectionSiteStub colSiteStub, AssaultPartyStub assaultPartyStub, MuseumStub museumStub, int thiefId, int maximumDisplacement, int partyId, int[][] museumRooms) {
        super(currentState);

        this.infReposStub = infReposStub;
        this.conSiteStub = conSiteStub;
        this.colSiteStub = colSiteStub;
        this.assaultPartyStub = assaultPartyStub;
        this.museumStub = museumStub;

        this.thiefId = thiefId;
        this.hasCanvas = false;
        this.maximumDisplacement = maximumDisplacement;
        this.partyId = partyId;
        this.partyTargetRoomId = 0;
        this.museumRooms = museumRooms;

        System.out.printf("< Ordinary Thief %d initialized (p = %d ; md = %d)\n", thiefId, partyId, maximumDisplacement);
    }

    @Override
    public void run() {
        // ordinary thief lifecycle logic
        while (true) {
            this.partyTargetRoomId = this.conSiteStub.amINeeded(this.thiefId, this.partyId);

            this.conSiteStub.prepareExcursion(this.thiefId, this.partyId);
            // break in case master assigned room with id -2
            if (this.partyTargetRoomId == -2)
                break;

            // thief crawling in
            changeState(OrdinaryThiefStates.CRAWLING_INWARDS, true, 0);
            while (!this.assaultPartyStub.crawlIn(this.thiefId, this.maximumDisplacement, museumRooms[0][this.partyTargetRoomId - 1] - 1 /*this.partyTargetRoomId*/))
                this.infReposStub.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, true, 0);
            changeState(OrdinaryThiefStates.AT_A_ROOM, true, 0);

            // thief inside museum room
            this.hasCanvas = this.museumStub.rollACanvas(thiefId, this.partyId, this.partyTargetRoomId);
            changeState(OrdinaryThiefStates.CONCENTRATION_SITE, false, this.hasCanvas ? 1 : 0);
            this.museumStub.reverseDirection(thiefId, this.partyId);

            // thief crawling out
            changeState(OrdinaryThiefStates.CRAWLING_OUTWARDS, true, 0);
            while (!this.assaultPartyStub.crawlOut(this.thiefId, this.maximumDisplacement))
                this.infReposStub.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, true, 0);
            changeState(OrdinaryThiefStates.COLLECTION_SITE, false, 0);

            // thief handing a canvas
            this.colSiteStub.handACanvas(this.thiefId, this.partyId, this.hasCanvas ? 1 : 0, this.partyTargetRoomId);
            this.hasCanvas = false;

            changeState(OrdinaryThiefStates.CONCENTRATION_SITE, false, 0);
        }

        this.conSiteStub.thiefLeaving();
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
        this.infReposStub.writeStatesLog(-1, this.thiefId, this.currentState, this.hasCanvas ? 1 : 0, this.partyTargetRoomId, isPartyActive, removeCanvas);
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
     * Get information repository stub.
     * 
     * @return information repository stub
     */
    public InformationRepositoryStub getInfReposStub() {
        return infReposStub;
    }

    /**
     * Set information repository stub.
     * 
     * @param infReposStub information repository stub
     */
    public void setInfReposStub(InformationRepositoryStub infReposStub) {
        this.infReposStub = infReposStub;
    }

    /**
     * Get concentration site stub.
     * 
     * @return concentration site stub
     */
    public ConcentrationSiteStub getConSiteStub() {
        return conSiteStub;
    }

    /**
     * Set concentration site stub.
     * 
     * @param conSiteStub concentration site stub
     */
    public void setConcentrationSite(ConcentrationSiteStub conSiteStub) {
        this.conSiteStub = conSiteStub;
    }

    /**
     * Get collection site stub.
     * 
     * @return collection site stub
     */
    public CollectionSiteStub getColSiteStub() {
        return colSiteStub;
    }

    /**
     * Set collection site stub
     * 
     * @param colSiteStub collection site stub
     */
    public void setCollectionSiteStub(CollectionSiteStub colSiteStub) {
        this.colSiteStub = colSiteStub;
    }

    /**
     * Get assault party stub.
     * 
     * @return assault party stub
     */
    public AssaultPartyStub getAssaultPartyStub() {
        return assaultPartyStub;
    }

    /**
     * Set assault party stub
     * 
     * @param assaultPartyStub assault party stub
     */
    public void setAssaultPartyStub(AssaultPartyStub assaultPartyStub) {
        this.assaultPartyStub = assaultPartyStub;
    }

    /**
     * Get museum stub.
     * 
     * @return museum stub
     */
    public MuseumStub getMuseumStub() {
        return museumStub;
    }

    /**
     * Set museum stub
     * 
     * @param museumStub museum stub
     */
    public void setMuseumStub(MuseumStub museumStub) {
        this.museumStub = museumStub;
    }
}