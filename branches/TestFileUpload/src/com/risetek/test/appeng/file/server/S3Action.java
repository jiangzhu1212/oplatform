package com.risetek.test.appeng.file.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;

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
	
}
