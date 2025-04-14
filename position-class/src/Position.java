import java.util.Locale;

public class Position {

	private double x;
	private double y;
	
	Position(double pX, double pY) {
		this.x = pX;
		this.y = pY;
	}
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	
	public Position add(Position p) {
		double newX = this.x + p.getX();
		double newY = this.y + p.getY();
		
		return new Position(newX, newY);
	}
	
	public Position sub(Position p) {
		double newX = this.x - p.getX();
		double newY = this.y - p.getY();
		
		return new Position(newX, newY);
	}
	
	public Position add(double factor) {
		double newX = this.x + factor;
		double newY = this.y + factor;
		
		return new Position(newX, newY);
	}
	
	public Position sub(double factor) {
		double newX = this.x - factor;
		double newY = this.y - factor;
		
		return new Position(newX, newY);
	}
	
	public Position mul(double factor) {
		double newX = this.x * factor;
		double newY = this.y * factor;
		
		return new Position(newX, newY);
	}
	
	public Position div(double factor) {
		if(factor == 0) {
			throw new RuntimeException("Factor cannot be 0");
		}
		double newX = this.x / factor;
		double newY = this.y / factor;
		
		return new Position(newX, newY);
	}
	
	public double dot(Position p) {
		return this.x * p.getX() + this.y * p.getY();
	}
	
	public double distance(Position p) {
		double distX = (this.x - p.getX()) * (this.x - p.getX());
		double distY = (this.y - p.getY()) * (this.y - p.getY());
		
		return Math.sqrt(distX + distY);
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "%.1f/%.1f", x, y);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null) {
			if(o instanceof Position) {
				Position oP = (Position) o;
				if((oP.x == this.x) && (oP.y == this.y)) {
					return true;
				}
			}
			
		}
		return false;
	}
}
