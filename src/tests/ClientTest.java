package tests;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import rmi.IClient;
import rmi.IServer;

public class ClientTest extends UnicastRemoteObject implements IClient{

	private static final long serialVersionUID = 1L;
	
	private static final int REGISTRY_PORT = 12123;

	protected ClientTest() throws RemoteException {
		super();
	}

	@Override
	public void updateClients(List<String> clients) throws RemoteException {
		System.out.println("on update clients");
		
	}

	@Override
	public void updateMessages(String clientId, String message) throws RemoteException {
		System.out.println("on update messages");
		
	}
	
	public static void main(String[] args) {
		Registry registry = null;
		IServer server = null;
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
			ClientTest client = new ClientTest();
			server.register(client.toString(), client);
		} catch (RemoteException e) {
			System.out.println("REGISTER ERROR");
			System.exit(0);
		}
	}

}
