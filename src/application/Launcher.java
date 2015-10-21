package application;

import java.rmi.RemoteException;

import client.ClientImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Launcher extends Application {

	GridPane grid;
	Text scenetitle;
	Label userName;
	TextField userTextField;
	Button btn;

	ClientImpl client;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			client = new ClientImpl();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		addLogin(primaryStage);
	}

	private void addLogin(Stage primaryStage) {
		grid = new GridPane();
		btn = new Button();
		scenetitle = new Text("Welcome");
		userName = new Label("Name:");
		userTextField = new TextField();

		btn.setText("Sign in");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				client.setName(userTextField.getText());
				if (client.start()) {
					((Node) (event.getSource())).getScene().getWindow().hide();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Login Failed");
					alert.setHeaderText(null);
					alert.setContentText("Username already in use by another client");
					alert.showAndWait();
				}

			}
		});

		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		grid.add(scenetitle, 0, 0);
		grid.add(userName, 0, 1);
		grid.add(userTextField, 1, 1);
		grid.add(btn, 0, 2);

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setTitle("Chat Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
