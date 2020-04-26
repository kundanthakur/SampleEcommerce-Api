package com.omni.curis.aws;
import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;



@Service
public class EntryPointS3 {

	@Value("${app.accessKey}")
	private String accessKey;
	
	@Value("${app.secretkey}")
	private String secretkey;
	
	@Value("${app.bucketname}")
	private String bucketname;
	
	
	public String savefileintos3(File file) {
		try {
			
			AWSCredentials credentials = new BasicAWSCredentials(accessKey,secretkey);

			
			
			AmazonS3 s3client = AmazonS3ClientBuilder
					  .standard()
					  .withCredentials(new AWSStaticCredentialsProvider(credentials))
							  .withRegion(Regions.AP_SOUTH_1)
					 .build();

			String bucketName = bucketname;
			
			
			s3client.putObject(
					new PutObjectRequest(bucketName, file.getName(),file)
							.withCannedAcl(CannedAccessControlList.PublicRead));

			return String.valueOf(s3client.getUrl(bucketName,file.getName()));
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			return "";
		}
		
		
	

     	
	}



}