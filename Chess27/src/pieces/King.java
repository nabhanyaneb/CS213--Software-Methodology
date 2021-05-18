package pieces;

public class King extends Piece{

	public King(String name, String color) {
		super(name, color);
		this.name = name;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return this.color.charAt(0)+"K";
	}
	@Override
	public boolean isMoveValid(int rowS, int colS, int rowF, int colF) {
		// TODO Auto-generated method stub
		if (this.color.equals("white")) {
			if ((rowF == 7) && (rowS == 7)){
				if (((colS == 4)&&(colF == 6)) || ((colS == 4)&&(colF == 1))) {
					return true;
				}
			}
		}
		else {
			if ((rowF == 0) && (rowS == 0)){
				if (((colS == 4)&&(colF == 6)) || ((colS == 4)&&(colF == 1))) {
					return true;
				}
			}
		}
		if ((rowF >= 0) && (rowF < 8) && (rowS >= 0) && (rowS < 8)) {
			if ( (colF >= 0) && (colF < 8) && (colS >= 0) && (colS < 8)) {
				if ((rowF - rowS <= 1) && (rowS - rowF <= 1)) {
					if ((colF - colS <= 1) && (colS - colF <= 1)) {
						if (!((colF == colS) && (rowS == rowF))) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
