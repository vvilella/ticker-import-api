package com.ax;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ax.service.storage.StorageClient;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.StorageObject;

@SpringBootApplication
public class Application implements CommandLineRunner {

	
	  public static void main(String[] args) {
	  SpringApplication.run(Application.class, args); }
	 

	/*public static void main(String[] args) {

		String bucketName = "files_bmf";

		try {
			// Get metadata about the specified bucket.
			Bucket bucket = StorageClient.getBucket(bucketName);
			System.out.println("name: " + bucketName);
			System.out.println("location: " + bucket.getLocation());
			System.out.println("timeCreated: " + bucket.getTimeCreated());
			System.out.println("owner: " + bucket.getOwner());

			// List the contents of the bucket.
			List<StorageObject> bucketContents = StorageClient.listBucket(bucketName);
			if (null == bucketContents) {
				System.out.println("There were no objects in the given bucket; try adding some and re-running.");
			}
			for (StorageObject object : bucketContents) {
				System.out.println(object.getName() + " (" + object.getSize() + " bytes)");
			}

			// Create a temp file to upload
			//Path tempPath = Files.createTempFile("StorageSample", "txt");
			//Files.write(tempPath, "Sample file".getBytes());
			//File tempFile = tempPath.toFile();
			//tempFile.deleteOnExit();
			
			File file = new File("/Users/victor.vilella/Documents/files_bmf/NEG_20180702.zip");
			StorageClient.uploadFile(file.getName(), "text/plain", file, bucketName);

			// Now delete the file
			StorageClient.deleteObject(StorageClient.TEST_FILENAME, bucketName);

		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}*/

	@Override
	public void run(String... arg0) throws Exception {
	}
}