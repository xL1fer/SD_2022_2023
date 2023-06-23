package Main;

import java.util.ArrayList;

import SharedRegions.*;
import Thieves.*;

/**
 * Museum heist main class.
 */
public class MuseumHeist {
    // NOTE(L1fer): is it also needed to have minRoomPaintings, minRoomDistance, minimumDisplacement vars?
    /**
     * Simulation options enum.
     */
    public static enum Options {
		/**
		 * Thieves number config.
		 */
        thievesNumber,
        /**
         * Maximum displacement config.
         */
        maximumDisplacement,
        /**
         * Painting rooms config.
         */
        paintingRooms,
        /**
         * Maximum room distance config.
         */
        maxRoomDistance,
        /**
         * Maximum room paintings config.
         */
        maxRoomPaintings,
        /**
         * Maximum members number config.
         */
        partyMembersNumber,
        /**
         * Party separation limit config.
         */
        partySeparationLimit,
    }

    /**
     * Simulation debug flag.
     */
    public static boolean DEBUG = true;
    /**
     * Simulation simulation configs.
     */
    public static int[] configs;

    /**
     * Museum heist main entry point.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // start by getting simulation input
        SimulationInput.getSimulationInput();
        configs = SimulationInput.inputArray;

        for (int i = 0; i < 7; i++)
            System.out.printf(":: %s - %d\n", Options.values()[i], configs[i]);

        // initialize all monitors and threads instances
        initializeInstances();
    }

    /**
     * Initialize all museum heist instances.
     */
    public static void initializeInstances() {
        // get config variables
        int thievesNumber = configs[Options.thievesNumber.ordinal()];
        int partyMembersNumber = configs[Options.partyMembersNumber.ordinal()];
        int partiesNumber = thievesNumber / partyMembersNumber;
        int paintingRooms = configs[Options.paintingRooms.ordinal()];
        int maxRoomPaintings = configs[Options.maxRoomPaintings.ordinal()];
        int maxRoomDistance = configs[Options.maxRoomDistance.ordinal()];
        int partySeparationLimit = configs[Options.partySeparationLimit.ordinal()];
        int maximumDisplacement = configs[Options.maximumDisplacement.ordinal()];

        // initialize information logging repository monitor
        InformationRepository informationRepository = new InformationRepository(partiesNumber, partyMembersNumber);

        // initialize museum monitor
        Museum museum = new Museum(paintingRooms, partyMembersNumber, partiesNumber);

        // initialize museum rooms
        int[][] museumRooms = new int[2][paintingRooms];
        for (int i = 0; i < paintingRooms; i++) {
            int paintings = (int) (Math.random() * (maxRoomPaintings - 8 + 1)) + 8;     // minimum number of paintings hardcoded to 8
            int distance = (int) (Math.random() * (maxRoomDistance - 15 + 1)) + 15;     // minimum room distance hardcoded to 8

            museumRooms[0][i] = distance;
            museumRooms[1][i] = paintings;

            Room room = new Room(i + 1, paintings, distance);
            museum.addRoom(room);
        }

        // initialize assault parties monitors
        ArrayList<AssaultParty> assaultParties = new ArrayList<AssaultParty>();
        for (int i = 0; i < partiesNumber; i++) {
            AssaultParty p = new AssaultParty(maxRoomDistance, partyMembersNumber, partySeparationLimit, i + 1, museumRooms[0]);
            assaultParties.add(p);
        }

        // initialize collection site monitor
        CollectionSite collectionSite = new CollectionSite(partyMembersNumber, thievesNumber);
        
        // initialize concentration site monitor
        ConcentrationSite concentrationSite = new ConcentrationSite(partiesNumber, partyMembersNumber);        

        // initialize master thief thread
        MasterThief masterThief = new MasterThief(MasterThiefStates.PLANNING_THE_HEIST, informationRepository, concentrationSite, collectionSite, paintingRooms, partiesNumber);

        // initialize ordinary thieves threads
        ArrayList<OrdinaryThief> ordinaryThieves = new ArrayList<OrdinaryThief>();
        int[] maximumDisplacements = new int[thievesNumber - 1];
        for (int i = 0; i < thievesNumber - 1; i++) {
            int currentPartyInd = i / partyMembersNumber;
            AssaultParty currentParty = assaultParties.get(currentPartyInd);
            
            int curmaximumDisplacement = (int) (Math.random() * (maximumDisplacement - 2 + 1)) + 2;   // minimum displacement hardcoded to 2 for now
            maximumDisplacements[i] = curmaximumDisplacement;

            OrdinaryThief ordinaryThief = new OrdinaryThief(OrdinaryThiefStates.CONCENTRATION_SITE, informationRepository, museum, currentParty, concentrationSite, collectionSite, i + 1, maximumDisplacement, currentPartyInd + 1);
            
            ordinaryThieves.add(ordinaryThief);
            currentParty.addPartyMember(ordinaryThief.getThiefId());        // fill each party with thieves
        }

        // finish up information repository initializations
        informationRepository.setMuseumRooms(museumRooms);
        informationRepository.fillMaximumDisplacement(maximumDisplacements);

        // write header to log file
        informationRepository.writeHeaderLog();

        if (MuseumHeist.DEBUG)
            System.out.println("Museum Heist Started\n===================");

        // start master thief thread
        masterThief.start();
        // start ordinary thief threads
        for (OrdinaryThief ordinaryThief : ordinaryThieves)
            ordinaryThief.start();
        
        // wait for all thief threads to join
        try {
            masterThief.join();
            if (MuseumHeist.DEBUG) System.out.println("Master Thief joined");

            for (OrdinaryThief ordinaryThief : ordinaryThieves) {
                ordinaryThief.join();
                if (MuseumHeist.DEBUG) System.out.println("Ordinary Thief joined");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}