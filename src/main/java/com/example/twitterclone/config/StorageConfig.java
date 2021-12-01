package com.example.twitterclone.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

	@Bean
	public AmazonS3Client amazonS3Client() {
		AmazonS3Client amazonS3Client = new AmazonS3Client(new BasicAWSCredentials("AKIAYWPKIV2DI6AYK7UY", "I0m59qS3D5O+svBqZYJeUKYxuH1D501PtwyD4dPY"));
		amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
		System.out.println(amazonS3Client.getRegion());
		return amazonS3Client;
	}
}
