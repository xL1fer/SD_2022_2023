package AssaultParty;

import CommInfra.ConfigsOffsets;
import ServerInterfaces.AssaultPartyInterface;

/**
 * Assault party class.
 */
public class AssaultParty implements AssaultPartyInterface {
    /**
     * Assault party members.
     */
    private int[] partyMembers;
    /**
     * Crawling queue.
     */
    private int[] crawlingQueue;
    /**
     * Number of party members.
     */
    private int partyMembersNumber;
    /**
     * Party separation limit.
     */
    private int partySeparationLimit;
    /**
     * Party identifier.
     */
    private int partyId;
    /**
     * Party member insert position.
     */
    private int memberPos;
    /**
     * Party's next crawling thief pointer.
     */
    private int thiefPointer;
    /**
     * Party members queue pos.
     */
    private int[] partyMembersQueuePos;
    /**
     *   Number of entity groups requesting the shutdown.
     */
    private int nEntities;
    /**
     * Simulation debug flag.
     */
    private boolean DEBUG = true;

    /**
     * Assault party constructor.
     * 
     * @param maxRoomDistance maximum room distance
     * @param partyMembersNumber number of party members
     * @param partySeparationLimit party separation limit
     * @param partyId party identification
     */
    public AssaultParty(int maxRoomDistance, int partyMembersNumber, int partySeparationLimit, int partyId) {
        this.partyMembers = new int[partyMembersNumber];
        for (int i = 0; i < partyMembersNumber; i++) {
            this.partyMembers[i] = (partyId - 1) * partyMembersNumber + (i + 1);
            System.out.println("< Party " + partyId + " inserting member " + this.partyMembers[i]);
        }
        this.crawlingQueue = new int[maxRoomDistance];
        this.partyMembersNumber = partyMembersNumber;
        this.partySeparationLimit = partySeparationLimit;
        this.partyId = partyId;
        this.memberPos = 0;
        this.thiefPointer = 0;
        this.partyMembersQueuePos = new int[partyMembersNumber];
        for (int i = 0; i < partyMembersNumber; i++)
            this.partyMembersQueuePos[i] = -1;
        
        this.nEntities = 0;

        System.out.printf("< Assault Party %d initialized\n", partyId);
    }

    /**
     * Ordinary thief crawling in function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @param targetRoomId target room id
     * @return true if thief reached the targeted room
     */
    @Override
    public synchronized boolean crawlIn(int thiefId, int thiefMaximumDisplacement, int targetRoomId) {
        // wait until the thief's turn
        while (getThiefPartyPos(thiefId) != this.thiefPointer) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }      
        }

        int targetRoomDistance;
        // while the current thief is able to move in the crawling queue
        while (true) {
            // the room distance is subtracted by one because the queue starts at 0
            //targetRoomDistance = roomDistances[targetRoomId - 1] - 1;
            targetRoomDistance = targetRoomId;  // NOTE(L1fer): target room distance is being passed from the thiefs in the targetRoomId parameter
            int maxDistance = checkPartyDistance(thiefId, true);

            int maxIndex = getThiefQueuePos(thiefId) + Math.min(this.partySeparationLimit - maxDistance, thiefMaximumDisplacement);

            // if the maximum index surpasses the actual target room distance, truncate this value
            if (maxIndex > targetRoomDistance)
                maxIndex = targetRoomDistance;
            
            // try to move to the next maximum possible position
            boolean thiefMoved = false;
            for (int i = maxIndex; i > getThiefQueuePos(thiefId); i--) {
                // check if this queue position is empty or if it is the target room pos
                if (i == targetRoomDistance || this.crawlingQueue[i] == 0) {
                    if (getThiefQueuePos(thiefId) != -1)
                        this.crawlingQueue[getThiefQueuePos(thiefId)] = 0;                     // remove thief from the current queue pos
                    
                    if (i != targetRoomDistance)
                        this.crawlingQueue[i] = thiefId;            // set thief in the available queue pos

                    this.partyMembersQueuePos[(thiefId - 1) % this.partyMembersNumber] = i;
                    thiefMoved = true;                              // mark that the thief moved

                    if (DEBUG) {
                        System.out.printf("> Thief < %d > (p = %d) is CRAWLING IN (queue pos = %d)!\n", thiefId, this.partyId, getThiefQueuePos(thiefId));
                    }
        
                    break;              // do not check any more positions
                }
            }

            // signal the next available thief if current thief can't move
            if (!thiefMoved || getThiefQueuePos(thiefId) == targetRoomDistance) {
                int nextThiefPointer = this.thiefPointer;

                int thievesAtTheRoom = 0;

                // search for the next thief to signal
                while (true) {
                    nextThiefPointer += 1;
                    if (nextThiefPointer > this.partyMembersNumber - 1)
                        nextThiefPointer = 0;

                    // if the thief is not yet at the target room, he will be the next
                    if (getThiefQueuePos(nextThiefPointer + 1) != targetRoomDistance || thievesAtTheRoom == this.partyMembersNumber)
                        break;

                    thievesAtTheRoom++;
                }

                if (thievesAtTheRoom == this.partyMembersNumber)
                    this.thiefPointer = 0;
                else
                    this.thiefPointer = nextThiefPointer;

                notifyAll();
                break;
            }
        }

        if (getThiefQueuePos(thiefId) == targetRoomDistance)
            return true;

        return false;
    }

    /**
     * Ordinary thief crawling out function.
     * 
     * @param thiefId thief id
     * @param thiefMaximumDisplacement thief maximum displacement
     * @return true if thief reached the targeted room
     */
    @Override
    public synchronized boolean crawlOut(int thiefId, int thiefMaximumDisplacement) {
        // wait until the thief's turn
        while (getThiefPartyPos(thiefId) != this.thiefPointer) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }      
        }

        int outsideDistance;
        // while the current thief is able to move in the crawling queue
        while (true) {
            // the outside distance is -1 since the first room position is 0
            outsideDistance = -1;
            int maxDistance = checkPartyDistance(thiefId, false);

            int minIndex = getThiefQueuePos(thiefId) - Math.min(this.partySeparationLimit + maxDistance, thiefMaximumDisplacement);
            // if the minimum index surpasses the actual outside distance, truncate this value
            if (minIndex < outsideDistance)
                minIndex = outsideDistance;
            
            // try to move to the next minimum possible position
            boolean thiefMoved = false;
            for (int i = minIndex; i < getThiefQueuePos(thiefId); i++) {
                // check if this queue position is empty or if it is the outside site pos
                if (i == outsideDistance || this.crawlingQueue[i] == 0) {
                    this.crawlingQueue[getThiefQueuePos(thiefId)] = 0;                         // remove thief from the current queue pos
                    
                    if (i != outsideDistance)
                        this.crawlingQueue[i] = thiefId;            // set thief in the available queue pos

                    this.partyMembersQueuePos[(thiefId - 1) % this.partyMembersNumber] = i;
                    thiefMoved = true;                              // mark that the thief moved

                    if (DEBUG) {
                        System.out.printf("> Thief < %d > (p = %d) is CRAWLING OUT (queue pos = %d)!\n", thiefId, this.partyId, getThiefQueuePos(thiefId));
                    }

                    break;              // do not check any more positions
                }
            }

            // signal the next available thief if current thief can't move
            if (!thiefMoved || getThiefQueuePos(thiefId) == outsideDistance) {
                int nextThiefPointer = this.thiefPointer;

                int thievesAtTheSite = 0;

                // search for the next thief to signal
                while (true) {
                    nextThiefPointer += 1;
                    if (nextThiefPointer > this.partyMembersNumber - 1)
                        nextThiefPointer = 0;

                    // if the thief is not yet at the target room, he will be the next
                    if (getThiefQueuePos(nextThiefPointer + 1) != outsideDistance || thievesAtTheSite == this.partyMembersNumber)
                        break;

                    thievesAtTheSite++;
                }

                if (thievesAtTheSite == this.partyMembersNumber)
                    this.thiefPointer = 0;
                else
                    this.thiefPointer = nextThiefPointer;

                notifyAll();
                break;
            }
        }

        if (getThiefQueuePos(thiefId) == outsideDistance)
            return true;
            
        return false;
    }
    
    /**
     * Add thief to party.
     * 
     * @param thiefId thief id
     * @return added thief's position
     */
    public synchronized int addPartyMember(int thiefId) {
        this.partyMembers[memberPos] = thiefId;
        this.memberPos += 1;

        return this.memberPos;
    }

    /**
     * Check maximum distance from party members
     * 
     * @param thiefId thief id
     * @param forwards direction flag
     * @return maximum distance
     */
    public int checkPartyDistance(int thiefId, boolean forwards) {
        int maxDistance = 0;

        // get the maximum distance between the other thiefs and the current thief
        for (int i = 0; i < this.partyMembers.length; i++) {
            if (thiefId == this.partyMembers[i])
                continue;

            int curDistance = getThiefQueuePos(thiefId) - getThiefQueuePos(this.partyMembers[i]);
            
            // crawling in
            if (forwards) {
                if (curDistance > maxDistance) {
                    maxDistance = curDistance;
                }
                else if (maxDistance == 0) {
                    maxDistance = curDistance;
                }
            }
            // crawling out
            else {
                if (curDistance < maxDistance) {
                    maxDistance = curDistance;
                }
                else if (maxDistance == 0) {
                    maxDistance = curDistance;
                }
            }
        }

        return maxDistance;
    }

    private int getThiefQueuePos(int thiefId) {
        return this.partyMembersQueuePos[(thiefId - 1) % this.partyMembersNumber];
    }

    private int getThiefPartyPos(int thiefId) {
        for (int i = 0; i < this.partyMembers.length; i++) {
            if (this.partyMembers[i] == thiefId)
                return i;
        }
        return -1;
    }

    /**
     * Get party members.
     * 
     * @return party members
     */
    public int[] getPartyMembers() {
        return partyMembers;
    }

    /**
     * Set party members.
     * 
     * @param partyMembers party members
     */
    public void setPartyMembers(int[] partyMembers) {
        this.partyMembers = partyMembers;
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
     * Get member position.
     * 
     * @return member position
     */
    public int getMemberPos() {
        return memberPos;
    }

    /**
     * Set member position.
     * 
     * @param memberPos member position
     */
    public void setMemberPos(int memberPos) {
        this.memberPos = memberPos;
    }

    /**
     * Get thief pointer.
     * 
     * @return thief pointer
     */
    public int getThiefPointer() {
        return thiefPointer;
    }

    /**
     * Set thief pointer.
     * 
     * @param thiefPointer thief pointer
     */
    public void setThiefPointer(int thiefPointer) {
        this.thiefPointer = thiefPointer;
    }

    /**
     * Operation server shutdown.
     *
     * New operation.
     */
    public synchronized void shutdown() {
        this.nEntities += 1;
        if (this.nEntities >= SimulationInput.inputArray[ConfigsOffsets.thievesNumber] - 1)
            Main.shutdown();
    }
}