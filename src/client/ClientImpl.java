package client;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import rmi.IClient;
import rmi.IServer;
import ui.ClientUI;
import ui.ServerUI;

public class ClientImpl extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;

	private ClientUI clientUI;
	private static IServer server = null;

	private static final int REGISTRY_PORT = 12123;

	private Map<String, IClient> clients;
	private Map<String, Integer> messagesCount;

	private Registry registry;

	private static String id;

	public ClientImpl(String id) throws RemoteException {
		super();
		clients = new HashMap<>();
		messagesCount = new HashMap<>();
		this.id = id;
	}

	@Override
	public void updateClients(List<String> clients) throws RemoteException {
		System.out.println("on update clients");
	}

	@Override
	public void updateMessages(String clientId, String message) throws RemoteException {
		System.out.println("on update messages");
		System.out.println(message);
	}

	public void start() {
		configureRMI();

		clientUI = new ClientUI();
		clientUI.showUp(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stop();
			}
		});
	}

	private void configureRMI() {
		registry = null;

		try {
			registry = LocateRegistry.createRegistry(REGISTRY_PORT);
		} catch (RemoteException e) {
			try {
				registry = LocateRegistry.getRegistry(REGISTRY_PORT);
			} catch (RemoteException e1) {
				System.out.println("RMI ERROR");
				System.exit(0);
			}
		}

		try {
			server = (IServer) registry.lookup("server");
		} catch (RemoteException | NotBoundException e) {
			System.out.println("LOOKUP ERROR");
			System.exit(0);
		}

		try {
			server.register(id, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void stop() {
		System.out.println("Server Stopped");

		try {
			registry.unbind("server");
		} catch (RemoteException | NotBoundException e) {
			System.out.println("UNBIND ERROR");
			System.exit(0);
		}

		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			System.out.println("UNEXPORT ERROR");
			System.exit(0);
		}

		try {
			server.unregister(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void sendMessage(String message) {
		try {
			server.sendMessage(id, "ok");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
