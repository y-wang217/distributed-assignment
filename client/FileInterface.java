
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
	public byte[] downloadFile(String fileName, int clientID) throws RemoteException;

	public void uploadFile(String fileName, byte[] content) throws RemoteException;
	
	public String listServerFiles() throws RemoteException;
}