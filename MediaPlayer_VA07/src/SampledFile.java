import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
	protected long duration;

	public SampledFile() {
		super();
	}
	
	public SampledFile(String path) {
		super(path);
	}
	
	public void setDuration(long duration) {
		this.duration = duration; 
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public void play() {
		BasicPlayer.play(this.getPathname());
	}
	
	public void togglePause() {
		BasicPlayer.togglePause();
	}

	public void stop() {
		BasicPlayer.stop();
	}

	public String formatDuration() {
		return formatMicrosecondsAsString(this.getDuration());
	}

	public String formatPosition() {
		return formatMicrosecondsAsString(BasicPlayer.getPosition());
	}
	
	public static String timeFormatter(long timeInMicroSeconds) {
		return formatMicrosecondsAsString(timeInMicroSeconds);
	}
	
	private static String formatMicrosecondsAsString(long l) {
		if(l < 0 || l > 5999999999L) {
			throw new RuntimeException(String.format("Invalid value %d for microseconds.", l));
		}
		
		double microAsSec = l / 1e6;
		long min = 0;
		double sec = (long) microAsSec;
		while(microAsSec >= 60.0) {
			min++;
			microAsSec-=60;
			sec -= 60;
		}
		
		return String.format("%02d:%02.0f", min, sec);
	}
}
