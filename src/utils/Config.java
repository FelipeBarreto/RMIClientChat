package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Config {
	
	public static String serverIp;
	
	public static void readConfigs(){
		File configFile = new File("config.txt");
		
		try {
			Scanner scanner = new Scanner(new FileInputStream(configFile));
			String serverIpLine = scanner.nextLine();
			serverIp = serverIpLine.split("=")[1];
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
