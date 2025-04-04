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
	
	public ControllablePlayListIterator(List<AudioFile> pAudioFiles, SortCriterion pSortCriterion, String pSearch) {
		List<AudioFile> audioFiles = pAudioFiles;
		
		
		
		if(pSearch == null || pSearch.isEmpty()) {
			for(AudioFile file : pAudioFiles) {
				audioFiles.add(file);
//				pSortCriterion = SortCriterion.DEFAULT;
			}
		}
		
		if(pSearch != null && pSearch.length() > 0) {
			for(AudioFile file : audioFiles) {
				if(file instanceof TaggedFile) {
					if( ((TaggedFile) file).getAlbum().contains(pSearch) ) {
						audioFiles.add(file);
//						pSortCriterion = SortCriterion.ALBUM;
					}
				}
				
				if(file.getAuthor().contains(pSearch)) {
					audioFiles.add(file);
//					pSortCriterion = SortCriterion.AUTHOR;
				}
						
				if( file.getTitle().contains(pSearch)) {
					audioFiles.add(file);
//					pSortCriterion = SortCriterion.TITLE;
				}
			}
		}
		switch (pSortCriterion) {
		case ALBUM: {
			audioFiles.sort(new AlbumComparator());
			break;
		}
		case AUTHOR: {
			audioFiles.sort(new AuthorComparator());
			break;
		}
		case DURATION: {
			audioFiles.sort(new DurationComparator());
			break;
		}
		case TITLE: {
			audioFiles.sort(new TitleComparator());
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pSortCriterion);
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
