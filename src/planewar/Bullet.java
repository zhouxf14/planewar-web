package planewar;

public class Bullet extends GameObject {

	// the speed of the bullet
//	static int a = PlaneWar.proplane.getWidth() + 1;
	static int a = 6;
	static int speed = a;
	public static int order;

	Bullet(int x,int y){
		this.x = x;
		this.y = y;
		this.notUsed = true;
		this.image = PlaneWar.bullet;
	}
	
	@Override
	public void step() {
		// TODO Auto-generated method stub
		this.y-= speed;	
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return this.getY() < 0;
	}

}
