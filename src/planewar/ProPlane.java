package planewar;

import java.util.Random;

public class ProPlane extends GameObject implements Enemy {
	
	// the moving speed of the pro plane
	int xSpeed = 1;
	int xAbsoluteSpeed = 1;
	int ySpeed = 1;
	
	public ProPlane(){
		int random = new Random().nextInt(PlaneWar.WIDTH - 2 * PlaneWar.proplane.getWidth()) + PlaneWar.proplane.getWidth();
		this.x = random;
		this.y = -PlaneWar.proplane.getHeight();
		this.life = 2;
		this.image = PlaneWar.proplane;
	}

	@Override
	public void step() {
		this.x += xSpeed;
		this.y += ySpeed;
		
		if (this.x > PlaneWar.WIDTH - PlaneWar.proplane.getWidth()) {
			xSpeed = (-1) * xAbsoluteSpeed; 
		}
		if (this.x < 0) {
			xSpeed = xAbsoluteSpeed;
		}
		
	}

	@Override
	public boolean outOfBounds() {
		
		return this.getY() > PlaneWar.HEIGHT;
	}
	

	public int getScore() {
		return 3;
	}
	
	public int getLife() {
		return 1;
	}
	
}

