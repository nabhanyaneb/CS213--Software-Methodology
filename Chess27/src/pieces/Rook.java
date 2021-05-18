package pieces;

public class Rook extends Piece {

	public Rook(String name, String color) {
		super(name, color);
		this.name = name;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return this.color.charAt(0)+"R";
	}
	@Override
	public boolean isMoveValid(int rowS, int colS, int rowF, int colF) {
		if ((rowF >= 0) && (rowF < 8) && (rowS >= 0) && (rowS < 8)) {
			if ( (colF >= 0) && (colF < 8) && (colS >= 0) && (colS < 8)) {
				if (rowF == rowS){
					if (colF != colS) {
						return true;
					}
				} else {
					if (colF == colS) {
						return true;
					}
				} 
			}
		}
		return false;
	}
}
