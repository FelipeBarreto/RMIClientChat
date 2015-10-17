package server;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import rmi.IClient;
import rmi.IServer;
import ui.ServerUI;

public class ServerImpl extends UnicastRemoteObject implements IServer {

	private static final long serialVersionUID = 1L;

	private ServerUI serverUI;

	private final static int REGISTRY_PORT = 12123;

	public Map<String, IClient> clients;
	//private Map<String, Integer> messagesCount;
	private int messagesCount = 0;

	private Registry registry;

	public ServerImpl() throws RemoteException {
		super();
		clients = new HashMap<>();
		//messagesCount = new HashMap<>();
	}

	public void start() {
		configureRMI();

		serverUI = new ServerUI();
		serverUI.showUp(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stop();
			}
		});

		serverUI.log("Server Started!");
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
			registry.rebind("server", this);
		} catch (RemoteException e) {
			System.out.println("REBIND ERROR");
			System.exit(0);
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
	}

	@Override
	public synchronized void register(String id, IClient client) throws RemoteException {
		clients.put(id, client);
		//messagesCount.put(id, 0);
		updateClientsList();
		log("New Client: " + id);
	}

	@Override
	public synchronized void unregister(String id) throws RemoteException {
		if (clients.containsKey(id)) {
			clients.remove(id);
			//messagesCount.remove(id);
			updateClientsList();
			log("Client " + id + " Left");
		}
	}

	@Override
	public synchronized void sendMessage(String id, String message) throws RemoteException {
		//System.out.println(formatMessage(messagesCount.get(id), id, message));
		System.out.println(formatMessage(messagesCount, id, message));
		
		for (IClient client : clients.values()) {
			client.updateMessages(id, id + " : " +  message);
		}
		
		//messagesCount.replace(id, messagesCount.get(id) + 1); // update count
		messagesCount++;
		
		log("New Message from Client " + id);
	}

	private String formatMessage(Integer integer, String id, String message) {
		String formatedMessage = "Mensagem " + integer + " - " + id + ": " + message;
		return formatedMessage;
	}

	private void updateClientsList() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				serverUI.updateClientList(clients.keySet());
			}
		});

		for (IClient client : clients.values()) {
			try {
				client.updateClients(new ArrayList<String>(clients.keySet()));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	private void log(final String text) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				serverUI.log(text);
			}
		});
	}
}
