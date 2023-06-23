package CommInfra;

/**
* Type of the exchanged messages.
*
* Implementation of a client-server model of type 2 (server replication).
* Communication is based on a communication channel under the TCP protocol.
*/
public class MessageType {
    /**
    * Appraise sit (service request).
    */
    public static final int APSIT = 1;

    /**
    * Appraise sit (reply).
    */
    public static final int APSITDONE = 2;

    /**
    * Prepare assault party (service request).
    */
    public static final int PREPASSPARTY = 3;

    /**
    * Prepare assault party (reply).
    */
    public static final int PREPASSPARTYDONE = 4;

    /**
    * Send assault party (service request).
    */
    public static final int SNDASSPARTY = 5;

    /**
    * Send assault party (reply).
    */
    public static final int SNDASSPARTYDONE = 6;

    /**
    * Sum up results (service request).
    */
    public static final int SUMUPRES = 7;

    /**
    * Sum up results (reply).
    */
    public static final int SUMUPRESDONE = 8;

    /**
    * Take a rest (service request).
    */
    public static final int TAKEAREST = 9;

    /**
    * Take a rest (reply).
    */
    public static final int TAKEARESTDONE = 10;

    /**
    * Collect a canvas (service request).
    */
    public static final int COLLACANVAS = 11;

    /**
    * Collect a canvas (reply).
    */
    public static final int COLLACANVASDONE = 12;

    /**
    * Write header log (service request).
    */
    public static final int WRTHEADLOG = 13;

    /**
    * Write header log (reply).
    */
    public static final int WRTHEADLOGDONE = 14;

    /**
    * Write earnings log (service request).
    */
    public static final int WRTEARNLOG = 15;

    /**
    * Write earnings log (reply).
    */
    public static final int WRTEARNLOGDONE = 16;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
    * Am I needed (service request).
    */
    public static final int AMINEED = 17;

    /**
    * Am I needed (reply).
    */
    public static final int AMINEEDDONE = 18;

    /**
    * Prepare excursion (service request).
    */
    public static final int PREPEXC = 19;

    /**
    * Prepare excursion (reply).
    */
    public static final int PREPEXCDONE = 20;

    /**
    * Thief leaving (service request).
    */
    public static final int THIEFLEAV = 21;

    /**
    * Thief leaving (reply).
    */
    public static final int THIEFLEAVDONE = 22;

    /**
    * Hand a canvas (service request).
    */
    public static final int HANDACANVAS = 23;

    /**
    * Hand a canvas (reply).
    */
    public static final int HANDACANVASDONE = 24;

    /**
    * Crawl in (service request).
    */
    public static final int CRAWLIN = 25;

    /**
    * Crawl in (reply).
    */
    public static final int CRAWLINDONE = 26;

    /**
    * Crawl out (service request).
    */
    public static final int CRAWLOUT = 27;

    /**
    * Crawl out (reply).
    */
    public static final int CRAWLOUTDONE = 28;

    /**
    * Roll a canvas (service request).
    */
    public static final int ROLLACANVAS = 29;

    /**
    * Roll a canvas (reply).
    */
    public static final int ROLLACANVASDONE = 30;

    /**
    * Reverse direction (service request).
    */
    public static final int REVERSEDIR = 31;

    /**
    * Reverse direction (reply).
    */
    public static final int REVERSEDIRDONE = 32;

    /**
    * Fill museum (service request).
    */
    public static final int FILLMUSEUM = 33;

    /**
    * Fill museum (reply).
    */
    public static final int FILLMUSEUMDONE = 34;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
    * Write status log (service request).
    */
    public static final int WRTSTATLOG = 35;

    /**
    * Write status log (reply).
    */
    public static final int WRTSTATLOGDONE = 36;

    /**
    * Server shutdown (service request).
    */
    public static final int SHUT = 37;

    /**
    * Server was shutdown (reply).
    */
    public static final int SHUTDONE = 38;
}
