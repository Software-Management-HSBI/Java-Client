
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public class Stats {

    private long startTime, prevTime;
    private long ms, msMin, msMax;
    public int fps, fpsMin, fpsMax = 20;
    public int frames, mode;

    int fastLapTime;

    Raylib.Color backgroundColor;
    Raylib.Color childenColor;

    private Game game;


    Timer currentLapTime;
    Timer fastestLapTime;

    Timer lastLapTime;

    public Stats(Game game) {
        this.game = game;
        currentLapTime = new Timer();
        fastestLapTime = new Timer();
        lastLapTime = new Timer();
        backgroundColor = Util.color(255, 0, 0, (int) (0.4 * 255));
        childenColor = Util.color(255, 255, 255, (int) (0.6 * 255));
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
        mode = 0;
    }


    public long end() {
        long time = System.currentTimeMillis();

        // Weitere Berechnungen für vergangene Zeit, Bildrate und Aktualisierung von Statistiken

        frames++;

        if (time > prevTime + 1000) {
            fps = (int) Math.round((frames * 1000.0) / (time - prevTime));
            fpsMin = Math.min(fpsMin, fps);
            fpsMax = Math.max(fpsMax, fps);

            // Weitere Berechnungen für Bildrate und Aktualisierung von Statistiken

            prevTime = time;
            frames = 0;
        }

        return time;
    }

    public void update() {
        startTime = end();


        Road.Segment playerSegment = Road.findSegment(game.position + Constants.PLAYERZ);
        if (isCounting()) {

if(currentLapTime.isStarted() && playerSegment.p1.world.z<=0) {

    if (lastLapTime.mil == 0) {
        lastLapTime.mil = currentLapTime.mil;
    } else {
        if (Timer.CompareTimer(currentLapTime, lastLapTime)) {
            fastestLapTime.mil = currentLapTime.mil;
            lastLapTime.mil = currentLapTime.mil;
        } else {
            lastLapTime.mil = currentLapTime.mil;
        }
    }
    currentLapTime.mil = 0;


}


            currentLapTime.update();
        }





    }





    public void draw() {
        long time = System.currentTimeMillis();

        ms = time - startTime;
        msMin = Math.min(msMin, ms);
        msMax = Math.max(msMax, ms);


        DrawText(String.valueOf(fps)+ ":FPS",0,0,50,BLACK);
        DrawText(String.valueOf((int)game.speed/100)+ ":MPH",0,50,50,BLACK);


        DrawText(currentLapTime.getTimeString()+ ":Current Time",Constants.WIDTH/2-50,0,50,BLACK);
        DrawText(lastLapTime.getTimeString()+ ":Last Laptime",Constants.WIDTH/2-50,50,50,BLACK);
        DrawText(fastestLapTime.getTimeString()+ ":Fastest Laptime",Constants.WIDTH/2-50,100,50,BLACK);
    }



    boolean isCounting() {
        return game.position > Constants.PLAYERZ;
    }
}

class Timer {


    float mil;

    void update() {

        mil +=  (1* Constants.STEP);

    }

    public String getTimeString(){

        return String.format("%.2f", mil);
    }
    public void setTime(float mil){
       this.mil = mil;
    }
    public boolean isStarted(){
        return mil>0;
    }

    static boolean CompareTimer(Timer timer1, Timer timer2) {
       return  timer1.mil < timer2.mil;
    }


    void resetTimer(){
       mil = 0;
    }

}



