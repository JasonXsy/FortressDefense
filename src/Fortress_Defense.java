import game_logic.Board;
import game_ui.GameGUI;

/*This Fortress_Defense class , where the main() is 
 * to invoke game logic and game-gui object.   
 */
public class Fortress_Defense {
	
	public static void main(String[] args) {
		final int ROW = 10;
		final int COLUMN = 10;
		final int FORTRESS_HP = 1500;
		final int NUMBER_TANKS = 5;
		final int TANK_CELLS = 4;
		Board game = new Board(ROW, COLUMN, FORTRESS_HP, NUMBER_TANKS, TANK_CELLS);
		new GameGUI("Tank Battlefield",game);
	}
}
