package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		
		if( o1.getAuthor() == null || o2.getAuthor() == null) {
			throw new RuntimeException("One of the comparables is null!");
		}
		
		return o1.getAuthor().compareTo(o2.getAuthor());
	}
	
	

}
