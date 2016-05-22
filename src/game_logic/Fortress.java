package game_logic;

/*Fortress class takes game board's coordinate
 * to fire cannons on the map. and keep hp count
 *  
 */

public class Fortress {
	private int hp;
	public Fortress(int strength){
		setHp(strength);
	}
	public void setHp(int strength){
		hp = strength;
	}

	public int getHp(){
		if (hp >= 0){
			return hp;
		}
		else{
			return 0;
		}
	}
	public int fireCannons(Cell cell, Tank[] tanks){
		if(!cell.getHasBeenAttacked()){//he cell has not been attacked, check if it is tank
			cell.setHasBeenAttacked(true);
			if(cell.getIsTank()){// it is part of tank
				cell.setTankHit(true);
				for(Tank t: tanks){
					if(t.getCells().contains(cell)){
						t.setNumber_undamaged_cell(t.getNumber_undamaged_cell() -1 );
					}
				}
				return 1; //hit
			}
			else{
				return -1;//miss
			}
			

		}
		else {// the cell has been attacked, next check if it is tank and 
			if (cell.isTankHit()){
				return 0; //hit, but do nothing
			}
			else {
				return -1;
			}
		}
	}
}
