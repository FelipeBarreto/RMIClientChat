package application;
	
import java.rmi.RemoteException;

import client.ClientImpl;
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
		
		try {
			new ClientImpl("anderson").start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
