package ui;

import java.util.Set;

import client.ClientImpl;
import javafx.application.Platform;
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

public class ClientUI extends Pane {

	private final static float PREF_WIDTH = 670;
	private final static float PREF_HEIGHT = 550;

	private final static String CSS_PATH = System.class.getResource("/application.css").toExternalForm();

	private final static String TITLE = "Chat Client";

	private Stage mStage;

	private ListView<String> clientsList;
	private TextArea messageView;
	private TextArea messageSend;
	private String id;

	public ClientUI(String id) {
		this.id = id;
	}

	public void showUp(EventHandler<WindowEvent> exitCallback) {
		configureStage(exitCallback);

		addMessageView();
		addLabels();
		addButtons();

		mStage.show();
	}

	private void configureStage(EventHandler<WindowEvent> exitCallback) {
		mStage = new Stage();

		Scene scene = new Scene(this, PREF_WIDTH, PREF_HEIGHT);
		scene.getStylesheets().add(CSS_PATH);
		mStage.setTitle(TITLE + " : " + id);
		mStage.setScene(scene);

		mStage.setOnCloseRequest(exitCallback);
	}

	private void addListView() {
		clientsList = new ListView<>();
		clientsList.setPrefSize(150, 300);
		clientsList.setTranslateX(460);
		clientsList.setTranslateY(50);

		getChildren().add(clientsList);
	}

	private void addMessageView() {
		messageView = new TextArea();
		messageView.setPrefSize(400, 300);
		messageView.setTranslateX(50);
		messageView.setTranslateY(50);
		messageView.setEditable(false);
		
		getChildren().add(messageView);
		
		messageSend = new TextArea();
		messageSend.setPrefSize(500, 100);
		messageSend.setTranslateX(50);
		messageSend.setTranslateY(370);
		
		getChildren().add(messageSend);
	}

	private void addLabels() {
		Text clients = new Text("Connected Clients");
		clients.setX(460);
		clients.setY(30);
				
		getChildren().add(clients);
		
		Text messages = new Text("Messages");
		messages.setX(50);
		messages.setY(30);
				
		getChildren().add(messages);
	}

	private void addButtons() {	
		Button sendMessage = new Button("Send");
		sendMessage.setTranslateX(560);
		sendMessage.setTranslateY(380);
		sendMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ClientImpl.sendMessage(id, messageSend.getText());
			}
		});

		getChildren().add(sendMessage);
	}

	public void updateClientList(Set<String> clients) {
		ObservableList<String> list = FXCollections.observableArrayList();

		for (String id : clients) {
			list.add(id);
		}
		
		if(clientsList == null) 
			addListView();
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				clientsList.setItems(list);
			}
		});
	}

	public void updateMessages(String message) {
		messageView.setText(messageView.getText() + "\n" + message);
	}
}
