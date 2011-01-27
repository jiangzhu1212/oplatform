package com.risetek.test.appeng.file.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Streams;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Action {

	AmazonS3 s3;
	String bucketName = "test-s3-bucket-0001";
	
	public S3Action(){
		InputStream input = S3Action.class.getResourceAsStream("AwsCredentials.properties");
		PropertiesCredentials pc;
		try {
			pc = new PropertiesCredentials(input);
			s3 = new AmazonS3Client(pc);
			List<Bucket> bucketsList = s3.listBuckets();
			boolean creat = true;
			for(int i=0;i<bucketsList.size();i++){
				Bucket buck = bucketsList.get(i);
				if(buck.getName().equals(bucketName)){
					creat = false;
					break;
				}
			}
			if(creat){
				s3.createBucket(bucketName);
			}
		} catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	
	public void uploadFile(FileItemStream item){
		File file = createFile(item);
		String key = file.getName();
//		key = key.substring(0, key.lastIndexOf("."));
		Date now = new Date();
		key = key + "_" + Long.toString(now.getTime());
		s3.putObject(new PutObjectRequest(bucketName, key, file));
	}
	
	private File createFile(FileItemStream item) {
		String fileName = item.getName();
        File file = new File(fileName);
        file.deleteOnExit();
		try {
			InputStream stream = item.openStream();
			BufferedInputStream inputStream = new BufferedInputStream(stream);
	        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
	        Streams.copy(inputStream, outputStream, true);
	        stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return file;
    }
	
	public List<S3ObjectSummary> getFileList(){
		ObjectListing object = s3.listObjects(bucketName);
		List<S3ObjectSummary> list = object.getObjectSummaries();
		return list;
	}
	
	public void deleteFile(String key){
		s3.deleteObject(bucketName, key);
	}
	
	public S3Object downFile(String key){
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
		return object;
	}
}
