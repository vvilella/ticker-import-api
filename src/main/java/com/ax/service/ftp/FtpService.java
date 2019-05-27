package com.ax.service.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

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

	private FTPClient ftpClient;

	private static String ROOT_DIRECTORY = "MarketData/";
	
	@PostConstruct
	private void init() throws SocketException, IOException {
		if (ftpClient == null) {
			ftpClient = new FTPClient();
			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUser, ftpPassword);
			ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		}
	}

	public List<String> ListFiles(String directory) throws SocketException, IOException {
		String[] files = ftpClient.listNames(ROOT_DIRECTORY + directory);
		return Arrays.asList(files);
	}
	
	public void Download(String ftpFile, String tempFile) throws IOException {
        File downloadFile = new File(tempFile);
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
        boolean success = ftpClient.retrieveFile(ftpFile, outputStream);
        outputStream.close();
	}

}
