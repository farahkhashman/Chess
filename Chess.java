	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;

	public class Chess extends JPanel implements MouseListener, Runnable {
		
		// constants that are predefined and won't change
		private final int width = 600, height = 600;
		private final int square_width = width/8;
		private final int square_height = height/8;
		private final int img_width = square_width - 10;
		private final int img_height = square_width - 10;
		private Piece[][] board;
		private Piece last_clicked = null;
		private int lastx = 0,lasty = 0;
		private HashMap<String,Image> images;
		private int turn = 0;
		private boolean game_over = false, moved = false;
		private King[] kings;
		
		private boolean human1 = true;
		private boolean human2 = true;

		public Chess() {
			King king2 = new King(1,7,3,"BlackKing");
			King king1 = new King(0,0,3,"WhiteKing");
			kings = new King[2];
			kings[0] = king1;
			kings[1] = king2;
			
			Piece[] col1 = new Piece[8];
			col1[0] = new Rook(0,0,0,"WhiteRook1");
			col1[1] = new Knight(0,0,1,"WhiteKnight1");
			col1[2] = new Bishop(0,0,2,"WhiteBishop1");
			col1[3] = king1;
			col1[4] = new Queen(0,0,4,"WhiteQueen");
			col1[5] = new Bishop(0,0,5,"WhiteBishop2");
			col1[6] = new Knight(0,0,6,"WhiteKnight2");
			col1[7] = new Rook(0,0,7,"WhiteRook2");
			
			Piece[] col8 = new Piece[8];
			col8[0] = new Rook(1,7,0,"BlackRook1");
			col8[1] = new Knight(1,7,1,"BlackKnight1");
			col8[2] = new Bishop(1,7,2,"BlackBishop1");
			col8[3] = king2;
			col8[4] = new Queen(1,7,4,"BlackQueen");
			col8[5] = new Bishop(1,7,5,"BlackBishop2");
			col8[6] = new Knight(1,7,6,"BlackKnight2");
			col8[7] = new Rook(1,7,7,"BlackRook2");
				
			Piece[] col2 = new Piece[8];
			for (int i = 0; i < 8; i ++) 
				col2[i] = new Pawn(0,1,i,"WhitePawn" + i);
			
			Piece[] col7 = new Piece[8];
			for (int i = 0; i < 8; i ++) 
				col7[i] = new Pawn(1,6,i,"BlackPawn" + i);
			
			board = new Piece[8][];
			board[0] = col1;
			board[1] = col2;
			board[6] = col7;
			board[7] = col8;
			for (int i = 2; i < 6; i++) {
				Piece[] coli = new Piece[8];
				for (int j = 0; j < 8; j ++) {
					coli[j] = new Empty(i,j);
				}
				board[i] = coli;
			}
			
			images = new HashMap<String, Image>();
			
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/" + col1[i].get_name() + ".png");	
				images.put(col1[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/" + col8[i].get_name() + ".png");	
				images.put(col8[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/BlackPawn.png");	
				images.put(col7[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/WhitePawn.png");	
				images.put(col2[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
		}
		
		public void run() {

			// while(true) should rarely be used to avoid infinite loops, but here it is ok because
			// closing the graphics window will end the program
		}
		
		// very simple main method to get the game going
		public static void main(String[] args) {
			Chess game = new Chess(); 
			game.start_game();
		}
	 
		// does complicated stuff to initialize the graphics and key listeners
		public void start_game() {
			
			JFrame frame = new JFrame();
			frame.setSize(width+2, height+24);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			frame.add(this);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			this.addMouseListener(this);
			this.setFocusable(true);
			Thread t = new Thread(this);
			t.start();
		}

		// defines what we want to happen anytime we draw the game.
		public void paint(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);

			for (int i = 0; i < 8; i ++) {
				for (int j = 0; j < 8; j ++) {
					g.draw3DRect(i*square_width, j*square_height, square_width, square_height, false);
					if (!(board[i][j].get_name().equals("Empty"))) {
						if (board[i][j].equals(last_clicked)) {
							g.setColor(Color.yellow);
							g.fillRect(i*square_width +1, j*square_height+1, square_width-1, square_height-1);
						}
						g.drawImage(images.get(board[i][j].get_name()), i*square_width+5, j*square_height+5, this);
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		
			if (!game_over && ((turn == 0 && human1) || (turn == 1 && human2))) {
				Piece clicked = board[e.getX()/square_width][e.getY()/square_height];
				
				if (clicked.get_team() == turn) {
					lastx = e.getX()/square_width;
					lasty = e.getY()/square_height;
					last_clicked = clicked;
				}
				
				else if (last_clicked != null)
					if (is_in(e.getX()/square_width, e.getY()/square_height, last_clicked.get_moves(board))) {
						if (clicked.equals(kings[0])) {
							System.out.println("Player 2 wins!");
							game_over = true;
						}
						if (clicked.equals(kings[1])) {
							System.out.println("Player 1 wins!");
							game_over = true;
						}
							
						board[e.getX()/square_width][e.getY()/square_height] = last_clicked;
						board[lastx][lasty] = new Empty(lastx,lasty);
						last_clicked.move_to(e.getX()/square_width, e.getY()/square_height);
						last_clicked = null;
						if (game_over) {
							repaint();
							return;
						}
						check_board();
						turn = (turn + 1)%2;
						moved = true;
					}
			}
			if (!game_over&& ((!human1 && turn == 0) || (!human2 && turn == 1))) {
				//computer_move();
				check_board();
			}
			repaint();
		}
		
		public boolean is_in(int x, int y, ArrayList<int[]> moves) {
			for (int[] pair : moves) {
				if (x == pair[0] && y == pair[1])
					return true;
			}
			return false;
			
			
		}
		
		public boolean check_board() {
		
			// test for check
			for (int i = 0; i < 8; i ++)
				for (int j = 0; j < 8; j++) {
					if (board[i][j].get_team()!= -1)
						if (board[i][j].check(kings[(board[i][j].get_team()+1)%2], board)) {
							System.out.println("Check!");
							return true;
						}
				}
			return false;
		}
		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

	}