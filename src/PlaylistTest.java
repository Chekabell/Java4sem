import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    private static final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }
    @Test
    void testGetPlaylistListTrack() {
        Playlist playlist = new Playlist("testPlaylist");
        LinkedList<Track> expectedList = new LinkedList<>();
        assertIterableEquals(expectedList, playlist.getListTrack());
    }
    @Test
    void testGetTrackNow() {
        Playlist playlist = new Playlist ("testPlaylist");
        assertNull(playlist.getTrackNow());
    }
    @Test
    void testGetName() {
        Playlist playlist = new Playlist("testPlaylist");
        assertEquals("testPlaylist",playlist.getName());
    }
    @Test
    void testAddTrack() {
        Playlist playlist = new Playlist("testPlaylist");
        assertEquals(playlist.addTrack("testName"),playlist.getListTrack().getFirst());
    }
    @Test
    void testSearchTrackName() {
        Playlist playlist = new Playlist("testPlaylist");
        playlist.addTrack("test");
        assertEquals(0,playlist.searchTrackName("test"));
    }
    @Test
    void testSearchTrackNameIfNotExist() {
        Playlist playlist = new Playlist("testPlaylist");
        playlist.addTrack("test1");
        assertEquals(-1,playlist.searchTrackName("test"));
    }
    @Test
    void testDeleteTrack() {
        Playlist playlist = new Playlist("testPlaylist");
        Track track = new Track("testName");
        playlist.getListTrack().add(track);
        assertEquals(track,playlist.deleteTrack(0));
    }
    @Test
    void testStartPlaylistMethodIfEmpty() {
        Playlist playlist = new Playlist("testPlaylist");
        assertThrows(RuntimeException.class, playlist::start);
    }
    @Test
    void testStartPlaylistMethod() {
        Playlist playlist = new Playlist("testPlaylist");
        playlist.addTrack("test");
        playlist.start();
        assertEquals("test",playlist.getTrackNow().getName());
    }
    @Test
    void testNext() {
        Playlist playlist = new Playlist("testPlaylist");
        playlist.addTrack("track1");
        playlist.addTrack("track2");
        playlist.start();
        playlist.next();
        assertEquals("track2",playlist.getTrackNow().getName());
    }
    @Test
    void testPrev() {
        Playlist playlist = new Playlist("testPlaylist");
        playlist.addTrack("track1");
        playlist.addTrack("track2");
        playlist.start();
        playlist.prev();
        assertEquals("track2",playlist.getTrackNow().getName());
    }
    @Test
    void showAllTracks() {
        Playlist playlist = new Playlist("TEST");
        playlist.addTrack("test");
        playlist.showAllTracks();
        assertEquals("Список песен в плейлисте TEST:\r\n1 : test\r\n", output.toString());
    }
    @Test
    void showAllTracksIfEmpty() {
        Playlist playlist = new Playlist("TEST");
        assertThrows(RuntimeException.class,playlist::showAllTracks);
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }
}