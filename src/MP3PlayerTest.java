import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MP3PlayerTest {
    private static final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
    }

    @Test
    void testAddPlaylist() {
        MP3Player player = new MP3Player();
        assertEquals(player.addPlaylist("test"), player.getPlaylistsList().getFirst());
    }
    @Test
    void testChoosePlaylist(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        assertEquals(player.choosePlaylist("test"), player.getPlaylistNow());
    }
    @Test
    void testChoosePlaylistIfEmpty(){
        MP3Player player = new MP3Player();
        assertThrows(RuntimeException.class,() -> player.choosePlaylist("test"));
    }
    @Test
    void testChoosePlaylistIfPlaylistNotExist(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        assertThrows(RuntimeException.class,() -> player.choosePlaylist("test2"));
    }
    @Test
    void testAddTrack(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        assertThrows(RuntimeException.class, () -> player.addTrack ("test"));
    }
    @Test
    void testStartIfEmpty(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertThrows(RuntimeException.class, player::start);
    }
    @Test
    void testNextIfEmpty(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertThrows(RuntimeException.class, player::start);
    }
    @Test
    void testPrevIfEmpty(){
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertThrows(RuntimeException.class, player::next);
    }
    @Test
    void testSearchPlaylistName() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertEquals(0,player.searchIndexPlaylistByName("test"));
    }
    @Test
    void testSearchPlaylistNameIfNotExist() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertEquals(-1,player.searchIndexPlaylistByName("test2"));
    }
    @Test
    void testRemovePlaylist() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        Playlist playlist = player.getPlaylistsList().getFirst();
        assertEquals(playlist,player.removePlaylist("test"));
    }
    @Test
    void testRemovePlaylistIfEmpty() {
        MP3Player player = new MP3Player();
        assertThrows(RuntimeException.class,() -> player.removePlaylist("2"));
    }
    @Test
    void testRemovePlaylistIfThisPlaylistNotExist() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        assertThrows(RuntimeException.class,() -> player.removePlaylist("test2"));
    }
    @Test
    void testRemoveTrackIfEmpty() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertThrows(RuntimeException.class,() -> player.removeTrack("test2"));
    }
    @Test
    void testRemoveTrackIfThisTrackNotExist() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        player.addTrack("test");
        assertThrows(RuntimeException.class,() -> player.removeTrack("test2"));
    }
    @Test
    void testShowAllPlaylists() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.showAllPlaylists();
        assertEquals("Список плейлистов:\r\n1 : test\r\n", output.toString());
    }
    @Test
    void testShowAllPlaylistsIfEmpty() {
        MP3Player player = new MP3Player();
        assertThrows(RuntimeException.class ,player::showAllPlaylists);
    }
    @Test
    void testSavePlaylist() {
        MP3Player player = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        player.addTrack("test");
        player.savePlaylist("test");
        MP3Player player2 = new MP3Player();
        player2.loadPlaylist("test");
        player2.choosePlaylist("test");
        assertEquals(player.getPlaylistNow().getName(), player2.getPlaylistNow().getName());
    }
    @Test
    void testLoadPlaylist() {
        MP3Player player = new MP3Player();
        player.loadPlaylist("test");
        player.choosePlaylist("test");
        MP3Player player2 = new MP3Player();
        player.addPlaylist("test");
        player.choosePlaylist("test");
        assertEquals(player.getPlaylistNow().getName(), player2.getPlaylistNow().getName());
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }

}