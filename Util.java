import java.util.Date;


/**
 * Eine Utility-Klasse mit verschiedenen Methoden für allgemeine Berechnungen und Transformationen.
 */
public class Util {

    /**
     * Gibt den aktuellen Zeitstempel zurück.
     *
     * @return Der aktuelle Zeitstempel in Millisekunden.
     */
    public static long getCurrentTimestamp() {
        return new Date().getTime();
    }

    /**
     * Konvertiert ein Objekt in einen Integer mit Standardwert, falls die Konvertierung fehlschlägt.
     *
     * @param obj Das zu konvertierende Objekt.
     * @param def Der Standardwert, der zurückgegeben wird, wenn die Konvertierung fehlschlägt.
     * @return Der konvertierte Integer-Wert oder der Standardwert.
     */
    public static int toInt(Object obj, int def) {
        if (obj != null) {
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
                // Falls die Konvertierung fehlschlägt, wird der Standardwert zurückgegeben
            }
        }
        return def;
    }

    /**
     * Konvertiert ein Objekt in einen Float mit Standardwert, falls die Konvertierung fehlschlägt.
     *
     * @param obj Das zu konvertierende Objekt.
     * @param def Der Standardwert, der zurückgegeben wird, wenn die Konvertierung fehlschlägt.
     * @return Der konvertierte Float-Wert oder der Standardwert.
     */
    public static float toFloat(Object obj, float def) {
        if (obj != null) {
            try {
                return Float.parseFloat(obj.toString());
            } catch (NumberFormatException e) {
                // Falls die Konvertierung fehlschlägt, wird der Standardwert zurückgegeben
            }
        }
        return def;
    }

    /**
     * Begrenzt einen Wert auf ein Intervall zwischen min und max.
     *
     * @param value Der zu begrenzende Wert.
     * @param min   Der untere Grenzwert des Intervalls.
     * @param max   Der obere Grenzwert des Intervalls.
     * @return Der begrenzte Wert.
     */
    public static int limit(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Generiert eine zufällige Ganzzahl im Intervall [min, max].
     *
     * @param min Der untere Grenzwert des Intervalls.
     * @param max Der obere Grenzwert des Intervalls.
     * @return Die generierte zufällige Ganzzahl.
     */
    public static int randomInt(int min, int max) {
        return Math.round(interpolate(min, max, (int) Math.random()));
    }

    /**
     * Interpoliert zwischen zwei Werten basierend auf einem Prozentsatz.
     *
     * @param a       Der Startwert der Interpolation.
     * @param b       Der Endwert der Interpolation.
     * @param percent Der Prozentsatz der Interpolation.
     * @return Der interpolierte Wert.
     */
    public static int interpolate(int a, int b, int percent) {
        return a + (b - a) * percent;
    }


    /**
     * Wählt zufällig ein Element aus einem Array von Optionen aus.
     *
     * @param options Das Array von Optionen.
     * @return Das zufällig ausgewählte Element.
     */
    public static int randomChoice(int[] options) {
        return options[randomInt(0, options.length - 1)];
    }


    /**
     * Berechnet den prozentualen Anteil von n im Verhältnis zu total.
     *
     * @param n     Der zu berechnende Wert.
     * @param total Der Gesamtwert.
     * @return Der prozentuale Anteil von n.
     */
    public static int percentRemaining(int n, int total) {
        return (n % total) / total;
    }

    /**
     * Beschleunigt einen Wert v basierend auf Beschleunigung accel und der Zeit dt.
     *
     * @param v     Der zu beschleunigende Wert.
     * @param accel Die Beschleunigung.
     * @param dt    Die Zeit.
     * @return Der beschleunigte Wert.
     */
    public static int accelerate(int v, int accel, int dt) {
        return v + (accel * dt);
    }

    /**
     * Wendet eine "Ease In" Interpolation auf einen Bereich von a bis b basierend auf einem Prozentsatz an.
     *
     * @param a       Der Startwert der Interpolation.
     * @param b       Der Endwert der Interpolation.
     * @param percent Der Prozentsatz der Interpolation.
     * @return Der interpolierte Wert mit "Ease In".
     */
    public static int easeIn(int a, int b, int percent) {
        return (int) (a + (b - a) * Math.pow(percent, 2));
    }

    /**
     * Wendet eine "Ease Out" Interpolation auf einen Bereich von a bis b basierend auf einem Prozentsatz an.
     *
     * @param a       Der Startwert der Interpolation.
     * @param b       Der Endwert der Interpolation.
     * @param percent Der Prozentsatz der Interpolation.
     * @return Der interpolierte Wert mit "Ease Out".
     */
    public static int easeOut(int a, int b, int percent) {
        return (int) (a + (b - a) * (1 - Math.pow(1 - percent, 2)));
    }

    /**
     * Wendet eine "Ease In/Out" Interpolation auf einen Bereich von a bis b basierend auf einem Prozentsatz an.
     *
     * @param a       Der Startwert der Interpolation.
     * @param b       Der Endwert der Interpolation.
     * @param percent Der Prozentsatz der Interpolation.
     * @return Der interpolierte Wert mit "Ease In/Out".
     */
    public static int easeInOut(int a, int b, int percent) {
        return (int) (a + (b - a) * (1 - Math.pow(1 - percent, 2)));
    }

    /**
     * Berechnet den Wert einer exponentiellen Nebel-Funktion basierend auf Entfernung und Dichte.
     *
     * @param distance Die Entfernung.
     * @param density  Die Dichte.
     * @return Der Wert der exponentiellen Nebel-Funktion.
     */
    public static double exponentialFog(double distance, double density) {
        return 1 / Math.pow(Math.E, (distance * distance * density));
    }

    /**
     * Erhöht einen Wert start um increment und sorgt dafür, dass er im Bereich von 0 bis max bleibt.
     *
     * @param start     Der Startwert.
     * @param increment Der Inkrementwert.
     * @param max       Der maximale Wert.
     * @return Der erhöhte und begrenzte Wert.
     */
    public static int increase(int start, int increment, int max) {
        int result = start + increment;
        while (result >= max)
            result -= max;
        while (result < 0)
            result += max;
        return result;
    }

    /**
     * Eine Klasse, die einen Punkt in verschiedenen Koordinatensystemen repräsentiert.
     */
    public static class Point {
        WorldPoint world = new WorldPoint();
        CameraPoint camera = new CameraPoint();
        ScreenPoint screen = new ScreenPoint();

        @Override
        public String toString() {
            return "Point{" +
                    "world=" + world +
                    ", camera=" + camera +
                    ", screen=" + screen +
                    '}';
        }
    }

    /**
     * Eine Klasse, die einen Punkt im Weltkoordinatensystem repräsentiert.
     */
    public static class WorldPoint {
        Double x;
        Double y;
        Double z;
    }

    /**
     * Eine Klasse, die einen Punkt im Kamerakoordinatensystem repräsentiert.
     */
    public static class CameraPoint {
        double x;
        double y;
        double z;
    }

    /**
     * Eine Klasse, die einen Punkt im Bildschirmkoordinatensystem repräsentiert.
     */
    public static class ScreenPoint {
        double scale;
        double x;
        double y;
        double w;
    }

    /**
     * Projiziert einen Punkt auf den Bildschirm basierend auf Kamerakoordinaten und anderen Parametern.
     *
     * @param p           Der zu projizierende Punkt.
     * @param cameraX     Die X-Koordinate der Kamera.
     * @param cameraY     Die Y-Koordinate der Kamera.
     * @param cameraZ     Die Z-Koordinate der Kamera.
     * @param cameraDepth Die Tiefe der Kamera.
     * @param width       Die Breite des Bildschirms.
     * @param height      Die Höhe des Bildschirms.
     * @param roadWidth   Die Breite der Straße.
     */
    public static void project(Point p, double cameraX, double cameraY, double cameraZ,
                               double cameraDepth, double width, double height, double roadWidth) {
        p.camera.x = (p.world.x != null ? p.world.x : 0) - cameraX;
        p.camera.y = (p.world.y != null ? p.world.y : 0) - cameraY;
        p.camera.z = (p.world.z != null ? p.world.z : 0) - cameraZ;

        p.screen.scale = cameraDepth / p.camera.z;
        p.screen.x = Math.round((width / 2) + (p.screen.scale * p.camera.x * width / 2));
        p.screen.y = Math.round((height / 2) - (p.screen.scale * p.camera.y * height / 2));
        p.screen.w = Math.round(p.screen.scale * roadWidth * width / 2);
    }

    /**
     * Überprüft, ob zwei Bereiche auf dem Bildschirm überlappen.
     *
     * @param x1      Die X-Koordinate des ersten Bereichs.
     * @param w1      Die Breite des ersten Bereichs.
     * @param x2      Die X-Koordinate des zweiten Bereichs.
     * @param w2      Die Breite des zweiten Bereichs.
     * @param percent Der Prozentsatz zur Anpassung der Überlappungsberechnung (0 bis 1).
     * @return {true}, wenn die Bereiche überlappen, ansonsten {false}.
     */
    public static boolean overlap(double x1, double w1, double x2, double w2, double percent) {
        double half = (percent > 0 ? percent : 1) / 2;
        double min1 = x1 - (w1 * half);
        double max1 = x1 + (w1 * half);
        double min2 = x2 - (w2 * half);
        double max2 = x2 + (w2 * half);

        return !((max1 < min2) || (min1 > max2));
    }

}

/**
 * Eine  Klasse, die einen Punkt im dreidimensionalen Raum repräsentiert.
 */
  class Point {
    /** Die X-Koordinate des Punkts. */
    int x;
    /** Die Y-Koordinate des Punkts. */
    int y;
    /** Die Z-Koordinate des Punkts. */
    int z;

    /** Der Maßstab (Scale) des Punkts. */
    int scale;

    /** Die Kamerakoordinaten des Punkts. */
    public Point camera;

    /** Die Weltkoordinaten des Punkts. */
    public Point world;

    /** Die Bildschirmkoordinaten des Punkts. */
    public Point screen;
}
