
import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FileClient {
	public static void main(String argv[]) {
		if (argv.length < 2 || (!argv[0].equals("u") && !argv[0].equals("d") && !argv[0].equals("l"))) {
			System.out.println("Usage: java FileClient action fileName ID");
			System.out.println("action = u upload or d download or l list");
			System.out.println("ID = number");
			System.exit(0);
		}
		String action = argv[0];
		try {
			Registry registry = LocateRegistry.getRegistry();

			FileInterface fi = (FileInterface) registry.lookup("FileServer");
			System.out.println("Client Ready – remote stub active...");

			if (action.equals("d")) {
				String fileName = argv[1];
				int clientID = Integer.parseInt(argv[2]);
				// Download action
				byte[] filedata = fi.downloadFile(fileName, clientID);
				File file = new File(fileName);
				BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file.getName()));
				output.write(filedata, 0, filedata.length);
				output.flush();
				output.close();
				System.out.println("file: " + fileName + " was downloaded successfully.");

			} else if (action.equals("u")) {
				String fileName = argv[1];
				// Upload action
				File file = new File(fileName);
				byte buffer[] = new byte[(int) file.length()];
				BufferedInputStream input = new BufferedInputStream(new FileInputStream(fileName));
				input.read(buffer, 0, buffer.length);
				input.close();
				
				fi.uploadFile(fileName, buffer);
				System.out.println("file: " + fileName + " was uploaded successfully.");

			} else if (action.equals("l")) {
				// List files action
				
				System.out.println("The following files are availale for download: " + fi.listServerFiles());

			}
		} catch (Exception e) {
			System.err.println("FileServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}