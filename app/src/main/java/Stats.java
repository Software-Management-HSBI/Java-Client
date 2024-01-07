import com.raylib.Jaylib;
import com.raylib.Raylib;
import org.bytedeco.javacpp.BytePointer;

import static com.raylib.Jaylib.*;
import static java.awt.SystemColor.text;


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
        long time = System.currentTimeMillis();

        ms = time - startTime;
        msMin = Math.min(msMin, ms);
        msMax = Math.max(msMax, ms);

        ///Draw background
        DrawRectangle(0,0,Constants.WIDTH,50,backgroundColor);


        String fpsToString = String.valueOf(fps)+" FPS";
        drawBoxWithText(5,0,100,50,BLACK,25,fpsToString);


        String speedToString = String.valueOf((int )Game.speed/100)+" mph";
        drawBoxWithText(Constants.WIDTH-(100+5),0,100,50,BLACK,25,speedToString);

        String timeToSpeed = String.valueOf(Math.min( 30, 30 - ( ms / 200 ) * 30 ));
        drawBoxWithText(Constants.WIDTH/2-(25),0,150,50,BLACK,25,timeToSpeed);



    }

     void drawBoxWithText(int x, int y, int width, int height, Raylib.Color fontColor, int fontSize, String text) {

        // Draw the rectangle
        DrawRectangle(x, y, width, height, childenColor);

        // Draw the text inside the rectangle

        DrawTextEx(GetFontDefault(), text, new Jaylib.Vector2(((float) fontSize /4)+x, ((float) fontSize /4)+ y), fontSize, 2, fontColor);

    }




}
