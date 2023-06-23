package CommInfra;

import java.io.*;
import java.util.ArrayList;

/**
 * Internal structure of the exchanged messages.
 *
 * Implementation of a client-server model of type 2 (server replication).
 * Communication is based on a communication channel under the TCP protocol.
 */
public class Message implements Serializable {
    /**
     * Serialization key.
     */
    private static final long serialVersionUID = 2021L;
    /**
     * Message type.
     */
    private int msgType = -1;
    /**
     * Thief identification.
     */
    private int thiefId = -1;
    /**
     * Master thief state.
     */
    private int masterThiefState = -1;
    /**
     * Ordinary thief state.
     */
    private int ordinaryThiefState = -1;
    /**
     * Total available rooms.
     */
    private int totalAvailableRooms = -1;
    /**
     * Total assault parties.
     */
    private int totalAssaultParties = -1;
    /**
     * Available rooms list.
     */
    private ArrayList<Integer> availableRooms = null;
    /**
     * Targeted rooms list.
     */
    private ArrayList<Integer> targetedRooms = null;
    /**
     * Total painting earnings.
     */
    private int paintingEarnings = -1;
    /**
     * Thief party identification.
     */
    private int partyId = -1;
    /**
     * Thief party target room identification.
     */
    private int partyTargetRoomId = -1;
    /**
     * Thief maximum displacement.
     */
    private int maximumDisplacement = -1;
    /**
     * Thief canvas status.
     */
    private int canvasStatus = -1;
    /**
     * Party status.
     */
    private boolean partyStatus = false;
    /**
     * Remove canvas flag.
     */
    private int removeCanvas = -1;
    /**
     * Museum rooms.
     */
    private int[][] museumRooms;
    /**
     * Message return boolean.
     */
    private boolean returnBoolean = false;
    /**
     * Message return integer.
     */
    private int returnInteger = -1;
    /**
     * Message return integer array.
     */
    private int[] returnIntegerArray = null;

    /**
     * Message instantiation.
     *
     * 
     * @param msgType message type
     * @param thiefId thief identification
     * @param masterThiefState master thief state
     * @param ordinaryThiefState ordinary thief state
     * @param totalAvailableRooms total available rooms
     * @param totalAssaultParties total assault parties
     * @param availableRooms available rooms list
     * @param targetedRooms targeted rooms list
     * @param paintingEarnings total painting earnings
     * @param partyId thief party identification
     * @param partyTargetRoomId thief party target room identification
     * @param maximumDisplacement thief maximum displacement
     * @param canvasStatus thief canvas status
     * @param partyStatus party status
     * @param removeCanvas remove canvas flag
     * @param museumRooms museum rooms
     * @param returnBoolean message return boolean
     * @param returnInteger message return integer
     * @param returnIntegerArray message return integer array
     */
    public Message (int msgType, int thiefId, int masterThiefState, int ordinaryThiefState, int totalAvailableRooms, int totalAssaultParties, ArrayList<Integer> availableRooms,
                    ArrayList<Integer> targetedRooms,int paintingEarnings, int partyId, int partyTargetRoomId, int maximumDisplacement,
                    int canvasStatus, boolean partyStatus, int removeCanvas, int[][] museumRooms, boolean returnBoolean, int returnInteger, int[] returnIntegerArray) {
        this.msgType = msgType;
        this.thiefId = thiefId;
        this.masterThiefState = masterThiefState;
        this.ordinaryThiefState = ordinaryThiefState;
        this.totalAvailableRooms = totalAvailableRooms;
        this.totalAssaultParties = totalAssaultParties;
        this.availableRooms = availableRooms;
        this.targetedRooms = targetedRooms;
        this.paintingEarnings = paintingEarnings;
        this.partyId = partyId;
        this.partyTargetRoomId = partyTargetRoomId;
        this.maximumDisplacement = maximumDisplacement;
        this.canvasStatus = canvasStatus;
        this.partyStatus = partyStatus;
        this.removeCanvas = removeCanvas;
        this.museumRooms = museumRooms;
        this.returnBoolean = returnBoolean;
        this.returnInteger = returnInteger;
        this.returnIntegerArray = returnIntegerArray;
    }

    /**
     * Get message type.
     *
     * @return message type
     */
    public int getMsgType () {
        return (msgType);
    }

    /**
     * Set message type.
     *
     * @param msgType message type
     */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    /**
     * Get thief id.
     *
     * @return thief id
     */
    public int getThiefId() {
        return thiefId;
    }

    /**
     * Set thief id.
     *
     * @param thiefId thief id
     */
    public void setThiefId(int thiefId) {
        this.thiefId = thiefId;
    }

    /**
     * Get master thief state.
     *
     * @return master thief state
     */
    public int getMasterThiefState() {
        return masterThiefState;
    }

    /**
     * Set master thief state.
     *
     * @param masterThiefState master thief state
     */
    public void setMasterThiefState(int masterThiefState) {
        this.masterThiefState = masterThiefState;
    }

    /**
     * Get ordinary thief state.
     *
     * @return ordinary thief state
     */
    public int getOrdinaryThiefState() {
        return ordinaryThiefState;
    }

    /**
     * Set ordinary thief state.
     *
     * @param ordinaryThiefState ordinary thief state
     */
    public void setOrdinaryThiefState(int ordinaryThiefState) {
        this.ordinaryThiefState = ordinaryThiefState;
    }

    /**
     * Get total available rooms.
     *
     * @return total available rooms
     */
    public int getTotalAvailableRooms() {
        return totalAvailableRooms;
    }

    /**
     * Set total available rooms.
     *
     * @param totalAvailableRooms total available rooms
     */
    public void setTotalAvailableRooms(int totalAvailableRooms) {
        this.totalAvailableRooms = totalAvailableRooms;
    }

    /**
     * Get total assault parties.
     *
     * @return total assault parties
     */
    public int getTotalAssaultParties() {
        return totalAssaultParties;
    }

    /**
     * Set total assault parties.
     *
     * @param totalAssaultParties total assault parties
     */
    public void setTotalAssaultParties(int totalAssaultParties) {
        this.totalAssaultParties = totalAssaultParties;
    }

    /**
     * Get available rooms list.
     *
     * @return available rooms list
     */
    public ArrayList<Integer> getAvailableRooms() {
        return availableRooms;
    }

    /**
     * Set available rooms list.
     *
     * @param availableRooms available rooms list
     */
    public void setAvailableRooms(ArrayList<Integer> availableRooms) {
        this.availableRooms = availableRooms;
    }

    /**
     * Get targeted rooms list.
     *
     * @return targeted rooms list
     */
    public ArrayList<Integer> getTargetedRooms() {
        return targetedRooms;
    }

    /**
     * Set targeted rooms list.
     *
     * @param targetedRooms targeted rooms list
     */
    public void setTargetedRooms(ArrayList<Integer> targetedRooms) {
        this.targetedRooms = targetedRooms;
    }

    /**
     * Get painting earnings.
     *
     * @return painting earnings
     */
    public int getPaintingEarnings() {
        return paintingEarnings;
    }

    /**
     * Set painting earnings.
     *
     * @param paintingEarnings painting earnings
     */
    public void setPaintingEarnings(int paintingEarnings) {
        this.paintingEarnings = paintingEarnings;
    }

    /**
     * Get party id.
     *
     * @return party id
     */
    public int getPartyId() {
        return partyId;
    }

    /**
     * Set party id.
     *
     * @param partyId party id
     */
    public void setPartyId(int partyId) {
        this.partyId = partyId;
    }

    /**
     * Get party targeted room id.
     *
     * @return party targeted room id
     */
    public int getPartyTargetRoomId() {
        return partyTargetRoomId;
    }

    /**
     * Set party targeted room id.
     *
     * @param partyTargetRoomId party targeted room id
     */
    public void setPartyTargetRoomId(int partyTargetRoomId) {
        this.partyTargetRoomId = partyTargetRoomId;
    }

    /**
     * Get maximum displacement.
     *
     * @return maximum displacement
     */
    public int getMaximumDisplacement() {
        return maximumDisplacement;
    }

    /**
     * Set maximum displacement.
     *
     * @param maximumDisplacement maximum displacement
     */
    public void setMaximumDisplacement(int maximumDisplacement) {
        this.maximumDisplacement = maximumDisplacement;
    }

    /**
     * Get canvas status.
     *
     * @return canvas status
     */
    public int getCanvasStatus() {
        return canvasStatus;
    }

    /**
     * Set canvas status.
     *
     * @param canvasStatus canvas status
     */
    public void setCanvasStatus(int canvasStatus) {
        this.canvasStatus = canvasStatus;
    }

    /**
     * Get party status.
     *
     * @return party status
     */
    public boolean getPartyStatus() {
        return partyStatus;
    }

    /**
     * Set party status.
     *
     * @param partyStatus party status
     */
    public void setPartyStatus(boolean partyStatus) {
        this.partyStatus = partyStatus;
    }

    /**
     * Get remove canvas flag.
     *
     * @return remove canvas flag
     */
    public int getRemoveCanvas() {
        return removeCanvas;
    }

    /**
     * Set remove canvas flag.
     *
     * @param removeCanvas remove canvas flag
     */
    public void setRemoveCanvas(int removeCanvas) {
        this.removeCanvas = removeCanvas;
    }
    
    /**
     * Get museum rooms.
     *
     * @return museum rooms
     */
    public int[][] getMuseumRooms() {
        return museumRooms;
    }

    /**
     * Set museum rooms.
     *
     * @param museumRooms museum rooms
     */
    public void setMuseumRooms(int[][] museumRooms) {
        this.museumRooms = museumRooms;
    }

    /**
     * Get return boolean.
     *
     * @return return boolean
     */
    public boolean getReturnBoolean() {
        return returnBoolean;
    }

    /**
     * Set return boolean.
     *
     * @param returnBoolean return boolean
     */
    public void setReturnBoolean(boolean returnBoolean) {
        this.returnBoolean = returnBoolean;
    }

    /**
     * Get return integer.
     *
     * @return return integer
     */
    public int getReturnInteger() {
        return returnInteger;
    }

    /**
     * Set return integer.
     *
     * @param returnInteger return integer
     */
    public void setReturnInteger(int returnInteger) {
        this.returnInteger = returnInteger;
    }

    /**
     * Get return integer array.
     *
     * @return return integer array
     */
    public int[] getReturnIntegerArray() {
        return returnIntegerArray;
    }

    /**
     * Set return integer array.
     *
     * @param returnIntegerArray return integer array
     */
    public void setReturnIntegerArray(int[] returnIntegerArray) {
        this.returnIntegerArray = returnIntegerArray;
    }
}
