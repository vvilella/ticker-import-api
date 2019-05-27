package com.ax.service.orquestrator;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ax.service.ftp.FtpService;
import com.ax.service.storage.StorageClient;
import com.google.api.services.storage.model.StorageObject;

@Component
public class FileOrquestrator {

	@Autowired
	FtpService ftp;

	@Value("${orquestrator.segments}")
	private String segments;
	
	@Value("${orquestrator.type}")
	private String fileType;

	@Value("${orquestrator.bucketname}")
	private String bucketName;

	@Value("${orquestrator.temporary.directory}")
	private String temporaryDirectory;

	public void SyncFiles() {
		Arrays.asList(segments.split(";")).forEach(item -> syncDirectory(item));
	}

	private void syncDirectory(String directory) {

		try {

			List<String> ftpFiles = ftp.ListFiles(directory);
			List<StorageObject> bucketFiles = StorageClient.listBucket(bucketName);

			for (String item : ftpFiles) {
				Optional<StorageObject> matchingObject = bucketFiles.stream().filter(p -> p.getName().contains(item))
						.findFirst();

				if (!matchingObject.isPresent()) {
					System.out.println("File not found: " + item);
					uploadToBucket(item);
					System.out.println("File uploaded: " + item);

				} else {
					System.out.println("File already exists: " + item);
				}
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	private void uploadToBucket(String item) throws IOException, GeneralSecurityException {
		if (ftp.Download(item, temporaryDirectory + item)) {
			File file = new File(temporaryDirectory + item);
			StorageClient.uploadFile(item, "application/x-binary", file, bucketName);
			file.delete();
		}
	}

	public void LoadFile(String segment, String type, String date) {
		try {
			if (segments.contains(segment) && fileType.contains(type)) {

				List<StorageObject> bucketFiles = StorageClient.listBucket(bucketName);

				List<StorageObject> selectedFiles = bucketFiles.stream()
						.filter(it -> it.getName().toLowerCase().contains("/" + type.toLowerCase() + "/"))
						.collect(Collectors.toList());

				for (StorageObject it : selectedFiles) {
					System.out.println(it.getName());
				}
			} else {
				System.out.println("Invalid directory!");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
