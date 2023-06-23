package SharedRegions;

import Main.MuseumHeist;
import java.util.ArrayList;

/**
 * Museum class.
 */
public class Museum {
    /**
     * Museum rooms list.
     */
    private ArrayList<Room> rooms;
    /**
     * Museum rooms number.
     */
    private int roomsNumber;
    /**
     * Party members number.
     */
    private int partyMembersNumber;
    /**
     * Number of party thieves ready to reverse direction.
     */
    private int partyThievesReverse[];
    /**
     * Party thieves leaving room flag.
     */
    private boolean partyThievesLeaving[];

    /**
     * Museum constructor.
     * 
     * @param roomsNumber number of museum rooms
     * @param partyMembersNumber party members number
     * @param partiesNumber parties number
     */
    public Museum(int roomsNumber, int partyMembersNumber, int partiesNumber) {
        this.rooms = new ArrayList<Room>();
        this.roomsNumber = roomsNumber;
        this.partyMembersNumber = partyMembersNumber;
        this.partyThievesReverse = new int[partiesNumber];
        this.partyThievesLeaving = new boolean[partiesNumber];
        System.out.println("< Museum initialized");
    }

    /**
     * Ordinary thief roll a museum canvas function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     * @param targetRoomId target room id
     * @return true if a canvas was rolled
     */
    public synchronized boolean rollACanvas(int thiefId, int thiefPartyId, int targetRoomId) {
        boolean hasCanvas = false;

        for (Room room : this.rooms) {
            if (room.getRoomId() == targetRoomId) {
                if (MuseumHeist.DEBUG) System.out.printf("> Thief < %d > (p = %d) is ::ROLLING A CANVAS:: (total paintings = %d)\n", thiefId, thiefPartyId, room.getPaitingsNumber());
            
                if (room.getPaitingsNumber() > 0) {
                    room.decrementPaitingsNumber();
                    hasCanvas = true;
                }

                break;
            }
        }

        return hasCanvas;
    }

    /**
     * Ordinary thief get ready to reverse direction function.
     * 
     * @param thiefId thief id
     * @param thiefPartyId thief party id
     */
    public synchronized void reverseDirection(int thiefId, int thiefPartyId) {
        if (MuseumHeist.DEBUG) System.out.printf("> Thief < %d > (p = %d) is reversing direction\n", thiefId, thiefPartyId);

        this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber]++;

        if (this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber] == this.partyMembersNumber)
            this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber] = true;

        notifyAll();
        while (!this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber]) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber]--;

        // last thief to arrive at the room resets party pointer before starting crawling out
        if (this.partyThievesReverse[(thiefId - 1) / this.partyMembersNumber] == 0)
            this.partyThievesLeaving[(thiefId - 1) / this.partyMembersNumber] = false;
    }

    /**
     * Get distance from outside to a certain room giving its id.
     * 
     * @param roomId room id
     * @return room distance
     */
    public int getMuseumRoomDistance(int roomId) {
        for (int i = 0; i < this.roomsNumber; i++) {
            if (rooms.get(i).getRoomId() == roomId) {
                return rooms.get(i).getRoomDistance();
            }
        }
        return -1;
    }

    /**
     * Remove room from museum rooms.
     * 
     * @param room room object
     */
    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    /**
     * Add room to museum rooms.
     * 
     * @param room room object
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Get number of museum rooms.
     * 
     * @return rooms number
     */
    public int getRoomsNumber() {
        return roomsNumber;
    }

    /**
     * Set number of rooms of the museum
     * 
     * @param roomsNumber number of rooms
     */
    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    /**
     * Get museum rooms list.
     * 
     * @return rooms list
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Set museum rooms list.
     * 
     * @param rooms museum rooms list
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}