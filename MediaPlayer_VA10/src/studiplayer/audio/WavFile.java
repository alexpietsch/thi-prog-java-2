package studiplayer.audio;
import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	public WavFile() throws NotPlayableException {
		super();
		this.readAndSetDurationFromFile();
		this.title = this.getFilename();
	}
	
	public WavFile(String path) throws NotPlayableException {
		super(path);
		this.readAndSetDurationFromFile();
	}
	
	public void setDuration(long duration) {
		this.duration = duration;
	}
	
	public long getDuration() {
		return this.duration;
	}
	
	public void readAndSetDurationFromFile() throws NotPlayableException {
		try {
			WavParamReader.readParams(this.getPathname());
		} catch (Exception e) {
			throw new NotPlayableException(getPathname(), "Could not read params.", e);
		}
		long duration = computeDuration(WavParamReader.getNumberOfFrames(), WavParamReader.getFrameRate());
		this.setDuration(duration);
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", super.toString(), this.formatDuration());
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		float durationSeconds = (float) numberOfFrames /  frameRate;
		long durationMicroseconds = (long) (durationSeconds * 1000000);
		return durationMicroseconds;
	}
}
