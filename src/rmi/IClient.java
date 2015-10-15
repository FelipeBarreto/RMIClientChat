package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote {
	
	public void updateClients(List<String> clients) throws RemoteException;

	public void updateMessages(String clientId, String message) throws RemoteException;
}
