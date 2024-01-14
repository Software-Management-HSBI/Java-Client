import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoadTest {

    public RoadTest(){
        Game.segments = new ArrayList<>();
    }

    @Test
    public void testAddSegment() {
        // Arrange
        int initialSegments = Game.segments.size();

        // Act
        Road.addSegment(10.0, 0.0);

        // Assert
        assertEquals(initialSegments + 1, Game.segments.size());
        assertNotNull(Game.segments.get(initialSegments));
    }



    @Test
    public void testFindSegment() {
        // Arrange
        Road.addSegment(10.0, 0.0);
        double positionZ = 15.0;

        // Act
        Road.Segment foundSegment = Road.findSegment(positionZ);

        // Assert
        assertNotNull(foundSegment);
        assertEquals(0, foundSegment.index);
    }

    @Test
    public void testAddRoad() {
        // Arrange
        int initialSegments = Game.segments.size();

        // Act
        Road.addRoad(2, 2, 2, 5, 0.5);

        // Assert
        assertEquals(initialSegments + 6, Game.segments.size());
    }

    @Test
    void testRoadConstants() {
        assertEquals(0, Road.ROAD.LENGTH.NONE, "Road Length NONE should be 0");
        assertEquals(25, Road.ROAD.LENGTH.SHORT, "Road Length SHORT should be 25");
        assertEquals(50, Road.ROAD.LENGTH.MEDIUM, "Road Length MEDIUM should be 50");
        assertEquals(100, Road.ROAD.LENGTH.LONG, "Road Length LONG should be 100");

        assertEquals(0, Road.ROAD.CURVE.NONE, "Curve NONE should be 0");
        assertEquals(2, Road.ROAD.CURVE.EASY, "Curve EASY should be 2");
        assertEquals(4, Road.ROAD.CURVE.MEDIUM, "Curve MEDIUM should be 4");
        assertEquals(6, Road.ROAD.CURVE.HARD, "Curve HARD should be 6");

        assertEquals(0, Road.ROAD.HILL.NONE, "Hill NONE should be 0");
        assertEquals(20, Road.ROAD.HILL.LOW, "Hill LOW should be 20");
        assertEquals(40, Road.ROAD.HILL.MEDIUM, "Hill MEDIUM should be 40");
        assertEquals(60, Road.ROAD.HILL.HIGH, "Hill HIGH should be 60");
    }



}
