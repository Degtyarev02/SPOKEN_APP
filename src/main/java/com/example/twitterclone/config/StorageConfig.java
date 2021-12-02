package com.example.twitterclone.config;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StorageConfig {

	@Value("${aws.access.key}")
	private String accessKey;
	@Value("${aws.secret.key}")
	private String secretKey;

	@Bean
	public AmazonS3Client amazonS3Client() {
		AmazonS3Client amazonS3Client = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));
		amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
		return amazonS3Client;
	}
}
