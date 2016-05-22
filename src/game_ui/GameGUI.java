package game_ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import game_logic.Board;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * The GameGUI class has is-a JFrame, it has 3 panel, the panel on the
 * top display the fortress health, the panel on the center is a grid
 * that shows the game board graphically using images and the panel on
 * the bottom display the shots that hit the fortress by tanks.
 */


@SuppressWarnings("serial")
public class GameGUI extends JFrame {
	private GameBoard gameBoard;
	private String gameTitle;
	private boolean isFirstGame = true;
	private Board game;

	public GameGUI(String gameTitle, Board board) {
		this.gameTitle = gameTitle;
		this.game = board;
		
		JFrame frame = new JFrame(this.gameTitle);
		frame.add(playGame());
		frame.pack();
		frame.setSize(920,1020);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//frame.setResizable(false);
	}
	
	public JPanel playGame() {
		JPanel panel = new JPanel();
		panel.setLayout (new BorderLayout());
		setTitle(gameTitle);
		panel.add(getFortressHP(), BorderLayout.NORTH);
		panel.add(getGameBoard(), BorderLayout.CENTER);
		panel.add(getTankDamage(), BorderLayout.SOUTH);
		return panel;
	}
	
	private Component getGameBoard() {
		return gameBoard = new GameBoard(game,this);
	}
	
	private Component getFortressHP() {
		setLayout(new BorderLayout());
		String health = "Fortress Health: "+ game.getFortress().getHp();
		final JLabel healthText = new JLabel(health);
		Board.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				updateHealth(healthText);
			}
		});
		healthText.setFocusable(false);
		add(healthText);
		return healthText;
	}
	
	private void updateHealth(JLabel status) {
		status.setText("Fortress Health: "+ game.getFortress().getHp());
	}
	
	private Component getTankDamage() {
			setLayout(new BorderLayout());
			String status = getEachDamage();
			final JLabel statusText = new JLabel(status);
			Board.addChangeListener(new ChangeListener() {
				
				@Override
				public void stateChanged(ChangeEvent arg0) {
					updateDamage(statusText);
				}
			});
			statusText.setFocusable(false);
			return statusText;
	}
	private String getEachDamage() {
		String status="";
		ArrayList<Integer> damages = game.getTankDamage_notFire();
		for(int d:damages){
			if(d > 0){
				status = status + "You were shot for "+ d + "!<br>";				
			}
		}
		String test = "<html><body>" + status + "</body></html>";
		if(isFirstGame){
			isFirstGame = false;
			return "<html><body><br><br><br><br><body><html>";
		}else {
			return test;
		}
	}
	private void updateDamage(JLabel status) {
		status.setText(getEachDamage());
	}
	
}
