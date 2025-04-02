package studiplayer.audio;

import java.util.Arrays;
import java.util.List;

public class TestIter {

	public static void main(String[] args) throws NotPlayableException {
		List<AudioFile> files = Arrays.asList(
			new TaggedFile("audiofiles/Rock 812.mp3"),
			new TaggedFile("audiofiles/Eisbach Deep Snow.ogg"),
			new TaggedFile("audiofiles/wellenmeister_awakening.ogg")
		);
		ControllablePlayListIterator it = new ControllablePlayListIterator(files);
		while(it.hasNext()) {
			System.out.println(it.next());
		}

	}

}
