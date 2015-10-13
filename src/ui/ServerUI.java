package ui;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerUI extends Pane{
	
	private final static float PREF_WIDTH = 600;
	private final static float PREF_HEIGHT = 400;
	
	private final static String CSS_PATH = System.class.getResource("/application.css").toExternalForm();
	
	private final static String TITLE = "Chat Server";
	
	private Stage mStage;
	
	private ListView<String> clientsList;
	
	public void showUp(EventHandler<WindowEvent> exitCallback){
		configureStage(exitCallback);
		
		addListView();
		
		mStage.show();
	}

	private void configureStage(EventHandler<WindowEvent> exitCallback) {
		mStage = new Stage();
		
		Scene scene = new Scene(this, PREF_WIDTH, PREF_HEIGHT);
		scene.getStylesheets().add(CSS_PATH);
		mStage.setTitle(TITLE);
		mStage.setScene(scene);
		
		mStage.setOnCloseRequest(exitCallback);
		
	}
	
	private void addListView() {
		clientsList = new ListView<>();
		clientsList.setPrefSize(200, 300);
		clientsList.setTranslateX(50);
		clientsList.setTranslateY(50);
		
		getChildren().add(clientsList);
	}
	
	public void updateClientList(Set<String> clients){
		ObservableList<String> list = FXCollections.observableArrayList();
		
		for(String id : clients){
			list.add(id);
		}
		
		clientsList.setItems(list);
	}

}
