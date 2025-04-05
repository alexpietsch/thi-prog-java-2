package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

	@Override
	public int compare(AudioFile o1, AudioFile o2) {

		if(o1 == null || o2 == null) {
			throw new RuntimeException("One of the comparables is null!");
		}
		if (!(o1 instanceof SampledFile) && !(o2 instanceof SampledFile)) {
			return 0;
		}
		if (!(o1 instanceof SampledFile) && (o2 instanceof SampledFile)) {
			return -1;
		}
		if ((o1 instanceof SampledFile) && !(o2 instanceof SampledFile)) {
			return 1;
		}

		SampledFile sfO1 = (SampledFile) o1;
		SampledFile sfO2 = (SampledFile) o2;

		return Long.compare(sfO1.getDuration(), sfO2.getDuration());

		// if(((SampledFile) o1).getDuration() < ((SampledFile) o2).getDuration()) {
		// 	return -1;
		// }
		
		// if(((SampledFile) o1).getDuration() == ((SampledFile) o2).getDuration()) {
		// 	return 0;
		// }
		
		// if(((SampledFile) o1).getDuration() > ((SampledFile) o2).getDuration()) {
		// 	return 1;
		// }
		
		// return 0;
	}

}
