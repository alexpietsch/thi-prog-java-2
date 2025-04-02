package studiplayer.audio;
import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
	protected String album;
	
	public TaggedFile() throws NotPlayableException {
		super();
		this.readAndStoreTags();
	}
	
	public TaggedFile(String path) throws NotPlayableException {
		super(path);
		this.readAndStoreTags();
	}
	
	public void setAlbum(String album) {
		this.album = album; 
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public void readAndStoreTags() throws NotPlayableException {
		Map<String, Object> tagMap = Map.of();
		try {
			TagReader.readTags(this.getPathname());
		} catch (Exception e) {
			throw new NotPlayableException(this.getPathname(), "Could not get tags.", e);
		}
		Object titleTag = tagMap.get("title");
		Object authorTag = tagMap.get("author");
		Object albumTag = tagMap.get("album");
		Object durationTag = tagMap.get("duration");
	
		
		if(titleTag != null) {
			this.title = titleTag.toString().strip();
		}
		
		if(authorTag != null) {
			this.author = authorTag.toString().strip();
		}
		
		if(albumTag != null) {
			this.setAlbum(albumTag.toString().strip());
		}
		
		if(durationTag != null) {
			long durationParsed = Long.parseLong(durationTag.toString().strip());
			this.setDuration(durationParsed);
		}
	}
	
	@Override
	public String toString() {
		if(this.getAlbum() != null) {
			return String.format("%s - %s - %s", super.toString(), this.getAlbum(), this.formatDuration());
		}
		return String.format("%s - %s", super.toString(), this.formatDuration());
	}

}
