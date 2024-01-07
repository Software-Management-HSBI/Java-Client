import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.RED;
import static com.raylib.Jaylib.WHITE;


public class Stats {

    private long startTime, prevTime;
    private long ms, msMin, msMax;
    public int fps, fpsMin, fpsMax = 20;
    public int frames, mode;


   Raylib.Color backgroundColor;
    Raylib.Color childenColor;
    public Stats() {
        backgroundColor =  Util.color(255,0,0, (int) (0.4*255));
        childenColor =  Util.color(255,255,255, (int) (0.6*255));
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
        mode = 0;
    }

    public void begin() {
        startTime = System.currentTimeMillis();
    }

    public long end() {
        long time = System.currentTimeMillis();

        ms = time - startTime;
        msMin = Math.min(msMin, ms);
        msMax = Math.max(msMax, ms);

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
    }


    public void draw(){
        ///Draw background
        Raylib.DrawRectangle(0,0,Constants.WIDTH,60,backgroundColor);

        Raylib.DrawRectangle(5, 5,150,50,childenColor);
        Raylib.DrawRectangle(Constants.WIDTH/2-(75), 5,150,50,childenColor);

        Raylib.DrawRectangle(Constants.WIDTH-(150+5), 5,150,50,childenColor);


    }

}
