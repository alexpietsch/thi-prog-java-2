package studiplayer.audio;


public class AudioFileFactory {
	public static AudioFile createAudioFile(String pathname) throws NotPlayableException {
		char[] chars = pathname.toCharArray();
		int extensionIdx = chars.length;
		for(int i = chars.length-1; i >= 0; i--) {
			// from string end, check for '.' and check for current
			// idx, to only set it once
			if(chars[i] == '.' && extensionIdx == chars.length ) {
				extensionIdx = i;
			}
		}
		
		if(extensionIdx == chars.length) {
			throw new NotPlayableException(pathname, String.format("Missing suffix for AudioFile \"%s\"", pathname));
		}
		
		
		
		String extension = pathname.strip().substring(extensionIdx + 1).toLowerCase().strip();

		switch (extension) {
			case "wav":
				return new WavFile(pathname);
			case "ogg":
			case "mp3":
				return new TaggedFile(pathname); 
			default:
				throw new NotPlayableException(pathname, String.format("Unknown suffix for AudioFile \"%s\"", pathname));
		}
	}
}
