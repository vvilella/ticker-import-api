package com.ax.service.orquestrator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ax.service.ftp.FtpService;
import com.ax.service.storage.StorageClient;
import com.google.api.services.storage.model.StorageObject;

@Component
public class FileOrquestrator {

	@Autowired
	FtpService ftp;

	public void SyncFiles() {
		String bucketName = "files_bmf";

		try {
			
			ftp.ListFiles("MarketData/BMF");

			List<StorageObject> bucketContents = StorageClient.listBucket(bucketName);

			if (bucketContents != null) {

				for (StorageObject object : bucketContents) {
					System.out.println(object.getName());
				}
			}

			File file = new File("/Users/victor.vilella/Documents/files_bmf/NEG_20180702.zip");
			StorageClient.uploadFile(file.getName(), "text/plain", file, bucketName);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}

	}

}
