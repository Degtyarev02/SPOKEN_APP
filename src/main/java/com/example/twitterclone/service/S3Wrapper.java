package com.example.twitterclone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

@Service
public class S3Wrapper {


	private final AmazonS3Client s3Client;
	private final String bucket;

	//создаем амазонклиент с помощью бина из StorageConfig конструктором
	//Присваиваем названию bucket значение из application.properties
	@Autowired
	public S3Wrapper(AmazonS3Client s3Client, @Value("${aws.bucket.name}") String bucket) {
		this.bucket = bucket;
		this.s3Client = s3Client;
	}

	//Метод загрузки фоток на aws s3
	public PutObjectResult upload(InputStream inputStream, String uploadKey) {
		//Получаем запрос на добавление, передаем в параметры имя bucket'а, имя файла, поток файла
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest);
		IOUtils.closeQuietly(inputStream);
		return putObjectResult;
	}

	//Метод удаления файлов из aws s3
	//Используется при удалении постов, где есть фотографии и при смене пользователем аватарки
	public void deleteFile(final String keyName) {
		final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, keyName);
		s3Client.deleteObject(deleteObjectRequest);
	}

}
