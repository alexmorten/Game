import java.awt.*;


public class Store {
	public static int shopWidth = 8;
	public static int buttonsize = 52;
	public static int cellSpace = 2;
	public static int AFR = 29;
	public static int iconsize = 20,iconspace=6,iconsizeY=15;
	public static int[] buttonId={Value.airTowerLaser,Value.airTowerBlueLaser,Value.airTowerBlackLaser,Value.airTowerCannon,Value.airAir,Value.airTowerFireTrap,Value.airTowerTar,Value.airTrashcan};
	public static int[] buttonPrice={10,25,50,80,0,25,20,0};
	public static int itemIn = 4;
	public static int heldId=-1;
	public static int realId=-1;
	public static Boolean holdsItem=false;
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	public Rectangle buttonKills;
public void click(int clickedButton){
	if (clickedButton==1) {
	 for (int i = 0;i<button.length;i++){
		if( button[i].contains(Screen.mse)){
			if(buttonId[i]!=Value.airAir){
			 if (buttonId[i] == Value.airTrashcan){
			 holdsItem=false;
		   } else {
			 heldId=buttonId[i];
			 realId=i;
			 holdsItem=true;
		}}}
	 }
	 if(holdsItem){
		 if(Screen.coins>=buttonPrice[realId] || Screen.debug){
			 for(int y=0;y<Screen.room.block.length;y++){
				 for(int x=0;x<Screen.room.block[0].length;x++){
					 if(Screen.room.block[y][x].contains(Screen.mse)){
						 if(Screen.room.block[y][x].GroundId!= Value.groundRoad && Screen.room.block[y][x].AirId!=heldId && heldId != Value.airTowerTar && heldId != Value.airTowerFireTrap && Screen.room.block[y][x].AirId!=Value.airCave){
							 Screen.room.block[y][x].AirId = heldId;
							 Screen.room.block[y][x].isTower = true;
							 Screen.room.block[y][x].refreshTower();
							 Screen.coins -=buttonPrice[realId];
						 }else if(Screen.room.block[y][x].GroundId== Value.groundRoad && Screen.room.block[y][x].AirId!=heldId && heldId ==Value.airTowerTar&& Screen.room.block[y][x].AirId!=Value.airCave){
							 Screen.room.block[y][x].AirId = heldId;
							 Screen.room.block[y][x].isTower = false;
							 Screen.coins -=buttonPrice[realId];
							 Screen.room.block[y][x].refreshTower();
						 }else if(Screen.room.block[y][x].GroundId== Value.groundRoad && Screen.room.block[y][x].AirId!=heldId && heldId ==Value.airTowerFireTrap&& Screen.room.block[y][x].AirId!=Value.airCave){
							 Screen.room.block[y][x].AirId = heldId;
							 Screen.room.block[y][x].isTower = false;
							 Screen.coins -=buttonPrice[realId];
							 Screen.room.block[y][x].refreshTower();
					 }}
				 } 
			 }
		 }
	 }
}}
	
public Store (){
	define();
}
public void define(){
	for (int i = 0;i < button.length;i++){
		button[i] = new Rectangle((Screen.myWidth/2)-((shopWidth*(buttonsize+cellSpace))/2)+ ((buttonsize+cellSpace)*i),(Screen.room.block[Screen.room.worldHeight-1][0].y)+Screen.room.blocksize + AFR, buttonsize, buttonsize);
	}
	buttonHealth=new Rectangle(Screen.room.block[0][0].x-1,button[0].y,iconsize,iconsize);
	buttonCoins=new Rectangle(Screen.room.block[0][0].x-1,button[0].y + button[0].height-iconsize,iconsize,iconsize);
	buttonKills=new Rectangle(Screen.room.worldHeight*Screen.room.blocksize+150,button[0].y,iconsize,iconsize);
}
public void draw(Graphics g){
	for (int i = 0;i < button.length;i++){
		if(button[i].contains(Screen.mse)){
			g.setColor(new Color(255,255,255,100));
			g.fillRect(button[i].x,button[i].y, button[i].width, button[i].height);
			
		}
		g.drawImage(Screen.tileset_res[0],button[i].x,button[i].y, button[i].width, button[i].height,null);
	 if (buttonId[i]!=Value.airAir)   g.drawImage(Screen.tileset_air[buttonId[i]],button[i].x+itemIn,button[i].y+itemIn, button[i].width-(itemIn*2), button[i].height-(itemIn*2),null);
	 if (buttonPrice[i]>0){
		 g.setColor(new Color(255,255,255));
		 g.setFont(new Font("Courier New",Font.BOLD,14));
		 g.drawString(buttonPrice[i]+"€",button[i].x+itemIn,button[i].y+itemIn+10);}
	 }
	    g.drawImage(Screen.tileset_res[1],buttonHealth.x,buttonHealth.y,buttonHealth.width,buttonHealth.height,null);
		g.drawImage(Screen.tileset_res[2],buttonCoins.x,buttonCoins.y,buttonCoins.width,buttonCoins.height,null);
		g.drawImage(Screen.tileset_res[3],buttonKills.x,buttonKills.y,buttonKills.width,buttonKills.height,null);
		g.setFont(new Font("Courier New",Font.BOLD,14));
		g.setColor(new Color(255,255,255));
		g.drawString(""+Screen.health,buttonHealth.x + buttonHealth.width+iconspace,buttonHealth.y+iconsizeY);
		g.drawString(""+Screen.coins,buttonCoins.x + buttonCoins.width+iconspace,buttonCoins.y+iconsizeY);
		g.drawString(""+Screen.Kills+"/"+Screen.spawned,buttonKills.x + buttonKills.width+iconspace,buttonKills.y+iconsizeY);
		
	if(holdsItem){
		g.drawImage(Screen.tileset_air[heldId],Screen.mse.x-((button[0].width-(itemIn*2))/2)+itemIn,Screen.mse.y-((button[0].width-(itemIn*2))/2)+itemIn,button[0].width-(itemIn*2), button[0].height-(itemIn*2),null);
	}
	


	
}}

