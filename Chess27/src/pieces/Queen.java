package pieces;

public class Queen extends Piece{

	public Queen(String name, String color) {
		super(name, color);
		this.name = name;
		this.color = color;
		// TODO Auto-generated constructor stub
	}
	public String toString() {
		return this.color.charAt(0)+"Q";
	}
	@Override
	public boolean isMoveValid(int rowS, int colS, int rowF, int colF) {
		if ((rowF >= 0) && (rowF < 8) && (rowS >= 0) && (rowS < 8)) {
			if ( (colF >= 0) && (colF < 8) && (colS >= 0) && (colS < 8)) {
				if (rowF == rowS){
					if (colF != colS) {
						return true;
					}
				} 
				else {
					if (colF == colS) {
						return true;
					}
				}  
				if ((rowF - rowS == colF - colS) || (rowF - rowS == colS - colF)){
					if ((colF != colS) || (rowS != rowF)) {
						return true;
					}
				} 
			}
		}
		return false;
	}
}
