package Thieves;

/**
 * Thief abstract class.
 */
public abstract class Thief extends Thread {
    /**
     * Thief current state.
     */
    int currentState;

    /**
     * Thief constructor.
     * 
     * @param currentState thief's current state
     */
    public Thief(int currentState) {
        this.currentState = currentState;
    }

    /**
     * Get thief's current state.
     * 
     * @return thief's current state
     */
    public int getCurrentState() {
        return this.currentState;
    }

    /**
     * Set thief's current state.
     * 
     * @param currentState thief's current state
     */
    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    @Override
    public abstract void run();
}