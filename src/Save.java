import java.io.*;
import java.util.*;


public class Save {
public void loadSave(File loadpath){
	try {
		System.out.println("oje");
		Scanner loadScanner = new Scanner(loadpath);
		while (loadScanner.hasNext()){
			Screen.coins=loadScanner.nextInt();
			Screen.targetKills=loadScanner.nextInt();
			for (int y = 0;y<Screen.room.block.length;y++){
				for (int x= 0;x<Screen.room.block[0].length;x++){
				Screen.room.block[y][x].GroundId = loadScanner.nextInt(); 
				}
			}
			for (int y = 0;y<Screen.room.block.length;y++){
				for (int x= 0;x<Screen.room.block[0].length;x++){
					Screen.room.block[y][x].AirId = loadScanner.nextInt();
				}
			}
		}
		Screen.running=true;
		loadScanner.close();
	}catch(Exception e ){}
}
}
