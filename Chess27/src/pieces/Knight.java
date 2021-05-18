package pieces;

public class Knight extends Piece{

	public Knight(String name, String color) {
		super(name, color);
		this.name = name;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return this.color.charAt(0)+"N";
	}
	@Override
	public boolean isMoveValid(int rowS, int colS, int rowF, int colF) {
		if ((rowF >= 0) && (rowF < 8) && (rowS >= 0) && (rowS < 8)) {
			if ( (colF >= 0) && (colF < 8) && (colS >= 0) && (colS < 8)) {
				if ((rowF == rowS-1) || (rowF == rowS+1)){
					if ((colS == colF-2) || (colS == colF+2)) {
						return true;
					}
				} else if ((rowF == rowS-2) || (rowF == rowS+2)){
					if ((colS == colF-1) || (colS == colF+1)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
