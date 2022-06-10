package com.patel.org.sewa;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;

@SpringBootApplication
public class SewaApplication {

//	public static void main(String[] args) {
//		FTPClient client = new FTPClient();
//		String filename = "/home/gaian/Pictures/images.jpeg";
//
//		// Read the file from resources folder.
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		try (InputStream is = classLoader.getResourceAsStream(filename)) {
//			client.connect("0.tcp.in.ngrok.io", 10825);
//			client.setRemoteVerificationEnabled(false);
//
//			boolean login = client.login("newftpuser", "newftpuser");
//			if (login) {
//				System.out.println("Login success...");
//
//				// Store file to server
////	                client.storeFile(filename, is);
//				//client.enterRemoteActiveMode(InetAddress.getByAddress("3.6.30.85".getBytes()), 10825);
//
//				System.out.println(client.storeFile(filename, is));
//				System.out.println(client.getReplyCode());
//				System.out.println(client.getReplyString());
//				client.logout();
//
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				client.disconnect();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}



	public static void main(String[] args) {
//	    SpringApplication.run(EnensyscloudApplication.class, args);
		FTPClient conn = null;
		boolean isUploadSuccess = false;
		try {
			conn = new FTPClient();
			conn.setRemoteVerificationEnabled(false);

			System.out.println(conn);
			conn.connect("0.tcp.in.ngrok.io", 10825);

			System.out.println("----After connection----");
			if (conn.login("newftpuser", "newftpuser")) {

				System.out.println("-----Connected-----" + conn.getReplyString());
				// conn.enterLocalPassiveMode();
				conn.enterLocalActiveMode();
				conn.changeToParentDirectory();

				System.out.println("----I am here");
				conn.setFileType(FTP.BINARY_FILE_TYPE);

				System.out.println("-----Your are here");
				File file = new File("/home/gaian/Pictures/images.jpeg");

				System.out.println("------Here i am here...");

				System.out.println("---- :" + file.getName());
				System.out.println(file.getPath());
				conn.enterLocalActiveMode();
				isUploadSuccess = conn.storeFile(file.getName(),
						new FileSystemResource(file.getPath()).getInputStream());

				System.out.println("---Uploaded");
				System.out.println("Upload is Success: " + isUploadSuccess);
				conn.logout();
				conn.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
//
//	static FTPClient ftp = null;
//
//	public SewaApplication(String host, String user, String pwd) throws Exception {
//		ftp = new FTPClient();
//		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
//		int reply;
//		ftp.connect("0.tcp.in.ngrok.io", 10825);
//		reply = ftp.getReplyCode();
//		if (!FTPReply.isPositiveCompletion(reply)) {
//			ftp.disconnect();
//			throw new Exception("Exception in connecting to FTP Server");
//		}
//		ftp.login(user, pwd);
//		ftp.setFileType(FTP.BINARY_FILE_TYPE);
//		// ftp.enterLocalPassiveMode();
////		ftp.enterLocalActiveMode();
//
//		ftp.setRemoteVerificationEnabled(false);
//
//		System.out.println(ftp.getReplyString());
//	}
//
//	public void uploadFile(String localFileFullName, String fileName, String hostDir) throws Exception {
//		
//	}
//
//	public void disconnect() {
//		if (this.ftp.isConnected()) {
//			try {
//				this.ftp.logout();
//				this.ftp.disconnect();
//			} catch (IOException f) {
//				// DO NOTHING
//			}
//		}
//	}
//
//	public static void main(String[] args) {
//		try {
//			System.out.println("Start");
//			SewaApplication ftpUploader = new SewaApplication("0.tcp.in.ngrok.io", "newftpuser", "newftpuser");
//			try (InputStream input = new FileInputStream(new File("/home/gaian/Pictures/images.jpeg"))) {
//
//				System.out.println("ddddddddddddddddddd");
//				ftp.enterLocalActiveMode();
//
////				ftp.enterLocalPassiveMode();
//				ftp.setRemoteVerificationEnabled(false);
//				ftp.storeFile("/home/gaian/Pictures/images.jpeg", input);
//				System.out.println(":::::::::::::::::::::::::::::::::");
//			}
//			//ftpUploader.uploadFile("/home/gaian/Pictures/images.jpeg", "images.jpeg", "/etc");
//			ftpUploader.disconnect();
//			System.out.println("Done");
//		} catch (Exception exception) {
//			exception.printStackTrace();
//		}
//	}
//	}

//	public static void main(String[] args) {
//		String server = "0.tcp.in.ngrok.io";
//		int port = 10825;
//		String user = "newftpuser";
//		String pass = "newftpuser";
//
//		FTPClient ftpClient = new FTPClient();
//		try {
//
//			ftpClient.connect(server, port);
//			ftpClient.login(user, pass);
//			// ftpClient.enterLocalPassiveMode();
//
//			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//			ftpClient.setRemoteVerificationEnabled(false);
//
//			System.out.println(ftpClient.getReplyString());
//			// APPROACH #1: uploads first file using an InputStream
//			File firstLocalFile = new File("/home/gaian/Pictures/images.jpeg");
//
//			String firstRemoteFile = "images.jpeg";
//			InputStream inputStream = new FileInputStream(firstLocalFile);
//
//			System.out.println("Start uploading first file");
//
//			System.out.println(ftpClient.getDefaultPort());
//
//			boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
//
//			inputStream.close();
//			if (done) {
//				System.out.println("The first file is uploaded successfully.");
//			} else {
//				System.out.println("-----Bhai dushra try karo....");
//			}
//
////			// APPROACH #2: uploads second file using an OutputStream
////			File secondLocalFile = new File("/home/gaian/Pictures/images.jpeg");
////			String secondRemoteFile = "/home/gaian/Pictures/images.jpeg";
////			inputStream = new FileInputStream(secondLocalFile);
////
////			System.out.println("Start uploading second file");
////			OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
////			byte[] bytesIn = new byte[4096];
////			int read = 0;
////
////			while ((read = inputStream.read(bytesIn)) != -1) {
////				outputStream.write(bytesIn, 0, read);
////			}
////			inputStream.close();
////			outputStream.close();
//
//			System.out.println("-----Try karo.....");
//			boolean completed = ftpClient.completePendingCommand();
//			if (completed) {
//				System.out.println("The second file is uploaded successfully.");
//			} else {
//				System.out.println("------Upar ka solve karo pehle.");
//			}
//
//		} catch (IOException ex) {
//			System.out.println("Error: " + ex.getMessage());
//			ex.printStackTrace();
//		} finally {
//			try {
//				if (ftpClient.isConnected()) {
//					ftpClient.logout();
//					ftpClient.disconnect();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//	}
