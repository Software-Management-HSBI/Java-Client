import com.raylib.Jaylib;
import com.raylib.Raylib;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Pointer;

import java.util.concurrent.Callable;

import static com.raylib.Jaylib.*;
import static com.raylib.Raylib.*;

/**
 * Die OptionsManager-Klasse verwaltet die Optionen des Spiels.
 * Singleton Pattern wird benutzt
 */
public class OptionsManager {

    // Instanzvariable für das Singleton-Muster
    private static final OptionsManager instance = new OptionsManager();

    // Anzeige der Optionen
    boolean show;

    // Optionen für das Spiel
    float roadWidth;
    float cameraHeight;
    float drawDistance;
    float fogDensity;
    float fieldOfView;

    // Dropdown-Fenster-Status
    boolean dropdownWindowOpened;

    // Status der Schaltflächen im Fenster
    boolean windowButton1Clicked;

    // Rechtecke für GUI-Elemente
    Jaylib.Rectangle laneTitle = new Jaylib.Rectangle(500, 150, 200, 50);
    Jaylib.Rectangle laneButtonSize1 = new Jaylib.Rectangle(500, 200, 200, 50);
    Jaylib.Rectangle laneButtonSize2 = new Jaylib.Rectangle(500, 250, 200, 50);
    Jaylib.Rectangle laneButtonSize3 = new Jaylib.Rectangle(500, 300, 200, 50);
    Jaylib.Rectangle laneButtonSize4 = new Jaylib.Rectangle(500, 350, 200, 50);

    Jaylib.Rectangle roadWidthSize = new Jaylib.Rectangle(120, 200, 300, 50);
    Jaylib.Rectangle cameraHeightSize = new Jaylib.Rectangle(120, 250, 300, 50);
    Jaylib.Rectangle drawDistanceSize = new Jaylib.Rectangle(120, 300, 300, 50);
    Jaylib.Rectangle fieldOfViewSize = new Jaylib.Rectangle(120, 350, 300, 50);
    Jaylib.Rectangle fogDensitySize = new Jaylib.Rectangle(120, 400, 300, 50);

    // Private Konstruktor für das Singleton-Muster
    private OptionsManager() {
        // Standardwerte für die Optionen
        roadWidth = Constants.ROADWIDTH;
        cameraHeight = Constants.CAMERAHEIGHT;
        drawDistance = Constants.DRAWDISTANCE;
        fieldOfView = (float) Constants.FOV;
        fogDensity = Constants.FOGDENSITY;
    }

    /**
     * Gibt die einzige Instanz der OptionsManager-Klasse zurück.
     *
     * @return Instanz von OptionsManager
     */
    public static OptionsManager getInstance() {
        return instance;
    }

    /**
     * Aktualisiert die Optionen basierend auf Benutzereingaben.
     */
    public void update() {
        // Tastendruck 'S' zeigt/versteckt die Optionen
        if (IsKeyPressed(KEY_S)) {
            show = !show;
        }

        // Aktualisiert die Konstanten basierend auf den Schiebereglern
        Constants.ROADWIDTH = (int) roadWidth;
        Constants.CAMERAHEIGHT = (int) cameraHeight;
        Constants.DRAWDISTANCE = (int) drawDistance;
        Constants.FOV = (int) fieldOfView;
        Constants.FOGDENSITY = (int) fogDensity;
    }

    /**
     * Zeigt das Hintergrundfenster mit den Optionen.
     */
    public void showBackground() {
        if (show) {
            updateGui();
            checkButtonWindowSize();
        }
    }

    /**
     * Überprüft, ob eine Schaltfläche im Dropdown-Fenster geklickt wurde.
     */
    private void checkButtonWindowSize() {
        if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize1)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 1;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize2)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 2;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize3)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 3;
            }
        } else if (CheckCollisionPointRec(GetMousePosition(), laneButtonSize4)) {
            if (IsMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
                Constants.LANES = 4;
            }
        }
    }

    //Aktualisiert die grafische Benutzeroberfläche (GUI) für die Optionen.

    private void updateGui() {
        DrawRectangle(0, 0, Constants.WIDTH, Constants.HEIGHT, ColorAlpha(Util.color(0, 0, 0), 0.5f));
        DrawText("Lanes:", 500, 170, 20, GRAY);

        // Aktualisiert die Schieberegler und Schaltflächen
        roadWidth = GuiSlider(roadWidthSize, "Road Width: " + (int) roadWidth, "", roadWidth, 500, 3000);
        cameraHeight = GuiSlider(cameraHeightSize, "Camera Height: " + (int) cameraHeight, "", cameraHeight, 500, 5000);
        drawDistance = GuiSlider(drawDistanceSize, "Draw Distance: " + (int) drawDistance, "", drawDistance, 100, 500);
        fieldOfView = GuiSlider(fieldOfViewSize, "Field of View: " + (int) fieldOfView, "", fieldOfView, 80, 140);
        fogDensity = GuiSlider(fogDensitySize, "Fog Density: " + (int) fogDensity, "", fogDensity, 0, 50);

        GuiButton(laneButtonSize1, "Lane 1");
        GuiButton(laneButtonSize2, "Lane 2");
        GuiButton(laneButtonSize3, "Lane 3");
        GuiButton(laneButtonSize4, "Lane 4");
    }
}
