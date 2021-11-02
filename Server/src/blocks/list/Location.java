package blocks.list;

public class Location {

	private int x, y;

	public Location(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public Location(int[] xy) {
		this.setXY(xy);
	}

	public Location setXY(int x, int y) {
		this.setX(x);
		this.setY(y);
		return this;
	}
	
	public Location setXY(int[] xy) {
		this.setX(xy[0]);
		this.setY(xy[1]);
		return this;
	}

	public int getX() {
		return x;
	}

	public Location setX(int x) {
		this.x = x;
		return this;
	}

	public int getY() {
		return y;
	}

	public Location setY(int y) {
		this.y = y;
		return this;
	}

	public int[] getXY() {
		return new int[] { x, y };
	}

	@Override
	public String toString() {
		return "{" + this.getX() + "," + this.getY() + "}";
	}

}
