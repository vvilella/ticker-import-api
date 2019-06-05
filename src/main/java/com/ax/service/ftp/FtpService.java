package com.ax.service.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FtpService {

	@Value("${ftp.server}")
	private String ftpServer;

	@Value("${ftp.port}")
	private Integer ftpPort;

	@Value("${ftp.user}")
	private String ftpUser;

	@Value("${ftp.pass}")
	private String ftpPassword;

	@Value("${ftp.root}")
	private String rootDirectory;

	private FTPClient ftpClient;

	private void connect() throws SocketException, IOException {
		if (ftpClient == null) {
			ftpClient = new FTPClient();
			ftpClient.setConnectTimeout(60000 * 60);
			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		}
	}

	private void disconnect() throws IOException {
		if (ftpClient != null) {
			ftpClient.disconnect();
			ftpClient = null;
		}
	}

	public List<String> ListFiles(String directory) throws SocketException, IOException {
		try {
			connect();
			String[] files = ftpClient.listNames(rootDirectory + directory);
			return Arrays.asList(files);
		} finally {
			disconnect();
		}
	}

	public boolean Download(String ftpFile, String tempFile) throws IOException {
		try {
			connect();
			File downloadFile = new File(tempFile);
			downloadFile.getParentFile().mkdirs();
			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
			boolean success = ftpClient.retrieveFile(ftpFile, outputStream);
			outputStream.close();
			return success;
		} finally {
			disconnect();
		}
	}
}
