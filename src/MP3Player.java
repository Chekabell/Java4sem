import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;

public class MP3Player{
    static ArrayList<Playlist> playlists;
    static Playlist playlistNow;
    static boolean repeat;
    public MP3Player() {
        playlists = new ArrayList<>();
        playlistNow = null;
        repeat = false;
    }
    ArrayList<Playlist> getPlaylistsList(){return playlists;}
    Playlist getPlaylistNow (){return playlistNow;}
    boolean getRepeat(){return repeat;}
    void swapRepeat(){repeat = !repeat;}
    Playlist addPlaylist(String n) {
        Playlist p = new Playlist(n);
        playlists.add(p);
        return p;
    }
    Playlist choosePlaylist(String playlistNameOrIndex) throws RuntimeException{
        if (playlists.isEmpty())
            throw new RuntimeException("List of the playlists is empty!\n");
        else {
            try {
                if (playlistNameOrIndex.matches("[-+]?\\d+")) {
                    playlistNow = playlists.get(Integer.parseInt(playlistNameOrIndex)-1);
                } else {
                    playlistNow = playlists.get(searchIndexPlaylistByName(playlistNameOrIndex));
                }
                return playlistNow;
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("Not find this playlist!");
            }
        }
    }
    void addTrack(String n) throws RuntimeException{
        if(playlistNow == null) throw new RuntimeException("Playlist no choice!");
        playlistNow.addTrack(n);
    }
    void start() throws RuntimeException{
        if (playlistNow == null){
            throw new RuntimeException("Playlist no choice!");
        }
        playlistNow.start();
    }
    void next() throws RuntimeException{
        if(playlistNow==null) {
            throw new RuntimeException("Playlist not choice!");
        }
        if(repeat){
            throw new RuntimeException("Playlist repeat ON!");
        }
        playlistNow.next();
    }
    void prev() throws RuntimeException {
        if (playlistNow == null) {
            throw new RuntimeException("Playlist no choice!");
        }
        if (repeat) {
            throw new RuntimeException("Playlist repeat ON!");
        }
        playlistNow.prev();
    }
    int searchIndexPlaylistByName(String keyName) {
        int len = playlists.size();
        for (int i = 0; i < len; i++) {
            if (keyName.equals(playlists.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }
    Playlist removePlaylist(String playlistNameOrIndex) throws RuntimeException {
        if (playlists.isEmpty())
            throw new RuntimeException("Playlist list is empty!\n");
        else {
            Playlist playlistToDelete;
            try {
                if (playlistNameOrIndex.matches("[-+]?\\d+"))
                    playlistToDelete = playlists.get(Integer.parseInt(playlistNameOrIndex)-1);
                 else
                    playlistToDelete = playlists.get(searchIndexPlaylistByName(playlistNameOrIndex));

                playlistToDelete.clear();
                playlists.remove(playlistToDelete);

                if(playlistNow == playlistToDelete) {
                    playlistNow = null;
                }

                return playlistToDelete;
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("Not find this playlist!");
            }
        }
    }
    void removeTrack(String trackNameOrIndex) throws RuntimeException {
        if (playlistNow.getListTrack().isEmpty())
            throw new RuntimeException("Playlist is empty!\n");
        else {
            try {
                if (trackNameOrIndex.matches("[-+]?\\d+"))
                    playlistNow.deleteTrack(Integer.parseInt(trackNameOrIndex)-1);
                else
                    playlistNow.deleteTrack(playlistNow.searchTrackName(trackNameOrIndex));
            } catch (IndexOutOfBoundsException e) {
                throw new RuntimeException("Not find this track in this playlist!");
            }
        }
    }
    void showAllTracks() throws RuntimeException{
        playlistNow.showAllTracks();
    }
    void showAllPlaylists() throws RuntimeException{
        if (playlists.isEmpty())
            throw new RuntimeException("Playlist is empty!");
        else {
            System.out.println("Список плейлистов:");
            int len = playlists.size();
            for (int i = 1; i <= len; i++) {
                System.out.println(i + " : " + playlists.get(i-1).getName());
            }
        }
    }
    boolean loadPlaylist(String s) throws RuntimeException{
        String name = s + ".txt";
        Path filePath = Path.of(".\\Playlists",name).toAbsolutePath().normalize();
        Playlist pl = new Playlist(s);
        BufferedReader rd;
        int len;
        try {
            rd = Files.newBufferedReader(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть буфер чтения.");
        }
        try{
            len = Integer.parseInt(rd.readLine());
        } catch (IOException e){
            throw new RuntimeException("Не удалось считать размер плейлиста.");
        }
        for (int i = 0; i < len; i++) {
            try {
                pl.addTrack(rd.readLine());
            } catch (IOException e) {
                throw new RuntimeException("Не удалось считать название трека.");
            }
        }
        try {
            rd.close();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось закрыть буфер чтения.");
        }
        playlists.add(pl);
        return true;
    }
    boolean savePlaylist(String buf) throws RuntimeException{
        int i;
        if (buf.matches("[-+]?\\d+"))
            i = (Integer.parseInt(buf)-1);
        else
            i = searchIndexPlaylistByName(buf);
        Path dirPath =  Path.of(".\\Playlists").toAbsolutePath().normalize();
        Playlist playlistToSave = playlists.get(i);
        String name = playlistToSave.getName()+".txt";
        Path filePath = Path.of(".\\Playlists",name).toAbsolutePath().normalize();
        if (Files.notExists(dirPath)){
            try {
                Files.createDirectory(dirPath);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать директорию по указанному пути.");
            }
        }
        try{
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось удалить файл для перезаписи.");
        }
        LinkedList<Track> arr = playlistToSave.getListTrack();
        BufferedWriter wr;
        try {
            wr = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть буфер записи.");
        }
        try{
            wr.write(String.valueOf(arr.size()),0,String.valueOf(arr.size()).length());
            wr.newLine();
        } catch(IOException e){
            throw new RuntimeException("Не удалось записать количество треков в плейлисте");
        }

        for (Track track : arr) {
            try {
                wr.write(track.getName(), 0, track.getName().length());
                wr.newLine();
            } catch (IOException e) {
                throw new RuntimeException("Не удалось записать название трека.");
            }
        }
        try {
            wr.close();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось закрыть буфер записи.");
        }
        return true;
    }
}
