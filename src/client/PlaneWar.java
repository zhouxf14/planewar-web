package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import planewar.Bullet;
import planewar.CommonPlane;
import planewar.Enemy;
import planewar.GameObject;
import planewar.MyPlane;
import planewar.ProPlane;
import planewar.SuicidalPlane;

/**
 * @author b_anhr
 *
 */
public class PlaneWar extends JPanel implements Runnable{

	// Common Class
	public MyPlane myPlane = new MyPlane();
	public MyPlane plane2 = new MyPlane();
	private GameObject[] flyings = {};
	// bullets of my plane
	private Bullet[] bullets = {};

//	public static JFrame frame = new JFrame();
	public static int lastBullet = 0;
	
    // Thread
    private Thread thread = null;
    private SetupConnection setupConnection = null;

    // sever and socket
    //socket
    //socket
    private Socket socket;
    private BufferedReader is;
    private PrintWriter os;

    // message
    private String message = "";

	// the images in the plane war
	public static BufferedImage suicidalplane;
	public static BufferedImage background;
	public static BufferedImage proplane;
	public static BufferedImage commonplane;
	public static BufferedImage bullet;
	public static BufferedImage gameover;
	public static BufferedImage myplane0;
	public static BufferedImage myplane1;
	public static BufferedImage pause;
	public static BufferedImage start;
	public static BufferedImage win;
	public static BufferedImage bulletUsed;

	// four game state
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	public static final int WIN = 4;
	// the initial game state is START
	private int state = START;

	// the size of game panel
	public static int WIDTH = 518;
	public static int HEIGHT = 708;

	static{
		// initialize the images in the plane game
		try {
			suicidalplane = ImageIO.read(planewar.PlaneWar.class.getResource("suicidalplane.png"));
			background = ImageIO.read(planewar.PlaneWar.class.getResource("background.png"));
			proplane = ImageIO.read(planewar.PlaneWar.class.getResource("proplane.png"));
			commonplane = ImageIO.read(planewar.PlaneWar.class.getResource("commonplane.png"));
			bullet = ImageIO.read(planewar.PlaneWar.class.getResource("bullet.png"));
			gameover = ImageIO.read(planewar.PlaneWar.class.getResource("gameover.png"));
			myplane0 = ImageIO.read(planewar.PlaneWar.class.getResource("myplane0.png"));
			myplane1 = ImageIO.read(planewar.PlaneWar.class.getResource("myplane1.png"));
			pause = ImageIO.read(planewar.PlaneWar.class.getResource("pause.png"));
			start = ImageIO.read(planewar.PlaneWar.class.getResource("start.png"));
			win = ImageIO.read(planewar.PlaneWar.class.getResource("win.png"));
			bulletUsed = ImageIO.read(planewar.PlaneWar.class.getResource("bullet2.png"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// draw the bullet
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			g.drawImage(bullets[i].image,bullets[i].getX(),bullets[i].getY(),null);
		}
	}
	
	// draw all the planes
	public void paintAirplane(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			g.drawImage(flyings[i].image,flyings[i].getX(),flyings[i].getY(),null);
		}
	}
	
	// draw the life of my plane, my scores and the life of enemy shot
	public void paintScoreAndLife(Graphics g) {
		g.setColor(new Color(0xfff000));
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24));
		g.drawString("SCORE " +score,10,25);
		g.drawString("LIFE " + plane2.life, 10,50);
		g.drawString("Enemy " + enemyLife, 10,75);
	}
	
	// draw the game state images
	public void paintState(Graphics g) {
		switch (this.state) {
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameover,0,0,null);
			break;
		case WIN:
			g.drawImage(win, 0, 0, null);
			break;
		}
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		// draw the background
		g.drawImage(background,0,0,null);
		// draw my plane(client plane)
		g.drawImage(myplane0,this.plane2.getX() + myplane0.getWidth(),this.plane2.getY(), null);
		// server plane
		g.drawImage(myplane0,this.myPlane.getX(),this.myPlane.getY(), null);
		// draw the bullets
		paintBullets(g);
		// draw all enemy planes
		paintAirplane(g);
		// draw the scores and life
		paintScoreAndLife(g);
		// draw the game state
		paintState(g);
	}

	public void step() {
		// one step for a bullet
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}
		// one step for a plane
		for (int i = 0; i < flyings.length; i++) {
			flyings[i].step();
		}
	}

	int bulletIndex = 0;			
	public void shootAction() {
		bulletIndex ++;
		KeyAdapter keyBullet = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (state == RUNNING) {
					if (e.getKeyCode() == e.VK_SPACE) {	
						int temp = bulletIndex;
						int interval = temp - lastBullet;
						if (interval > 20) {
							//The minimal shoot interval is 20 * 50ms = 1s
							Bullet bulletNew = plane2.shoot();
							int l = bullets.length;
							bullets = Arrays.copyOf(bullets, l + 1);
							bullets[l] = bulletNew;
							lastBullet = temp;
//							System.arraycopy(bulletsTem, 0, bullets, bullets.length - bulletsTem.length, bulletsTem.length);
						}
					}
				}
			}
		};
		this.addKeyListener(keyBullet);
	}

	public GameObject nextGameObject() {
		int x = new Random().nextInt(10);
		if (x <= 2) {
			return new ProPlane();
		}else if (x <= 5){
			return new SuicidalPlane(plane2.getX());
		}else {
			return new CommonPlane();
		}
	}
	
	// the generation of enemy planes
	int objIndex = 0;			// set up the interval of enemy planes generation
	public void enterFlyings() {
		objIndex++;
		if (objIndex % 60 == 0) {				// generation interval 60 * 50ms = 3s
			// control the proportion of different kinds of enemy planes
			GameObject flyObject = nextGameObject();
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = flyObject;
		}
	}
	
	// delete the flying objects out of bounds
	public void deleteOutOfBounds() {
		// delete the bullets out of bounds
		Bullet[] bulletsTem = new Bullet[bullets.length];
		int index = 0;
		for (int i = 0; i < bullets.length; i++) {
			if (!bullets[i].outOfBounds()) {
				bulletsTem[index] = bullets[i];
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsTem, index);
		// go through all the game objects
		GameObject[] flyObjectsTem = new GameObject[flyings.length];
		int objIndex = 0;
		for (int i = 0; i < flyings.length; i++) {
			if (!flyings[i].outOfBounds()) {
				flyObjectsTem[objIndex] = flyings[i];
				objIndex++;
			}
		}
		flyings = Arrays.copyOf(flyObjectsTem, objIndex);
	}
	
	// delete the enemy planes
	public void deleteBreakFly(GameObject fly) {
		flyings[flyings.length - 1] = fly;

		flyings = Arrays.copyOf(flyings, flyings.length - 1);
	}

	/**
	 * delete the flying objects
	 * @param flyObject    		the flying object shot
	 * @param index				the index of the flying object shot
	 */
	public void deleteFlyObj(GameObject flyObject,int index) {
		GameObject f = flyings[flyings.length - 1];
		flyings[flyings.length - 1] = flyObject;
		flyings[index] = f;

		flyings = Arrays.copyOf(flyings, flyings.length - 1);
	}
	
	public void deleteBullet(Bullet bullet) {
		bullet.image = bulletUsed;
		bullet.notUsed = false;
	}

	// bullets shoot the enemy plane
	public void boundBullet(Bullet bu, int indexBu) {
		int index = -1;				// the index of the enemy plane
		for (int i = 0; i < flyings.length; i++) {
			GameObject f = flyings[i];
			if (f.getShot(bu)) {
				deleteBullet(bullets[indexBu]);
				index = i;
				break;
			}
		}
		if (index != -1) {
			GameObject gameObject = flyings[index];
			if (gameObject instanceof Enemy) {
				Enemy enemy = (Enemy)gameObject;
				score += enemy.getScore();
				gameObject.life = gameObject.life - 1;
				if (gameObject.life == 0) {
					enemyLife += enemy.getLife();
					deleteFlyObj(gameObject,index);
				} 
			}
		}
	}

	int score = 0;
	int enemyLife = 0;

	//bound event
	public void boundAction() {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			// whether the bullet has shot an enemy plane
			if (b.notUsed) {
				boundBullet(b, i);
			}
		}
	}
	
	// bound of my plane
	public void checkGameOverAction() {		
		if (isGameOver()) {
			this.state = GAME_OVER;
	        message = "State|"+ state;
	        os.println(message);
		}
		if (isWin()) {
			this.state = WIN;
	        message = "State|"+ state;
	        os.println(message);
		}
	}
	
	// judge whether the game is over or not
	public boolean isGameOver() {
		for (int i = 0; i < flyings.length; i++) {
			GameObject f = flyings[i];

			if (plane2.boundHero(f)) {
				plane2.reduceLife();
				deleteFlyObj(f, i);
			}
		}
		return this.plane2.life <= 0;
	}
	
	public boolean isWin() {
		if (enemyLife >= 20) {
			return true;
		} else {
			return false;
		}
	}
	
	// start the plane war
	public void action() {		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (state == RUNNING) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						int y = plane2.getY();
						int x = plane2.getX() - 10;
						if (x + 3*PlaneWar.myplane0.getWidth()/2 > 0) {
							plane2.moveTo(x, y);
						} 
					}
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						int x = plane2.getX() + 10;
						int y = plane2.getY();
						if (x + 3*PlaneWar.myplane0.getWidth()/2 < WIDTH) {
							plane2.moveTo(x, y);
						}
					}
					else if (e.getKeyCode() == KeyEvent.VK_UP) {
						int x = plane2.getX();
						int y = plane2.getY() - 10;
						if (y + PlaneWar.myplane0.getHeight()/2 > 0) {
							plane2.moveTo(x, y);
						}
					}
					else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
						int x = plane2.getX();
						int y = plane2.getY() + 10;
						if (y + PlaneWar.myplane0.getHeight()/2 < HEIGHT) {
							plane2.moveTo(x, y);
						}
					}
			        message = "Plane2|"+plane2.getX()+"|"+ plane2.getY();
			        os.println(message);
				}
			}
		});
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
//				super.mouseMoved(e);
//				if (state == RUNNING) {
//					myPlane.moveTo(e.getX()- PlaneWar.myplane0.getWidth()/2, e.getY()- PlaneWar.myplane0.getHeight()/2);
//				}
			}

			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					enemyLife = 0;
					myPlane = new MyPlane();
					plane2 = new MyPlane();
					flyings = new GameObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				case WIN:
					score = 0;
					enemyLife = 0;
					myPlane = new MyPlane();
					plane2 = new MyPlane();
					flyings = new GameObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				case RUNNING:
					state = PAUSE;
					break;
				case PAUSE:
					state = RUNNING;
					break;
				}
		        message = "State|"+ state;
		        os.println(message);
			}
			
			// the game is pause when the mouse is out of the game panel
//			public void mouseExited(MouseEvent e) {
//				if (state == RUNNING) {
//					state = PAUSE;
//				}
//			}
//
//			// the game restart when the mouse get into the game panel again
//			public void mouseEntered(MouseEvent e) {
//				if (state == PAUSE) {
//					state = RUNNING;
//				}
//			}
		};

		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		
	}
	
	public void run() {
			while (true) {
				if (state == RUNNING) {
					enterFlyings();
					// the move of flying objects
					step();
					// my plane shoot
					shootAction();
					// delete flying objects
					deleteOutOfBounds();
					// the bump of flying objects
					boundAction();
					// check the game state
					checkGameOverAction();
				}
				repaint();
				try {
					Thread.sleep(50); // repaint every 50 ms
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	
    public PlaneWar(String ip,int port){
        setFocusable(true);
        // message = "hello server!";
        listenServer(ip,port);
        action();
        thread = new Thread(this);
     // start game
        thread.start();
        setVisible(true);
    }

    private void listenServer(String ip, int port){
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        System.out.println("Connect port "+ip+":"+port);
                        socket = new Socket(ip,port);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    System.out.println("Accept Connection!");
                    setupConnection = new SetupConnection();
                    setupConnection.start();
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private class SetupConnection extends Thread{
        public void run(){
            try{
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
                while (true){
                    try{
                       os.println(message);
                       String s = is.readLine();
                       if(s == null){
                    	   continue;
                    	   }
                       System.out.println(s);
                       if(s.startsWith("Close")) {
                    	   socket.close();
                    	   is.close();
                    	   os.close();
                    	   System.exit(0); // ？
                    	   }
                       if(s.startsWith("Plane1")) {
                    	   String ss[] = new String[3];
                    	   ss = s.split("\\|");
//                    	   System.out.println(ss[1]);
                    	   int x = Integer.parseInt(ss[1]);
                    	   int y = Integer.parseInt(ss[2]);
                    	   myPlane.moveTo(x, y);
                    	   }
                       if (s.startsWith("State")) {
                       	String ss[] = new String[2];
                       	ss = s.split("\\|");
                       	System.out.println(ss[1]);
                       	state = Integer.parseInt(ss[1]);
                       }
                       }catch(IOException e) {
                    	   e.printStackTrace();
                    	   }
                    }
                }catch (IOException e) {
                	e.printStackTrace();
                	}
            }
        }
    }
