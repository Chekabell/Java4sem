import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MP3Player {
    static ArrayList<Playlist> playlists = new ArrayList<>();
    static Playlist playlistNow = null;
    static String buf;
    static int key;
    static boolean repeat = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n");
            if (playlistNow != null) {
                System.out.print("Вы сейчас в: " + playlistNow.getName() + "\n");
                if (repeat) {
                    System.out.print("Трек сейчас на репите\n");
                }
                if (playlistNow.getTrackNow() != null) {
                    System.out.print("Играет трек - " + playlistNow.getTrackNow().getName() + "\n");
                }
            }
            System.out.print(
                    """
                            1 - Выбрать плейлист
                            2 - Включить плейлист
                            3 - Следующий трек
                            4 - Предыдущий трек
                            5 - Поставить\\убрать репит трека
                            6 - Создать плейлист
                            7 - Удалить плейлист
                            8 - Добавить трек в плейлист
                            9 - Удалить трек из плейлиста
                            10 - Показать все треки в плейлисте
                            11 - Показать все плейлисты
                            12 - Загрузить плейлист
                            13 - Сохранить плейлист
                            0 - Выход
                            """);
            System.out.print("Выберите вариант: ");
            key = in.nextInt();
            buf = in.nextLine();
            switch (key) {
                case (1):
                    if (!playlists.isEmpty()) {
                        System.out.print("Введите имя или номер плейлиста: ");
                        buf = in.nextLine();
                        try {
                            if (buf.matches("[-+]?\\d+")) {
                                playlistNow = playlists.get(Integer.parseInt(buf)-1);
                            } else {
                                playlistNow = playlists.get(searchPlaylistName(buf));
                            }
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print("\nИзвините, но такого плейлиста ещё не существует\n");
                        }
                    } else {
                        System.out.print("Плейлистов ещё нет! Добавьте хотя бы один!\n");
                    }
                    break;
                case (2):
                    if(playlistNow != null) {
                        playlistNow.start();
                    } else {
                        System.out.println("Сначала выберите плейлист!\n");
                    }
                    break;
                case (3):
                    if (repeat) {
                        System.out.print("Сначала снимите трек с повтора\n");
                    } else {
                        try {
                            playlistNow.next();
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    break;
                case (4):
                    if (repeat) {
                        System.out.println("Сначала снимите трек с повтора\n");
                    } else {
                        try {
                            playlistNow.prev();
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    break;
                case (5):
                    repeat = !repeat;
                    break;
                case (6):
                    System.out.print("Введите имя плейлиста: ");
                    buf = in.nextLine();
                    playlistNow = createPlaylist(buf);
                    break;
                case (7):
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try {
                        if (buf.matches("[-+]?\\d+")) {
                            deletePlaylist(Integer.parseInt(buf)-1);
                        } else {
                            deletePlaylist(searchPlaylistName(buf));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\nИзвините, но такого плейлиста ещё не существует\n");
                    }
                    break;
                case (8):
                    if(playlistNow != null) {
                        System.out.print("Введите имя трека: ");
                        buf = in.nextLine();
                        playlistNow.addTrack(buf);
                    } else {
                        System.out.println("Вы не находитесь в плейлисте сейчас. Создайте его или загрузите!\n");
                    }
                    break;
                case (9):
                    System.out.print("Введите имя или номер трека: ");
                    buf = in.nextLine();
                    try {
                        if (buf.matches("[-+]?\\d+")) {
                            playlistNow.deleteTrack(Integer.parseInt(buf)-1);
                        } else {
                            playlistNow.deleteTrack(playlistNow.searchTrackName(buf));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\nИзвините, но такого трека в этом плейлисте уже не существует\n");
                    }
                    break;
                case (10):
                    if(playlistNow != null) {
                        playlistNow.showAllTracks();
                    } else {
                        System.out.println("Плейлист не был выбран.");
                    }
                    break;
                case (11):
                    showAllPlaylists();
                    break;
                case (12):
                    System.out.print("Введите имя плейлиста: ");
                    buf = in.nextLine();
                    try {
                        loadPlaylist(buf);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage()+"\n");
                    }
                    break;
                case (13):
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try {
                        if (buf.matches("[-+]?\\d+")) {
                            savePlaylist(Integer.parseInt(buf)-1);
                        } else {
                            savePlaylist(searchPlaylistName(buf));
                        }
                    } catch (RuntimeException e) {
                        System.out.println("\n" + e.getMessage() + "\n");
                    }
                    break;
                case (0):
                    System.exit(0);
                    break;
            }
        }
    }

    static int searchPlaylistName(String keyName) {
        int len = playlists.size();
        for (int i = 0; i < len; i++) {
            if (keyName.equals(playlists.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    static Playlist createPlaylist(String n) {
        Playlist p = new Playlist(n);
        playlists.add(p);
        return p;
    }

    static void deletePlaylist(int i) throws IndexOutOfBoundsException {
        try {
            Playlist playlistToDelete = playlists.get(i);
            playlistToDelete.clear();
            playlists.remove(playlistToDelete);
            if(playlistNow == playlistToDelete){
                playlistNow = null;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    static void showAllPlaylists() {
        if (!playlists.isEmpty()) {
            System.out.println("Список плейлистов: \n");
            for (int i = 1; i <= playlists.size(); i++) {
                System.out.println(i + " : " + playlists.get(i-1).getName());
            }
        } else {
            System.out.println("Ни одного плейлиста ещё нет. Создайте хотя бы один!\n");
        }
    }

    static void loadPlaylist(String s) throws RuntimeException{
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
        System.out.println("Плейлист - " + s + " - успешно загружен.");
    }

    static void savePlaylist(int i) throws RuntimeException{
        Path dirPath =  Path.of(".\\Playlists").toAbsolutePath().normalize();
        Playlist playlistToSafe = playlists.get(i);
        String name = playlistToSafe.getName()+".txt";
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
        LinkedList<Track> arr = playlistToSafe.getListTrack();
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
        System.out.println("Плейлист успешно сохранён.");
    }
}
