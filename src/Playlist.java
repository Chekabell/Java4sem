import java.util.LinkedList;

public class Playlist {
	private final LinkedList<Track> tracks;
	private final String name;
	private static Track trackNow;
	public Playlist(String n) {
		tracks = new LinkedList<>();
		name = n;
		trackNow = null;
	}
	LinkedList<Track> getListTrack(){ return tracks;}
	String getName() {return name;}
	Track getTrackNow() {
		return trackNow;
	}
	void addTrack(String name) {
		Track t = new Track(name);
		tracks.add(t);
	}
	int searchTrackName(String keyName) {
		int len = tracks.size();
		for (int i = 0; i < len; i++) {
			if (keyName.equals(tracks.get(i).getName())) {
				return i;
			}
		}
		return -1;
	}
	Track deleteTrack(int i) throws IndexOutOfBoundsException {
		try {
			Track trackToDelete = tracks.get(i);
			tracks.remove(trackToDelete);
			if (trackNow == trackToDelete) {
				try {
					next();
				} catch (IndexOutOfBoundsException e) {
					trackNow = null;
				}
			}
			return trackToDelete;
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
		}
	}
	void start() throws RuntimeException{
		if (!tracks.isEmpty()) {
			trackNow = tracks.getFirst();
		}
		else{
			throw new RuntimeException ("Playlist is empty!");
		}
	}
	void next() throws IndexOutOfBoundsException {
		if (tracks.size() <= 1) {
			throw new IndexOutOfBoundsException(
					"\nВ данном плейлисте слишком мало треков!\nДобавьте ещё! Нам интересно узнать, что вы слушаете");
		}
		int i = tracks.indexOf(trackNow);
		if (trackNow == tracks.getLast()) {
			trackNow = tracks.getFirst();
		} else {
			trackNow = tracks.get(i + 1);
		}
	}
	void prev() throws IndexOutOfBoundsException {
		if (tracks.size() <= 1) {
			throw new IndexOutOfBoundsException(
					"\nВ данном плейлисте слишком мало треков!\nДобавьте ещё! Нам интересно узнать, что вы слушаете");
		}
		int i = tracks.indexOf(trackNow);
		if (trackNow == tracks.getFirst()) {
			trackNow = tracks.getLast();
		} else {
			trackNow = tracks.get(i - 1);
		}
	}
	void clear() {
		tracks.clear();
	}
	void showAllTracks() {
		if (!tracks.isEmpty()) {
			System.out.print("Список песен в плейлисте " + name + ":\n");
			for (int i = 1; i <= tracks.size(); i++) {
				System.out.print(i + " : " + tracks.get(i-1).getName());
			}
		} else {
			System.out.println("Плейлист пуст\n");
		}
	}
}
