package studiplayer.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ControllablePlayListIterator implements Iterator<AudioFile> {
	PlayList playList = new PlayList();

	public ControllablePlayListIterator(List<AudioFile> audioFiles) {
		for(AudioFile file : audioFiles) {
			playList.add(file);
		}
	}
	
	public boolean hasNext() {
		return playList.getCurrent() < playList.size();
	}

	public AudioFile next() {
		AudioFile currentAudioFile = playList.currentAudioFile();
		playList.setCurrent(playList.getCurrent() + 1);
		return currentAudioFile;
	}

	public AudioFile jumpToAudioFile(AudioFile file) {
		ArrayList<AudioFile> arrayList = new ArrayList<AudioFile>();
		arrayList.addAll(playList.getList());
		if(!arrayList.contains(file)) {
			return null;
		}
		playList.setCurrent(arrayList.indexOf(file) +1 );
		return file;
	}
	
}
