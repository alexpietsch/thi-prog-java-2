package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {

		if(o1 == null || o2 == null) {
			throw new RuntimeException("One of the comparables is null!");
		}
		if (!(o1 instanceof TaggedFile) && !(o2 instanceof TaggedFile)) {
			return 0;
		}
		if (!(o1 instanceof TaggedFile) && (o2 instanceof TaggedFile)) {
			return -1;
		}
		if ((o1 instanceof TaggedFile) && !(o2 instanceof TaggedFile)) {
			return 1;
		}

		
		if( ((TaggedFile) o1).getAlbum() == null & ((TaggedFile) o2).getAlbum() == null) {
			throw new RuntimeException("One of the comparables album is null!");
		}
		
		
		return ((TaggedFile) o1).getAlbum().compareTo(((TaggedFile) o2).getAlbum());

	}

}
