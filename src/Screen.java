import java.awt.*;
import java.awt.image.*;
import java.io.*;


import javax.swing.*;

public class Screen extends JPanel implements Runnable{
public Thread gameloop = new Thread(this);

public static int myWidth,myHeight;
public static Image[] tileset_ground = new Image[100];
public static Image[] tileset_air = new Image[100];
public static Image[] tileset_res = new Image[100];
public static Image[] tileset_mob = new Image[100];
public static Mob[] mobs=new Mob[150];
public static Save save;	
public static Room room ;
public static Store store;

public static Boolean isFirst=true;	
public static Boolean debug=false;	
public static Boolean less=false;
public static Boolean running=true;

public static Point mse = new Point(0,0);
public static int coins ,health=100,targetKills;

public static int mobraise= 15;
public static int Kills=0;	
public static int spawned=0;
public static int MomLevel=1;
	public Screen(Frame frame){
		frame.addMouseMotionListener(new KeyHandel());
		frame.addMouseListener(new KeyHandel() );
		
		
		
	gameloop.start();
}
	public void define(){
		room =  new Room();
		save = new Save();
		store = new Store();
		
		
		
		for (int i = 0;i<tileset_ground.length;i++){
			tileset_ground[i] = new ImageIcon("Res/tileset_ground.png").getImage();
			tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
			}
		for (int i = 0;i<tileset_air.length;i++){
			tileset_air[i] = new ImageIcon("Res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
			}
		tileset_res[0]=new ImageIcon("Res/cell.png").getImage();
		tileset_res[1]=new ImageIcon("Res/heart.png").getImage();
		tileset_res[2]=new ImageIcon("Res/coin.png").getImage();
		tileset_res[3]=new ImageIcon("Res/Kill.png").getImage();
		tileset_res[4]=new ImageIcon("Res/missle.png").getImage();
		tileset_mob[0]=new ImageIcon("Res/Mob1.png").getImage();
		tileset_mob[1]=new ImageIcon("Res/Mob2.png").getImage();
		tileset_mob[2]=new ImageIcon("Res/Mob3.png").getImage();
		

		tileset_air[5]=new ImageIcon("Res/tar.png").getImage();
		tileset_air[6]=new ImageIcon("Res/fire.png").getImage();

		for (int i = 0;i<mobs.length;i++){
			mobs[i]=new Mob();
		
			
		}
			loadLevel(1);
		
	}
	public final int spawntimefirst= 2300;
	public int spawntime=spawntimefirst;
	public int spawnframe=0;
	public int raise=0;
	public int raiseFrame=0;
	public int mobraisetime=spawntimefirst;
	public int mobraiseframe=0;
	public int greenystart=spawntimefirst;
	public int stoonystart=2000;
	public int greenyend=1500;
	public int Firerystart = 1000;
	public int stoonyend = 500;
	public int Fireryend=50;
	public int waitframe=0,waittime=5000;
	public void mobspawner(){
		System.out.println(spawntime);
		if(spawntime>100){
		if(mobraiseframe>mobraisetime){
			
			spawntime-=mobraise;
			mobraiseframe=0;
		}else{
			mobraiseframe++;
		}}
		
		if(spawnframe>=spawntime){
			
			
				
				
				spawnframe=0;
			
				
			for(int i = 0;i<mobs.length;i++){
				if(spawntime<greenystart && spawntime>stoonystart){
				
				   if (!mobs[i].inGame){
					
					mobs[i].spawnMob(Value.mobGreeny);
					spawned++;
					break;
					
				}}else if(spawntime<stoonystart && spawntime>greenyend){
					if(random(1, 100)<=50){
						if (!mobs[i].inGame){
						mobs[i].spawnMob(Value.mobGreeny);
						spawned++;
						break;	}
					}else {
						if (!mobs[i].inGame){
						mobs[i].spawnMob(Value.mobStony);
						spawned++;
						break;}	
					}
				}else if(spawntime<greenyend && spawntime>Firerystart){
					if (!mobs[i].inGame){
					mobs[i].spawnMob(Value.mobStony);
					spawned++;
					break;}
				}else if(spawntime<Firerystart && spawntime>stoonyend){
				if(random(1, 100)<=50){
					if (!mobs[i].inGame){
					mobs[i].spawnMob(Value.mobFirery);
					spawned++;
					break; }
				}else {
					if (!mobs[i].inGame){
						mobs[i].spawnMob(Value.mobStony);
					
					spawned++;
					break;}
				}
			}else if(spawntime<stoonyend && spawntime>Fireryend){
				if (!mobs[i].inGame){
				mobs[i].spawnMob(Value.mobFirery);
				spawned++;
				break;}
			}
				
			}
				
			
			}else {
			spawnframe += 1;
		}
	}
	
public void paintComponent(Graphics g){
	
	if (isFirst){
		myWidth = getWidth();
		myHeight = getHeight();
		
		define();
		isFirst = false;
	}
	
	g.setColor(new Color(50,50,50));
	
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(new Color (0,0,0));
	if(!isFirst && checkhealth()&&checkKills()){
		room.draw(g);//draws the room
	store.draw(g);
	}
	
	for (int i = 0;i<mobs.length;i++){
		if(mobs[i].inGame){
			mobs[i].draw(g);
			
		}
	}
	
	if(!checkhealth()){
		g.setColor(new Color(240,20,20,150));
		g.fillRect(0,0,myWidth,myHeight);
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Courier New",Font.BOLD,100));
	    g.drawString("Game Over",0,200);
	   // gameloop.stop();
	}
	
	if(!checkKills()){
		g.setColor(new Color(255,255,0,100));
		g.fillRect(0,0,myWidth,myHeight);
		//g.setColor(new Color(255,255,255));
		g.setFont(new Font("Courier New",Font.BOLD,90));
	    g.drawString("You have won!",0,200);
	   
	   if(waitframe>=waittime){
		   running = false;
		   spawntime=spawntimefirst;
		   waitframe=0;
		   MomLevel++;
		   loadLevel(MomLevel);}
	   else{
		   waitframe++;
	   }
	   
	    //gameloop.stop();
	}
}

public void run(){
	
	while (true){
		if(running){
		repaint();	
		if(!isFirst && checkhealth()&&checkKills()){
		
	    room.physic();
	    mobspawner();
	    for (int i = 0;i<mobs.length;i++){
	    	if(mobs[i].inGame){
	    		mobs[i].Physics();
	    	}
	    }
					}}
			
				try{
		Thread.sleep(1);
	}catch(Exception e){}		
				
	}}
public Boolean checkhealth(){
	if(health<0&&!debug){
		return false;
	}else{
		return true;
	}
}
public Boolean checkKills(){
	if(Kills>=targetKills){
		return false;
	}else{
		return true;
	}
}
public static int random(int low,int high){
	
	return (int) (Math.random() * (high - low) + low);
}
public static void loadLevel(int level){
	System.out.println("loading save :" +level);
room=null;
room=new Room();

	Kills=0;
		health=100;
		save.loadSave(new File ("Save/mission"+level+".alexdata"));
		for(int i =0;i<mobs.length;i++){
		mobs[i].deleteMob();
	}
		
	}
}



