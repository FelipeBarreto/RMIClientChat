package ui;

import java.util.Set;

import client.ClientImpl;
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

	private final static float PREF_WIDTH = 600;
	private final static float PREF_HEIGHT = 390;

	private final static String CSS_PATH = System.class.getResource("/application.css").toExternalForm();

	private final static String TITLE = "Chat Client";

	private Stage mStage;

	private ListView<String> clientsList;
	private TextArea messageView;

	public void showUp(EventHandler<WindowEvent> exitCallback) {
		configureStage(exitCallback);

		addListView();
		addMessageView();
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

	private void addMessageView() {
		messageView = new TextArea();
		messageView.setPrefSize(200, 300);
		messageView.setTranslateX(300);
		messageView.setTranslateY(50);
		
		getChildren().add(messageView);
	}

	private void addLabels() {
		Text clients = new Text("Connected Clients");
		clients.setX(85);
		clients.setY(30);

		getChildren().add(clients);
	}

	private void addButtons() {	
		Button sendMessage = new Button("Send");
		sendMessage.setTranslateX(520);
		sendMessage.setTranslateY(150);
		sendMessage.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println(messageView.getText());
				ClientImpl.sendMessage("ok");
			}
		});

		getChildren().add(sendMessage);
	}

	public void updateClientList(Set<String> clients) {
		ObservableList<String> list = FXCollections.observableArrayList();

		for (String id : clients) {
			list.add(id);
		}

		clientsList.setItems(list);
	}
}
