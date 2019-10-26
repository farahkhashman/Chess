import java.awt.Graphics;
import java.util.ArrayList;

public class King extends Piece{
	
	public King(int turn, int xloc, int yloc, String piece_name) {
		super(turn,xloc,yloc,piece_name);
	}

	@Override
	public ArrayList<int[]> get_moves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
	
		
		for (int dist = 1; dist < 2; dist ++) {
			if (x + dist > 7 )
				break;
			if (board[x + dist][y].get_team() == team)
				break;
			int[] move = {x + dist,y};
			moves.add(move);
			if (board[x + dist][y].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if (x - dist < 0 )
				break;
			if (board[x - dist][y].get_team() == team)
				break;
			int[] move = {x - dist, y};
			moves.add(move);
			if (board[x - dist][y].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if ( y - dist < 0)
				break;
			if (board[x ][y - dist].get_team() == team)
				break;
			int[] move = {x, y - dist};
			moves.add(move);
			if (board[x][y - dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if ( y + dist > 7)
				break;
			if (board[x][y + dist].get_team() == team)
				break;
			int[] move = {x , y + dist};
			moves.add(move);
			if (board[x][y + dist].get_team() == (team+1)%2)
				break;
		}
		
		for (int dist = 1; dist < 2; dist ++) {
			if (x + dist > 7 || y + dist > 7)
				break;
			if (board[x + dist][y + dist].get_team() == team)
				break;
			int[] move = {x + dist, y + dist};
			moves.add(move);
			if (board[x + dist][y + dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if (x - dist < 0 || y + dist > 7)
				break;
			if (board[x - dist][y + dist].get_team() == team)
				break;
			int[] move = {x - dist, y + dist};
			moves.add(move);
			if (board[x - dist][y + dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if (x - dist < 0 || y - dist < 0)
				break;
			if (board[x - dist][y - dist].get_team() == team)
				break;
			int[] move = {x - dist, y - dist};
			moves.add(move);
			if (board[x - dist][y - dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 2; dist ++) {
			if (x + dist > 7 || y - dist < 0)
				break;
			if (board[x + dist][y - dist].get_team() == team)
				break;
			int[] move = {x + dist, y - dist};
			moves.add(move);
			if (board[x + dist][y - dist].get_team() == (team+1)%2)
				break;
		}
		


		
		return moves;
	}
}
