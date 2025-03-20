import java.util.regex.Pattern;

public class AudioFile {
	private String path = "";
	private String filename = "";
	
	private char osSeparator;
	
	public AudioFile() {
		osSeparator = isWindows()
				? '\\'
				: '/';
	}	
	
	public AudioFile(String p) {
		osSeparator = isWindows()
				? '\\'
				: '/';
		p.charAt(0);
	}
	
	public String getPathname() {
		return this.path;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public void setPathname(String path) {
		this.path = path;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void parsePathname(String path) {
		String drive = "";
		String workingPath = path.strip();
		
		if(workingPath.length() < 1) {
			path = "";
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
			System.out.println(workingPath.substring(lastSepIdx + 1));
			this.setFilename(workingPath.substring(lastSepIdx + 1));
		} else {
			this.setFilename(workingPath);
		}
	}
	
	public void parseFilename(String p) {
		
	}
	
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase()
		.indexOf("win") >= 0;
	}
}
