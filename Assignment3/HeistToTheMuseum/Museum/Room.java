package Museum;

/**
 * Room class.
 */
public class Room {
    /**
     * Room id.
     */
    private int roomId;
    /**
     * Room paintings number.
     */
    private int paintingsNumber;

    /**
     * Room constructor.
     * 
     * @param paintingsNumber number of room's paintings
     * @param roomId room id
     */
    public Room(int roomId, int paintingsNumber) {
        this.roomId = roomId;
        this.paintingsNumber = paintingsNumber;
        System.out.printf("< Room %d filled (p = %d)\n", roomId, paintingsNumber);
    }

    /**
     * Get number of room paintings.
     * 
     * @return number of paintings
     */
    public int getPaitingsNumber() {
        return paintingsNumber;
    }

    /**
     * Decrement number of room paintings.
     */
    public void decrementPaitingsNumber() {
        this.paintingsNumber -= 1;
    }

    /**
     * Get room id.
     * 
     * @return room id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Set room id.
     * 
     * @param roomId room id
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Get number of room paintings.
     * 
     * @return room paintings
     */
    public int getPaintingsNumber() {
        return paintingsNumber;
    }

    /**
     * Set number of room paintings.
     * 
     * @param paintingsNumber room paintings
     */
    public void setPaintingsNumber(int paintingsNumber) {
        this.paintingsNumber = paintingsNumber;
    }
}