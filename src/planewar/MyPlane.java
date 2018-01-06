package planewar;

public class MyPlane extends GameObject {
	// the time interval between two bullets
	int interval = 1000;
	
	MyPlane(){
		// the image for my plane
		this.image = PlaneWar.myplane0;
		// the life for my plane
		this.life = 10;
		// set the initial position of my plane
		this.x = 150;
		this.y = 600;
	}
	
	// ??? my plane shoot
	public Bullet shoot() {
		int xPoint = PlaneWar.myplane0.getWidth()/4;
		int yPoint = 10;
		Bullet bullet = new Bullet(xPoint * 2 + this.getX(), this.getY() - yPoint);
		return bullet;
	}
	
	// move my plane
	public void moveTo(int x,int y) {
//		this.setX(x - MyShoot.hero0.getWidth()/2);
//		this.setY(y - MyShoot.hero0.getHeight()/2);
		this.setX(x);
		this.setY(y);
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub	
	}

	@Override
	public boolean outOfBounds() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// reduce the life of my plane when my plane is pumped by enemy planes
	public void reduceLife() {
		life--;
	}
	
	// judge whether my plane out of bounds
	public boolean boundHero(GameObject gameObject) {		
		int x1 = gameObject.getX() - this.getWidth()/2;
		int x2 = gameObject.getX() + gameObject.getWidth() + this.getWidth()/2;
		
		int y1 = gameObject.getY() - this.getHeight()/2;
		int y2 = gameObject.getY() + gameObject.getHeight() + this.getHeight()/2;
		
		int x = this.getX() + this.getWidth()/2;
		int y = this.getY() + this.getHeight()/2;
		
		return (x > x1 && x < x2) && (y > y1 && y < y2);
	}

}

