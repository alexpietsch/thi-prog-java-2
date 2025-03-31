import java.util.Map;

import studiplayer.basic.TagReader;

public class TaggedFile extends SampledFile {
	protected String album;
	
	public TaggedFile() {
		super();
		this.readAndStoreTags();
	}
	
	public TaggedFile(String path) {
		super(path);
		this.readAndStoreTags();
	}
	
	public void setAlbum(String album) {
		this.album = album; 
	}
	
	public String getAlbum() {
		return this.album;
	}
	
	public void readAndStoreTags() {
		Map<String, Object> tagMap = TagReader.readTags(this.getPathname());
		Object titleTag = tagMap.get("title");
		Object authorTag = tagMap.get("author");
		Object albumTag = tagMap.get("album");
		Object durationTag = tagMap.get("duration");
	
		
		if(titleTag != null) {
			this.setTitle(titleTag.toString().strip());
		}
		
		if(authorTag != null) {
			this.setAuthor(authorTag.toString().strip());
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
