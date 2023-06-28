package BB;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	
	public int mapArray [][];
	
	public int brickWidth;
	public int  brickHeight;
	
	public MapGenerator(int row, int col)
	{
		mapArray = new int [row][col];
		for(int i = 0; i< mapArray.length ; i++)
		{
			for( int j=0; j< mapArray[0].length; j++)
			{
				mapArray[i][j] = 1;
			}
		}
		
		brickWidth = 540/col;
		brickHeight = 150/row;
		
	}
	
	public void draw(Graphics2D g)
	{
		for(int i = 0; i< mapArray.length ; i++)
		{
			for( int j=0; j< mapArray[0].length; j++)
			{
				if(mapArray[i][j] > 0)
				{
					g.setColor(Color.blue);
					// 80 and 50 is to position of bricks
					g.fillRect(j*brickWidth +80, i*brickHeight +50, brickWidth, brickHeight);
					
					//dividing the bricks
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black); //color in between bricks
					g.drawRect(j*brickWidth +80, i*brickHeight +50, brickWidth, brickHeight);
					
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		mapArray[row][col]= value;
	}

}
