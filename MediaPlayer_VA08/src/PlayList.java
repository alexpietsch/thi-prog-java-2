import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class PlayList {
	private LinkedList<AudioFile> audioFiles;
	private int current;
	
	public PlayList() {
		this.audioFiles = new LinkedList<AudioFile>();
		this.current = 0;
	}
	public PlayList(String m3uPathname) {
		this.audioFiles = new LinkedList<AudioFile>();
		this.current = 0;
		this.loadFromM3U(m3uPathname);
	}
	
	public List<AudioFile> getList() {
		return this.audioFiles;
	}
	
	public void setCurrent(int idx) {
		this.current = idx;
	}
	
	public int getCurrent() {
		return this.current;
	}
	
	public void add(AudioFile file) {
		this.audioFiles.add(file);
	}
	
	public void remove(AudioFile file) {
		this.audioFiles.remove(file);
	}
	
	public int size() {
		return this.audioFiles.size();
	}
	
	public AudioFile currentAudioFile() {
		AudioFile currentFile;
		try {
			currentFile = this.audioFiles.get(this.current);
		} catch (IndexOutOfBoundsException e) {
			System.err.println(String.format("Index %d out of bounds for audioFiles list", this.current));
			return null;
		}
		return currentFile;
	}
	
	public void nextSong() {
		if(this.current >= this.audioFiles.size() || this.current < 0) {
			this.current = 0;
			return;
		}
		this.current = this.current + 1 > this.audioFiles.size() - 1
				? 0
				: this.current + 1;
		
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
		this.current = 0;
		
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
			AudioFile audioFile = AudioFileFactory.createAudioFile(audioFilePath);
			this.audioFiles.add(audioFile);
		}
	}
}
