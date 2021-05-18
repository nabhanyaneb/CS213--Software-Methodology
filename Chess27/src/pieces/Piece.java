package pieces;

public abstract class Piece {
	public String name;
	public String color;
	
	public Piece(String name, String color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getOppositeColor() {
		if (this.color.equals("white")){
			return "black";
		}
		return "white";
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public abstract String toString();
	public abstract boolean isMoveValid(int rowS, int colS, int rowF, int colF);
}
