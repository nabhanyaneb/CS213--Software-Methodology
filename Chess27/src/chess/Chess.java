// Joshua Gole and Nabhanya Neb
// Left to code: Checkmate/Stop from moving into Check

package chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class Chess {
	//Global Variables
	static String[][] board = new String[8][8];
	static Piece[][] pieces = new Piece[8][8];
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static String input = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		pieces[0][0] = new Rook("rook", "black");
		pieces[0][1] = new Knight("knight", "black");
		pieces[0][2] = new Bishop("bishop", "black");
		pieces[0][3] = new Queen("queen", "black");
		pieces[0][4] = new King("king", "black");
		pieces[0][5] = new Bishop("bishop", "black");
		pieces[0][6] = new Knight("knight", "black");
		pieces[0][7] = new Rook("rook", "black");
				
		for (int c = 0; c<8; c++)
			pieces[1][c] = new Pawn("pawn", "black");
		
		for (int r = 2; r <6; r++) {
			for (int c = 0; c < 8; c++) {
				pieces[r][c] = null;
			}
		}
	
		for (int c = 0; c<8; c++)
			pieces[6][c] = new Pawn("pawn", "white");
		
		pieces[7][0] = new Rook("rook", "white");
		pieces[7][1] = new Knight("knight", "white");
		pieces[7][2] = new Bishop("bishop", "white");
		pieces[7][3] = new Queen("queen", "white");
		pieces[7][4] = new King("king", "white");
		pieces[7][5] = new Bishop("bishop", "white");
		pieces[7][6] = new Knight("knight", "white");
		pieces[7][7] = new Rook("rook", "white");	


		
		System.out.println("Welcome to Chess! Press Enter to Continue.");
		try {
			input = br.readLine();
			if (input.equals("")) {
				drawBoard();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		playGame();
		
		
	}
	
	public static void drawBoard() {
		
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (pieces[r][c] == null) {
					if (r%2 == 0) {
						if (c%2 == 1)
							board[r][c] = "##";
						else 
							board[r][c] = "  ";
					}
					else { 
						if (c%2 == 0)
							board[r][c] = "##";
						else 
							board[r][c] = "  ";						
					}
				}
				else board[r][c] = pieces[r][c].toString();
				
				System.out.print(board[r][c] + " ");
			}
			System.out.println(8-r);
		}
		
		System.out.println("a  b  c  d  e  f  g  h");
		System.out.println();
		
		
	}

	public static boolean isCastling(Piece temp, int rowS, int colS, int rowF, int colF) {
		if (temp.color.equals("white")) {
			if ((rowF == 7) && (rowS == 7)){
				if ((colS == 4)&&(colF == 6)) {
					if ((pieces[7][5] == null) && (pieces[7][6] == null) 
							&& 
							(pieces[7][7].getName().equals("rook"))){
						return true;						
					}
				}
				if ((colS == 4)&&(colF == 2)) {
					if ((pieces[7][1] == null) && (pieces[7][2] == null) 
							&& (pieces[7][3] == null) && 
							(pieces[7][0].getName().equals("rook"))){
						return true;						
					}
				}
			}
		}
		else {
			if ((rowF == 0) && (rowS == 0)){
				if ((colS == 4)&&(colF == 6)) {
					if ((pieces[0][5] == null) && (pieces[0][6] == null) && 
							(pieces[0][7].getName().equals("rook"))){
						return true;						
					}
				}
				if ((colS == 4)&&(colF == 2)) {
					if ((pieces[0][1] == null) && (pieces[0][2] == null) 
							&& (pieces[0][3] == null) && 
							(pieces[0][0].getName().equals("rook"))){
						return true;						
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isEnpassant(Piece temp, int rowS, int colS, int rowF, int colF) {
		if (temp.color.equals("white")) {
			if ((rowF == rowS-1) && (rowS == 3)){
				if ((colS - colF == 1) || (colF - colS == 1)) {
					if ((pieces[rowF][colF] == null) && 
					(pieces[rowS][colF].getName().equals("pawn"))){
						return true;
					}
				}
			}
		}
		else {
			if ((rowF == rowS+1) && (rowS == 4)){
				if ((colS - colF == 1) || (colF - colS == 1)) {
					if ((pieces[rowF][colF] == null) && 
					(pieces[rowS][colF].getName().equals("pawn"))){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean isPathBlocked(int rowS, int colS, 
			int rowF, int colF, Piece[][] pieces) {
		//Horizontal Movement
		if (rowS == rowF) {
			if (colF > colS) {
				int i = colS + 1;
				while (colF > i) {
					if (pieces[rowS][i] != null) {
						return true;
					}
					i++;
				}
				return false;
			}
			else {
				int i = colS - 1;
				while (colF < i) {
					if (pieces[rowS][i] != null) {
						return true;
					}
					i--;
				}
				return false;
			}
		}
		//Vertical Movement
		else if (colS == colF) {

			if (rowF > rowS) {
				int i = rowS + 1;
				while (rowF > i) {
					if (pieces[i][colF] != null) {
						return true;
					}
					i++;
				}
				return false;
			}
			else {
				int i = rowS - 1;
				while (rowF < i) {
					if (pieces[i][colF] != null) {
						return true;
					}
					i--;
				}
				return false;
			}
		}
		//Diagonal Movement
		else {
			if (rowF > rowS) {
				int i = rowS + 1;
				if (colF > colS) {
					int j = colS + 1;
					while (colF > j) {
						if (pieces[i][j] != null) {
							return true;
						}
						i++;
						j++;
					}
					return false;
				}
				else {
					int j = colS - 1;
					while (colF < j) {
						if (pieces[i][j] != null) {
							return true;
						}
						i++;
						j--;
					}
					return false;
				}
			}
			else {
				int i = rowS - 1;
				if (colF > colS) {
					int j = colS + 1;
					while (colF > j) {
						if (pieces[i][j] != null) {
							return true;
						}
						i--;
						j++;
					}
					return false;
				}
				else {
					int j = colS - 1;
					while (colF < j) {
						if (pieces[i][j] != null) {
							return true;
						}
						i--;
						j--;
					}
					return false;
				}
			}
		}
	}
	
	public static boolean isCheck(String color1, String color2, Piece[][] board) {
		int rowK = 0;
		int colK = 0;
		for (int kr = 0; kr < 8; kr++) {
			for (int kc = 0; kc < 8; kc++) {
				if(!(board[kr][kc] == null)) {
					if (board[kr][kc].getColor().equals(color1)) {
						if (board[kr][kc].getName().equals("king")) {
							rowK = kr;
							colK = kc;
							break;
						}
					}
				}
			}
		}
		for (int rowS = 0; rowS < 8; rowS++) {
			for (int colS = 0; colS < 8; colS++) {
				if (board[rowS][colS] == null) {
					continue;
				}
				Piece temp = board[rowS][colS];
				if (temp.getColor().equals(color1)) {
					continue;
				}
				if (temp.isMoveValid(rowS, colS, rowK, colK)) {
					if ((temp.getName().equals("pawn")) && (colK!=colS)){
						if ((!isPathBlocked(rowS, colS, rowK, colK, board))) { 
							return true;	
						}
					}
					else if (!(temp.getName().equals("knight"))){
						if ((!isPathBlocked(rowS, colS, rowK, colK, board))) { 
							return true;	
						}
					}else {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isCheckmate(String color1, String color2) {
		if (isCheck(color1, color2, pieces)) {
			for (int rowS = 0; rowS < 8; rowS++) {
				for (int colS = 0; colS < 8; colS++) {
					for (int rowF = 0; rowF < 8; rowF++) {
						for (int colF = 0; colF < 8; colF++) {
							if (pieces[rowS][colS] == null) {
								continue;
							}
							Piece temp = pieces[rowS][colS];
							if (temp.getColor().equals(color2)) {
								continue;
							}
							if (temp.isMoveValid(rowS, colS, rowF, colF)) {
								if (temp.getName().equals("pawn")){
									if (colF==colS){
										if ((!isPathBlocked(rowS, colS, rowF, colF, pieces))) {
											if (pieces[rowF][colF] == null) {	 
												if (!(seeIfCheck(color1, color2, 
														rowS, colS, rowF, colF))) {
													return false;
												}
											}
											continue;
										}
									} else if ((pieces[rowF][colF] != null) &&
											(!(pieces[rowF][colF].getColor()
													.equals(temp.getColor())))){	 
										if (!(seeIfCheck(color1, color2, 
												rowS, colS, rowF, colF))) {	
											return false;
										}
									}
								}
								else if (!(temp.getName().equals("knight"))){
									if ((pieces[rowF][colF] != null) &&
											(!(pieces[rowF][colF].getColor()
													.equals(temp.getColor())))){
										if ((!isPathBlocked(rowS, colS, rowF, colF, pieces))) { 
											if (!(seeIfCheck(color1, color2, 
													rowS, colS, rowF, colF))) {	
												return false;
											}
										}
									}
								}
								else {
									if ((pieces[rowF][colF] != null) &&
											(!(pieces[rowF][colF].getColor()
													.equals(temp.getColor())))){
										if (!(seeIfCheck(color1, color2, 
												rowS, colS, rowF, colF))) {	
											return false;
										}
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean seeIfCheck(String color1, String color2, 
			int rowS, int colS, int rowF, int colF) {
		Piece[][] copy = new Piece[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece temp = pieces[i][j];
				copy[i][j] = temp;
			}
		}
		Piece temp2 = copy[rowS][colS];
		copy[rowS][colS] = null;
		copy[rowF][colF] = temp2;
		
		return isCheck(color1, color2, copy);
	}
	
	public static void playGame() {
		int turn = 0;	
		boolean isGameOver = false;
		boolean[][] enpassantCondition = new boolean[2][8];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				enpassantCondition[i][j] = false;
			}
		}
		boolean[][] canCastle = {{true, true}, {true, true}};
		
		try {
			while (!(isGameOver)) {
				//Print out whose turn it is and read input
				if (turn % 2 == 0) {
					if (isCheckmate("white", "black")) {
						System.out.println("Checkmate\nBlack wins");
						isGameOver = true;
						continue;
					}
					if (isCheck("white", "black", pieces)) {
						System.out.println("Check");
					}
					System.out.print("Player White's Turn: ");
				}
				else {
					if (isCheckmate("black", "white")) {
						System.out.println("Checkmate\nWhite wins");
						isGameOver = true;
						continue;
					}
					if (isCheck("black", "white", pieces)) {
						System.out.println("Check");
					}
					System.out.print("Player Black's Turn: ");
				}
				System.out.println();
				input = br.readLine();


				//If there is no input
				if (input.equals(null)) {
					System.out.println("Illegal move, try again");
					continue;
				}
				//For a standard input
				else if (input.split("\\s+").length == 2) {
					for (int j = 0; j < 8; j++) {
						enpassantCondition[turn%2][j] = false;
					}
					String[] moves = input.split("\\s+");
					String start = moves[0];
					String fin = moves[1];
					
					char char_colS = start.charAt(0);
					char char_colF = fin.charAt(0);
					
					int colS = char_colS - 97;
					int colF = char_colF - 97;
					
					int rowS = 8 - Integer.parseInt(start.charAt(1)+"");
					int rowF = 8 - Integer.parseInt(fin.charAt(1)+"");
					
					//If an empty space is selected
					if (pieces[rowS][colS] == null) {
						System.out.println("Illegal move, try again");
						continue;
					}
					Piece temp = pieces[rowS][colS];
					
					//Detect if moving the wrong color
					if (turn % 2 == 0) {
						if (temp.getColor().equals("black")) {				
							System.out.println("Illegal move, try again");
							continue;
						}
					}
					else {
						if (temp.getColor().equals("white")) {
							System.out.println("Illegal move, try again");
							continue;
						}
					}
					
					//Check if the selected piece can make the move
					if (temp.isMoveValid(rowS, colS, rowF, colF)) {
						if ((pieces[rowF][colF] == null) || 
								(!(pieces[rowF][colF].getColor().equals(temp.getColor())))) {
							
							//King Specific Cases
							if (temp.getName().equals("king")){
								if ((colF - colS > 1) || (colS - colF > 1)) {
									if ((isCastling(temp, rowS, colS, rowF, colF)) &&
											(canCastle[turn%2][colF%2])){
										if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
												rowS, colS, rowF, colF)){
											System.out.println("Illegal move, try again");
											continue;								
										}
										pieces[rowS][colS] = null;
										pieces[rowF][colF] = temp;
										if (colF == 2) {
											Piece rook = pieces[rowS][0];
											pieces[rowS][colS-1] = rook;
											pieces[rowS][0] = null;								
										}
										else {
											Piece rook = pieces[rowS][7];
											pieces[rowS][colS+1] = rook;
											pieces[rowS][7] = null;	
										}
									}
									else {
										System.out.println("Illegal move, try again");
										continue;
									}
								}
								else {
									if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
											rowS, colS, rowF, colF)){
										System.out.println("Illegal move, try again");
										continue;								
									}
									pieces[rowS][colS] = null;
									pieces[rowF][colF] = temp;
								}
								canCastle[turn%2][0] = false;
								canCastle[turn%2][1] = false;
							}
							
							//Pawn Specific Cases
							else if (temp.getName().equals("pawn")){
								if (colS != colF) {
									if (isEnpassant(temp, rowS, colS, rowF, colF)) {
										if (enpassantCondition[(turn+1) % 2][colF]){
											if (seeIfCheck(temp.getColor(), 
													temp.getOppositeColor(), 
													rowS, colS, rowF, colF)){
												System.out.println("Illegal move, try again");
												continue;								
											}
											pieces[rowS][colS] = null;
											pieces[rowF][colF] = temp;
											pieces[rowS][colF] = null;
										}
										else {
											System.out.println("Illegal move, try again");
											continue;
										}
									}
									else {
										if (pieces[rowF][colF] == null) {
											System.out.println("Illegal move, try again");
											continue;
										}
										else if (pieces[rowF][colF].getColor()
												.equals(temp.getColor())) {
											System.out.println("Illegal move, try again");
											continue;
										}
										else {
											if (seeIfCheck(temp.getColor(), 
													temp.getOppositeColor(), 
													rowS, colS, rowF, colF)){
												System.out.println("Illegal move, try again");
												continue;								
											}
											pieces[rowS][colS] = null;
											pieces[rowF][colF] = temp;
										}
									}
								}
								else {
									if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
											rowS, colS, rowF, colF)){
										System.out.println("Illegal move, try again");
										continue;								
									}
									if (pieces[rowF][colF] == null) {
										if ((rowF - rowS == 2)) {
											if (pieces[rowF-1][colF] == null) {
												enpassantCondition[turn % 2][colF] = true;
											}
											else {
												System.out.println("Illegal move, try again");
												continue;
											}
										}
										if ((rowS - rowF == 2)) {
											if (pieces[rowF+1][colF] == null) {
												enpassantCondition[turn % 2][colF] = true;
											}
											else { 
												System.out.println("Illegal move, try again");
												continue;
											}
										}
										pieces[rowS][colS] = null;
										pieces[rowF][colF] = temp;
									}	
									else {
										System.out.println("Illegal move, try again");
										continue;
									}
								}
							}
							
							//For all other pieces
							else {
								if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
										rowS, colS, rowF, colF)){
									System.out.println("Illegal move, try again");
									continue;								
								}
								if (!(temp.getName().equals("knight"))){
									if (isPathBlocked(rowS, colS, rowF, colF, pieces)) {
										System.out.println("Illegal move, try again");
										continue;	
									}
								}
								if(temp.getName().equals("rook")) {
									canCastle[turn%2][(colF+1)%2] = false;
								}
								pieces[rowS][colS] = null;
								pieces[rowF][colF] = temp;
							}
							
							drawBoard();
							turn++;
							continue;
						}	
						else {
							System.out.println("Illegal move, try again");
							continue;
						}
					}
					else {
						System.out.println("Illegal move, try again");
						continue;
					}
				}
				//For pawn promotion
				else if (input.split("\\s+").length == 3) {
					String[] moves = input.split("\\s+");
					String start = moves[0];
					String fin = moves[1];
					String promo = moves[2];
					
					char char_colS = start.charAt(0);
					char char_colF = fin.charAt(0);
					
					int colS = char_colS - 97;
					int colF = char_colF - 97;
					
					int rowS = 8 - Integer.parseInt(start.charAt(1)+"");
					int rowF = 8 - Integer.parseInt(fin.charAt(1)+"");
					
					Piece temp = pieces[rowS][colS];
					if(!(temp.name.equals("pawn"))) {
						System.out.println("Illegal move, try again");
						continue;
					}
					if ((rowF == 0) || (rowF == 7)) {
						if (temp.isMoveValid(rowS, colS, rowF, colF)) {
							if (colS != colF) {
								if (!(pieces[rowF][colF].getColor().equals(temp.getColor()))) {
									if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
											rowS, colS, rowF, colF)){
										System.out.println("Illegal move, try again");
										continue;								
									}
									pieces[rowS][colS] = null;
									if (promo.equals("Q")){
										pieces[rowF][colF] = new Queen("queen", 
												temp.getColor());
									}
									else if (promo.equals("R")){
										pieces[rowF][colF] = new Rook("rook", 
												temp.getColor());
									}
									else if (promo.equals("N")){
										pieces[rowF][colF] = new Knight("knight", 
												temp.getColor());
									}
									else if (promo.equals("B")){
										pieces[rowF][colF] = new Bishop("bishop", 
												temp.getColor());
									}
									else {
										System.out.println("Illegal move, try again");
										continue;
									}
								}
								else {
									System.out.println("Illegal move, try again");
									continue;
								}
							}
							else {
								if (pieces[rowF][colF] == null) {
									if (seeIfCheck(temp.getColor(), temp.getOppositeColor(), 
											rowS, colS, rowF, colF)){
										System.out.println("Illegal move, try again");
										continue;								
									}
									pieces[rowS][colS] = null;
									if (promo.equals("Q")){
										pieces[rowF][colF] = new Queen("queen", 
												temp.getColor());
									}
									else if (promo.equals("R")){
										pieces[rowF][colF] = new Rook("rook", 
												temp.getColor());
									}
									else if (promo.equals("N")){
										pieces[rowF][colF] = new Knight("knight", 
												temp.getColor());
									}
									else if (promo.equals("B")){
										pieces[rowF][colF] = new Bishop("bishop", 
												temp.getColor());
									}
									else {
										System.out.println("Illegal move, try again");
										continue;
									}
								}else {
									System.out.println("Illegal move, try again");
									continue;
								}
							}
						}
						else {
							System.out.println("Illegal move, try again");
							continue;
						}
					}
					else {
						System.out.println("Illegal move, try again");
						continue;
					}
					
					drawBoard();
					turn++;
					continue;
				}
				//If the player resigns
				else if (input.equals("resign")) {
					if (turn % 2 == 0)
						System.out.print("Black wins");
					else 
						System.out.print("White wins");
					isGameOver = true;
					continue;
				}
				//If the player asks for a draw
				else if (input.equals("draw?")) {
					//other player must enter 'draw'
					input = br.readLine();
					while (!(input.equals("draw"))) {
						System.out.print("Please type 'draw'");
						input = br.readLine();
					}
					isGameOver = true;
					continue;
				}
				else {
					System.out.print("Illegal move, try again");
					continue;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
