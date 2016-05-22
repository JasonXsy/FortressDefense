package game_logic;

import java.util.LinkedList;

/*Tank objects keeps tracks of its cell components
 * and attack fortress in alternative turns * 
 */

public class Tank {
private int number_of_cells ; 
private int damage_of_4_cell = 20;
private int damage_of_3_cell = 5; 
private int damage_of_2_cell = 2; 
private int damage_of_1_cell = 1; 
private int damage_of_0_cell = 0;
private int number_undamaged_cell; 
private LinkedList<Cell>	tank_cells;

public Tank (int cell_number){
	setNumber_of_cells(cell_number);
	setNumber_undamaged_cell(cell_number);
	tank_cells = new LinkedList<Cell>();
}

public void addCells(Cell c){
	tank_cells.addLast(c);
}
public void setNumber_undamaged_cell(int a ){
	number_undamaged_cell = a;
}
public int getNumber_undamaged_cell(){
	return number_undamaged_cell;
}
public int current_damge(){
	int damage = 0;
	if(number_undamaged_cell == 4){
		damage = damage_of_4_cell;
	}
	else if (number_undamaged_cell == 3){
		damage = damage_of_3_cell;
	}
	else if(number_undamaged_cell == 2){
		damage = damage_of_2_cell;
	}
	else if (number_undamaged_cell == 1){
		damage = damage_of_1_cell;
	}
	else if (number_undamaged_cell == 0){
		damage = damage_of_0_cell;
	}
	return damage;
}
public int fire(Fortress fortress){
	int damage = 0;
	if(number_undamaged_cell == 4){
		fortress.setHp(fortress.getHp() - damage_of_4_cell);
		damage = damage_of_4_cell;
	}
	else if (number_undamaged_cell == 3){
		fortress.setHp(fortress.getHp() - damage_of_3_cell);
		damage = damage_of_3_cell;
	}
	else if(number_undamaged_cell == 2){
		fortress.setHp(fortress.getHp() - damage_of_2_cell);
		damage = damage_of_2_cell;
	}
	else if (number_undamaged_cell == 1){
		fortress.setHp(fortress.getHp() - damage_of_1_cell);
		damage = damage_of_1_cell;
	}
	else if (number_undamaged_cell == 0){
		fortress.setHp(fortress.getHp() - damage_of_0_cell);
		damage = damage_of_0_cell;
	}
	return damage;
}

public LinkedList<Cell> getCells() {
	return tank_cells;
}

public void setCells(LinkedList<Cell> cells) {
	this.tank_cells = cells;
}

public int getNumber_of_cells() {
	return number_of_cells;
}

public void setNumber_of_cells(int number_of_cells) {
	this.number_of_cells = number_of_cells;
}


}
