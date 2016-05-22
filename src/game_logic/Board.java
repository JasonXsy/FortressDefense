package game_logic;

import java.util.ArrayList;
import java.util.Random;
import java.util.List; 

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*board class generates required game objects. 
 * handles game objects' interaction. 
 * ex. passing fortress to tank in order to let tanks attack reduce
 * the fortress' strength
 *  
 */

public class Board {
	private static List<ChangeListener> listeners = new ArrayList<ChangeListener>();

	private Cell[][] cells;
	private Tank[] tanks;
	private Fortress fortress;
	private int number_row;
	private int number_column;
	private int number_tanks;
	private int number_tank_cells;
	private int fortress_hp;


	public Board(int row, int col, int fortress_strength, 
			int num_tanks, int tank_cells){

		number_row = row;
		number_column = col;
		fortress_hp = fortress_strength;
		number_tanks = num_tanks;
		number_tank_cells = tank_cells;
		/*get game objects*/
		setCells(new Cell[number_row][number_column]);
		for (int i = 0; i<number_row;i++){
			for(int j = 0; j<number_column;j++){
				cells[i][j] = new Cell(i,j);
			}
		}
		tanks = new Tank[number_tanks];
		for (int i = 0; i<number_tanks; i++){
			tanks[i] = new Tank(number_tank_cells);
		}
		setFortress(new Fortress(fortress_hp));
		generateTanks();
	}
	public Cell getCell ( int i, int j){
		return cells[i][j];
	}

	private void generateTanks(){
		for(Tank tanks: tanks){
			getNextTankCell(tanks);
		}			
	}
	private void getNextTankCell(Tank tank) {
		Random rand_num = new Random();
		int start_point_row = 0;
		int start_point_col = 0;		
		boolean isAllTankCellsDone = false;
		while(!isAllTankCellsDone){
			//first cell starts here, if all other cells fail to generate, start over from here
			start_point_row = rand_num.nextInt(number_row);
			start_point_col = rand_num.nextInt(number_column);
			if (cells[start_point_row][start_point_col].getIsTank() == false){
				tank.addCells(cells[start_point_row][start_point_col]);
				cells[start_point_row][start_point_col].setIsTank(true);
				isAllTankCellsDone = getRestTankCells(tank, rand_num);

			}
		}
	}

	private boolean getRestTankCells(Tank t, Random rand_num) {
		while(t.getCells().size() != 4){
			int next_row = t.getCells().getLast().getCurrent_row();
			int next_col = t.getCells().getLast().getCurrent_column();
			/*chose direction, 0 = north, 1 = east, 2 south, 3 =west */
			int next_cell = rand_num.nextInt(4);
			if (next_cell == 0){//north
				/*check if the next cell is occupied by another tank or out of boundary*/
				int north = next_row-1;
				if(	(north >= 0 && north < number_row) &&! (cells[north][next_col].getIsTank())){
					t.addCells(cells[north][next_col]);
					cells[north][next_col].setIsTank(true);
				}
			}	
			else if (next_cell == 1){
				int east = next_col+1;
				if((east >= 0&&east < number_column)
						&&! cells[next_row][east].getIsTank()){
					t.addCells(cells[next_row][east]);
					cells[next_row][east].setIsTank(true);
				}
			}
			else if (next_cell == 2){
				int south = next_row+1;
				if((south >=0 && south<number_row)
						&&! cells[south][next_col].getIsTank()){
					t.addCells(cells[south][next_col]);
					cells[south][next_col].setIsTank(true);
				}
			}
			else if (next_cell == 3){
				int west = next_col-1;
				if((west >= 0 &&west < number_column)
						&&! cells[next_row][west].getIsTank()){
					t.addCells(cells[next_row][west]);
					cells[next_row][west].setIsTank(true);
				}
			}
		}
		if(t.getCells().size() == 4){
			return true;
		}
		else{
			return false;
		}
	}
	public int isFortressHit(int row, int col){

		notifyListeners();
		return fortress.fireCannons(cells[row][col], tanks);

	}

	public ArrayList<Integer> getTankDamage_notFire(){
		ArrayList<Integer> damages = new ArrayList<Integer>();
		for (Tank t : tanks){
			damages.add(t.current_damge());
		}
		return damages;
	}

	public ArrayList<Integer> getEachTankDamage(){
		ArrayList<Integer> damages = new ArrayList<Integer>();
		for(Tank t : tanks){
			damages.add(t.fire(getFortress()));
		}
		notifyListeners();
		return damages;
	}

	public String inGameCells(int row, int col){
		String symbol = new String();
		if (cells[row][col].getHasBeenAttacked()){
			if(cells[row][col].getIsTank()){
				symbol = "X";
			}else{
				symbol = ".";
			}
		}else{
			symbol = "~";
		}
		return symbol;
	}
	public String afterGameCells(int row, int col){
		String symbol = new String();
		if (cells[row][col].getIsTank() && cells[row][col].getHasBeenAttacked()){
			symbol = "X";
		} else if(cells[row][col].getIsTank() && !cells[row][col].getHasBeenAttacked()) {
			symbol = "T";
		}
		else{
			if(cells[row][col].getHasBeenAttacked() && !cells[row][col].getIsTank()){
				symbol = ".";
			}else{
				symbol = " ";
			}			
		}
		return symbol;
	}
	public int isWon(){
		/*1 == lose, -1 == win*/
		int game_continue =0;
		if(fortress.getHp() <= 0){	 		
			return 1;
		}
		else{
			for(Tank t : tanks){
				if(t.getNumber_undamaged_cell() != 0){
					return game_continue;
				}
			}
			return -1; //win
			//			for(int i =0; i<number_tanks;i++){
			//				if (tanks[i].getNumber_undamaged_cell()!=0){
			//					isAllTankDown = false;
			//				}
			//				if (isAllTankDown){
			//					return -1;
			//				}
			//			}	
		}
	}

	public int getRow(){
		return number_row;
	}

	public int getCol(){
		return number_column;
	}

	public Fortress getFortress() {
		return fortress;
	}

	public void setFortress(Fortress fortress) {
		this.fortress = fortress;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	public static void addChangeListener (ChangeListener listener){
		listeners.add(listener);
	}

	public void notifyListeners() {
		if (listeners == null) {
			return; 
		}
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners){
			listener.stateChanged(event);
		}

	}

}
