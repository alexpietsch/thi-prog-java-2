import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() {
		super();
		this.readAndSetDurationFromFile();
	}
	
	public WavFile(String path) {
		super(path);
		this.readAndSetDurationFromFile();
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public void readAndSetDurationFromFile() {
		WavParamReader.readParams(this.getPathname());
		long duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
		this.setDuration(duration);
	}
	
	public String toString() {
		return null;
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		float durationSeconds = (float) numberOfFrames /  frameRate;
		long durationMicroseconds = (long) (durationSeconds * 1000000);
		return durationMicroseconds;
	}
}
