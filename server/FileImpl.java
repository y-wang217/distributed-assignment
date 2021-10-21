
import java.io.*;
import java.rmi.*;
import java.rmi.server.RemoteServer;
import java.util.ArrayList;

public class FileImpl implements FileInterface {

	private String name;
	private ArrayList<Integer> clientList;

	public FileImpl(String s) throws RemoteException {
		super();
		name = s;
		clientList = new ArrayList<Integer>(10);
	}

	public byte[] downloadFile(String fileName, int clientID) {
		try {
			System.out.println("Client connected on IP: " + RemoteServer.getClientHost());
			System.out.println("Client requesting file: " + fileName);

			File file = new File(fileName);
			byte buffer[] = new byte[(int) file.length()];
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
			input.read(buffer, 0, buffer.length);
			input.close();
			
			clientList.add(clientID);

			System.out.println("File " + fileName + " sent.");
			return (buffer);
		} catch (Exception e) {
			System.out.println("FileImpl: " + e.getMessage());
			e.printStackTrace();
			return (null);
		}
	}

	public void uploadFile(String fileName, byte[] content) throws RemoteException {
		try {
			System.out.println("Client connected on IP: " + RemoteServer.getClientHost());
			System.out.println("Client uploading file: " + fileName);

			File file = new File(fileName);
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName));
			output.write(content);
			output.flush();
			output.close();

			System.out.println("File " + fileName + " successfully uploaded.");
			return;

		} catch (Exception e) {
			System.out.println("FileImpl: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	public String listServerFiles() throws RemoteException {
		try {
			System.out.println("Client connected on IP: " + RemoteServer.getClientHost());
			System.out.println("Client requesting list of files available for download");
			StringBuffer listOfFiles = new StringBuffer();

			File curDir = new File(".");
			File[] filesList = curDir.listFiles();
			for (File f : filesList) {
				if (f.isFile()) {
					listOfFiles.append(f.getName() + " ");
				}
			}

			System.out.println("Sending list of files to client.");
			return new String(listOfFiles);
		} catch (

		Exception e) {
			System.out.println("FileImpl: " + e.getMessage());
			e.printStackTrace();
			return (null);
		}
	}
}