package planewar;

import java.util.Random;

public class CommonPlane extends GameObject implements Enemy {
	
	// the moving speed of the common plane
	int xSpeed = 1;
	int xAbsoluteSpeed = 1;
	int ySpeed = 1;
	
	public CommonPlane(){
		int random = new Random().nextInt(PlaneWar.WIDTH - 2 * PlaneWar.commonplane.getWidth()) + PlaneWar.commonplane.getWidth();
		this.x = random;
		this.y = -PlaneWar.commonplane.getHeight();
		this.life = 1;
		
		this.image = PlaneWar.commonplane;
	}

	@Override
	public void step() {
		this.x += xSpeed;
		this.y += ySpeed;
		
		if (this.x > PlaneWar.WIDTH - PlaneWar.commonplane.getWidth()) {
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

