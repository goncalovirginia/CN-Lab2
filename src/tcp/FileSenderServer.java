package tcp;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class FileSenderServer {
	static final int BUF_SIZE = 1024;
	public static int PORT = 8000;

	public static String readLine(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		String buff;
		
		while (!(buff = new String(is.readNBytes(1))).equals("\n")) {
			sb.append(buff);
		}
		
		return sb.toString();
	}

	static void receiveFile(Socket cs) throws IOException {
		InputStream is = cs.getInputStream();
		String filename = readLine(is);
		
		try (FileOutputStream fos = new FileOutputStream( "Copy-of-" + filename)) {
			byte[] fileData = is.readAllBytes();
			fos.write(fileData);
		}
		catch (Exception x) {
			x.printStackTrace();
		}
		
		cs.close();
	}

	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(PORT)) {
			for (;;) {
				try (Socket cs = ss.accept()) {
					receiveFile(cs);
				} catch (IOException x) {
					x.printStackTrace();
				}
			}
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
}
