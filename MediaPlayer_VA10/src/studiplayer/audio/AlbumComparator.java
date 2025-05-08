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

		TaggedFile tfO1 = (TaggedFile) o1;
		TaggedFile tfO2 = (TaggedFile) o2;
		
		
		if (tfO1.getAlbum() == null && tfO2.getAlbum() == null) {
			return 0;
		}
		if (tfO1.getAlbum() == null && tfO2.getAlbum() != null) {
			return -1;
		}
		if (tfO1.getAlbum() != null && tfO2.getAlbum() == null) {
			return 1;
		}
		
		return tfO1.getAlbum().compareTo(tfO2.getAlbum());

	}

}
