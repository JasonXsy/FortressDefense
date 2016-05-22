package game_ui;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JOptionPane;

import game_logic.Board;

/*
 * The GameBoard class has a function that create a grid that has a
 * image inside each cell of it in the center of the panel.
 */


@SuppressWarnings("serial")
public class GameBoard extends JPanel{
	private Board game;
	private JLabel [][] cells;
	private JFrame frame;
	
	private static final int IMAGE_SIZE = 90;
	
	private static ImageIcon fog  = 
			getScaleImageIcon(new ImageIcon("images/fog.png"), IMAGE_SIZE, IMAGE_SIZE);
	private static ImageIcon field  = 
			getScaleImageIcon(new ImageIcon("images/field.jpg"), IMAGE_SIZE, IMAGE_SIZE);
	private static ImageIcon hit  = 
			getScaleImageIcon(new ImageIcon("images/hit.png"), IMAGE_SIZE, IMAGE_SIZE);
	private static ImageIcon miss  = 
			getScaleImageIcon(new ImageIcon("images/miss.png"), IMAGE_SIZE, IMAGE_SIZE);
	private static ImageIcon tank  = 
			getScaleImageIcon(new ImageIcon("images/tank.png"), IMAGE_SIZE, IMAGE_SIZE);


	public GameBoard(Board game, JFrame frame){
		this.game = game;
		this.frame = frame;
		makeGameBoard();
	}

	private void makeGameBoard() {
		cells = new JLabel[game.getRow()][game.getCol()];
		setLayout(new GridLayout(game.getRow(), game.getCol()));
		for (int row = 0; row < game.getRow(); row++){
			for ( int col = 0; col < game.getCol(); col++){
				final int yCoordinate = row;
				final int xCoordinate = col;
				JLabel image = new JLabel();
				cells[row][col] = image; 
				
				if (game.isWon() == 0){ // display Game Board at begin					
					inGameImage(row ,col);
				}
				// add mouse click listener
				cells[row][col].addMouseListener( new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent arg0) {
						shootTank(yCoordinate, xCoordinate);
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
					}
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
					}					
				});	
				Board.addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent e) {
						if (game.isWon() == 0){ // game in progress					
							inGameImage(yCoordinate, xCoordinate);
						}
						else if (game.isWon() > 0){ // Lost
							afterGameImage();
							JOptionPane.showMessageDialog(frame, "I'm sorry; you lost.");
							System.exit(0);
						}
						else { // Won
							afterGameImage();
							JOptionPane.showMessageDialog(frame, "Congratulations! You won!");
							System.exit(0);
						}
					}
				});
				add(cells[row][col]);
			}
		}
	}


	private void inGameImage(int row, int col) {
		String symbol = game.inGameCells(row, col);
		if (symbol == "X") {
			cells[row][col].setIcon(hit);
		}
		else if (symbol == "."){
			cells[row][col].setIcon(miss);
		}
		else if (symbol == "~"){
			cells[row][col].setIcon(fog);
		}
	}

	private void afterGameImage(){
		for (int row = 0; row < game.getRow(); row++){
			for ( int col = 0; col < game.getCol(); col++){
				displayAfterGame(row, col);
			}
		}
	}
	
	private void displayAfterGame(int row, int col) {
		String symbol = game.afterGameCells(row, col);
		if(symbol.equals("T")){ 
			cells[row][col].setIcon(tank);
		}
		else if(symbol.equals(".")){
			cells[row][col].setIcon(miss);
		}
		else if(symbol.equals(" ")){
			cells[row][col].setIcon(field);
		}
		else if(symbol.equals("X") ){
			cells[row][col].setIcon(hit);
		}
	}

	private void shootTank(int row, int col) {
		game.isFortressHit(row, col);
		ArrayList<Integer> damages = game.getEachTankDamage();
	}
	static public ImageIcon getScaleImageIcon(ImageIcon icon, int width, int height) {
		return new ImageIcon(getScaledImage(icon.getImage(), width, height));
	}
	static private Image getScaledImage(Image srcImg, int width, int height){
		BufferedImage resizedImg =
				new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, width, height, null);
		g2.dispose();
		return resizedImg;
	}


}
