import java.awt.*;

public class Block extends Rectangle{

public int GroundId;
public int AirId;
public Rectangle towerSquare;

public int towerSquareSize=110;
public int shotMob = -1;
public Boolean shooting=true;
public int loosetime=80,looseframe=0,flyframe=0,flytime=20,reloadtime=5000,reloadframe=0;
public Rectangle BallSquare;
public int mobToShoot =-1;
public int X,Y,Ballx,Bally;

public Boolean isTower=false,flying=false,reloaded=true;


public Block(int x,int y,int width,int height,int GroundId,int airId){
	
		
	
	setBounds(x,y,width,height);
	towerSquare=new Rectangle(x-(towerSquareSize/2),y-(towerSquareSize/2),width+(towerSquareSize),height+(towerSquareSize));
	this.GroundId=GroundId;
	this.AirId=airId;
	this.X=x;
	this.Y=y;
	Ballx=X+width/2;
	Bally=Y+height/2;
	BallSquare=new Rectangle(Ballx-20,(int)Bally-20,40,40);

}
public void draw(Graphics g){
	g.drawImage(Screen.tileset_ground[GroundId],x,y,width,height,null);
	
	
	if(AirId!=Value.airAir){
		g.drawImage(Screen.tileset_air[AirId],x,y,width,height,null);

	}
	
}
public void physics(){
	BallSquare=new Rectangle(Ballx-20,Bally-20,40,40);
	if(shotMob!=-1 && towerSquare.intersects(Screen.mobs[shotMob])&& AirId!=Value.airTowerCannon){
		shooting=true;
	}else {
		shooting = false;
	}
	if(shotMob!=-1 && towerSquare.intersects(Screen.mobs[shotMob])&& AirId==Value.airTowerCannon && shotMob !=-1 && reloaded){
		flying=true;
		shooting=true;
	}
	
	
	
	
	if(!shooting){
	
	if (isTower  ){
	 findTarget();}	
	if(AirId==Value.airTowerFireTrap){
			for(int i=0;i<Screen.mobs.length;i++){
				if(Screen.mobs[i].inGame){
					if(towerSquare.intersects(Screen.mobs[i])&& Screen.mobs[i].mobId !=Value.mobFirery){
						Screen.mobs[i].loosetime=Value.MobHealth[Screen.mobs[i].mobId];
						if(Screen.mobs[i].mobId==Value.mobStony){
							Screen.mobs[i].loosetime=(int)(Screen.mobs[i].loosetime*1.5);	
						}
						Screen.mobs[i].loosetime=Screen.mobs[i].loosetime/Value.TowerDamage[AirId];
						if (Screen.mobs[i].looseframe>=Screen.mobs[i].loosetime){
							Screen.mobs[i].loseHealth(1);
							Screen.mobs[i].looseframe=0;		}
					
					else {
						Screen.mobs[i].looseframe+=1;
					}
					}
				}
		}
		}



	}

	if(shooting && AirId != Value.airTowerCannon){
		Screen.mobs[shotMob].loosetime=Value.MobHealth[Screen.mobs[shotMob].mobId];
		
		Screen.mobs[shotMob].loosetime=Screen.mobs[shotMob].loosetime/Value.TowerDamage[AirId];
		
			
			if (Screen.mobs[shotMob].looseframe>=Screen.mobs[shotMob].loosetime){
				Screen.mobs[shotMob].loseHealth(1);
				Screen.mobs[shotMob].looseframe=0;		}
		
		else {
			Screen.mobs[shotMob].looseframe+=1;
		}
		
		
		if(Screen.mobs[shotMob].isDead()){
			//getMoney(Screen.mobs[shotMob].mobId);
			shooting=false;
			shotMob=-1;
			mobToShoot=-1;
		
	}} if(AirId == Value.airTowerCannon && shotMob!=-1){
		
	if(reloaded){
	

	if (flyframe >= flytime){
		flyframe=0;
		
		if(Bally>Screen.mobs[shotMob].y+Screen.mobs[shotMob].width/2){
			Bally= Bally-1;
		}else if(Bally<Screen.mobs[shotMob].y+Screen.mobs[shotMob].width/2){
			Bally=Bally+1;
		}
		
		if(Ballx>Screen.mobs[shotMob].x+Screen.mobs[shotMob].height/2){
			Ballx= Ballx-1  ;
		}else if(Screen.mobs[shotMob].x+Screen.mobs[shotMob].height/2> Ballx){
			Ballx= Ballx+1;
		}
	    BallSquare=null;
		BallSquare=new Rectangle(Ballx-20,Bally-20,40,40);
		
	}
else {
flyframe++;
	}
	
	
	if(shotMob !=-1 && AirId == Value.airTowerCannon && flying){
		
		for (int p = 0;p<Screen.mobs.length;p++){
			
			if(Screen.mobs[p].intersects(BallSquare)&&Screen.mobs[p].inGame){
			
		Screen.mobs[p].loseHealth(26);
	
		shooting=false;
	    flying = false;
	    reloaded=false;
	    shotMob=-1;
	    resetBall();
		
			}}		}}
	}
	if(!reloaded){Reload();

	}
	
	if(flying){
		if(!BallinRoom()){
			resetBall();
		}
			
		
	}
	
	}


public void fight(Graphics g){
	if(Screen.debug){
	if (isTower){
		g.setColor(new Color(0,255,255));
		g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
	}}
	if (shooting&& shotMob !=-1 && AirId==Value.airTowerBlueLaser ){
		g.setColor(new Color(0,255,255));
		g.drawLine(x+(width/2), y+(height/2), Screen.mobs[shotMob].x + (Screen.mobs[shotMob].width/2),Screen.mobs[shotMob].y + (Screen.mobs[shotMob].height/2));
	}
	if (shooting&& shotMob !=-1 && AirId==Value.airTowerLaser ){
		g.setColor(new Color(255,255,0));
		g.drawLine(x+(width/2), y+(height/2), Screen.mobs[shotMob].x + (Screen.mobs[shotMob].width/2),Screen.mobs[shotMob].y + (Screen.mobs[shotMob].height/2));
	}
	if (shooting&& shotMob !=-1 && AirId==Value.airTowerBlackLaser ){
		g.setColor(new Color(0,0,0));
		g.drawLine(x+(width/2), y+(height/2), Screen.mobs[shotMob].x + (Screen.mobs[shotMob].width/2),Screen.mobs[shotMob].y + (Screen.mobs[shotMob].height/2));
	}
	if(reloaded && flying && AirId==Value.airTowerCannon){
		BallSquare=new Rectangle(Ballx-20,Bally-20,40,40);
		g.drawImage(Screen.tileset_res[4],Ballx-20, Bally-20, 20, 20,null);
	}
}
public void findTarget(){
	
	int maxAge = 0;
	for(int i=0;i<Screen.mobs.length;i++){
		if(Screen.mobs[i].inGame){
			if(towerSquare.intersects(Screen.mobs[i])){
				if(Screen.mobs[i].age > maxAge) {
					mobToShoot = i;
					maxAge = Screen.mobs[i].age;				
				}
			}
		}
		if(mobToShoot > -1){
			
			shotMob = mobToShoot;
		}
	}
}

public void refreshTower(){
	if(AirId != Value.airAir)towerSquareSize = Value.TowerRange[AirId];
	towerSquare=new Rectangle(x-(towerSquareSize/2),y-(towerSquareSize/2),width+(towerSquareSize),height+(towerSquareSize));
}
public void resetBlock(){
	shooting=false;
	flying=false;
	shotMob=-1;
	
}
public void resetBall(){
	BallSquare=null;
	BallSquare=new Rectangle(Ballx-20,Bally-20,40,40);
	Ballx=X+width/2;
	Bally=Y+height/2;
	flying=false;
	shooting=false;
	reloaded=false;
	flyframe=0;

}
public Boolean BallinRoom(){
	if(new Rectangle(0,0,Frame.width,Frame.height).contains(BallSquare)){
		return true;
	}else return false;
}
public void Reload(){
	if(reloadframe>=reloadtime){
		reloadframe=0;
		reloaded=true;
	}else reloadframe++;
}

}
