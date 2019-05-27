/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package com.ax.service.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.Bucket;
import com.google.api.services.storage.model.Objects;
import com.google.api.services.storage.model.StorageObject;

/**
 * Main class for the Cloud Storage JSON API sample.
 *
 * Demonstrates how to make an authenticated API call using the Google Cloud Storage API client
 * library for java, with Application Default Credentials.
 */
public class StorageClient {

  /** Global instance of the JSON factory. */
  public static final String TEST_FILENAME = "json-test.txt";

  // [START list_bucket]
  /**
   * Fetch a list of the objects within the given bucket.
   *
   * @param bucketName the name of the bucket to list.
   * @return a list of the contents of the specified bucket.
   */
  public static List<StorageObject> listBucket(String bucketName)
      throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();
    Storage.Objects.List listRequest = client.objects().list(bucketName);

    List<StorageObject> results = new ArrayList<StorageObject>();

    Objects objects;
    // Iterate through each page of results, and add them to our results list.
    do {
      objects = listRequest.execute();
      // Add the items in this page of results to the list we'll return.
      results.addAll(objects.getItems());

      // Get the next page, in the next iteration of this loop.
      listRequest.setPageToken(objects.getNextPageToken());
    } while (null != objects.getNextPageToken());

    return results;
  }
  // [END list_bucket]

  // [START get_bucket]
  /**
   * Fetches the metadata for the given bucket.
   *
   * @param bucketName the name of the bucket to get metadata about.
   * @return a Bucket containing the bucket's metadata.
   */
  public static Bucket getBucket(String bucketName) throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();

    Storage.Buckets.Get bucketRequest = client.buckets().get(bucketName);
    // Fetch the full set of the bucket's properties (e.g. include the ACLs in the response)
    bucketRequest.setProjection("full");
    return bucketRequest.execute();
  }
  // [END get_bucket]

  // [START upload_stream]
  /**
   * Uploads data to an object in a bucket.
   *
   * @param name the name of the destination object.
   * @param contentType the MIME type of the data.
   * @param file the file to upload.
   * @param bucketName the name of the bucket to create the object in.
   */
  public static void uploadFile(
      String name, String contentType, File file, String bucketName)
      throws IOException, GeneralSecurityException {
    InputStreamContent contentStream = new InputStreamContent(
        contentType, new FileInputStream(file));
    // Setting the length improves upload performance
    contentStream.setLength(file.length());
    StorageObject objectMetadata = new StorageObject()
        // Set the destination object name
        .setName(name);

    // Do the insert
    Storage client = StorageFactory.getService();
    Storage.Objects.Insert insertRequest = client.objects().insert(
        bucketName, objectMetadata, contentStream);

    insertRequest.execute();
  }
  // [END upload_stream]

  // [START delete_object]
  /**
   * Deletes an object in a bucket.
   *
   * @param path the path to the object to delete.
   * @param bucketName the bucket the object is contained in.
   */
  public static void deleteObject(String path, String bucketName)
      throws IOException, GeneralSecurityException {
    Storage client = StorageFactory.getService();
    client.objects().delete(bucketName, path).execute();
  }
  // [END delete_object]

  /**
   * Exercises the class's functions - gets and lists a bucket, uploads and deletes an object.
   *
   * @param args the command-line arguments. The first argument should be the bucket name.
   */
  
}
//[END all]
