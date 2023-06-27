package BB;

import javax.swing.JFrame;

public class Main {
	
	//Game frame size
	public static int frameWidth = 700;
	public static int frameHeight = 600;

	public static void main(String[] args) {
		
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		
		obj.setBounds(450, 50,frameWidth, frameHeight); //size of the frame
		obj.setTitle("Brick Breaker");	//Title of the frame
		obj.setResizable(false);		// Is frame Re-sizable or not
		obj.setVisible(true);			//Frame visible
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//action on close option
		
		obj.add(gamePlay);
	}

}
