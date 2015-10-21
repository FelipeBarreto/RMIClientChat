package client;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import rmi.IClient;
import rmi.IServer;
import ui.ClientUI;
import utils.Config;

public class ClientImpl extends UnicastRemoteObject implements IClient {

	private static final long serialVersionUID = 1L;

	private ClientUI clientUI;
	private static IServer server = null;

	private static final int REGISTRY_PORT = 12123;

	private Registry registryRemote;

	private String id;

	public ClientImpl() throws RemoteException {
		super();	
	}
	
	public void setName(String id){
		this.id = id;
		clientUI = new ClientUI(id);
	}

	@Override
	public void updateClients(List<String> clients) throws RemoteException {
		Set<String> setClients = new HashSet<>();
		
		for(String client : clients){
			setClients.add(client);
		}
		
		clientUI.updateClientList(setClients);
	}

	@Override
	public void updateMessages(String clientId, String message) throws RemoteException {		
		clientUI.updateMessages(message);
	}

	public boolean start() {	
		if(!configureRMI()){
			return false;
		}

		clientUI.showUp(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stop();
			}
		});
		
		return true;
	}

	private boolean configureRMI() {
		registryRemote = null;
		
		try {
			registryRemote = LocateRegistry.getRegistry(Config.serverIp, REGISTRY_PORT);
		} catch (RemoteException e1) {
			e1.printStackTrace();
			System.exit(0);

		}

		try {
			server = (IServer) registryRemote.lookup("server");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {
			return server.register(id, this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stop();
		return false;
	}

	private void stop() {	
		try {
			server.unregister(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			
		}

		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			System.out.println("UNEXPORT ERROR");
			System.exit(0);
		}

	}

	public static void sendMessage(String id, String message) {
		try {
			server.sendMessage(id, message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
