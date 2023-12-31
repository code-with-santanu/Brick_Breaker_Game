package BB;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay  extends JPanel implements KeyListener,ActionListener {
	
	//Game frame size
	private final int frameWidth = 700; 
	private final int frameHeight = 600;
	
	private boolean play =false;
	private int score;
	
	//Initial bricks
	private final int row = 10;
	private final int col = 20;
	private int totalBricks;
	
	private Timer timer;
	private int delay = 0;	//speed of the movement of the ball
	
	//Movable Pedal data
	private int playerX;	//x  position of the movable bar
	private int playerY;    //y position of the movable bar 
	private final int playerWidth = 100;
	private final int playerHeight = 8;
	
	Random random = new Random();
	
	//Initial position of the ball
	private int ballPosX;
	private int ballPosY;
	private final int ballWidth = 20;
	private final int ballHeight = 20;
	
	private int ballXdir;
	private int ballYdir;
	
	private MapGenerator map;
	
	public GamePlay()
	{
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay , this);
		
		initGame();
	}
	
	public void initGame()
	{
		map = new MapGenerator(row,col); //Bricks Generate
		totalBricks = row*col;
		
		Random random1 = new Random();
		ballPosX = random1.nextInt((frameWidth-50) - 10) + 10;;
		ballPosY = random1.nextInt((frameHeight-100) - (frameHeight-300)) + (frameHeight-300);
		ballXdir = -1;
		ballYdir = -2;
		
		playerX = frameWidth/2;
		playerY = frameHeight-50;
		
		score =0;
	
		timer.start();
		
	}
	
	public void paint(Graphics g)
	{
		 //background paint
		 g.setColor(Color.black);
		 g.fillRect(1, 1, frameWidth, frameHeight);
		 
		 //display of bricks
		 map.draw((Graphics2D)g);
		 
		//Border paint
		 g.setColor(Color.red);
		 g.fillRect(0, 0, 3, frameHeight);	//left Border
		 g.fillRect(0, 0, frameWidth, 3);	//top Border
		 g.fillRect((frameWidth-16), 0, 3, frameHeight);	//right Border
		 
		//Movable Pedal paint
		 g.setColor(Color.cyan);
		 g.fillRect(playerX,  playerY,  playerWidth, playerHeight);
		 
		//Ball paint
		 g.setColor(Color.green);
		 g.fillOval(ballPosX, ballPosY, 20, 20);
		 
		 //score Display
		 g.setColor(Color.yellow);
		 g.setFont(new Font("serif", Font.BOLD, 30));
		 g.drawString("" + score, 590, 30);
		 
		 
		 if(totalBricks <= 0)
		 {
			 play = false;
			 ballXdir = 0;
			 ballYdir = 0;
			 g.setColor(Color.green);
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("You Win, Score: " + score, 190, 300);
			 
			 g.setFont(new Font("serif", Font.BOLD, 20));
			 g.drawString("Press Enter to Restart..", 230, 350);
		 }
		 
		 //Display game over
		 if(ballPosY > 570)
		 {
			 play = false;
			 ballXdir = 0;
			 ballYdir = 0;
			 g.setColor(Color.red);
			 g.setFont(new Font("serif", Font.BOLD, 30));
			 g.drawString("Game Over, Score: " + score, 190, 300);
			 
			 g.setFont(new Font("serif", Font.BOLD, 20));
			 g.drawString("Press Enter to Restart..", 230, 350);
			 
		 }
		 
		 g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//timer.start();
		
		if(play)
		{
			// Ball - Pedal interaction
			if(new Rectangle(ballPosX, ballPosY, ballWidth, ballHeight).intersects(new Rectangle(playerX, playerY, playerWidth, playerHeight)))
			{
				ballYdir = -ballYdir;
			}
			
			for(int i=0; i< map.mapArray.length;i++)
			{
				for(int j= 0; j< map.mapArray[0].length;j++)
				{
					if(map.mapArray[i][j] >0)
					{
						int bricksX = j*map.brickWidth +80;
						int bricksY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						
						//creating imaginary rectangle to check the intersect between ball and brick
						Rectangle rect = new Rectangle(bricksX, bricksY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
						Rectangle brickRect = rect;
						
						//vanish the brick and increase the score
						if(ballRect.intersects(brickRect))
						{
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX +1 >= ballRect.x + brickRect.width)
							{
								ballXdir =-ballXdir;
							}
							else
							{
								ballYdir = -ballYdir;
							}
						}
						
					}
				}
			}
			
			//Movement of the ball
			ballPosX += ballXdir;
			ballPosY += ballYdir;
			
			
			if(ballPosX < 0)
			{
				ballXdir = -ballXdir;
			}
			
			if(ballPosY < 0)
			{
				ballYdir = -ballYdir;
			}
			
			if(ballPosX > (frameWidth-30))
			{
				ballXdir = -ballXdir;
			}
		}
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerX > frameWidth-120)
			{
				playerX = frameWidth-120;
			}
			else
			{
				moveRight();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(playerX < 10)
			{
				playerX =10;
			}
			else
			{
				moveLeft();
			}
		}
		
		//Restart by pressing enter key
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(play == false)
			{
				play = true;
				
				initGame();
				repaint();
			}
		}
		
		
		//Pause the game by pressing P Key
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			if(timer.isRunning())
			{
				timer.stop();
			}
			else
			{
				timer.start();
			}
		}
		
	}
	
    //Movement of the Pedal
	public void moveRight()
	{
		play =true;
		playerX += 25;
	}
	
	
	public void moveLeft()
	{
		play =true;
		playerX -= 25;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
