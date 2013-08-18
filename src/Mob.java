import java.awt.*;


public class Mob extends Rectangle{
	public  int mobSize = 52,xC,yC;
	public  int mobId = Value.mobair;
	public  Boolean inGame = false;
	public int mobwalk = 0;
	public int health;
	public int healthspace = 3,healthheight = 6;
	public int upward = 0,downward = 1,right=2,left=3;
	public int direction = right;
	public int danger=5;
	public int age=0;
	//public int addcoinsGreeny = 2;
	//public int addcoinsStony = 12;
	//public int addcoinsFirery = 25;
	public int looseframe=0,loosetime=0;
	public Boolean hasupward=false,hasdownward=false,hasright=true,hasleft=false,slowed = false;
	public Rectangle MobSquare = new Rectangle(x, y, width, height);
public Mob(){

	
	
}
public int walkframe=0,walkspeedoriginal=15,walkspeed=walkspeedoriginal;
public void Physics(){
	checkdeath();
	slowed=false;
	for (int y = 0 ; y < Screen.room.block.length; y++){
	for (int x = 0; x < Screen.room.block[0].length;x++){
	if(Screen.room.block[y][x].towerSquare.intersects(this) && Screen.room.block[y][x].AirId == Value.airTowerTar){
	slowed = true;
	break;
	
	}
	if(!slowed){walkspeed=walkspeedoriginal;}
	else if (slowed){walkspeed=35;}
	}	
	
	}
	
	if(walkframe >=walkspeed){
		
		if(direction==right){x++;}
		else if(direction==upward){
			y--;
		}
		else if(direction == downward){
			y++;
		}
		else if(direction==left){
			x--;
		}
		mobwalk++;
		if (mobwalk == Screen.room.blocksize){
			if(direction==right){
				xC++;
				hasright=true;
				
				}
					else if(direction==upward){
						yC--;
						
						hasupward=true;
					}
					else if(direction == downward){
						yC++;
					
						hasdownward=true;
						
					}
					else if(direction==left){
						xC--;
						hasleft=true;
						
					}
		if(!hasleft){	try {if(Screen.room.block[yC][xC+1].GroundId == Value.groundRoad){
				direction=right;
			}}catch(Exception e){}}
	    if(!hasright){try {if(Screen.room.block[yC][xC-1].GroundId == Value.groundRoad){
				direction=left;
			}}catch(Exception e){}}
		if(!hasdownward){	try {if(Screen.room.block[yC-1][xC].GroundId == Value.groundRoad){
				direction=upward;
			}}catch(Exception e){}}
		if(!hasupward){	try {if(Screen.room.block[yC+1][xC].GroundId == Value.groundRoad){
				direction=downward;
			}}catch(Exception e){}}
		if(Screen.room.block[yC][xC].AirId==Value.airCave){
			dealHealth(danger);
			deleteMob();
		}
			
			mobwalk = 0;	     

			age ++;
		}
	walkframe=0;
	hasleft=false;
	hasdownward=false;
	hasright=false;
	hasupward=false;
	}else {
		
		walkframe++;
		}
}
public void deleteMob(){
	inGame=false;
	mobwalk = 0;
	age=0;
	direction=right;
}
public void dealHealth(int loose){
	Screen.health-= loose;
}
public void loseHealth(int loose){
	health -=loose;
	
	}
public void checkdeath(){
	if(health<1){
		
		Screen.coins+=Value.Mobworth[mobId];
			
		
		Screen.Kills++;
		health=0;
		deleteMob();
	}
	}
public Boolean isDead(){
	if(inGame)return false;
	else return true;
		
	
	
}
public void spawnMob(int mobId){
	for(int y=0;y<Screen.room.block.length;y++){
		if(Screen.room.block[y][0].GroundId==Value.groundRoad){
			setBounds(Screen.room.block[y][0].x,Screen.room.block[y][0].y,mobSize,mobSize);
		xC=0;
		yC=y;
		}
	}
	
	this.mobId=mobId;
	this.health=mobSize;
	inGame=true;
	
}

	

public void draw(Graphics g){
	
		g.drawImage(Screen.tileset_mob[mobId],x,y,width,height,null);
		g.setColor(new Color(180,50,50));
		g.fillRect(x , y- (healthspace+healthheight), width, healthheight);
		g.setColor(new Color(50,180,50));
		g.fillRect(x , y- (healthspace+healthheight), health, healthheight);
	    g.setColor(new Color(0,0,0));
		g.drawRect(x , y- (healthspace+healthheight), health-1, healthheight-1);
		//g.drawRect(x, y, width, height);
}}