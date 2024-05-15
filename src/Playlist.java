import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Playlist {
	private final LinkedList<Track> tracks;
	private final String name;
	private static Track trackNow;
	public Playlist(String n) {
		tracks = new LinkedList<>();
		name = n;
		trackNow = null;
	}
	LinkedList<Track> getListTrack(){return tracks;}
	String getName() {return name;}
	Track getTrackNow() {return trackNow;}
	Track addTrack(String name){
		Track t = new Track(name);
		tracks.add(t);
		return t;
	}
	int searchTrackName(String keyName) throws IndexOutOfBoundsException {
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
			if (trackNow == trackToDelete)
					next();
			return trackToDelete;
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException("Not find this track in this playlist!");
		}
	}
	void start() throws RuntimeException{
		if(tracks.isEmpty())
			throw new RuntimeException ("Playlist is empty!");
		else
			trackNow = tracks.getFirst();
	}
	void next(){
		int i = tracks.indexOf(trackNow);

		if (trackNow == tracks.getLast())
			trackNow = tracks.getFirst();
		else
			trackNow = tracks.get(i + 1);
	}
	void prev(){
		int i = tracks.indexOf(trackNow);

		if (trackNow == tracks.getFirst())
			trackNow = tracks.getLast();
		else
			trackNow = tracks.get(i - 1);
	}
	void clear() {
		tracks.clear();
	}
	void showAllTracks() throws RuntimeException {
		if (!tracks.isEmpty()) {
			System.out.println("Список песен в плейлисте " + name + ":");
			for (int i = 1; i <= tracks.size(); i++) {
				System.out.println(i + " : " + tracks.get(i-1).getName());
			}
		} else {
			throw new RuntimeException("Playlist is empty!");
		}
	}

}
