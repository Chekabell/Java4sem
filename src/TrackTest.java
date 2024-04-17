import static org.junit.jupiter.api.Assertions.*;

class TrackTest{
    @org.junit.jupiter.api.Test
    void testGetTrackNameMethod() {
        Track track = new Track("testName");
        assertEquals("testName",track.getName());
    }
}