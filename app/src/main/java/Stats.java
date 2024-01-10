import static com.raylib.Jaylib.*;

import com.raylib.Raylib;

/**
 * The Stats class is responsible for tracking and displaying various statistics related to the
 * game, including frames per second, lap times, and collision information. It utilizes the Timer
 * class for time-related operations.
 */
public class Stats {

    // Private fields

    // Starting time used for tracking the elapsed time and frame statistics.
    private long startTime;

    // Previous time used for calculating frame statistics.
    private long prevTime;

    // Elapsed time in milliseconds.
    private long ms;

    // Minimum elapsed time in milliseconds.
    private long msMin;

    // Maximum elapsed time in milliseconds.
    private long msMax;

    /** Current frames per second (FPS). */
    public int fps;

    /** Minimum frames per second (FPS) recorded. */
    public int fpsMin;

    /** Maximum frames per second (FPS) recorded. */
    public int fpsMax;

    /** Total number of frames rendered. */
    public int frames;

    // The Game object associated with these statistics.
    private final Game game;

    // The current lap round, initialized to -2.
    private int round = 0;

    // The timer object representing the current lap time.
    private final Timer currentLapTime;

    // The timer object representing the fastest lap time.
    private final Timer fastestLapTime;

    // The timer object representing the last lap time.
    private final Timer lastLapTime;

    // A boolean flag indicating whether the lap timer has started.
    private boolean timerStarted;

    // A boolean flag indicating whether the player was colliding in the previous frame.
    private boolean wasColliding = false;

    Road.Segment start1;
    Road.Segment playerSegment;

    /**
     * Constructs a Stats object with necessary initializations.
     *
     * @param game The Game object associated with these statistics.
     */
    public Stats(Game game) {
        this.game = game;
        currentLapTime = new Timer();
        fastestLapTime = new Timer();
        lastLapTime = new Timer();
        // The background color used for rendering statistics on the screen.
        Raylib.Color backgroundColor = Util.color(255, 0, 0, (int) (0.4 * 255));
        // The color used for rendering children-related statistics on the screen.
        Raylib.Color childenColor = Util.color(255, 255, 255, (int) (0.6 * 255));
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
    }

    /**
     * Ends the frame, updating statistics such as frames per second.
     *
     * @return The current time in milliseconds.
     */
    public long end() {
        long time = System.currentTimeMillis();
        frames++;

        if (time > prevTime + 1000) {
            fps = (int) Math.round((frames * 1000.0) / (time - prevTime));
            fpsMin = Math.min(fpsMin, fps);
            fpsMax = Math.max(fpsMax, fps);

            prevTime = time;
            frames = 0;
        }

        return time;
    }

    /** Updates the game statistics, lap times, and collision information. */
    public void update() {
        startTime = end();

        if (IsKeyDown(KEY_UP)) {
            timerStarted = true;
        }

        if (timerStarted) {
            currentLapTime.incrememt();
        }

        boolean isCurrentlyColliding = isColliding();

        if (isCurrentlyColliding && !wasColliding) {
            round++;
            if (round > 0) {
                timerAssignment();
            }
        }

        wasColliding = isCurrentlyColliding;
    }

    /** Draws the current game statistics on the screen. */
    public void draw() {
        long time = System.nanoTime();

        ms = time - startTime;
        msMin = Math.min(msMin, ms);
        msMax = Math.max(msMax, ms);

        DrawText(String.valueOf(fps) + ":FPS", 0, 0, 50, BLUE);
        DrawText(String.valueOf((int) game.speed / 100) + ":MPH", 0, 50, 50, BLUE);

        DrawText(
                currentLapTime.getTimeString() + ":Current Time",
                Constants.WIDTH / 2 - 50,
                0,
                50,
                BLACK);
        DrawText(
                lastLapTime.getTimeString() + ":Last Laptime",
                Constants.WIDTH / 2 - 50,
                50,
                50,
                BLACK);
        DrawText(
                fastestLapTime.getTimeString() + ":Fastest Laptime",
                Constants.WIDTH / 2 - 50,
                100,
                50,
                BLACK);

        DrawText(
                round + ":Round ",
                Constants.WIDTH / 2 - 50,
                150,
                50,
                RED);
    }

    /** Handles lap time assignments based on the current round. */
    void timerAssignment() {
        if (round == 1) {
            lastLapTime.mil = currentLapTime.mil;
            fastestLapTime.mil = currentLapTime.mil;
            currentLapTime.mil = 0;
        } else if (round > 1) {
            if (Timer.CompareTimer(currentLapTime, lastLapTime)) {
                fastestLapTime.mil = currentLapTime.mil;
                lastLapTime.mil = currentLapTime.mil;
                currentLapTime.mil = 0;
            } else {
                lastLapTime.mil = currentLapTime.mil;
                currentLapTime.mil = 0;
            }
        }
    }

    /**
     * Checks if the player is currently colliding with an object in the game.
     *
     * @return True if a collision is detected, false otherwise.
     */
    boolean isColliding() {
        Road.Segment endSegment = Game.segments.get(Road.findSegment(Constants.PLAYERZ).index - 4);
        playerSegment = Road.findSegment(game.position + Constants.PLAYERZ);
        return endSegment.p1.camera.z == playerSegment.p1.camera.z;
    }
}

/**
 * The Timer class represents a timer used for tracking elapsed time. It provides methods for
 * incrementing the timer and formatting the elapsed time.
 */
class Timer {

    /** The elapsed time in milliseconds. */
    public float mil;

    /** Increments the timer by a predefined step. */
    public void incrememt() {
        mil += (float) (1 * Constants.STEP);
    }

    /**
     * Formats the elapsed time as a string with two decimal places.
     *
     * @return The formatted time string.
     */
    public String getTimeString() {
        return String.format("%.2f", mil);
    }

    /**
     * Compares two Timer objects to determine which has a smaller elapsed time.
     *
     * @param timer1 The first Timer object.
     * @param timer2 The second Timer object.
     * @return True if the elapsed time of timer1 is less than timer2, false otherwise.
     */
    static boolean CompareTimer(Timer timer1, Timer timer2) {
        return timer1.mil < timer2.mil;
    }
}
