package application;
	
import java.rmi.RemoteException;

import javafx.application.Application;
import javafx.stage.Stage;
import server.ServerImpl;


public class Launcher extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			new ServerImpl().start();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}