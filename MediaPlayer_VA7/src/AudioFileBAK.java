import java.io.File;

public abstract class AudioFileBAK {
	public abstract void play();
	public abstract void togglePause();
	public abstract void stop();
	public abstract String formatDuration();
	public abstract String formatPosition();
	
	private String pathname;
	private String filename;
	protected String title;
	protected String author;
	private char osSeparator = this.isWindows()
	? '\\'
	: '/';
	
	public AudioFileBAK() {
		this("");
	}	
	
	public AudioFileBAK(String path) {
		this.parsePathname(path);
		File file = new File(this.getPathname());
		if(!file.canRead()) {
			throw new RuntimeException("Cannot read file!");
		}
	}
	
	public String getPathname() {
		return this.pathname;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public void setPathname(String path) {
		this.pathname = path;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setTitle(String title) {
		this.title = title; 
	}
	
	public void setAuthor(String author) {
		this.author = author; 
	}
	
	public void parsePathname(String path) {
		String drive = "";
		String workingPath = path.strip();
		
		if(workingPath.length() == 0) {
			this.setPathname(workingPath);
			this.setFilename(workingPath);
			return;
		}
		
		char firstChar = workingPath.charAt(0);
		
		if(Character.isLetter(firstChar) && workingPath.charAt(1) == ':') {
			if(isWindows()) {
				drive = String.format("%s:", firstChar);
			} else {
				drive = String.format("/%s", firstChar);
			}
			workingPath = drive + workingPath.substring(2);
		}
		
		if(isWindows() && workingPath.contains("/")) {
			workingPath = workingPath.replace('/', osSeparator);
		}
		
		if(!isWindows() && workingPath.contains("\\")) {
			workingPath = workingPath.replace('\\', osSeparator);
		}
		
	
		boolean lastCharWasSep = false;
		StringBuilder workingStringBuilder = new StringBuilder();
		char[] chars = workingPath.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == osSeparator) {
				if(lastCharWasSep) {
					continue;
				}
				lastCharWasSep = true;
			} else {
				lastCharWasSep = false;
			}
			workingStringBuilder.append(chars[i]);
		}
		workingPath = workingStringBuilder.toString();
		
		this.setPathname(workingPath);
		
		if(workingPath.contains(String.valueOf(osSeparator))) {
			int lastSepIdx = 0;
			for(int i = 0; i < workingPath.length(); i++) {
				if(workingPath.charAt(i) == osSeparator) {
					lastSepIdx = i;
				}
			}
			this.setFilename(workingPath.substring(lastSepIdx + 1));
		} else {
			this.setFilename(workingPath);
		}
	}
	
	public void parseFilename(String filename) {
		
	}
	
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase()
		.indexOf("win") >= 0;
	}
	
	public String toString() {
		return null;
	}
	
}
