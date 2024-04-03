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
	Track getTrackNow() {
		return trackNow;
	}
	void start(){
		trackNow = tracks.getFirst();
	}
	String getName() {
		return name;
	}

	void addTrack(String name) {
		Track t = new Track(name);
		tracks.add(t);
	}
	void deleteTrack(int i) throws IndexOutOfBoundsException {
		try {
			Track trackToDelete = tracks.get(i);
			tracks.remove(trackToDelete);
			if (trackNow == trackToDelete){
				try{
					next();
				} catch (IndexOutOfBoundsException e){
					trackNow = null;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException();
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
			System.out.println("Список песен в плейлисте " + name + ": \n");
			for (int i = 1; i <= tracks.size(); i++) {
				System.out.println(i + " : " + tracks.get(i-1).getName());
			}
		} else {
			System.out.println("Плейлист пуст\n");
		}
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
}
