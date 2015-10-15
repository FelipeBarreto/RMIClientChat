package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

	public void register(String id, IClient client) throws RemoteException;

	public void unregister(String id) throws RemoteException;

	public void sendMessage(String id, String message) throws RemoteException;
}
