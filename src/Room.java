import java.awt.*;


public class Room {

	public int worldWidth = 12;
	public int worldHeight = 8;
	public int blocksize = 52;
	
	public Block [][] block;
	
	public void define(){
		block = new Block[worldHeight][worldWidth];
		for (int y = 0;y<block.length;y++){
			for (int x = 0;x<block[0].length;x++){
				block [y][x] = new Block((Screen.myWidth/2)-((worldWidth*blocksize)/2) + x * blocksize,y*blocksize,blocksize,blocksize,Value.groundGrass,Value.airAir);
			}
		}
	}
	public Room(){
		define();
	}
	public void draw (Graphics g){
		for (int y = 0;y<block.length;y++){
			for (int x = 0;x<block[0].length;x++){
				block[y][x].draw(g);
			}
		}

	for (int y = 0;y<block.length;y++){
		for (int x = 0;x<block[0].length;x++){
			block[y][x].fight(g);
		}
	}}
	public void physic(){
	
		for(int y = 0;y<block.length;y++){
			for(int x = 0;x<block[0].length;x++){
		block[y][x].physics();
			}
			
	}}
	

	
	
	
	
	
	
}
