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
	private int frameWidth = 700; 
	private int frameHeight = 600;
	
	private boolean play =false;
	private int score =0;
	
	//Initial bricks
	private int row = 10;
	private int col = 20;
	private int totalBricks= row*col;
	
	private Timer timer;
	private int delay = 0;	//speed of the movement of the ball
	
	private int playerX = frameWidth/2; //x  position of the movable bar
	private int playerY = frameHeight-50; //y position of the movable bar 
	
	Random random = new Random();
	
	//Initial position of the ball
	private int ballPosX = random.nextInt((frameWidth-50) - 10) + 10;;
	private int ballPosY = random.nextInt((frameHeight-100) - (frameHeight-300)) + (frameHeight-300);

	private int ballXdir=-1;
	private int ballYdir=-2;
	
	private MapGenerator map;
	
	public GamePlay()
	{
		map = new MapGenerator(row,col); //Bricks Generate
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay , this);
		timer.start();
	}
	
	public void paint(Graphics g)
	{
		 //background paint
		 g.setColor(Color.cyan);
		 g.fillRect(1, 1, frameWidth, frameHeight);
		 
		 //display of map
		 map.draw((Graphics2D)g);
		 
		 g.setColor(Color.yellow);
		 g.fillRect(0, 0, 3, 592);
		 g.fillRect(0, 0, 692, 3);
		 g.fillRect(691, 0, 3, 592);
		 
		 g.setColor(Color.blue);
		 g.fillRect(playerX,  playerY,  100, 8);
		 
		 g.setColor(Color.red);
		 g.fillOval(ballPosX, ballPosY, 20, 20);
		 
		 //score Display
		 g.setColor(Color.black);
		 g.setFont(new Font("serif", Font.BOLD, 25));
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
		// TODO Auto-generated method stub
		
		timer.start();
		
		if(play)
		{
			// Ball - Pedal interaction
			if(new Rectangle(ballPosX, ballPosY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			for(int i=0; i< map.map.length;i++)
			{
				for(int j= 0; j< map.map[0].length;j++)
				{
					if(map.map[i][j] >0)
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
			
			if(ballPosX > 670)
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
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				score =0;
				totalBricks = row*col;
				map = new MapGenerator(row,col);
				
				repaint();
			}
		}
		
	}
	
	
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
