import java.util.ArrayList;
import java.util.Scanner;

public class MP3Player {
    static ArrayList<Playlist> playlists = new ArrayList<>();
    static Playlist playlistNow = null;
    static String buf;
    static int key;
    static boolean repit = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n");
            if (playlistNow != null) {
                System.out.print("Вы сейчас в: " + playlistNow.getName() + "\n");
                if (repit) {
                    System.out.println("Трек сейчас на репите\n");
                }
                if (playlistNow.getTrackNow() != null) {
                    System.out.print("Играет трек - " + playlistNow.getTrackNow().getName() + "\n");
                }
            }
            System.out.print(
                    """
                            1 - Включить плейлист
                            2 - Следующий трек
                            3 - Предыдущий трек
                            4 - Поставить\\убрать репит трека
                            5 - Создать плейлист
                            6 - Удалить плейлист
                            7 - Добавить песню в плейлист
                            8 - Удалить песню из плейлиста
                            9 - Показать все треки в плейлисте
                            10 - Показать все плейлисты
                            11 - Загрузить плейлист
                            12 - Сохранить плейлист
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
                                playlistNow = playlists.get(Integer.parseInt(buf));
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
                    if (repit) {
                        System.out.print("Сначала снимите трек с повтора\n");
                    } else {
                        try {
                            playlistNow.next();
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    break;
                case (3):
                    if (repit) {
                        System.out.println("Сначала снимите трек с повтора\n");
                    } else {
                        try {
                            playlistNow.prev();
                        } catch (IndexOutOfBoundsException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    break;
                case (4):
                    repit = !repit;
                    break;
                case (5):
                    System.out.print("Введите имя плейлиста: ");
                    buf = in.nextLine();
                    playlistNow = createPlaylist(buf);
                    break;
                case (6):
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try {
                        if (buf.matches("[-+]?\\d+")) {
                            deletePlaylist(Integer.parseInt(buf));
                        } else {
                            deletePlaylist(searchPlaylistName(buf));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\nИзвините, но такого плейлиста ещё не существует\n");
                    }
                    break;
                case (7):
                    System.out.print("Введите имя трека: ");
                    buf = in.nextLine();
                    playlistNow.addTrack(buf);
                    break;
                case (8):
                    System.out.print("Введите имя или номер трека: ");
                    buf = in.nextLine();
                    try {
                        if (buf.matches("[-+]?\\d+")) {
                            playlistNow.deleteTrack(Integer.parseInt(buf));
                        } else {
                            playlistNow.deleteTrack(playlistNow.searchTrackName(buf));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("\nИзвините, но такого трека в этом плейлисте уже не существует\n");
                    }
                    break;
                case (9):
                    playlistNow.showAllTracks();
                    break;
                case (10):
                    showAllPlaylists();
                    break;
                case (11):
                    break;
                case (12):
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
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException();
        }
    }

    static void showAllPlaylists() {
        if (!playlists.isEmpty()) {
            System.out.println("Список плейлистов: \n");
            for (int i = 0; i < playlists.size(); i++) {
                System.out.println(i + " : " + playlists.get(i).getName());
            }
        } else {
            System.out.println("Ни одного плейлиста ещё нет. Создайте хотя бы один!\n");
        }
    }
}
