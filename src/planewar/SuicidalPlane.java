package planewar;

import java.util.Random;

public class SuicidalPlane extends GameObject implements Enemy {
	
	// the moving speed of the suicidal plane
	int speed = 1;
	
	public SuicidalPlane(int x){
//		int random = new Random().nextInt(PlaneWar.WIDTH - 2 * PlaneWar.suicidalplane.getWidth()) + PlaneWar.suicidalplane.getWidth();
		this.x = x;
		this.y = -this.height;
		this.life = 1;		
		this.image = PlaneWar.suicidalplane;
	}

	@Override
	public void step() {
		this.y += speed;
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.getY() > PlaneWar.HEIGHT;
	}
	
	public int getScore() {
		return 1;
	}
	
	public int getLife() {
		return 1;
	}
}

