package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {
		if (!(o1 instanceof SampledFile) || !(o2 instanceof SampledFile)) {
			return -1;
//			throw new RuntimeException("Can only compare TaggedFile for duration");
		}
		if(((SampledFile) o1).getDuration() < ((SampledFile) o2).getDuration()) {
			return -1;
		}
		
		if(((SampledFile) o1).getDuration() == ((SampledFile) o2).getDuration()) {
			return 0;
		}
		
		if(((SampledFile) o1).getDuration() > ((SampledFile) o2).getDuration()) {
			return 1;
		}
		
		return 0;
	}

}
