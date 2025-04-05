package studiplayer.audio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList implements Iterable<AudioFile> {
	private LinkedList<AudioFile> audioFiles;
	private String search;
	private SortCriterion sortCriterion = SortCriterion.DEFAULT;
	private ControllablePlayListIterator playlistIterator;
	private AudioFile currentAudioFile = null;
	
	public PlayList() {
		this.audioFiles = new LinkedList<AudioFile>();
		this.playlistIterator = null;
		this.currentAudioFile = null;
	}
	public PlayList(String m3uPathname) {
		this.audioFiles = new LinkedList<AudioFile>();
		this.playlistIterator = null;
		this.currentAudioFile = null;
		this.loadFromM3U(m3uPathname);
	}
	
	public List<AudioFile> getList() {
		return this.audioFiles;
	}
	
	public Iterator<AudioFile> getIterator(){
		return this.playlistIterator;
	}

	// public void setCurrent(int idx) {
	// 	this.current = idx;
	// }
	
	// public int getCurrent() {
	// 	return this.current;
	// }
	
	public void setSearch(String pSearch) {
		this.search = pSearch;
		this.playlistIterator = this.iterator();
		this.currentAudioFile = this.playlistIterator.next();
	}
	
	public String getSearch() {
		return this.search;
	}
	
	public void setSortCriterion(SortCriterion pSortCriterion) {
		this.sortCriterion = pSortCriterion;
		this.playlistIterator = this.iterator();
		this.currentAudioFile = this.playlistIterator.next();
	}
	
	public SortCriterion getSortCriterion() {
		return this.sortCriterion;
	}
	
	public void add(AudioFile file) {
		this.audioFiles.add(file);
		this.playlistIterator = this.iterator();
		this.currentAudioFile = this.playlistIterator.next();
	}
	
	public void remove(AudioFile file) {
		this.audioFiles.remove(file);
		this.playlistIterator = this.iterator();
		this.currentAudioFile = this.playlistIterator.next();
	}
	
	public int size() {
		return this.audioFiles.size();
	}
	
	public AudioFile currentAudioFile() {
		return this.currentAudioFile;
	}
	
	public void nextSong() {
		if(this.playlistIterator == null ) {
			this.playlistIterator = this.iterator();
		}
		if(this.audioFiles.size() == 0) {
			this.currentAudioFile = null;
			return;
		}
		if(this.playlistIterator.hasNext()) {
			this.currentAudioFile = this.playlistIterator.next();
		} else {
				this.playlistIterator = this.iterator();
			if (this.playlistIterator.hasNext()) {
				this.currentAudioFile = this.playlistIterator.next();
			} else {
				this.currentAudioFile = null;
			}
		}
	}
	
	public void saveAsM3U(String pathname) {
		FileWriter writer = null;
		String lineSep = System.getProperty("line.separator");
		
		try {
			writer = new FileWriter(pathname);
			for (AudioFile audioFile : this.getList()) {
				String audioFilePath = audioFile.getPathname();
				writer.write(audioFilePath + lineSep);
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to write file " + pathname + "!");
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				System.err.println("Could not close writer: " + e.getMessage());
			}
		}
	}
	
	public void loadFromM3U(String path) {
		List<String> filePaths = new ArrayList<>();
		Scanner scanner = null;
		
		this.audioFiles.clear();
		
		try {
			scanner = new Scanner(new File(path));	
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if(!nextLine.startsWith("#") && !(nextLine.strip().length() < 1)) {
					filePaths.add(nextLine);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				scanner.close();	
			} catch (Exception e) {
				System.err.println("Could not close scanner: " + e.getMessage());
			}
		}
		
		for(String audioFilePath : filePaths) {
			try {
				AudioFile audioFile = AudioFileFactory.createAudioFile(audioFilePath);
				this.audioFiles.add(audioFile);
			} catch (Exception e) {
				System.err.println("Could not add file: " + e.getMessage());
			}
		}

		if(!this.audioFiles.isEmpty()) {
			this.playlistIterator = this.iterator();
			this.currentAudioFile = this.playlistIterator.next();
		}
	}

	public AudioFile jumpToAudioFile(AudioFile file) {
		if(!this.audioFiles.contains(file)) {
			return null;
		}
		
		if(this.playlistIterator == null ) {
			this.playlistIterator = this.iterator();
		}
		
		this.currentAudioFile = this.playlistIterator.jumpToAudioFile(file);
		return file;
	}
	
	@Override
	public ControllablePlayListIterator iterator() {
		return new ControllablePlayListIterator(this.audioFiles, this.search, this.sortCriterion);
	}
}
