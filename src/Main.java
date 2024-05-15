import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String buf;
        int key;
        MP3Player player = new MP3Player();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("\n");
            if (player.getPlaylistNow() != null) {
                System.out.print("Вы сейчас в: " + player.getPlaylistNow().getName() + "\n");
                if (player.getRepeat()) {
                    System.out.print("Трек сейчас на репите\n");
                }
                if (player.getPlaylistNow().getTrackNow() != null) {
                    System.out.print("Играет трек - " + player.getPlaylistNow().getTrackNow().getName() + "\n");
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
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try{
                        player.choosePlaylist(buf);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case (2):
                    try{
                        player.start();
                    } catch(RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case (3):
                    try {
                        player.next();
                    } catch (RuntimeException e) {
                        System.out.print(e.getMessage());
                    }
                    break;
                case (4):
                    try {
                        player.prev();
                    } catch (RuntimeException e) {
                        System.out.print(e.getMessage());
                    }
                    break;
                case (5):
                    player.swapRepeat();
                    break;
                case (6):
                    System.out.print("Введите имя плейлиста: ");
                    buf = in.nextLine();
                    try {
                        player.addPlaylist(buf);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case (7):
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try {
                        player.removePlaylist(buf);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case (8):
                    System.out.print("Введите имя трека: ");
                    buf = in.nextLine();
                    try{
                        player.addTrack(buf);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case (9):
                    System.out.print("Введите имя или номер трека: ");
                    buf = in.nextLine();
                    try {
                        player.removeTrack(buf);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case (10):
                    try{
                        player.showAllTracks();
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case (11):
                    try {
                        player.showAllPlaylists();
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case (12):
                    System.out.print("Введите имя плейлиста: ");
                    buf = in.nextLine();
                    try {
                        player.loadPlaylist(buf);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case (13):
                    System.out.print("Введите имя или номер плейлиста: ");
                    buf = in.nextLine();
                    try {
                        player.savePlaylist(buf);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case (0):
                    System.exit(0);
                    break;
            }
        }
    }
}
