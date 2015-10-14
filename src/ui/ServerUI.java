package ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ServerUI extends Pane{
	
	private final static float PREF_WIDTH = 600;
	private final static float PREF_HEIGHT = 390;
	
	private final static String CSS_PATH = System.class.getResource("/application.css").toExternalForm();
	
	private final static String TITLE = "Chat Server";
	
	private Stage mStage;
	
	private ListView<String> clientsList;
	private TextArea logView;
	
	public void showUp(EventHandler<WindowEvent> exitCallback){
		configureStage(exitCallback);
		
		addListView();
		addLogView();
		addLabels();
		addButtons();
		
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
	
	private void addLogView(){
		logView = new TextArea();
		logView.setPrefSize(200, 300);
		logView.setTranslateX(300);
		logView.setTranslateY(50);
		
		logView.setEditable(false);
		
		getChildren().add(logView);
	}
	
	private void addLabels(){
		Text clients = new Text("Connected Clients");
		clients.setX(85);
		clients.setY(30);
		
		getChildren().add(clients);
		
		Text log = new Text("Server log");
		log.setX(360);
		log.setY(30);
		
		getChildren().add(log);
	}
	
	private void addButtons() {
		Button saveLog = new Button("Save");
		saveLog.setTranslateX(520);
		saveLog.setTranslateY(100);
		saveLog.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String content = logView.getText();
				saveFile(content, new File("log.txt"));
				
			}
		});
		
		getChildren().add(saveLog);
		
		Button clearLog = new Button("Clear");
		clearLog.setTranslateX(520);
		clearLog.setTranslateY(150);
		clearLog.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logView.clear();
				log("Log Cleared");
				
			}
		});
		
		getChildren().add(clearLog);
		
	}
	
	public void updateClientList(Set<String> clients){
		ObservableList<String> list = FXCollections.observableArrayList();
		
		for(String id : clients){
			list.add(id);
		}
		
		clientsList.setItems(list);
	}
	
	private void saveFile(String content, File file){
        try {
            FileWriter fileWriter = null;
             
            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
            log("Log Saved");
        } catch (IOException ex) {
            log("Error Saving the Log");
        }
         
    }
	
	public void log(String text){
		logView.appendText(text + "\n");
	}

}
