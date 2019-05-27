package com.ax.service.orquestrator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ax.service.ftp.FtpService;
import com.ax.service.storage.StorageClient;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

@Component
public class FileOrquestrator {

	@Autowired
	FtpService ftp;

	@Value("${orquestrator.directories}")
	private String directories;

	@Value("${orquestrator.bucketname}")
	private String bucketName;
	
	@Value("${orquestrator.temporary.directory}")
	private String temporaryDirectory;

	public void SyncFiles() {
		Arrays.asList(directories.split(";")).forEach(item -> syncDirectory(item));
	}

	private void syncDirectory(String directory) {

		try {

			List<String> ftpFiles = ftp.ListFiles(directory);
			List<StorageObject> bucketContents = StorageClient.listBucket(bucketName);
			
			for(String item : ftpFiles) {
				Optional<StorageObject> matchingObject = bucketContents.stream().
					    filter(p -> p.getName().contains(item)).
					    findFirst();
				
				if(!matchingObject.isPresent()) {
					System.out.println("File not found : " + item);
					ftp.Download(item, temporaryDirectory + item);
				}
				
				File file = new File("/Users/victor.vilella/Documents/files_bmf/NEG_20180702.zip");
				StorageClient.uploadFile(file.getName(), "text/plain", file, bucketName);
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

}
