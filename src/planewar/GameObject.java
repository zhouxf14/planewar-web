package planewar;

import java.awt.image.BufferedImage;

public abstract class GameObject {
	
	// the position of the game object
	public int x;
	public int y;
	
	// the size of the game object
	public int width;
	public int height;
	
	//the image of the game object
	public BufferedImage image;
	
	// the life of the game object
	public int life;
	
	public boolean notUsed;
	
	//get the position of the game object	
	public int getX() {
		return x;
	}	
	public int getY() {
		return y;
	}
	
	// set the position of the game object
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	// get the width and height of the game object
	public int getWidth() {
		return this.image.getWidth();
	}
	public int getHeight() {
		return this.image.getHeight();
	}
	
	// set the width and height of the game object
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		 this.height = height;
	}
	
	// move the game object for a step
	public abstract void step();
	
	// judge whether the game object our of bounds
	public abstract boolean outOfBounds();
	
	// get shot
	public boolean getShot(Bullet bullet) {
		int x1 = this.x;
		int x2 = this.x + this.getWidth();
		
		int y1 = this.y;
		int y2 = this.y + this.getHeight();
		
		int x = bullet.getX();
		int y = bullet.getY();
		
		return (x < x2 && x > x1) && (y < y2 && y >y1);
	}
	

}

