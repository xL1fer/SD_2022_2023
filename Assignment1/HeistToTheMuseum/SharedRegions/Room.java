package SharedRegions;

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
     * Room distance to outside.
     */
    private int roomDistance;

    /**
     * Room constructor.
     * 
     * @param paintingsNumber number of room's paintings
     * @param roomId room id
     * @param roomDistance room distance
     */
    public Room(int roomId, int paintingsNumber, int roomDistance) {
        this.roomId = roomId;
        this.paintingsNumber = paintingsNumber;
        this.roomDistance = roomDistance;
        System.out.printf("< Room %d initialized (p = %d ; d = %d)\n", roomId, paintingsNumber, roomDistance);
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
     * Get room distance to outside.
     * 
     * @return room distance
     */
    public int getRoomDistance() {
        return roomDistance;
    }

    /**
     * Set room distance to outside.
     * 
     * @param roomDistance room distance
     */
    public void setRoomDistance(int roomDistance) {
        this.roomDistance = roomDistance;
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