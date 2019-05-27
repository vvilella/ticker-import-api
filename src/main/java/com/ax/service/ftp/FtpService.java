package com.ax.service.ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
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

	public void ListFiles(String directory) throws SocketException, IOException {
		FTPClient client = new FTPClient();
		client.connect(ftpServer, ftpPort);
		client.login(ftpUser, ftpPassword);
		FTPFile[] files = client.listFiles(directory);
		for (FTPFile file : files) {
			System.out.println(file.getName());
		}
	}
}
