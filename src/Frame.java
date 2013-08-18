import javax.swing.*;
import java.awt.*;



public class Frame extends JFrame{
public static String title = "MyTowerDefence";
public static int width=700,height=550;
public static Dimension size=new Dimension(width,height);





public Frame(){
	setTitle(title);
	setSize(size);
	setResizable(false);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	
	
	
	init();
}
public void init(){
	setLayout(new GridLayout(1,1,0,0));
	setVisible(true);
	
	Screen screen = new Screen(this);
	add(screen);
	
}

public static void main (String  args[]){
Frame frame = new Frame();
 

	
	
}




}
