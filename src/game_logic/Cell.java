package game_logic;

/*Cell class is the basic element of the game board 
 * each cell contains a set of information to store 
 * changes of states of a cell
 * i.e. the map of the game board
 */

public class Cell {
	private boolean isTank;
	private boolean isMissed;
	private boolean hasBeenAttacked;
	private int current_row;
	private int current_column;
	private boolean isTankHit;
	public Cell(int r, int c){
		isTankHit = false;
		isTank = false;
		isMissed = false;
		hasBeenAttacked = false;
		setCurrent_row(r);
		setCurrent_column(c);
	}
	public void setIsTank(boolean a){
		isTank = a;
	}
	public boolean getIsTank(){
		return isTank;
	}
	public void setIsMissed(boolean a){
		isMissed = a;
	}
	public boolean getIsMissed(){
		return isMissed;
	}
	public void setHasBeenAttacked(boolean a){
		hasBeenAttacked = a; 
	}
	public boolean getHasBeenAttacked(){
		return hasBeenAttacked;
	}
	public int getCurrent_row() {
		return current_row;
	}
	public void setCurrent_row(int current_row) {
		this.current_row = current_row;
	}
	public int getCurrent_column() {
		return current_column;
	}
	public void setCurrent_column(int current_column) {
		this.current_column = current_column;
	}
	public boolean isTankHit() {
		return isTankHit;
	}
	public void setTankHit(boolean isTankHit) {
		this.isTankHit = isTankHit;
	}
	
}
