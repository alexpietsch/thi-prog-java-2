package studiplayer.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ControllablePlayListIterator implements Iterator<AudioFile> {
	private List<AudioFile> audioFiles = new ArrayList<AudioFile>();
	private int pos;

	public ControllablePlayListIterator(List<AudioFile> audioFiles) {
		this.audioFiles = new ArrayList<AudioFile>(audioFiles);
		this.pos = 0;
	}
	
	public ControllablePlayListIterator(List<AudioFile> pAudioFiles, String pSearch, SortCriterion pSortCriterion) {
		List<AudioFile> tmpAudioFiles = new ArrayList<AudioFile>();
		
		if(pSearch != null && !pSearch.isEmpty()) {
			for(AudioFile file : pAudioFiles) {
				if(file != null && file.toString().contains(pSearch)) {
					tmpAudioFiles.add(file);
				}
			}
		} else {
			tmpAudioFiles.addAll(pAudioFiles);
		}
		
		switch (pSortCriterion) {
			case ALBUM: {
				tmpAudioFiles.sort(new AlbumComparator());
				break;
			}
			case AUTHOR: {
				tmpAudioFiles.sort(new AuthorComparator());
				break;
			}
			case DURATION: {
				tmpAudioFiles.sort(new DurationComparator());
				break;
			}
			case TITLE: {
				tmpAudioFiles.sort(new TitleComparator());
				break;
			}
			case DEFAULT: {
				break;
			}
			default: {
				throw new IllegalArgumentException("Unexpected value: " + pSortCriterion);
			}
		}
		
		for(AudioFile file : tmpAudioFiles) {
			this.audioFiles.add(file);
		}
		this.pos = 0;
	}
	
	public boolean hasNext() {
		return this.pos < this.audioFiles.size();
	}

	public AudioFile next() {
		if(this.audioFiles.size() < 1 || (pos + 1 > this.audioFiles.size())) {
			return null;
		}
		if(!hasNext()) {
			pos = 0;
		}
		AudioFile currentAudioFile = audioFiles.get(pos);
		pos++;
		return currentAudioFile;
	}

	public AudioFile jumpToAudioFile(AudioFile file) {
		int targetIdx = audioFiles.indexOf(file);
		if(targetIdx < 0) {
			return null;
		}
		this.pos = targetIdx + 1; 
		return file;
	}
	
}
