
public abstract class AudioFile {
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
	
	public AudioFile() {

	}
	
	public AudioFile(String path) {
		this.parsePathname(path);
		this.parseFilename(this.getFilename());
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
	
	public void parsePathname(String path) {
		String drive = "";
		String workingPath = path.strip();
		
		if(workingPath.length() == 0) {
			this.pathname = workingPath;
			this.filename = workingPath;
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
		
		this.pathname = workingPath;
		
		if(workingPath.contains(String.valueOf(osSeparator))) {
			int lastSepIdx = 0;
			for(int i = 0; i < workingPath.length(); i++) {
				if(workingPath.charAt(i) == osSeparator) {
					lastSepIdx = i;
				}
			}
			this.filename = workingPath.substring(lastSepIdx + 1).strip();
		} else {
			this.filename = workingPath.strip();
		}
	}
	
	public void parseFilename(String filename) {
		char[] chars = filename.toCharArray();
		int endungIdx = chars.length;
		int sepIdx = -1;
		for(int i = chars.length-1; i >= 0; i--) {
			// from string end, check for '.' and check for current
			// idx, to only set it once
			if(chars[i] == '.' && endungIdx == chars.length ) {
				endungIdx = i;
			}
			if(
					(i > 0 && i < chars.length) &&
					chars[i] == '-' && chars[i-1] == ' ' && chars[i+1] == ' '
			) {
				sepIdx = i;
			}
		}
		
		String cleanFileName = filename.substring(0, endungIdx).replace('\u00A0',' ').trim();
		
		if(sepIdx == -1) {
			if(cleanFileName.length() == 0) {
				this.author = "";
				this.title = "";
			}
			if(cleanFileName.length() > 0) {
				this.author = "";
				this.title = cleanFileName.strip();
			}
			return;
		}
		if(cleanFileName.equals("-")) {
			this.author = "";
			this.title = "";
			return;
		}
		String authorSubstr = cleanFileName.substring(0,sepIdx).strip();
		String titleSubstr = cleanFileName.substring(sepIdx + 1).strip();
		this.author = authorSubstr;
		this.title = titleSubstr;
	}
	
	@Override
	public String toString() {
		if(this.getAuthor().length() < 1) {
			return this.getTitle();
		}
		
		return String.format("%s - %s", this.getAuthor(), this.getTitle());
	}
	
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase()
		.indexOf("win") >= 0;
	}
}
