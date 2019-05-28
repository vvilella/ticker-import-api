package com.ax.service.orquestrator;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ax.service.csv.CsvService;
import com.ax.service.ftp.FtpService;
import com.ax.service.storage.StorageClient;
import com.google.api.services.storage.model.StorageObject;

@Component
public class FileOrquestrator {

	@Autowired
	FtpService ftp;
	
	@Autowired
	CsvService csv;

	@Value("${orquestrator.segments}")
	private String segments;

	@Value("${orquestrator.type}")
	private String fileTypes;

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

			String fileName = buildFileName(segment, type, date);

			List<StorageObject> bucketFiles = StorageClient.listBucket(bucketName);

			List<StorageObject> selectedFiles = bucketFiles.stream()
					.filter(it -> it.getName().contains(fileName)).collect(Collectors.toList());

			for (StorageObject it : selectedFiles) {
				String downloadedFile = downloadFromBucket(it.getName());
				csv.ProcessFile(downloadedFile);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String downloadFromBucket(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	private String buildFileName(String segment, String type, String date) {
		String fileName = null;

		if (segments.contains(segment) && fileTypes.contains(type) && date != null) {
			fileName = MessageFormat.format("{0}/{1}/{2}_{1}_{3}", "MarketData", segment, type, date);
		}

		return fileName;

	}
}
