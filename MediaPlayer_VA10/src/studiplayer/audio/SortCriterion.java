package studiplayer.audio;

public enum SortCriterion {
	DEFAULT,
	AUTHOR,
	TITLE,
	ALBUM,
	DURATION;
	
	@Override
	public String toString() {
		switch (this.ordinal()) {
		case 1:
			return "Interpret";
		case 2:
			return "Titel";
		case 3:
			return "Album";
		case 4:
			return "Länge";
		default:
			return "Standard";
		}
	}
}
