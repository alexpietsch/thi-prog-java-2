package studiplayer.audio;


public class NotPlayableException extends Exception {
	private String filePathname;

	public NotPlayableException(String pathname, String msg){
		super(msg);
		this.filePathname = pathname;
	}
	
	public NotPlayableException(String pathname, Throwable t) {
		super(t);
		this.filePathname = pathname;
	}
	
	public NotPlayableException(String pathname, String msg, Throwable t) {
		super(msg, t);
		this.filePathname = pathname;
	}
}
