package com.example.twitterclone.service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class S3Wrapper {


	public AmazonS3Client amazonS3Client() {
		AmazonS3Client amazonS3Client = new AmazonS3Client(new BasicAWSCredentials("AKIAYWPKIV2DI6AYK7UY", "I0m59qS3D5O+svBqZYJeUKYxuH1D501PtwyD4dPY"));
		amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_2));
		return amazonS3Client;
	}


	private final AmazonS3Client s3Client = amazonS3Client();

	private String bucket = "spokenresourcesbucket";

	private PutObjectResult upload(String filePath, String uploadKey) throws FileNotFoundException {
		return upload(new FileInputStream(filePath), uploadKey);
	}

	private PutObjectResult upload(InputStream inputStream, String uploadKey) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

		PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);

		IOUtils.closeQuietly(inputStream);

		return putObjectResult;
	}

	public List<PutObjectResult> upload(MultipartFile[] multipartFiles, String filename) {
		List<PutObjectResult> putObjectResults = new ArrayList<>();

		Arrays.stream(multipartFiles)
				.filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
				.forEach(multipartFile -> {
					try {
						putObjectResults.add(upload(multipartFile.getInputStream(), filename));
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

		return putObjectResults;
	}
}
