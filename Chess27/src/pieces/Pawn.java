package pieces;

public class Pawn extends Piece{
	
	public Pawn(String name, String color) {
		super(name, color);
		this.name = name;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	
	public String toString() {
		return this.color.charAt(0)+"p";
	}
	@Override
	public boolean isMoveValid(int rowS, int colS, int rowF, int colF) {
		if ((rowF >= 0) && (rowF < 8) && (rowS >= 0) && (rowS < 8)) {
			if ( (colF >= 0) && (colF < 8) && (colS >= 0) && (colS < 8)) {
				if (this.color.equals("white")) {
					if ((rowF == 4) && (rowS == 6)){
						if (colS == colF) {
							return true;
						}
					} else {
						if (rowF == rowS-1) {
							if ((colS - colF <= 1) && (colS - colF >= -1)) {
								return true;
							}
						}
					}
				}
				else {
					if ((rowF == 3) && (rowS == 1)){
						if (colS == colF) {
							return true;
						}
					} else {
						if (rowF == rowS+1) {
							if ((colS - colF <= 1) && (colS - colF >= -1)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}


}
