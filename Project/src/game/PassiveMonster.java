package game;

import org.newdawn.slick.Image;

public abstract class PassiveMonster extends Monster {

	public PassiveMonster(int x, int y, float speed, Image sprite, Map map) {
		super(x, y, speed, sprite, map);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void see(Player player) {
		// TODO Auto-generated method stub
		
	}

}
